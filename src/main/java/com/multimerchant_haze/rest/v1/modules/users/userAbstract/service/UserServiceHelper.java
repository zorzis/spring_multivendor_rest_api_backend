package com.multimerchant_haze.rest.v1.modules.users.userAbstract.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import org.springframework.http.HttpStatus;

/**
 * Created by zorzis on 5/27/2017.
 */
public class UserServiceHelper
{
    // Verify if user already exists in database by user email
    // Email is UNIQUE for each user
    // Check only at Users_Accounts Table
    public static ClientDTO createClientDTOIfClientEntityExists(Client clientToBeCheckedIfExists, String dataKeyToBeChecked, String dataValue) throws AppException
    {
        if(clientToBeCheckedIfExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Client Not Found!");
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
            appException.setDevelopersMessageExtraInfoAsSingleReason("Client entity with specific credentials not found at the Database");
            throw appException;
        }
        else
        {
            // create a user model object using Client(UserEntity userEntity) constructor
            return new ClientDTO(clientToBeCheckedIfExists);
        }
    }


    public static ProducerDTO createProducerDTOIfProducerEntityExists(Producer producerToBeCheckedIfExists, String dataKeyToBeChecked, String dataValue) throws AppException
    {
        if(producerToBeCheckedIfExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Producer Not Found!");
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
            appException.setDevelopersMessageExtraInfoAsSingleReason("Producer entity with specific credentials not found at the Database");
            throw appException;
        }
        else
        {
            // create a user model object using Client(UserEntity userEntity) constructor
            return new ProducerDTO(producerToBeCheckedIfExists);
        }
    }

    public static void checkIfProducerEntityExistsElseThrowAppException(Producer producerToBeCheckedIfExists, String dataKeyToBeChecked, String dataValue) throws AppException
    {
        if(producerToBeCheckedIfExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Producer Not Found!");
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
            appException.setDevelopersMessageExtraInfoAsSingleReason("Producer entity with specific credentials not found at the Database");
            throw appException;
        }
    }


    public static void checkIfClientEntityExistsElseThrowException(Client clientToBeCheckedIfExists, String dataKeyToBeChecked, String dataValue)
    {
        if(clientToBeCheckedIfExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Client Not Found!");
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
            appException.setDevelopersMessageExtraInfoAsSingleReason("Client entity with specific credentials not found at the Database");
            throw appException;
        }
    }
}
