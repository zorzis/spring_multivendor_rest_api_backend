package com.multimerchant_haze.rest.v1.modules.payments.service.paypal_api;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zorzis on 7/24/2017.
 */
@Service
public class PaypalRestAPIServiceImplementation implements PaypalRestAPIService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PaypalRestAPIServiceImplementation.class);
    private String className =  this.getClass().getSimpleName();

    // Replace these values with your clientId and secret. You can use these to get started right now.
    final static String CLIENT_ID = "change_me";
    final static String CLIENT_SECRET = "change_me";
    final static String PAYPAL_MODE = "sandbox";



    // set the cancel and success callback urls
    private String MY_CANCEL_CALL_BACK_URL = "http://localhost:9090/client/order/cancel_paypal_checkout";
    private String MY_APPROVAL_CALLBACK_URL = "http://localhost:4200/account/orders";


    private  final APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, PAYPAL_MODE);

    @Override
    public Payment setPaypalExpressCheckout(Payment paypalOriginalPaymentObject) throws AppException, PayPalRESTException
    {
        Payment createdPayment;

        // Create a payment by posting to the APIService using a valid AccessToken
        // The return object contains the status;
        createdPayment = paypalOriginalPaymentObject.create(this.apiContext);

        String createdPaypalPaymentResponse = createdPayment.toJSON();
        System.out.println("CreatedPayment Paypal Response is: " + createdPaypalPaymentResponse);
        return createdPayment;
    }



    @Override
    public Payment executePaypalExpressCheckout(String paymentId, String PayerID) throws AppException, PayPalRESTException
    {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(PayerID);
        Payment payment = new Payment();
        payment.setId(paymentId);
        payment.execute(this.apiContext, paymentExecution);
        return payment;
    }

    @Override
    public Payment getPaymentFromPaypalAPIByPaypalPaymentID(String originalPaypalPaymentID) throws AppException
    {
        Payment payment = null;
        try {

            // Retrieve the payment object by calling the
            // static `get` method
            // on the Payment class by passing a valid
            // AccessToken and Payment ID
            payment = Payment.get(this.apiContext, originalPaypalPaymentID);


            LOGGER.info("Payment retrieved ID = " + payment.getId()
                    + ", status = " + payment.getState());

        } catch (PayPalRESTException e) {

            StringBuilder sb = new StringBuilder();
            sb.append("Failed to Get Paypal Payment.");
            sb.append(" ");
            sb.append("A PayPalRestException is thrown.");
            sb.append(" ");
            sb.append("Error follows");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(e.toString());
            sb.append("]");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            appException.setAppErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("A PayPal Error Exception is thrown.If Error continues contact Developer.");
            throw appException;
        }

        return payment;
    }


    public Payment createPaypalCheckoutPaymentObject(com.multimerchant_haze.rest.v1.modules.orders.model.Order order)
    {
        // ###Details
        // Let’s you specify details of a payment amount.
        Details details = new Details();
        details.setShipping(Float.toString(0));
        details.setSubtotal(String.format ("%,.2f", order.getSubTotalOrderPrice()));
        details.setTax(String.format ("%,.2f", order.getTotalProductsTax()));

        // ###Amount
        // Let’s you specify a payment amount.
        Amount amount = new Amount();
        amount.setCurrency("EUR");
        // Total must be equal to sum of shipping, tax and subtotal.
        amount.setTotal(String.format ("%,.2f", order.getTotalOrderPrice()));
        amount.setDetails(details);

        // ###Transaction
        // A transaction defines the contract of a
        // payment – what is the payment for and who
        // is fulfilling it. Transaction is created with
        // a Payee and Amount types
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);

        StringBuilder sbPaypalTransDesc = new StringBuilder();
        sbPaypalTransDesc.append("haze.gr Paypal Transaction between: ");
        sbPaypalTransDesc.append("Customer: ");
        sbPaypalTransDesc.append("[");
        sbPaypalTransDesc.append(order.getOrderHasClientHasProducer().getClient().getEmail());
        sbPaypalTransDesc.append("]");
        sbPaypalTransDesc.append(" ");
        sbPaypalTransDesc.append("with clientID: ");
        sbPaypalTransDesc.append("[");
        sbPaypalTransDesc.append(order.getOrderHasClientHasProducer().getClient().getClientProfile().getClientID());
        sbPaypalTransDesc.append("]");
        sbPaypalTransDesc.append(" --- ");
        sbPaypalTransDesc.append("Merchant: ");
        sbPaypalTransDesc.append("[");
        sbPaypalTransDesc.append(order.getOrderHasClientHasProducer().getProducer().getEmail());
        sbPaypalTransDesc.append("]");
        sbPaypalTransDesc.append(" ");
        sbPaypalTransDesc.append("with clientID: ");
        sbPaypalTransDesc.append("[");
        sbPaypalTransDesc.append(order.getOrderHasClientHasProducer().getProducer().getProducerProfile().getProducerID());
        sbPaypalTransDesc.append("]");

        transaction.setDescription(sbPaypalTransDesc.toString());


        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<Item>();

        for(OrderProduct orderProduct : order.getOrderProductsFromOrderHasProductsEntities())
        {
            Float totalItemPrice = (orderProduct.getOrderProductPrice() * orderProduct.getOrderProductQuantity());

            // ###Items
            Item item = new Item();
            item.setName(orderProduct.getOrderProductName() + " - (" + orderProduct.getOrderProductQuantity() + "Liters)")
                    .setCategory("PHYSICAL")
                    .setDescription(orderProduct.getOrderProductDescription())
                    .setSku(orderProduct.getProductID())
                    .setQuantity("1")
                    .setCurrency("EUR")
                    .setPrice(String.format ("%,.2f", totalItemPrice));
            items.add(item);
        }

        itemList.setItems(items);
        transaction.setItemList(itemList);

        // The Payment creation API requires a list of
        // Transaction; add the created Transaction
        // to a List
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);


        // ###Payer
        // A resource representing a Payer that funds a payment
        // Payment Method
        // as ‘paypal’
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        //payer.setPayerInfo(payerInfo);

        // ###Payment
        // A Payment Resource; create one using
        // the above types and intent as ‘sale’
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);


        // ###Redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(MY_CANCEL_CALL_BACK_URL);
        redirectUrls.setReturnUrl(MY_APPROVAL_CALLBACK_URL);
        payment.setRedirectUrls(redirectUrls);


        return payment;
    }
}
