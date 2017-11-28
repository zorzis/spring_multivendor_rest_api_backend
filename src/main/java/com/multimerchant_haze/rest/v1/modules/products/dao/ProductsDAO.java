package com.multimerchant_haze.rest.v1.modules.products.dao;

import com.multimerchant_haze.rest.v1.modules.products.model.Product;

import java.util.List;

/**
 * Created by zorzis on 6/14/2017.
 */
public interface ProductsDAO
{
    public List<Product> getAllProducts();

    public List<Product> getAllProductsBasedOnCategory(String categoryID);

    public List<Product> getAllProducetsBasedOnCategoryName(String categoryName);

    public List<Product> getAllProductsBasedOnProducer(String producerEmail);

    public Product getSingleProductBasedOnProducerAndProductID(String producerEmail, String productID);

    public String addNewProduct(Product product);

    public String updateProduct(Product product);

    public String deleteProduct(Product product);

}
