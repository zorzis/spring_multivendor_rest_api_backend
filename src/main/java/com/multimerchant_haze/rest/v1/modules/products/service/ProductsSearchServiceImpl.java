package com.multimerchant_haze.rest.v1.modules.products.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductDTO;
import com.multimerchant_haze.rest.v1.modules.products.dao.ProductsDAO;
import com.multimerchant_haze.rest.v1.modules.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zorzis on 6/15/2017.
 */
@Service
public class ProductsSearchServiceImpl implements ProductsSearchService
{
    @Autowired
    ProductsDAO productsDAO;

    public List<ProductDTO> getAllProducts() throws AppException
    {
        List<Product> products = productsDAO.getAllProducts();

        List<ProductDTO> productDTOS = new ArrayList();

        for(int i = 0; i<products.size(); i++)
        {
            ProductDTO productDTO = new ProductDTO(products.get(i));
            productDTOS.add(productDTO);
        }

        return productDTOS;
    }
}
