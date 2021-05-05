package jeff.technical.test.recommendationservice.productsmodule.impl;

import jeff.technical.test.recommendationservice.model.dto.Product;
import jeff.technical.test.recommendationservice.productsmodule.api.ProductsService;
import jeff.technical.test.recommendationservice.productsmodule.model.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    ProductsRepository repository;


    @Override
    public List<Integer> getProductIdList() {
        return repository.getAllProductIDs();
    }

    @Override
    public List<Product> getProductsByIds(List<Integer> ids) {

        List<Product> products = new ArrayList<>();

        repository.findByIdIn(ids).forEach( pe -> {
            products.add(new Product(pe.getId(), pe.getVertical(), pe.getProduct(), pe.getProductType(), pe.getPrice(), pe.getMargin()));
        });

        return products;
    }

    @Override
    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        repository.findAll().forEach( pe -> {
            products.add(new Product(pe.getId(), pe.getVertical(), pe.getProduct(), pe.getProductType(), pe.getPrice(), pe.getMargin()));
        });

        return products;
    }
}
