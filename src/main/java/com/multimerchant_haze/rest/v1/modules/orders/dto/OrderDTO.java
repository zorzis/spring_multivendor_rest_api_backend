package com.multimerchant_haze.rest.v1.modules.orders.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.orders.model.*;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateDeserializer;
import com.multimerchant_haze.rest.v1.modules.payments.dto.payment_types_dto.PaymentDTO;
import com.multimerchant_haze.rest.v1.modules.payments.dto.payment_types_dto.PaymentToPaymentDTOService;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;

import java.util.*;

/**
 * Created by zorzis on 7/9/2017.
 */
public class OrderDTO
{
    @JsonView(JsonACL.ClientsView.class)
    private String orderID;

    @JsonView(JsonACL.ClientsView.class)
    private Set<OrderProductDTO> orderProducts = new HashSet<>(0);

    @JsonView(JsonACL.OrderListBelongingToProducer.class)
    private ClientDTO client;

    @JsonView(JsonACL.OrderListBelongingToClient.class)
    private ProducerDTO producer;

    @JsonView(JsonACL.ClientsView.class)
    private OrderClientDetailsDTO clientDetails;

    @JsonView(JsonACL.ClientsView.class)
    private OrderProducerDetailsDTO producerDetails;

    @JsonView(JsonACL.ClientsView.class)
    private OrderStatusCodeDTO orderStatusCode;

    @JsonView(JsonACL.ClientsView.class)
    private OrderClientAddressDetailsDTO clientAddressDetails;

    @JsonView(JsonACL.ClientsView.class)
    private OrderProducerAddressDetailsDTO producerAddressDetails;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.ClientsView.class)
    private Date dateOrderPlaced;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.ClientsView.class)
    private Date dateOrderPaid;

    @JsonView(JsonACL.ClientsView.class)
    private Float totalOrderPrice;

    @JsonView(JsonACL.ClientsView.class)
    private Float totalProductsTax;

    @JsonView(JsonACL.ClientsView.class)
    private Float totalProcessorTax;

    @JsonView(JsonACL.ClientsView.class)
    private Float totalShippingTax;

    @JsonView(JsonACL.ClientsView.class)
    private Float subTotalOrderPrice;

    @JsonView(JsonACL.ClientsView.class)
    private String otherOrderDetails;

    @JsonView(JsonACL.ClientsView.class)
    private PaymentDTO orderPayment;

    @JsonView(JsonACL.ClientsView.class)
    private ShoppingCartDTO shoppingCart;

    public OrderDTO()
    {

    }

    public OrderDTO(Order order)
    {
        this.orderID = order.getOrderID();
        this.dateOrderPlaced = order.getDateOrderPlaced();
        this.dateOrderPaid = order.getDateOrderPaid();
        this.otherOrderDetails = order.getOtherOrderDetails();

        this.totalOrderPrice = order.getTotalOrderPrice();
        this.subTotalOrderPrice = order.getSubTotalOrderPrice();
        this.totalProductsTax = order.getTotalProductsTax();
        this.totalProcessorTax = order.getTotalProcessorTax();
        this.totalShippingTax = order.getTotalShippingTax();

        this.orderPayment = PaymentToPaymentDTOService.convertPaymentEntityToPaymentDTO(order.getOrderPayment());
        this.orderProducts = this.mapProductsDTOsFromProductsEntities(order.getOrderProductsFromOrderHasProductsEntities());
        this.orderStatusCode = this.mapOrderStatusCodeEntityToOrderStatusCodeDTO(order.getOrderStatusCode());
        this.clientDetails = this.mapOrderClientDetailsEntityToOrderClientDetailsDTO(order.getOrderClientDetails());
        this.producerDetails = this.mapOrderProducerDetailsEntityToOrderProducerDetailsDTO(order.getOrderProducerDetails());
        this.clientAddressDetails = this.mapOrderClientAddressDetailsEntityToOrderClientAddressDetailsDTO(order.getOrderClientAddressDetails());
        this.producerAddressDetails = this.mapOrderProducerAddressDetailsEntityToOrderProducerAddressDetailsDTO(order.getOrderProducerAddressDetails());

        //this.shoppingCart = this.mapShoppingCartEntityToShoppingCartDTO(order.getShoppingCart());

    }





    public String getOrderID()
    {
        return orderID;
    }

    public void setOrderID(String orderID)
    {
        this.orderID = orderID;
    }

    public Set<OrderProductDTO> getOrderProducts()
    {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProductDTO> orderProducts)
    {
        this.orderProducts = orderProducts;
    }

    public ClientDTO getClient()
    {
        return client;
    }

    public void setClient(ClientDTO client)
    {
        this.client = client;
    }

    public ProducerDTO getProducer()
    {
        return producer;
    }

    public void setProducer(ProducerDTO producer)
    {
        this.producer = producer;
    }

    public OrderStatusCodeDTO getOrderStatusCode()
    {
        return orderStatusCode;
    }

    public void setOrderStatusCode(OrderStatusCodeDTO orderStatusCode)
    {
        this.orderStatusCode = orderStatusCode;
    }

    public Date getDateOrderPlaced()
    {
        return dateOrderPlaced;
    }

    public void setDateOrderPlaced(Date dateOrderPlaced)
    {
        this.dateOrderPlaced = dateOrderPlaced;
    }

    public Date getDateOrderPaid()
    {
        return dateOrderPaid;
    }

    public void setDateOrderPaid(Date dateOrderPaid)
    {
        this.dateOrderPaid = dateOrderPaid;
    }

    public Float getTotalOrderPrice()
    {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(Float totalOrderPrice)
    {
        this.totalOrderPrice = totalOrderPrice;
    }

    public String getOtherOrderDetails()
    {
        return otherOrderDetails;
    }

    public void setOtherOrderDetails(String otherOrderDetails)
    {
        this.otherOrderDetails = otherOrderDetails;
    }

    public PaymentDTO getOrderPayment()
    {
        return orderPayment;
    }

    public void setOrderPayment(PaymentDTO orderPayment)
    {
        this.orderPayment = orderPayment;
    }

    public OrderClientDetailsDTO getClientDetails()
    {
        return clientDetails;
    }

    public void setClientDetails(OrderClientDetailsDTO clientDetails)
    {
        this.clientDetails = clientDetails;
    }

    public OrderProducerDetailsDTO getProducerDetails()
    {
        return producerDetails;
    }

    public void setProducerDetails(OrderProducerDetailsDTO producerDetails)
    {
        this.producerDetails = producerDetails;
    }


    public OrderClientAddressDetailsDTO getClientAddressDetails()
    {
        return clientAddressDetails;
    }

    public void setClientAddressDetails(OrderClientAddressDetailsDTO clientAddressDetails)
    {
        this.clientAddressDetails = clientAddressDetails;
    }

    public OrderProducerAddressDetailsDTO getProducerAddressDetails()
    {
        return producerAddressDetails;
    }

    public void setProducerAddressDetails(OrderProducerAddressDetailsDTO producerAddressDetails)
    {
        this.producerAddressDetails = producerAddressDetails;
    }

    public Float getTotalProductsTax()
    {
        return totalProductsTax;
    }

    public void setTotalProductsTax(Float totalProductsTax)
    {
        this.totalProductsTax = totalProductsTax;
    }

    public Float getTotalProcessorTax()
    {
        return totalProcessorTax;
    }

    public void setTotalProcessorTax(Float totalProcessorTax)
    {
        this.totalProcessorTax = totalProcessorTax;
    }

    public Float getTotalShippingTax()
    {
        return totalShippingTax;
    }

    public void setTotalShippingTax(Float totalShippingTax)
    {
        this.totalShippingTax = totalShippingTax;
    }

    public Float getSubTotalOrderPrice()
    {
        return subTotalOrderPrice;
    }

    public void setSubTotalOrderPrice(Float subTotalOrderPrice)
    {
        this.subTotalOrderPrice = subTotalOrderPrice;
    }

    public ShoppingCartDTO getShoppingCart()
    {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCartDTO shoppingCart)
    {
        this.shoppingCart = shoppingCart;
    }



    public Set<OrderProductDTO> mapProductsDTOsFromProductsEntities(Set<OrderProduct> orderProductsEntities)
    {
        Set<OrderProductDTO> orderProductDTOSet = new HashSet<>(0);

        if(orderProductsEntities == null )
        {
            return orderProductDTOSet;
        }
        else
        {
            for(OrderProduct orderProductEntity :orderProductsEntities)
            {
                orderProductDTOSet.add(new OrderProductDTO(orderProductEntity));
            }
            return orderProductDTOSet;
        }
    }


    public ClientDTO mapClientEntityToClientDTO(Client client)
    {
        ClientDTO clientDTO;
        if(client == null)
        {
            clientDTO = new ClientDTO();
        }
        else
        {
            clientDTO = new ClientDTO(client);
        }

        return clientDTO;
    }

    public ProducerDTO mapProducerEntityToProducerDTO(Producer producer)
    {
        ProducerDTO producerDTO;
        if(producer == null)
        {
            producerDTO = new ProducerDTO();
        }
        else
        {
            producerDTO = new ProducerDTO(producer);
        }
        return producerDTO;
    }

    public OrderStatusCodeDTO mapOrderStatusCodeEntityToOrderStatusCodeDTO(OrderStatusCodes orderStatusCodeEntity)
    {
        OrderStatusCodeDTO orderStatusCodeDTO;
        if(orderStatusCodeEntity == null)
        {
            orderStatusCodeDTO = new OrderStatusCodeDTO();
        }
        else
        {
            orderStatusCodeDTO = new OrderStatusCodeDTO(orderStatusCodeEntity);
        }
        return orderStatusCodeDTO;
    }



    public List<OrderDTO> mapOrdersDTOsFromOrderEntities(List<Order> orderListEntities)
    {
        List<OrderDTO> orderDTOList = new ArrayList(0);

        for(Order orderEntity :orderListEntities)
        {
            orderDTOList.add(new OrderDTO(orderEntity));
        }
        return orderDTOList;
    }


    public OrderClientDetailsDTO mapOrderClientDetailsEntityToOrderClientDetailsDTO(OrderClientDetails orderClientDetailsEntity)
    {
        OrderClientDetailsDTO orderClientDetailsDTO;
        if(orderClientDetailsEntity == null)
        {
            orderClientDetailsDTO = new OrderClientDetailsDTO();
        }
        else
        {
            orderClientDetailsDTO = new OrderClientDetailsDTO(orderClientDetailsEntity);
        }
        return orderClientDetailsDTO;
    }


    public OrderProducerDetailsDTO mapOrderProducerDetailsEntityToOrderProducerDetailsDTO(OrderProducerDetails orderProducerDetailsEntity)
    {
        OrderProducerDetailsDTO orderProducerDetailsDTO;
        if(orderProducerDetailsEntity == null)
        {
            orderProducerDetailsDTO = new OrderProducerDetailsDTO();
        }
        else
        {
            orderProducerDetailsDTO = new OrderProducerDetailsDTO(orderProducerDetailsEntity);
        }
        return orderProducerDetailsDTO;
    }


    public OrderClientAddressDetailsDTO mapOrderClientAddressDetailsEntityToOrderClientAddressDetailsDTO(OrderClientAddressDetails orderClientAddressDetailsEntity)
    {
        OrderClientAddressDetailsDTO orderClientAddressDetailsDTO;
        if(orderClientAddressDetailsEntity == null)
        {
            orderClientAddressDetailsDTO = new OrderClientAddressDetailsDTO();
        }
        else
        {
            orderClientAddressDetailsDTO = new OrderClientAddressDetailsDTO(orderClientAddressDetailsEntity);
        }
        return orderClientAddressDetailsDTO;
    }

    public OrderProducerAddressDetailsDTO mapOrderProducerAddressDetailsEntityToOrderProducerAddressDetailsDTO(OrderProducerAddressDetails orderProducerAddressDetailsEntity)
    {
        OrderProducerAddressDetailsDTO orderProducerAddressDetailsDTO;
        if(orderProducerAddressDetailsEntity == null)
        {
            orderProducerAddressDetailsDTO = new OrderProducerAddressDetailsDTO();
        }
        else
        {
            orderProducerAddressDetailsDTO = new OrderProducerAddressDetailsDTO(orderProducerAddressDetailsEntity);
        }
        return orderProducerAddressDetailsDTO;
    }


    public ShoppingCartDTO mapShoppingCartEntityToShoppingCartDTO(ShoppingCart shoppingCart)
    {
        ShoppingCartDTO shoppingCartDTO;
        if(shoppingCart == null)
        {
            shoppingCartDTO = new ShoppingCartDTO();
        }
        else
        {
            shoppingCartDTO = new ShoppingCartDTO(shoppingCart);
        }
        return shoppingCartDTO;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderDTO");
        sb.append(" ");
        sb.append("[");
        sb.append("OrderID");
        sb.append(" ");
        sb.append(this.orderID);
        sb.append(" | ");
        if(this.orderProducts != null)
        {
            sb.append("Order Products: ");
            for(OrderProductDTO orderProductDTO : this.orderProducts)
            {
                sb.append(orderProductDTO.toString());
                sb.append(" | ");
            }

        }
        if(this.client != null)
        {
            sb.append("Client Owns Order: ");
            sb.append(this.client.toString());
            sb.append(" | ");
        }

        if(this.producer != null)
        {
            sb.append("Producer Owns Order: ");
            sb.append(this.producer.toString());
            sb.append(" | ");
        }

        if(this.orderStatusCode != null)
        {
            sb.append("Order Status Code: ");
            sb.append(this.orderStatusCode.toString());
            sb.append(" | ");
        }

        if(this.orderPayment != null)
        {
            sb.append("Order Payment: ");
            sb.append(this.orderPayment.toString());
            sb.append(" | ");
        }

        sb.append("Date order place: ");
        sb.append(this.dateOrderPlaced);
        sb.append(" | ");
        sb.append("Date order paid: ");
        sb.append(this.dateOrderPaid);
        sb.append(" | ");
        sb.append("Total Order Price: ");
        sb.append(this.totalOrderPrice);
        sb.append(" | ");
        sb.append("SubTotal Order Price: ");
        sb.append(this.subTotalOrderPrice);
        sb.append(" | ");
        sb.append("Total Products Tax: ");
        sb.append(this.totalProductsTax);
        sb.append(" | ");
        sb.append("Total Shipping Tax: ");
        sb.append(this.totalShippingTax);
        sb.append(" | ");
        sb.append("Total Processor Tax: ");
        sb.append(this.totalProcessorTax);
        sb.append(" | ");
        sb.append("Other Order Details: ");
        sb.append(this.otherOrderDetails);
        sb.append("]");
        return sb.toString();
    }
}
