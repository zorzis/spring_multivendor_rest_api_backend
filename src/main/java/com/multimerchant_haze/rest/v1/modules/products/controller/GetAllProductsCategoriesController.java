package com.multimerchant_haze.rest.v1.modules.products.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductCategoryDTO;
import com.multimerchant_haze.rest.v1.modules.products.service.ProductsCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zorzis on 7/3/2017.
 */
@RestController
public class GetAllProductsCategoriesController
{
    @Autowired
    private ProductsCategoriesService productsCategoriesService;

    @RequestMapping(value = "/client/get_products_categories", method = RequestMethod.GET)
    @JsonView(JsonACL.SearchByProductsPublicView.class)
    public ResponseEntity<List<ProductCategoryDTO>> getAllProducts() throws AppException

    {
        List<ProductCategoryDTO> productCategoryDTOList = productsCategoriesService.getAllProductsCategories();
        return new ResponseEntity<List<ProductCategoryDTO>> (productCategoryDTOList , HttpStatus.OK);
    }
}
