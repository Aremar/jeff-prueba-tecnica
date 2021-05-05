package jeff.technical.test.recommendationservice.productsmodule.model.repository;

import jeff.technical.test.recommendationservice.productsmodule.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<ProductEntity, Integer> {

    List<ProductEntity> findByIdIn(List<Integer> ids);

    @Query(value="select ID from T_PRODUCTS_TPROD", nativeQuery = true)
    List<Integer> getAllProductIDs();

}
