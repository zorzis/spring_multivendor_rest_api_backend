package com.multimerchant_haze.rest.v1.modules.payments.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.payments.dao.PaymentMethodDAO;
import com.multimerchant_haze.rest.v1.modules.payments.dto.PaymentMethodDTO;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;
import com.multimerchant_haze.rest.v1.modules.users.producer.service.ProducerAddressesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zorzis on 9/8/2017.
 */
@Service
public class PaymentMethodServiceImplementation implements PaymentMethodService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerAddressesService.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    PaymentMethodDAO paymentMethodDAO;



    @Override
    public PaymentMethodDTO getPaymentMethodByPaymentMethodName(String paymentMethodName) throws AppException
    {
        PaymentMethod paymentMethodByPaymentethodName = paymentMethodDAO.getPaymentMethodByName(paymentMethodName);
        PaymentMethodDTO paymentMethodDTO = verifyPaymentMethodExists(paymentMethodByPaymentethodName,
                "PaymentMethodName", paymentMethodName);
        return paymentMethodDTO;
    }

    @Override
    public PaymentMethodDTO getPaymentMethodByPaymentMethodID(String paymentMethodID) throws AppException
    {
        PaymentMethod paymentMethodByPaymentMethodID = paymentMethodDAO.getPaymentMethodByID(paymentMethodID);
        PaymentMethodDTO paymentMethodDTO = verifyPaymentMethodExists(paymentMethodByPaymentMethodID,
                "PaymentMethodID", paymentMethodID);
        return paymentMethodDTO;
    }


    private PaymentMethodDTO verifyPaymentMethodExists(PaymentMethod paymentMethodToBeCheckedIfExists, String dataKeyToBeChecked, String dataValue) throws AppException
    {
        if(paymentMethodToBeCheckedIfExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Payment Method Not Found!");
            sb.append(" ");
            sb.append(dataKeyToBeChecked);
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(dataValue);
            sb.append("]");
            sb.append(" ");
            sb.append("is not registered to our system.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Payment method entity with specific credentials not found at the Database");
            throw appException;
        }
        else
        {
            return new PaymentMethodDTO(paymentMethodToBeCheckedIfExists);
        }
    }


    @Override
    public List<PaymentMethodDTO> getAllPaymentMethods() throws AppException
    {
        List<PaymentMethodDTO> paymentMethodDTOS = new ArrayList<>(0);

        List<PaymentMethod> paymentMethodEntities;

        paymentMethodEntities = this.paymentMethodDAO.getPaymentMethods();

        for(PaymentMethod paymentMethodEntity : paymentMethodEntities)
        {
            paymentMethodDTOS.add(new PaymentMethodDTO(paymentMethodEntity));
        }

        return paymentMethodDTOS;
    }


    @Override
    @Transactional("transactionManager")
    public String addNewPaymentMethod(PaymentMethodDTO paymentMethodDTO) throws AppException
    {


        List<PaymentMethodDTO> paymentMethodDTOS = this.getAllPaymentMethods();

        this.checkNotNullParameters(paymentMethodDTO.getPaymentMethodID(), "paymentMethodID");


        this.checkNotNullParameters(paymentMethodDTO.getPaymentMethodName(), "paymentMethodName");



        for(PaymentMethodDTO paymentMethodDTOFromList : paymentMethodDTOS)
        {

            if(paymentMethodDTOFromList.getPaymentMethodID().equals(paymentMethodDTO.getPaymentMethodID()))
            {
                StringBuilder sb = new StringBuilder();
                sb.append("Payment Method Addition Cannot Be Processed.");
                sb.append(" ");
                sb.append("[PaymentMethodID]");
                sb.append(":");
                sb.append(" ");
                sb.append("[");
                sb.append(paymentMethodDTOFromList.getPaymentMethodID());
                sb.append("]");
                sb.append(" ");
                sb.append("Already in Use!");
                String errorMessage = sb.toString();

                AppException appException = new AppException(errorMessage);
                appException.setHttpStatus(HttpStatus.CONFLICT);
                appException.setAppErrorCode(HttpStatus.CONFLICT.value());
                appException.setDevelopersMessageExtraInfoAsSingleReason("Payment Method Addition Cannot Be Processed. Payment Method ID already in use.");
                throw appException;
            }

            if(paymentMethodDTOFromList.getPaymentMethodName().equals(paymentMethodDTO.getPaymentMethodName()))
            {
                StringBuilder sb = new StringBuilder();
                sb.append("Payment Method Addition Cannot Be Processed.");
                sb.append(" ");
                sb.append("[PaymentMethodName]");
                sb.append(":");
                sb.append(" ");
                sb.append("[");
                sb.append(paymentMethodDTOFromList.getPaymentMethodName());
                sb.append("]");
                sb.append(" ");
                sb.append("Already in Use!");
                String errorMessage = sb.toString();

                AppException appException = new AppException(errorMessage);
                appException.setHttpStatus(HttpStatus.CONFLICT);
                appException.setAppErrorCode(HttpStatus.CONFLICT.value());
                appException.setDevelopersMessageExtraInfoAsSingleReason("Payment Method Addition Cannot Be Processed. Payment Method Name already in use.");
                throw appException;
            }
        }

        return this.paymentMethodDAO.addPaymentMethod(new PaymentMethod(paymentMethodDTO));

    }


    @Override
    @Transactional("transactionManager")
    public String deletePaymentMethodByName(PaymentMethodDTO paymentMethodDTO) throws AppException
    {
        // we use the already implemented method
        PaymentMethodDTO paymentMethodDTOClaimsToBeDeleted = this.getPaymentMethodByPaymentMethodName(paymentMethodDTO.getPaymentMethodName());

        return this.paymentMethodDAO.deletePaymentMethod(new PaymentMethod(paymentMethodDTOClaimsToBeDeleted));

    }

    @Override
    @Transactional("transactionManager")
    public String deletePaymentMethodByID(PaymentMethodDTO paymentMethodDTO) throws AppException
    {
        // we use the already implemented method
        PaymentMethodDTO paymentMethodDTOClaimsToBeDeleted = this.getPaymentMethodByPaymentMethodID(paymentMethodDTO.getPaymentMethodID());

        return this.paymentMethodDAO.deletePaymentMethod(new PaymentMethod(paymentMethodDTOClaimsToBeDeleted));
    }

    private void checkNotNullParameters(Object parameterToBeChecked, String parametererName) throws AppException
    {
        if(parameterToBeChecked == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Payment Method Addition Cannot Be Processed");
            sb.append(" ");
            sb.append("[");
            sb.append(parametererName);
            sb.append("]");
            sb.append(" ");
            sb.append("cannot be null!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Payment Method Addition Cannot Be Processed. Parameters cannot be null");
            throw appException;
        }

    }

}

