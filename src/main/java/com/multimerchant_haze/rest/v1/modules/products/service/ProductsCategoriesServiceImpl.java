package com.multimerchant_haze.rest.v1.modules.products.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.products.dao.ProductCategoryDAO;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductCategoryDTO;
import com.multimerchant_haze.rest.v1.modules.products.model.ProductCategory;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zorzis on 7/3/2017.
 */

@Service
public class ProductsCategoriesServiceImpl implements ProductsCategoriesService
{
    @Autowired
    ProducerDAO producerDAO;

    @Autowired
    ProductCategoryDAO productCategoryDAO;

    public List<ProductCategoryDTO> getAllProductsCategories() throws AppException
    {
        List<ProductCategory> productsCategoriesList = this.productCategoryDAO.getAllProductsCategories();

        ProductCategoryDTO productCategoryDTOSample = new ProductCategoryDTO();

        List<ProductCategoryDTO> productCategoryDTOList = productCategoryDTOSample.mapProductCategoriesDTOsFromProductCategoriesEntitiesList(productsCategoriesList);

        return productCategoryDTOList;
    }

    @Override
    public ProductCategory getProductCategoryByProductCategoryID(ProductCategoryDTO productCategoryDTO) throws AppException
    {
        ProductCategory productCategoryToBeSearched = this.productCategoryDAO.getCategoryByCategoryID(productCategoryDTO.getCategoryID());
        if(productCategoryDTO == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Product Category Not Found!");
            sb.append(" ");
            sb.append("Product Category with ID");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(productCategoryDTO.getCategoryID());
            sb.append("]");
            sb.append(" ");
            sb.append("not found to our system.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Product Category not found!");
            throw appException;

        }

        return productCategoryToBeSearched;
    }
}
