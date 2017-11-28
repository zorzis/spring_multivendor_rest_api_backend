package com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.emails.service.MailService;
import com.multimerchant_haze.rest.v1.modules.orders.dao.ShoppingCartDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dto.OrderDTO;
import com.multimerchant_haze.rest.v1.modules.orders.dto.ShoppingCartProductDTO;
import com.multimerchant_haze.rest.v1.modules.orders.model.Order;
import com.multimerchant_haze.rest.v1.modules.orders.model.ShoppingCart;
import com.multimerchant_haze.rest.v1.modules.orders.model.ShoppingCartProduct;
import com.multimerchant_haze.rest.v1.modules.payments.dao.PaymentMethodDAO;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerHasPaymentMethodDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.orders.dao.OrdersDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dto.ShoppingCartDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientAddress;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerDAO;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.service.UserServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by zorzis on 10/4/2017.
 */
@Service
public class OrderProcessorServiceImplementation implements OrderProcessorService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProcessorServiceImplementation.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    ProducerDAO producerDAO;

    @Autowired
    PaymentMethodDAO paymentMethodDAO;


    @Qualifier("orderEmailService")
    @Autowired
    private MailService mailService;

    @Autowired
    private OrdersDAO ordersDAO;

    @Autowired
    private ShoppingCartDAO shoppingCartDAO;


    @Override
    @Transactional("transactionManager")
    public String saveShoppingCartToDatabase(ShoppingCartDTO shoppingCartDTO) throws AppException
    {

        //TODO CHECK IF SHOPPING CART ID we gonna create exists already in ShoppingCart Table
        // we create the order unique ID but we also check the database if indeed it is unique
        // and no previous equal order id is stored
/*        String orderID = this.orderIDUniqueGenerator();
        ShoppingCart shoppingCartToBeCheckedThatNotExists = this.shoppingCartDAO.getShoppingCartByShoppinCartIDOnlyFromShoppinCartTable(shoppingCartID);
        while(orderToBeCheckedThatNotExists != null)
        {
            orderID = this.orderIDUniqueGenerator();
            orderToBeCheckedThatNotExists = this.ordersDAO.getOrderByOrderIDOnlyFromOrdersTable(orderID);
        }*/

        // ---------------------------------START DOING THE LOGICAL CHECKS-------------------------------- //


        // At first check parameter values are not null
        this.checkShoppingCartDTOReceivedFromFrontendHasNoNullValues(shoppingCartDTO);

        //** Check Client is valid
        Client clientPlacedTheOrder = this.clientDAO.getClientByEmailFetchingAddressesFetchingProfile(shoppingCartDTO.getCustomerEmail());
        ClientDTO clientDTOplacedTheOrder = UserServiceHelper.createClientDTOIfClientEntityExists(clientPlacedTheOrder, "Email", shoppingCartDTO.getCustomerEmail());

        //** Check Producer is valid
        Producer producerToWhomBelongsTheOrder = this.producerDAO.getProducerByProducerIDFetchingAddressFetchingProducts(shoppingCartDTO.getProducerID());
        ProducerDTO producerDTOToWhomBelongsTheOrder = UserServiceHelper.createProducerDTOIfProducerEntityExists(producerToWhomBelongsTheOrder, "ProducerID", shoppingCartDTO.getProducerID());
        producerDTOToWhomBelongsTheOrder.mapProductsDTOsFromProductsEntities(producerToWhomBelongsTheOrder.getProducerProductsEntities());

        //** Check if ClientAddress is valid(client can have multiple addresses)
        ClientAddress clientShippingAddressEntity = this.checkIfAddressBelongsToClient(clientPlacedTheOrder, shoppingCartDTO.getClientAddressID());

        //** Check if PaymentMethod is valid
        this.checkIfPaymentMethodExistsOnDatabase(shoppingCartDTO.getPaymentMethodID());

        //** Check if PaymentMethod Belongs to Producer
        this.checkIfPaymentMethodBelongsToProducer(shoppingCartDTO.getPaymentMethodID(), producerToWhomBelongsTheOrder);

        //** Check if products set in the order request is not empty and every product belongs to producer to whom belongs the order
        Set<ShoppingCartProduct> shoppingCartProductsEntities = this.checkIfProductsBelongToProducerAndReturnShoppingCartProductsSet(producerDTOToWhomBelongsTheOrder, shoppingCartDTO);


        // -----------------------------------END OF THE LOGICAL CHECKS---------------------------------- //


        ShoppingCart shoppingCartToBePersisted = new ShoppingCart();
        shoppingCartToBePersisted.setShoppingCartID(this.shoppingCartIDGenerator());
        shoppingCartToBePersisted.setCustomerEmail(shoppingCartDTO.getCustomerEmail());
        shoppingCartToBePersisted.setProducerID(shoppingCartDTO.getProducerID());
        shoppingCartToBePersisted.setClientAddressID(shoppingCartDTO.getClientAddressID());
        shoppingCartToBePersisted.setPaymentMethodID(shoppingCartDTO.getPaymentMethodID());
        shoppingCartToBePersisted.setShoppingCartProducts(shoppingCartProductsEntities);

        for(ShoppingCartProduct shoppingCartProduct: shoppingCartToBePersisted.getShoppingCartProducts())
        {
            shoppingCartProduct.setShoppingCart(shoppingCartToBePersisted);
        }

        return this.shoppingCartDAO.persistShoppingCart(shoppingCartToBePersisted);
    }


    @Override
    public void sendOrderConfirmationEmailToClient(OrderDTO orderDTO) throws AppException
    {
        // OrderDTO sent here contains only the orderID from the newly created order
        Order order = this.ordersDAO.getSingleOrderFullEntitiesByOrderID(orderDTO.getOrderID());
        OrderDTO orderDTOFromDatabase = new OrderDTO(order);
        this.mailService.sendEmail(orderDTOFromDatabase);
    }


    public void checkIfPaymentMethodExistsOnDatabase(String paymentMethodID) throws AppException
    {
        PaymentMethod paymentMethod = this.paymentMethodDAO.getPaymentMethodByID(paymentMethodID);

        if(paymentMethod == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Order Payment Method");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(paymentMethodID);
            sb.append("]");
            sb.append(" ");
            sb.append("cannot be found in the database!");
            sb.append("Order cannot be processed!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Order Payment Method assigned to order in the service layer cannot be found in the database.Please contact developers!");
            throw appException;
        }
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

    public Set<ShoppingCartProduct> checkIfProductsBelongToProducerAndReturnShoppingCartProductsSet(ProducerDTO producerDTO, ShoppingCartDTO shoppingCartDTO)
    {
        Set<ShoppingCartProduct> shoppingCartProducts = new HashSet<>(0);

        // if we get an empty hashset just throw app exception error
        if(shoppingCartDTO.getShoppingCartProducts().isEmpty())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("No products found.Your Shopping Cart does not contain any Product!");
            sb.append("Order cannot be processed!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Products Array is empty! Order cannot be processed!");
            throw appException;
        }

        // check if product exists more than once in the order
        for(ShoppingCartProductDTO shoppingCartProductFromIteratedSet : shoppingCartDTO.getShoppingCartProducts())
        {
            int count = 0;

            for(ShoppingCartProductDTO shoppingCartProductToBeSearched : shoppingCartDTO.getShoppingCartProducts())
            {
                if(shoppingCartProductFromIteratedSet.getProductID().equals(shoppingCartProductToBeSearched.getProductID()))
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
                sb.append(shoppingCartProductFromIteratedSet.getProductID());
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
        for(ShoppingCartProductDTO shoppingCartProductDTOClaimsToBeOriginal : shoppingCartDTO.getShoppingCartProducts())
        {
            ShoppingCartProduct shoppingCartProductEntity = null;

            for(ProductDTO originalProducerProduct : producerDTO.getProducts())
            {
                if(shoppingCartProductDTOClaimsToBeOriginal.getProductID().equals(originalProducerProduct.getProductID()))
                {
                    shoppingCartProductEntity = new ShoppingCartProduct(shoppingCartProductDTOClaimsToBeOriginal);
                }
            }

            if(shoppingCartProductEntity == null)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("Shopping Product with productID");
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
                shoppingCartProducts.add(shoppingCartProductEntity);
            }
        }

        return shoppingCartProducts;
    }


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
            sb.append("Shopping Cart Payment Method");
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
            appException.setDevelopersMessageExtraInfoAsSingleReason("Shopping Cart Payment Method assigned to order in the service layer is not assigned to coresponding Producer.Please contact developers!");
            throw appException;
        }

        return paymentMethod;
    }

    public String shoppingCartIDGenerator() throws AppException
    {
        String userUniqueID = null;
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        StringBuilder sb = new StringBuilder();
        sb.append("SHOPPING-CART-");
        sb.append(uuid);
        userUniqueID = sb.toString();
        return userUniqueID;
    }
}
