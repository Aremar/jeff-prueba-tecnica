package jeff.technical.test.recommendationservice.productscoreservice.service;

import jeff.technical.test.recommendationservice.productscoreservice.model.entity.ProductScoreId;
import jeff.technical.test.recommendationservice.productscoreservice.model.entity.ProductScoresEntity;
import jeff.technical.test.recommendationservice.productscoreservice.model.repository.ProductScoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * This class is in handle of retrieving and saving Scores from the ProductScores API
 * This functionality is made for a DB "cache" to reduce the calls to ProductScores API keeping in mind that the scores
 * will have a gap of time on which they will be outdated
 */
@Component
@EnableScheduling
@Profile({"local","live"})
public class ProductScoresRefreshScheduler {

    @Autowired
    ProductScoresRepository repository;

    @Autowired
    ProductScoreRestClient productScoreRestClient;

    @Scheduled(fixedDelayString = "${product.scores.service.refresh.interval}")
    public void refreshProductScoresData() {

        performRefresh();

    }

    private void performRefresh() {

        productScoreRestClient.getAllProductScores().forEach(scoresApiResponse -> {
            if (null != scoresApiResponse) {
                Optional<ProductScoresEntity> pse = repository.findById(new ProductScoreId(scoresApiResponse.productId(), scoresApiResponse.persona()));
                if (pse.isPresent()) {
                    pse.get().setScore(scoresApiResponse.score());
                    repository.save(pse.get());
                } else {
                    repository.save(new ProductScoresEntity(scoresApiResponse.productId(), scoresApiResponse.persona(), scoresApiResponse.score()));
                }
            }
        });
    }
}
