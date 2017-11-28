package com.multimerchant_haze.rest.v1.modules.products.dao;

import com.multimerchant_haze.rest.v1.modules.products.model.ProductCategory;

import java.util.List;

/**
 * Created by zorzis on 7/1/2017.
 */
public interface ProductCategoryDAO
{
    public List<ProductCategory> getAllProductsCategories();

    public ProductCategory getCategoryByCategoryID(String categoryID);

    public ProductCategory getCategoryByCategoryName(String categoryName);
}
