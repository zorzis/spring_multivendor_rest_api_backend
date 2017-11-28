package com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.helper_services;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.dto.ShoppingCartProductDTO;
import com.multimerchant_haze.rest.v1.modules.orders.model.*;
import com.multimerchant_haze.rest.v1.modules.payments.dao.PaymentMethodDAO;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerHasPaymentMethodDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.orders.dao.OrderStatusCodesDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dao.OrdersDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dto.ShoppingCartDTO;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientAddressDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientAddress;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerDAO;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.service.UserServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by zorzis on 10/5/2017.
 */
@Service
public class OrderEntityCreatorServiceImplementation implements OrderEntityCreatorService
{
    @Autowired
    OrdersDAO ordersDAO;

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    ProducerDAO producerDAO;

    @Autowired
    OrderStatusCodesDAO orderStatusCodesDAO;

    @Autowired
    PaymentMethodDAO paymentMethodDAO;

    private static final String ORDER_STATUS_CODE_TO_BE_ASSIGNED_TO_ORDER_STARTED = "started";
    private static final Float COUNTRY_TAX = (float)0.24;


    @Override
    public Order createOrderEntity(ShoppingCartDTO shoppingCartDTO) throws AppException
    {

        // At first we just check that ShoppingCartDTO object has NO NULL values
        this.checkShoppingCartDTOReceivedFromFrontendHasNoNullValues(shoppingCartDTO);

        // we create the order unique ID but we also check the database if indeed it is unique
        // and no previous equal order id is stored
        String orderID = this.orderIDUniqueGenerator();
        Order orderToBeCheckedThatNotExists = this.ordersDAO.getOrderByOrderIDOnlyFromOrdersTable(orderID);
        while(orderToBeCheckedThatNotExists != null)
        {
            orderID = this.orderIDUniqueGenerator();
            orderToBeCheckedThatNotExists = this.ordersDAO.getOrderByOrderIDOnlyFromOrdersTable(orderID);
        }


        // ---------------------------------START DOING THE LOGICAL CHECKS-------------------------------- //

        //** Check Client is valid
        Client clientPlacedTheOrder = this.clientDAO.getClientByEmailFetchingAddressesFetchingProfile(shoppingCartDTO.getCustomerEmail());
        ClientDTO clientDTOplacedTheOrder = UserServiceHelper.createClientDTOIfClientEntityExists(clientPlacedTheOrder, "Email", shoppingCartDTO.getCustomerEmail());

        //** Check Producer is valid
        Producer producerToWhomBelongsTheOrder = this.producerDAO.getProducerByProducerIDFetchingAddressFetchingProducts(shoppingCartDTO.getProducerID());
        ProducerDTO producerDTOToWhomBelongsTheOrder = UserServiceHelper.createProducerDTOIfProducerEntityExists(producerToWhomBelongsTheOrder, "ProducerID", shoppingCartDTO.getProducerID());
        producerDTOToWhomBelongsTheOrder.mapProductsDTOsFromProductsEntities(producerToWhomBelongsTheOrder.getProducerProductsEntities());

        //** Check if ClientAddress is valid(client can have multiple addresses)
        ClientAddress clientShippingAddressEntity = this.checkIfAddressBelongsToClient(clientPlacedTheOrder, shoppingCartDTO.getClientAddressID());

        //** Check if PaymentMethod Belongs to Producer
        this.checkIfPaymentMethodBelongsToProducer(shoppingCartDTO.getPaymentMethodID(), producerToWhomBelongsTheOrder);

        //** Check if products set in the order request is not empty and every product belongs to producer to whom belongs the order
        Set<OrderProduct> orderProductSet = this.checkIfProductsBelongToProducerAndReturnOrderProductsSet(producerDTOToWhomBelongsTheOrder, shoppingCartDTO.getShoppingCartProducts());

        //** Order Status Code Ref
        OrderStatusCodes orderStatusCodeRef = this.orderStatusCodesDAO.getSingleOrderStatusCodeRefByStatusID(ORDER_STATUS_CODE_TO_BE_ASSIGNED_TO_ORDER_STARTED);
        this.checkIfOrderStatusCodeRefExists(orderStatusCodeRef);

        // -----------------------------------END OF THE LOGICAL CHECKS---------------------------------- //



        //** ORDER Creation
        Order orderToBePlaced = new Order();
        orderToBePlaced.setOrderID(orderID);
        orderToBePlaced.setDateOrderPlaced(new Date());
        orderToBePlaced.setSubTotalOrderPrice(this.calculateSubTotalOrderPrice(orderProductSet));
        orderToBePlaced.setTotalProductsTax(this.calculateTotalProductsTaxes(orderProductSet));
        orderToBePlaced.setOrderStatusCode(orderStatusCodeRef);



        //** ORDER-OrderProducts
        Set<OrderHasProducts> orderHasProductsHashSet = new HashSet<>(0);
        for(OrderProduct orderProduct : orderProductSet)
        {
            OrderHasProducts orderHasProducts = new OrderHasProducts();
            orderHasProducts.setOrderProduct(orderProduct);
            orderHasProducts.setOrder(orderToBePlaced);

            orderHasProductsHashSet.add(orderHasProducts);
        }
        orderToBePlaced.setOrderHasProducts(orderHasProductsHashSet);


        //** ORDER-Client-Producer
        /**
         * // set the order Client and Producer based on Client - Producer Entities
         * // so we can address each one of them to the coresponding Orders belonging to each one of them
         */
        OrderHasClientHasProducer orderHasClientHasProducer = new OrderHasClientHasProducer();
        orderHasClientHasProducer.setOrder(orderToBePlaced);
        orderHasClientHasProducer.setClient(clientPlacedTheOrder);
        orderHasClientHasProducer.setProducer(producerToWhomBelongsTheOrder);
        orderToBePlaced.setOrderHasClientHasProducer(orderHasClientHasProducer);

        /**
         * // In order to avoid any future conflicts if any personal info or address is changed
         * // on Client , Client Shipping Address, Producer or Producer Address we restore them as new Entitities
         * // to the coresponding tables in the database
         */


        //** ORDER-ClientDetails(at the moment order is placed)
        OrderClientDetails orderClientDetails = new OrderClientDetails(clientDTOplacedTheOrder);
        orderClientDetails.setOrder(orderToBePlaced);
        orderToBePlaced.setOrderClientDetails(orderClientDetails);

        //** ORDER-ProducerDetails(at the moment order is placed)
        OrderProducerDetails orderProducerDetails = new OrderProducerDetails(producerDTOToWhomBelongsTheOrder);
        orderProducerDetails.setOrder(orderToBePlaced);
        orderToBePlaced.setOrderProducerDetails(orderProducerDetails);

        //** ORDER-ClientAddressDetails(chosen one --client addresses are DELETABLE not Updatable)
        /**
         * // set the customer address details based on customer chosen shiping address from customer multiple addresses
         * // at the moment order is placed
         */
        OrderClientAddressDetails orderClientAddressDetails = new OrderClientAddressDetails(new ClientAddressDTO(clientShippingAddressEntity));
        orderClientAddressDetails.setClientID(clientDTOplacedTheOrder.getClientID());
        orderClientAddressDetails.setOrder(orderToBePlaced);
        orderToBePlaced.setOrderClientAddressDetails(orderClientAddressDetails);

        //** ORDER-ProducerAddressDetails(chosen one --client addresses are DELETABLE not Updatable)
        // set the producer address details at the moment order is placed
        OrderProducerAddressDetails orderProducerAddressDetails = new OrderProducerAddressDetails(producerDTOToWhomBelongsTheOrder.getProducerAddress());
        orderProducerAddressDetails.setProducerID(producerDTOToWhomBelongsTheOrder.getProducerID());
        orderProducerAddressDetails.setOrder(orderToBePlaced);
        orderToBePlaced.setOrderProducerAddressDetails(orderProducerAddressDetails);


        return orderToBePlaced;
    }

    // Calculate the Total Order Price
    public Float calculateSubTotalOrderPrice(Set<OrderProduct> orderProductSet)
    {
        Float totalOrderPrice = (float)0;
        for(OrderProduct orderProduct : orderProductSet)
        {
            Float productPrice = orderProduct.getOrderProductPrice();
            Float productQuantityToBeOrdered = orderProduct.getOrderProductQuantity();
            Float productPriceBasedOnQuantityOrdered = (productPrice * productQuantityToBeOrdered);
            totalOrderPrice += productPriceBasedOnQuantityOrdered;
        }
        return totalOrderPrice;
    }

    public Float calculateTotalProductsTaxes(Set<OrderProduct> orderProductSet)
    {
        Float totalOrderPriceWithProductTaxShippingTax = (float)0;
        for(OrderProduct orderProduct : orderProductSet)
        {
            Float productPrice = orderProduct.getOrderProductPrice();
            Float productQuantityToBeOrdered = orderProduct.getOrderProductQuantity();
            Float productPriceBasedOnQuantityOrdered = (productPrice * productQuantityToBeOrdered);

            Float totalProductTax = productPriceBasedOnQuantityOrdered * COUNTRY_TAX;

            totalOrderPriceWithProductTaxShippingTax += totalProductTax;
        }
        return totalOrderPriceWithProductTaxShippingTax;
    }



    /**
     *
     * @param producerDTO
     * @param shoppingCartProductDTOS
     * @return
     *
     *
     * Here we firstly check if the products set is not empty, meaning indeed order has products to be ordered.
     * Secondly we check that no Same product is in the order
     * Thirdly we check if each one of the products in the order belongs to the Producer to whom coresponds the order
     */
    public Set<OrderProduct> checkIfProductsBelongToProducerAndReturnOrderProductsSet(ProducerDTO producerDTO, Set<ShoppingCartProductDTO> shoppingCartProductDTOS)
    {
        Set<OrderProduct> orderProductsEntities = new HashSet<>(0);

        // if we get an empty hashset just throw app exception error
        if(shoppingCartProductDTOS.isEmpty())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("No products found. Order does not contain any Product!");
            sb.append("Order cannot be processed!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Products Array is empty! Order cannot be processed!");
            throw appException;
        }

        // check if product exists more than once in the order
        for(ShoppingCartProductDTO shoppingCartProductDTO : shoppingCartProductDTOS)
        {
            int count = 0;

            for(ShoppingCartProductDTO shoppingCartProductDTOToBeSearched : shoppingCartProductDTOS)
            {
                if(shoppingCartProductDTO.getProductID().equals(shoppingCartProductDTOToBeSearched.getProductID()))
                {
                    count++;
                }
            }

            if(count > 1)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("Shopping Cart Product with productID");
                sb.append(":");
                sb.append(" ");
                sb.append("[");
                sb.append(shoppingCartProductDTO.getProductID());
                sb.append("]");
                sb.append(" ");
                sb.append("is placed more than once in the order!");
                sb.append(" ");
                sb.append("Order cannot be processed!");
                String errorMessage = sb.toString();

                AppException appException = new AppException(errorMessage);
                appException.setHttpStatus(HttpStatus.CONFLICT);
                appException.setAppErrorCode(HttpStatus.CONFLICT.value());
                appException.setDevelopersMessageExtraInfoAsSingleReason("Shopping Cart product found more than once in the Order.Order Cannot be Processed!");
                throw appException;
            }
        }

        // check if each product belongs to the Producer to whom coresponds the order
        // And create the OrderProduct HashSet to be exported
        for(ShoppingCartProductDTO shoppingCartProductDTOClaimsToBeOriginal : shoppingCartProductDTOS)
        {
            OrderProduct orderProductEntity = null;

            for(ProductDTO originalProducerProduct : producerDTO.getProducts())
            {
                if(shoppingCartProductDTOClaimsToBeOriginal.getProductID().equals(originalProducerProduct.getProductID()))
                {
                    orderProductEntity = new OrderProduct(originalProducerProduct);
                    // Set the Quantity of the product here in the Service Layer
                    orderProductEntity.setOrderProductQuantity(shoppingCartProductDTOClaimsToBeOriginal.getOrderProductQuantity());
                }
            }

            if(orderProductEntity == null)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("Order Product with productID");
                sb.append(":");
                sb.append(" ");
                sb.append("[");
                sb.append(shoppingCartProductDTOClaimsToBeOriginal.getProductID());
                sb.append("]");
                sb.append(" ");
                sb.append("does not belong to Producer with producerID");
                sb.append(":");
                sb.append(" ");
                sb.append("[");
                sb.append(producerDTO.getProducerID());
                sb.append("]");
                sb.append(" ");
                sb.append(" and Producer Email: ");
                sb.append("[");
                sb.append(producerDTO.getEmail());
                sb.append("]");
                sb.append(" ");
                sb.append("Order cannot be processed!");
                String errorMessage = sb.toString();

                AppException appException = new AppException(errorMessage);
                appException.setHttpStatus(HttpStatus.NOT_FOUND);
                appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
                appException.setDevelopersMessageExtraInfoAsSingleReason("Product does not belong to specific Producer.Order Cannot be Processed!");
                throw appException;
            }
            else
            {
                orderProductsEntities.add(orderProductEntity);
            }
        }

        return orderProductsEntities;
    }


    public ClientAddress checkIfAddressBelongsToClient(Client client, Long clientAddressID)
    {
        ClientAddress customerShippingAddress = null;
        for(ClientAddress clientAddress : client.getClientAddressesEntities())
        {
            if(clientAddress.getId().equals(clientAddressID))
            {
                customerShippingAddress = clientAddress;
            }
        }

        if(customerShippingAddress == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Shipping Address Not Found! Address with ID");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(clientAddressID);
            sb.append("]");
            sb.append(" ");
            sb.append("is not assigned to Client with email");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(client.getEmail());
            sb.append("]");
            sb.append(" ");
            sb.append("Order cannot be processed!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Address is not found to specific client addresses.");
            throw appException;
        }
        return customerShippingAddress;
    }



    public void checkIfOrderStatusCodeRefExists(OrderStatusCodes orderStatusCodeRef)
    {
        if(orderStatusCodeRef == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Order Status Code");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(orderStatusCodeRef.getOrderStatusCode());
            sb.append("]");
            sb.append(" ");
            sb.append("cannot be found in the database!");
            sb.append("Order cannot be processed!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Order Status Code assigned to order in the service layer cannot be found in the database.Please contact developers!");
            throw appException;
        }
    }


    @Override
    public PaymentMethod checkIfPaymentMethodBelongsToProducer(String paymentMethodID, Producer producerOwnTheOrder) throws AppException
    {
        PaymentMethod paymentMethod = this.paymentMethodDAO.getPaymentMethodByID(paymentMethodID);

        // check for Prodcuer Payment Methods if payment method claimed to be at Produccer assignments,
        // indeed belongs to him/her
        ProducerDTO producerDTOOwsnTheOrder = new ProducerDTO(producerOwnTheOrder);
        Set<ProducerHasPaymentMethodDTO> producerPaymentMethodDTOS = producerDTOOwsnTheOrder.mapProducerPaymentMethodsDTOsFromProducerPaymentMethodsEntities(producerOwnTheOrder.getProducerHasPaymentMethodSet());

        boolean isFoundOnProducerPaymentMethods = false;

        for(ProducerHasPaymentMethodDTO producerPaymentMethodDTOIter : producerPaymentMethodDTOS)
        {

            if(producerPaymentMethodDTOIter.getPaymentMethod().getPaymentMethodID().equals(paymentMethod.getPaymentMethodID()))
            {
                isFoundOnProducerPaymentMethods = true;
            }
        }

        if(isFoundOnProducerPaymentMethods == false)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Order Payment Method");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(paymentMethodID);
            sb.append("]");
            sb.append(" ");
            sb.append("is not assigned to Producer");
            sb.append(" [");
            sb.append(producerOwnTheOrder.getEmail());
            sb.append("]");
            sb.append("Order cannot be processed!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Order Payment Method assigned to order in the service layer is not assigned to coresponding Producer.Please contact developers!");
            throw appException;
        }

        return paymentMethod;
    }


    private void checkShoppingCartDTOReceivedFromFrontendHasNoNullValues(ShoppingCartDTO shoppingCartDTO)
    {
        if(shoppingCartDTO.getCustomerEmail() == null || shoppingCartDTO.getCustomerEmail().isEmpty())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Order cannot be processed!Bad Request!");
            sb.append(" ");
            sb.append("Parameter");
            sb.append(" ");
            sb.append("[");
            sb.append("customerEmail");
            sb.append("]");
            sb.append(" ");
            sb.append("cannot be empty!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.BAD_REQUEST);
            appException.setAppErrorCode(HttpStatus.BAD_REQUEST.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("One or more parameter values are empty.Cannot complete the request!");
            throw appException;
        }
        if(shoppingCartDTO.getProducerID() == null || shoppingCartDTO.getProducerID().isEmpty())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Order cannot be processed!Bad Request!");
            sb.append(" ");
            sb.append("Parameter");
            sb.append(" ");
            sb.append("[");
            sb.append("producerID");
            sb.append("]");
            sb.append(" ");
            sb.append("cannot be empty!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.BAD_REQUEST);
            appException.setAppErrorCode(HttpStatus.BAD_REQUEST.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("One or more parameter values are empty.Cannot complete the request!");
            throw appException;
        }
        if(shoppingCartDTO.getClientAddressID() == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Order cannot be processed!Bad Request!");
            sb.append(" ");
            sb.append("Parameter");
            sb.append(" ");
            sb.append("[");
            sb.append("clientAddressID");
            sb.append("]");
            sb.append(" ");
            sb.append("cannot be empty!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.BAD_REQUEST);
            appException.setAppErrorCode(HttpStatus.BAD_REQUEST.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("One or more parameter values are empty.Cannot complete the request!");
            throw appException;
        }
        if(shoppingCartDTO.getPaymentMethodID() == null || shoppingCartDTO.getPaymentMethodID().isEmpty())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Order cannot be processed!Bad Request!");
            sb.append(" ");
            sb.append("Parameter");
            sb.append(" ");
            sb.append("[");
            sb.append("paymentMethodID");
            sb.append("]");
            sb.append(" ");
            sb.append("cannot be empty!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.BAD_REQUEST);
            appException.setAppErrorCode(HttpStatus.BAD_REQUEST.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("One or more parameter values are empty.Cannot complete the request!");
            throw appException;
        }
        if(shoppingCartDTO.getShoppingCartProducts() == null || shoppingCartDTO.getShoppingCartProducts().isEmpty())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Order cannot be processed!Bad Request!");
            sb.append(" ");
            sb.append("Parameter");
            sb.append(" ");
            sb.append("[");
            sb.append("shoppingCartProducts Set");
            sb.append("]");
            sb.append(" ");
            sb.append("cannot be empty!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.BAD_REQUEST);
            appException.setAppErrorCode(HttpStatus.BAD_REQUEST.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("One or more parameter values are empty.Cannot complete the request!");
            throw appException;
        }
    }



    public String orderIDUniqueGenerator() throws AppException
    {
        String userUniqueID = null;
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        StringBuilder sb = new StringBuilder();
        sb.append("ORDER-");
        sb.append(uuid);
        userUniqueID = sb.toString();
        return userUniqueID;
    }

    public String paymentIDUniqueGenerator() throws AppException
    {
        String userUniqueID = null;
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        StringBuilder sb = new StringBuilder();
        sb.append("PAYMENT-");
        sb.append(uuid);
        userUniqueID = sb.toString();
        return userUniqueID;
    }

    public String paymentApprovalIDUniqueGenerator() throws AppException
    {
        String userUniqueID = null;
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        StringBuilder sb = new StringBuilder();
        sb.append("PAYMENTAPPROVAL-");
        sb.append(uuid);
        userUniqueID = sb.toString();
        return userUniqueID;
    }

    public String paymentDepositIDUniqueGenerator() throws AppException
    {
        String userUniqueID = null;
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        StringBuilder sb = new StringBuilder();
        sb.append("PAYMENTDEPOSIT-");
        sb.append(uuid);
        userUniqueID = sb.toString();
        return userUniqueID;
    }

}
