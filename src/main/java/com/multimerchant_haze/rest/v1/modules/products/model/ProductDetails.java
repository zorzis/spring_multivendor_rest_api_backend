package com.multimerchant_haze.rest.v1.modules.products.model;

import com.multimerchant_haze.rest.v1.modules.products.dto.ProductDetailsDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zorzis on 6/14/2017.
 */
@Entity
@Table(name = "products_details")
public class ProductDetails implements Serializable
{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "product_details_ID", nullable = false)
    private Long productDetailsID;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "productID", referencedColumnName = "productID")
    private Product product = new Product();

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "sell_price")
    private Float price;

    @Column(name ="has_aniseed")
    private Boolean hasAniseed;


    @Column(name = "alcohol_volume")
    private Float alcoholVolume;

    @Column(name = "date_distilled")
    private Date dateDistilled;

    @Column(name = "government_distill_approval_ID")
    private String governmentDistillApprovalID;



    public ProductDetails()
    {

    }


    public ProductDetails(ProductDetailsDTO productDetailsDTO)
    {
        this.productDetailsID = productDetailsDTO.getProductDetailsID();
        this.productDescription = productDetailsDTO.getProductDescription();
        this.price = productDetailsDTO.getPrice();
        this.hasAniseed = productDetailsDTO.isHasAniseed();
        this.alcoholVolume = productDetailsDTO.getAlcoholVolume();
        this.dateDistilled = productDetailsDTO.getDateDistilled();
        this.governmentDistillApprovalID = productDetailsDTO.getGovernmentDistillApprovalID();
    }


    public Long getProductDetailsID()
    {
        return productDetailsID;
    }

    public void setProductDetailsID(Long productDetailsID)
    {
        this.productDetailsID = productDetailsID;
    }

    public String getProductDescription()
    {
        return productDescription;
    }

    public void setProductDescription(String productDescription)
    {
        this.productDescription = productDescription;
    }

    public Float getPrice()
    {
        return price;
    }

    public void setPrice(Float price)
    {
        this.price = price;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public Boolean isHasAniseed()
    {
        return hasAniseed;
    }

    public void setHasAniseed(Boolean hasAniseed)
    {
        this.hasAniseed = hasAniseed;
    }

    public Float getAlcoholVolume()
    {
        return alcoholVolume;
    }

    public void setAlcoholVolume(Float alcoholVolume)
    {
        this.alcoholVolume = alcoholVolume;
    }

    public Date getDateDistilled()
    {
        return dateDistilled;
    }

    public void setDateDistilled(Date dateDistilled)
    {
        this.dateDistilled = dateDistilled;
    }

    public String getGovernmentDistillApprovalID()
    {
        return governmentDistillApprovalID;
    }

    public void setGovernmentDistillApprovalID(String governmentDistillApprovalID)
    {
        this.governmentDistillApprovalID = governmentDistillApprovalID;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ProductDetails Entity Details");
        sb.append(" ");
        sb.append("ProductDetailsID: ");
        sb.append(this.productDetailsID);
        sb.append(" | ");
        sb.append("Product Price: ");
        sb.append(this.price);
        sb.append(" | ");
        sb.append("Product Description ");
        sb.append(this.productDescription);
        sb.append(" | ");
        sb.append("Has Aniseed: ");
        sb.append(this.hasAniseed);
        sb.append(" | ");
        sb.append("Date Distilled: ");
        sb.append(this.dateDistilled);
        sb.append(" | ");
        sb.append("Government Distill Approval ID: ");
        sb.append(this.governmentDistillApprovalID);
        sb.append(" | ");
        sb.append("Alcohol Volume: ");
        sb.append(this.alcoholVolume);
        return sb.toString();
    }
}
