package com.multimerchant_haze.rest.v1.modules.products.model;

import com.multimerchant_haze.rest.v1.modules.products.dto.ProductCategoryDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorzis on 6/14/2017.
 */
@Entity
@Table(name = "products_categories")
public class ProductCategory implements Serializable
{

    @Id
    @Column(name = "categoryID", nullable = false)
    private String categoryID;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_description")
    private String categoryDescription;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productCategory")
    private Set<Product> products = new HashSet<>(0);


    public ProductCategory()
    {

    }

    public ProductCategory(ProductCategoryDTO productCategoryDTO)
    {
        this.categoryID = productCategoryDTO.getCategoryID();
        this.categoryName = productCategoryDTO.getCategoryName();
        this.categoryDescription = productCategoryDTO.getCategoryDescription();
        this.createdAt = productCategoryDTO.getCreatedAt();
        this.updatedAt = productCategoryDTO.getUpdatedAt();
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

    public Set<Product> getProducts()
    {
        return products;
    }

    public void setProducts(Set<Product> products)
    {
        this.products = products;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Product Category Entity");
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
