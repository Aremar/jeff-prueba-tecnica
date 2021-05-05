package jeff.technical.test.recommendationservice.productsmodule.api;

import jeff.technical.test.recommendationservice.model.dto.Product;

import java.util.List;

public interface ProductsService {

    /**
     * This function is used to retrieve the ids of all available products
     * @return List of productIds
     */
    List<Integer> getProductIdList();

    /**
     * This function is used to retrieve the data of the products to recommend
     * @param ids list of the ids of the products we want to obtain
     * @return List of Products
     */
    List<Product> getProductsByIds(List<Integer> ids);

    /**
     * Returns all products
     * @return All products
     */
    List<Product> getAllProducts();

}
