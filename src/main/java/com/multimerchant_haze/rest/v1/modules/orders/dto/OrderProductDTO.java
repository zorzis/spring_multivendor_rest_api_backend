package com.multimerchant_haze.rest.v1.modules.orders.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateDeserializer;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderProduct;

import java.util.Date;

/**
 * Created by zorzis on 7/14/2017.
 */
public class OrderProductDTO
{

    @JsonView(JsonACL.ClientsView.class)
    private String productID;

    @JsonView(JsonACL.ClientsView.class)
    private String orderProductName;

    @JsonView(JsonACL.ClientsView.class)
    private String orderProductDescription;

    @JsonView(JsonACL.ClientsView.class)
    private Float orderProductPrice;

    @JsonView(JsonACL.ClientsView.class)
    private Float orderProductStockQuantityAtMommentOrderPlaced;

    @JsonView(JsonACL.ClientsView.class)
    private Float orderProductQuantity;

    @JsonView(JsonACL.ClientsView.class)
    private String orderProductCategoryID;

    @JsonView(JsonACL.ClientsView.class)
    private String orderProductCategoryName;

    @JsonView(JsonACL.ClientsView.class)
    private Boolean hasAniseed;

    @JsonView(JsonACL.ClientsView.class)
    private Float alcoholVolume;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.ClientsView.class)
    private Date dateDistilled;

    @JsonView(JsonACL.ClientsView.class)
    private String governmentDistillApprovalID;


    public OrderProductDTO()
    {

    }

    public OrderProductDTO(OrderProduct orderProduct)
    {
        this.productID = orderProduct.getProductID();
        this.orderProductName = orderProduct.getOrderProductName();
        this.orderProductDescription = orderProduct.getOrderProductDescription();
        this.orderProductPrice = orderProduct.getOrderProductPrice();
        this.orderProductStockQuantityAtMommentOrderPlaced = orderProduct.getOrderProductStockQuantityAtMommentOrderPlaced();
        this.orderProductQuantity = orderProduct.getOrderProductQuantity();
        this.orderProductCategoryID = orderProduct.getOrderProductCategoryID();
        this.orderProductCategoryName = orderProduct.getOrderProductCategoryName();
        this.hasAniseed = orderProduct.getHasAniseed();
        this.alcoholVolume = orderProduct.getAlcoholVolume();
        this.dateDistilled = orderProduct.getDateDistilled();
        this.governmentDistillApprovalID = orderProduct.getGovernmentDistillApprovalID();
    }

    public String getProductID()
    {
        return productID;
    }

    public void setProductID(String productID)
    {
        this.productID = productID;
    }

    public String getOrderProductName()
    {
        return orderProductName;
    }

    public void setOrderProductName(String orderProductName)
    {
        this.orderProductName = orderProductName;
    }

    public String getOrderProductDescription()
    {
        return orderProductDescription;
    }

    public void setOrderProductDescription(String orderProductDescription)
    {
        this.orderProductDescription = orderProductDescription;
    }

    public Float getOrderProductPrice()
    {
        return orderProductPrice;
    }

    public void setOrderProductPrice(Float orderProductPrice)
    {
        this.orderProductPrice = orderProductPrice;
    }

    public Float getOrderProductStockQuantityAtMommentOrderPlaced()
    {
        return orderProductStockQuantityAtMommentOrderPlaced;
    }

    public void setOrderProductStockQuantityAtMommentOrderPlaced(Float orderProductStockQuantityAtMommentOrderPlaced)
    {
        this.orderProductStockQuantityAtMommentOrderPlaced = orderProductStockQuantityAtMommentOrderPlaced;
    }

    public Float getOrderProductQuantity()
    {
        return orderProductQuantity;
    }

    public void setOrderProductQuantity(Float orderProductQuantity)
    {
        this.orderProductQuantity = orderProductQuantity;
    }

    public String getOrderProductCategoryID()
    {
        return orderProductCategoryID;
    }

    public void setOrderProductCategoryID(String orderProductCategoryID)
    {
        this.orderProductCategoryID = orderProductCategoryID;
    }

    public String getOrderProductCategoryName()
    {
        return orderProductCategoryName;
    }

    public void setOrderProductCategoryName(String orderProductCategoryName)
    {
        this.orderProductCategoryName = orderProductCategoryName;
    }

    public Boolean getHasAniseed()
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
        sb.append("Order Product DTO");
        sb.append("[");
        sb.append("Order ProductID: ");
        sb.append(this.productID);
        sb.append(" | ");
        sb.append("Order ProductName: ");
        sb.append(this.orderProductName);
        sb.append(" | ");
        sb.append("Order Product Description: ");
        sb.append(this.orderProductDescription);
        sb.append(" | ");
        sb.append("Order Product Price: ");
        sb.append(this.orderProductPrice);
        sb.append(" | ");
        sb.append("Order Product Quantity: ");
        sb.append(this.orderProductQuantity);
        sb.append(" | ");
        sb.append("Order Product Stock Quantity At Moment Order Placed: ");
        sb.append(this.orderProductStockQuantityAtMommentOrderPlaced);
        sb.append(" | ");
        sb.append("Order Product CategoryID: ");
        sb.append(this.orderProductCategoryID);
        sb.append(" | ");
        sb.append("Order Product Category Name: ");
        sb.append(this.orderProductCategoryName);
        sb.append("]");
        return sb.toString();
    }

}
