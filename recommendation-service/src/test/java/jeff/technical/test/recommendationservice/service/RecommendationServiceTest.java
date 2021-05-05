package jeff.technical.test.recommendationservice.service;

import jeff.technical.test.recommendationservice.customerservice.api.CustomerService;
import jeff.technical.test.recommendationservice.model.dto.Customer;
import jeff.technical.test.recommendationservice.model.dto.Product;
import jeff.technical.test.recommendationservice.model.dto.ProductScore;
import jeff.technical.test.recommendationservice.model.dto.Recommendation;
import jeff.technical.test.recommendationservice.productscoreservice.api.ProductScoreService;
import jeff.technical.test.recommendationservice.productsmodule.api.ProductsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {RecommendationService.class})
public class RecommendationServiceTest {

    @MockBean
    ProductsService productsService;

    @MockBean
    CustomerService customerService;

    @MockBean
    ProductScoreService productScoreService;

    @InjectMocks
    RecommendationService service;

    @Test
    public void shouldReturnRecommendationWithTailoredProducts() {
        Mockito.when(customerService.getCustomerInfoForRecommendation(1)).thenReturn(getMockCustomer());
        Mockito.when(productsService.getAllProducts()).thenReturn(getMockProducts());
        Mockito.when(productScoreService.getProductAndScoresForPersona("JUNIOR")).thenReturn(getMockProductScores());

        Recommendation recommendation = service.getRecommendedProductsForUser(1, Optional.of(7), Optional.empty());

        assertEquals(3, recommendation.recommendedProducts().size());
    }

    @Test
    public void shouldReturnRecommendationWithTailoredProductsAndMarginCase() {
        Mockito.when(customerService.getCustomerInfoForRecommendation(1)).thenReturn(getMockCustomer());
        Mockito.when(productsService.getAllProducts()).thenReturn(getMockProducts());
        Mockito.when(productScoreService.getProductAndScoresForPersona("JUNIOR")).thenReturn(getMockProductScores());

        Recommendation recommendation = service.getRecommendedProductsForUser(1, Optional.of(7),Optional.of(2));

        assertEquals(4, recommendation.recommendedProducts().size());
    }

    private Customer getMockCustomer() {
        return new Customer(1, "JUNIOR", List.of(1));
    }

    private List<Product> getMockProducts() {
        return List.of(
                new Product(1, "vertical1", "product1", "type1", BigDecimal.valueOf(20.0), BigDecimal.valueOf(4.0)),
                new Product(2, "vertical1", "product2", "type1", BigDecimal.valueOf(5.0), BigDecimal.valueOf(0.5)),
                new Product(3, "vertical2", "product3", "type2", BigDecimal.valueOf(9.95), BigDecimal.valueOf(1.19)),
                new Product(4, "vertical2", "product4", "type2", BigDecimal.valueOf(50.0), BigDecimal.valueOf(16.0)),
                new Product(5, "vertical3", "product5", "type1", BigDecimal.valueOf(8.0), BigDecimal.valueOf(1.0))
        );
    }

    private List<ProductScore> getMockProductScores() {
        return List.of(
                new ProductScore(1, 6),
                new ProductScore(2, 9),
                new ProductScore(3, 8),
                new ProductScore(4, 6),
                new ProductScore(5, 7)
        );

    }
}
