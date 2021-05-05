package jeff.technical.test.recommendationservice.productscoreservice.impl;

import jeff.technical.test.recommendationservice.model.dto.ProductScore;
import jeff.technical.test.recommendationservice.productscoreservice.api.ProductScoreService;
import jeff.technical.test.recommendationservice.productscoreservice.model.repository.ProductScoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductScoreServiceImpl implements ProductScoreService {

    @Autowired
    ProductScoresRepository repository;


    @Override
    public List<ProductScore> getProductAndScoresForPersona(String persona) {
        List<ProductScore> segmentProductScores = new ArrayList<>();
        repository.findByPersona(persona).forEach(productScoresEntity -> {
            segmentProductScores.add(new ProductScore(productScoresEntity.getProductId(), productScoresEntity.getScore()));
        });

        return segmentProductScores;
    }
}
