package jeff.technical.test.recommendationservice.productscoreservice.api;

import jeff.technical.test.recommendationservice.model.dto.ProductScore;

import java.util.List;

public interface ProductScoreService {

    /**
     * This function retrieves the list of products with scores for persona
     * @param persona the segment type
     * @return List of product scores
     */
    List<ProductScore> getProductAndScoresForPersona(String persona);

}
