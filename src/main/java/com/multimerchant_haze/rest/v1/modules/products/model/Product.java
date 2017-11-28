package com.multimerchant_haze.rest.v1.modules.products.model;

import com.multimerchant_haze.rest.v1.modules.products.dto.ProductDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zorzis on 6/14/2017.
 */
@Entity
@Table(name = "products")
public class Product implements Serializable
{
    @Id
    @Column(name = "productID", nullable = false)
    private String productID;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "product")
    private ProductDetails productDetails;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "categoryID", referencedColumnName = "categoryID")
    private ProductCategory productCategory;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "product")
    private ProductStock productStockSet = new ProductStock();


   /* @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderProduct")
    private Set<OrderHasProducts> orderHasProducts = new HashSet<>(0);
*/

    public Product()
    {

    }

    public Product(ProductDTO productDTO)
    {
        this.productID = productDTO.getProductID();
        this.productName = productDTO.getProductName();
        this.createdAt = productDTO.getCreatedAt();
        this.updatedAt = productDTO.getUpdatedAt();

        this.productDetails = new ProductDetails(productDTO.getProductDetails());

        this.productCategory = new ProductCategory(productDTO.getProductCategory());

    }


    public String getProductID()
    {
        return productID;
    }

    public void setProductID(String productID)
    {
        this.productID = productID;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
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

    public ProductDetails getProductDetails()
    {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails)
    {
        this.productDetails = productDetails;
    }

    public ProductCategory getProductCategory()
    {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory)
    {
        this.productCategory = productCategory;
    }

    public ProductStock getProductStockSet()
    {
        return productStockSet;
    }

    public void setProductStockSet(ProductStock productStockSet)
    {
        this.productStockSet = productStockSet;
    }

    /*public Set<OrderHasProducts> getOrderHasProducts()
    {
        return orderHasProducts;
    }

    public void setOrderHasProducts(Set<OrderHasProducts> orderHasProducts)
    {
        this.orderHasProducts = orderHasProducts;
    }*/

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Product Entity");
        sb.append("[");
        sb.append("ProductID: ");
        sb.append(this.productID);
        sb.append(" | ");
        sb.append("ProductName: ");
        sb.append(this.productName);
        sb.append(" | ");

        if(this.productDetails != null)
        {
            sb.append(this.productDetails.toString());
        }

        sb.append(" | ");

        if(this.productStockSet != null)
        {
            sb.append(this.productStockSet.toString());
        }

        sb.append(" | ");

        if(this.productCategory != null)
        {
            sb.append(this.productCategory.toString());
        }

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
