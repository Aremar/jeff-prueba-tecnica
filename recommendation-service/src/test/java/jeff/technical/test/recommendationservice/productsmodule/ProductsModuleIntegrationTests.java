package jeff.technical.test.recommendationservice.productsmodule;

import jeff.technical.test.recommendationservice.model.dto.Product;
import jeff.technical.test.recommendationservice.productsmodule.api.ProductsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProductsModuleIntegrationTests {

    @Autowired
    ProductsService productsService;

    @Test
    void shouldRetrieveListOfProductIds() {

        assertTrue(productsService.getProductIdList().containsAll(List.of(1,2)));
    }

    @Test
    void shouldRetrieveProductsList() {
        List<Product> productList = productsService.getProductsByIds(List.of(1,2));
        assertThat(productList.get(0).vertical()).isIn("Laundry", "Fit");
        assertThat(productList.get(1).vertical()).isIn("Laundry", "Fit");
    }

    @Test
    void shouldRetrieveAllProducts() {
        assertEquals(2, productsService.getAllProducts().size());
    }

    @Test
    void shouldRetrieveEmptyProductList() {
        List<Product> productList = productsService.getProductsByIds(List.of(3));
        assertTrue(productList.isEmpty());
    }
}
