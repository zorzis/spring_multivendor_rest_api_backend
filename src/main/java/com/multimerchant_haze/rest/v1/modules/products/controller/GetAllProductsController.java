package com.multimerchant_haze.rest.v1.modules.products.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductDTO;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.products.service.ProductsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zorzis on 6/15/2017.
 */
@RestController
public class GetAllProductsController
{
    @Autowired
    private ProductsSearchService productsSearchService;

    @RequestMapping(value = "/client/get_products", method = RequestMethod.GET)
    @JsonView(JsonACL.SearchByProductsPublicView.class)
    public ResponseEntity getAllProducts() throws AppException

    {
        List<ProductDTO> productDTOS = productsSearchService.getAllProducts();
        return new ResponseEntity<List<ProductDTO>> (productDTOS , HttpStatus.OK);
    }
}
