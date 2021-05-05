package jeff.technical.test.recommendationservice.service;

import jeff.technical.test.recommendationservice.common.exceptions.NotFoundException;
import jeff.technical.test.recommendationservice.customerservice.api.CustomerService;
import jeff.technical.test.recommendationservice.model.dto.*;
import jeff.technical.test.recommendationservice.productscoreservice.api.ProductScoreService;
import jeff.technical.test.recommendationservice.productsmodule.api.ProductsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class handles the logic for the recommendation system
 */

@Slf4j
@Service
public class RecommendationService {

    @Value("${recommendation.score.threshold}")
    Integer threshold;

    @Autowired
    ProductsService productsService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductScoreService productScoreService;

    public Recommendation getRecommendedProductsForUser(Integer customerId, Optional<Integer> minScore, Optional<Integer> extraHighMarginProducts) throws NotFoundException {

        minScore.ifPresent(integer -> threshold = integer);

        log.info("Requested product recommendations for customer: {}", customerId);

        Customer customer = customerService.getCustomerInfoForRecommendation(customerId);

        Map<Integer, Product> products = productsService.getAllProducts().stream().collect(Collectors.toMap(Product::id, product -> product));
        Map<Integer, ProductScore> scores = productScoreService.getProductAndScoresForPersona(customer.persona()).stream().collect(Collectors.toMap(ProductScore::productId, productScore -> productScore));

        // Removing the products that customer already have
        customer.subscriptions().forEach(productId -> {
            products.remove(productId);
            scores.remove(productId);
        });

        Map<Integer,Product> recommendedProducts = new HashMap<>();

        // If the score is equal or higher to the threshold, is recommended
        scores.forEach((id, productScore) -> {
            if(productScore.score() >= threshold)
                recommendedProducts.put(id, products.get(id));
        });

        // If the extraHighMarginProducts parameter exist, we are going to add that number of products with highest margins
        if(extraHighMarginProducts.isPresent()){
            log.info("Requested to prioritize {} high margin products.", extraHighMarginProducts.get());
            Map<Integer, BigDecimal> marginAndScore = new HashMap<>();
            // Multiplies the margin with the score to obtain a margin-score relation
            scores.forEach((id, productScore) -> {
                if(null != products.get(id))
                    marginAndScore.put(id, products.get(id).margin().multiply(BigDecimal.valueOf(productScore.score())));
            });

            // Orders the map by the margin-score, then adds the first numberOfPrioritizedProducts with these ids
            marginAndScore.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                    (oldValue, newValue) -> oldValue, LinkedHashMap::new))
                    .keySet().stream().limit(extraHighMarginProducts.get())
                    .forEach(id -> {
                        log.info(products.get(id).toString());
                        if(null == recommendedProducts.get(id))
                            recommendedProducts.put(id, products.get(id));
            });
        }

        List<TailoredProduct> tailoredProducts = new ArrayList<>();
        recommendedProducts.forEach((id, product) -> {
            tailoredProducts.add(new TailoredProduct(product.id(),product.vertical(),product.product(),product.productType(),product.price()));
        });

        return new Recommendation(customerId, tailoredProducts);
    }

}
