package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.payments.dao.PaymentMethodDAO;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerDAO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerPaymentMethodDAO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.payments.dto.PaymentMethodDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerHasPaymentMethod;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.service.UserServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by zorzis on 9/9/2017.
 */
@Service
public class ProducerPaymentMethodsServiceImplementation implements ProducerPaymentMethodsService
{

    @Autowired
    ProducerDAO producerDAO;

    @Autowired
    ProducerPaymentMethodDAO producerPaymentMethodDAO;

    @Autowired
    PaymentMethodDAO paymentMethodDAO;

    @Override
    public List<PaymentMethodDTO> getAllStandardPaymentMethods() throws AppException
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
    public ProducerDTO getProducerWithPaymentMethodsByProducerEmail(ProducerDTO producerDTO) throws AppException
    {
        Producer producer = this.producerPaymentMethodDAO.getProducerByProducerIDFetchingAddressFetchingProductsFetchingPaymentMethods(producerDTO.getEmail());
        // check if producer exists
        ProducerDTO producerDTOToBeChecked = UserServiceHelper.createProducerDTOIfProducerEntityExists(producer, "Producer Email", producerDTO.getEmail());
        // asign products entities to producerDTO
        producerDTOToBeChecked.mapProductsDTOsFromProductsEntities(producer.getProducerProductsEntities());

        //assign paymentMethods entities to producerDTO
        producerDTOToBeChecked.mapProducerPaymentMethodsDTOsFromProducerPaymentMethodsEntities(producer.getProducerHasPaymentMethodSet());

        return producerDTOToBeChecked;
    }


    @Override
    @Transactional("transactionManager")
    public String addPaymentMethodToProducerPaymentMethodsByProducerEMAIL(ProducerDTO producerDTO, PaymentMethodDTO paymentMethodDTO) throws AppException
    {
        // At first we check for the paymentMethod of producerDTO object if indeed exists
        Producer producerEntity = this.producerPaymentMethodDAO.getProducerByProducerIDFetchingAddressFetchingProductsFetchingPaymentMethods(producerDTO.getEmail());

        // Check if user already exists else throw AppException and stop process
        UserServiceHelper.createProducerDTOIfProducerEntityExists(producerEntity,"Producer Email", producerDTO.getEmail());


        Set<PaymentMethod> paymentMethods = producerEntity.getProducerPaymentMethods();


        // check if PaymentMethod indeed exists on our database
        PaymentMethod paymentMethod = this.paymentMethodDAO.getPaymentMethodByID(paymentMethodDTO.getPaymentMethodID());

        if(paymentMethod == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Problem with addition of new Payment Method. ");
            sb.append("Payment Method with ID: ");
            sb.append("[");
            sb.append(paymentMethodDTO.getPaymentMethodID());
            sb.append("]");
            sb.append(" ");
            sb.append("does not exist on our acceptable payments system.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Payment Method does not exist on our payment system!");
            throw appException;
        }

        // Check if payment method already assigned to producer payment methods
        for(PaymentMethod paymentMethodIterator : paymentMethods)
        {
            if( paymentMethodIterator.getPaymentMethodID().equals(paymentMethodDTO.getPaymentMethodID()))
            {
                StringBuilder sb = new StringBuilder();
                sb.append("Problem with addition of new Payment Method. ");
                sb.append("Payment Method with ID: ");
                sb.append("[");
                sb.append( paymentMethodIterator.getPaymentMethodID());
                sb.append("]");
                sb.append(" ");
                sb.append("is already assigned to producer account.");
                String errorMessage = sb.toString();

                AppException appException = new AppException(errorMessage);
                appException.setHttpStatus(HttpStatus.CONFLICT);
                appException.setAppErrorCode(HttpStatus.CONFLICT.value());
                appException.setDevelopersMessageExtraInfoAsSingleReason("Payment Method has already been assigned producer account!");
                throw appException;
            }
        }


        // Lets Create the Object to be inserted to database to our ProducersHavePaymentMethodsTable
        ProducerHasPaymentMethod producerHasPaymentMethod = new ProducerHasPaymentMethod();
        producerHasPaymentMethod.setProducer(producerEntity);
        producerHasPaymentMethod.setPaymentMethod(paymentMethod);
        producerHasPaymentMethod.setCreatedAt(new Date());
        producerHasPaymentMethod.setTerminated(false);

        producerEntity.getProducerHasPaymentMethodSet().add(producerHasPaymentMethod);
        producerHasPaymentMethod.setProducer(producerEntity);

        return this.producerPaymentMethodDAO.addProducerHasPaymentMethod(producerHasPaymentMethod);
    }





}
