package com.multimerchant_haze.rest.v1.modules.products.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductCategoryDTO;
import com.multimerchant_haze.rest.v1.modules.products.model.ProductCategory;

import java.util.List;

/**
 * Created by zorzis on 7/3/2017.
 */
public interface ProductsCategoriesService
{
    public List<ProductCategoryDTO> getAllProductsCategories() throws AppException;

    public ProductCategory getProductCategoryByProductCategoryID(ProductCategoryDTO productCategoryDTO) throws AppException;

}