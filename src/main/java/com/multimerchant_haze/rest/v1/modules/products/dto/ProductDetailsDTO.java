package com.multimerchant_haze.rest.v1.modules.products.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.products.model.ProductDetails;

import java.util.Date;

/**
 * Created by zorzis on 6/15/2017.
 */
public class ProductDetailsDTO
{
    @JsonView(JsonACL.AdminsView.class)
    private Long productDetailsID;

    @JsonView(JsonACL.PublicView.class)
    private String productDescription;

    @JsonView(JsonACL.PublicView.class)
    private Float price;

    @JsonView(JsonACL.PublicView.class)
    private Boolean hasAniseed;

    @JsonView(JsonACL.PublicView.class)
    private Float alcoholVolume;

    @JsonView(JsonACL.PublicView.class)
    private Date dateDistilled;

    @JsonView(JsonACL.PublicView.class)
    private String governmentDistillApprovalID;



    public ProductDetailsDTO()
    {

    }

    public ProductDetailsDTO(ProductDetails productDetails)
    {
        this.productDetailsID = productDetails.getProductDetailsID();
        this.productDescription = productDetails.getProductDescription();
        this.price = productDetails.getPrice();
        this.hasAniseed = productDetails.isHasAniseed();
        this.dateDistilled = productDetails.getDateDistilled();
        this.governmentDistillApprovalID = productDetails.getGovernmentDistillApprovalID();
        this.alcoholVolume = productDetails.getAlcoholVolume();
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

}
