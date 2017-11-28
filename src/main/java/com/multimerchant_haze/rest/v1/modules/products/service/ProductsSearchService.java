package com.multimerchant_haze.rest.v1.modules.products.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductDTO;

import java.util.List;

/**
 * Created by zorzis on 6/15/2017.
 */
public interface ProductsSearchService
{
    public List<ProductDTO> getAllProducts() throws AppException;
}
