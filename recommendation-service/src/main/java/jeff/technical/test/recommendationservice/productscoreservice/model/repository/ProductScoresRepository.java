package jeff.technical.test.recommendationservice.productscoreservice.model.repository;

import jeff.technical.test.recommendationservice.productscoreservice.model.entity.ProductScoreId;
import jeff.technical.test.recommendationservice.productscoreservice.model.entity.ProductScoresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductScoresRepository extends JpaRepository<ProductScoresEntity, ProductScoreId> {

    List<ProductScoresEntity> findByPersona(String persona);
}
