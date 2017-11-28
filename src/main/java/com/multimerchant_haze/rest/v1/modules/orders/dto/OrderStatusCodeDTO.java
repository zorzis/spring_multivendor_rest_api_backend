package com.multimerchant_haze.rest.v1.modules.orders.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderStatusCodes;

/**
 * Created by zorzis on 7/9/2017.
 */
public class OrderStatusCodeDTO
{
    @JsonView(JsonACL.ClientsView.class)
    private String orderStatusCode;

    @JsonView(JsonACL.ClientsView.class)
    private String orderStatusCodeDescription;

    public OrderStatusCodeDTO()
    {

    }

    public OrderStatusCodeDTO(OrderStatusCodes orderStatusCodesEntity)
    {
        this.orderStatusCode = orderStatusCodesEntity.getOrderStatusCode();
        this.orderStatusCodeDescription = orderStatusCodesEntity.getOrderStatusCodeDescription();
    }


    public String getOrderStatusCode()
    {
        return orderStatusCode;
    }

    public void setOrderStatusCode(String orderStatusCode)
    {
        this.orderStatusCode = orderStatusCode;
    }

    public String getOrderStatusCodeDescription()
    {
        return orderStatusCodeDescription;
    }

    public void setOrderStatusCodeDescription(String orderStatusCodeDescription)
    {
        this.orderStatusCodeDescription = orderStatusCodeDescription;
    }



    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderStatusCodeDTO");
        sb.append(" ");
        sb.append("[");
        sb.append("Order Status Code");
        sb.append(" ");
        sb.append(this.orderStatusCode);
        sb.append(" | ");
        sb.append("Order Status Code Description");
        sb.append(this.orderStatusCodeDescription);
        sb.append("]");
        return sb.toString();
    }
}
