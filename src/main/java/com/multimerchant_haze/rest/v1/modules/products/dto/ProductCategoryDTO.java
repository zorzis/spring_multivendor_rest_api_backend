package com.multimerchant_haze.rest.v1.modules.products.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.products.model.ProductCategory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zorzis on 6/15/2017.
 */
public class ProductCategoryDTO
{
    @JsonView(JsonACL.PublicView.class)
    private String categoryID;

    @JsonView(JsonACL.PublicView.class)
    private String categoryName;

    @JsonView(JsonACL.PublicView.class)
    private String categoryDescription;

    @JsonView(JsonACL.AdminsView.class)
    private Date createdAt;

    @JsonView(JsonACL.AdminsView.class)
    private Date updatedAt;


    public ProductCategoryDTO()
    {

    }


    public ProductCategoryDTO(ProductCategory productCategory)
    {
        this.categoryID = productCategory.getCategoryID();
        this.categoryName = productCategory.getCategoryName();
        this.categoryDescription = productCategory.getCategoryDescription();
        this.createdAt = productCategory.getCreatedAt();
        this.updatedAt = productCategory.getUpdatedAt();
    }



    public List<ProductCategoryDTO> mapProductCategoriesDTOsFromProductCategoriesEntitiesList(List<ProductCategory> productCategoryEntitiesList)
    {
        List<ProductCategoryDTO> productCategoryDTOList = new ArrayList(0);

        for(ProductCategory productCategoryEntity : productCategoryEntitiesList)
        {
            productCategoryDTOList.add(new ProductCategoryDTO(productCategoryEntity));
        }

        return productCategoryDTOList;
    }


    public String getCategoryID()
    {
        return categoryID;
    }

    public void setCategoryID(String categoryID)
    {
        this.categoryID = categoryID;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription()
    {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription)
    {
        this.categoryDescription = categoryDescription;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }



    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Product Category DTO");
        sb.append("[");
        sb.append("ProductCategoryID: ");
        sb.append(this.getCategoryID());
        sb.append(" | ");
        sb.append("Product Category Name: ");
        sb.append(this.categoryName);
        sb.append(" | ");
        sb.append("Product Category Description: ");
        sb.append(this.categoryDescription);
        sb.append(" | ");
        sb.append("Created at: ");
        sb.append(this.createdAt);
        sb.append(" | ");
        sb.append("Updated at: ");
        sb.append(this.updatedAt);
        sb.append("]");
        return sb.toString();
    }


}

