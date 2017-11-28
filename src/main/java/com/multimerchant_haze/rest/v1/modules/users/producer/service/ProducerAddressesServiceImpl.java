package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerAddressDAO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerDAO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerAddressDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAddress;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.service.UserServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by zorzis on 6/8/2017.
 */
@Service
public class ProducerAddressesServiceImpl implements ProducerAddressesService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerAddressesService.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    ProducerDAO producerDAO;

    @Autowired
    ProducerAddressDAO producerAddressDAO;


    @Override
    public ProducerDTO getProducerAddress(String email) throws AppException
    {
        // the user entity retrieved from database based on email
        Producer producerByEmail = producerDAO.getProducerByEmailFetchingAddressFetchingProfile(email);
        // the user data transfer object we gonna create
        ProducerDTO producerDTO = UserServiceHelper.createProducerDTOIfProducerEntityExists(producerByEmail, "email", email);

        // check if there is an assigned address to Producer
        // if there is proceed else throw AppException with 404 error status message
        this.checkAddressExistsElseThrowError(producerByEmail.getProducerProfile().getProducerAddress());

        return producerDTO;
    }

    @Override
    @Transactional("transactionManager")
    public String addAddressToProducerByProducerEmailAndAddressDTO(ProducerDTO producerDTO, ProducerAddressDTO producerAddressDTO) throws AppException
    {
        // get the producer fetching also the addresses asigned to him/her(one address)
        Producer producerByEmail = producerDAO.getProducerByEmailFetchingAddressFetchingProfile(producerDTO.getEmail());
        // Check if user already exists else throw AppException and stop process
        UserServiceHelper.createProducerDTOIfProducerEntityExists(producerByEmail,"Email", producerDTO.getEmail());

        this.checkAddressNotExistsElseThrowError(producerByEmail.getProducerProfile().getProducerAddress());

        ProducerAddress producerAddressToBeAdded = new ProducerAddress(producerAddressDTO);

        producerAddressToBeAdded.setProducerProfile(producerByEmail.getProducerProfile());
        producerAddressToBeAdded.setCreatedAt(new Date());

        return producerDAO.addProducerAddress(producerAddressToBeAdded);
    }

    @Override
    @Transactional("transactionManager")
    public String updateAddressOfProducerByProducerEmailAndAddressDTO(ProducerDTO producerDTO, ProducerAddressDTO producerAddressDTO) throws AppException
    {
        // get the producer fetching also the addresses asigned to him/her(one address)
        Producer producerByEmail = producerDAO.getProducerByEmailFetchingAddressFetchingProfile(producerDTO.getEmail());
        // Check if user already exists else throw AppException and stop process
        UserServiceHelper.createProducerDTOIfProducerEntityExists(producerByEmail,"Email", producerDTO.getEmail());

        // Get the current producer address
        ProducerAddress currentStateProducerAddress = producerByEmail.getProducerProfile().getProducerAddress();


        // check if there is an assigned address to Producer
        // if there is proceed else throw AppException with 404 error status message
        this.checkAddressExistsElseThrowError(currentStateProducerAddress);



        ProducerAddress producerAddressToBeUpdated = new ProducerAddress(producerAddressDTO);

        producerAddressToBeUpdated.setProducerProfile(producerByEmail.getProducerProfile());

        // we set the id so the JPA knows where to merge the entity
        producerAddressToBeUpdated.setId(currentStateProducerAddress.getId());

        // because created_at cannot be null assign to the new address entity the previous value of created_at
        producerAddressToBeUpdated.setCreatedAt(currentStateProducerAddress.getCreatedAt());
        producerAddressToBeUpdated.setUpdatedAt(new Date());

        return producerDAO.updateProducerAddress(producerAddressToBeUpdated);
    }



    @Override
    @Transactional("transactionManager")
    public String deleteAddressFromProducerByProducerEmailAndAddress(ProducerDTO producerDTO, ProducerAddressDTO producerAddressDTO) throws AppException
    {
/*        //verify existence of controller in the db (email must be unique)
        Producer producerByEmail = producerDAO.getProducerByEmailOnlyFromProducersTable(producerDTO.getEmail());

        // Check if user already exists else throw AppException and stop process
        ProducerDTO producerDTOafterSuccessfullVerificationForExistence = UserServiceHelper.createProducerDTOIfProducerEntityExists(producerByEmail,"Email", producerDTO.getEmail());

        ProducerAddress producerAddressForDeletion = new ProducerAddress(producerAddressDTO);

        ProducerHasAddress producerHasAddressToBeDeleted = this.checkIfProducerHasSpecificAddressByAddressID(producerByEmail, producerAddressDTO);

        if(producerHasAddressToBeDeleted == null)
        {
            LOGGER.debug("ProducerAddress [" + producerAddressDTO.getShoppingCartID() + "] is not part of producer [" + producerByEmail.getEmail() + "] addresses");

            StringBuilder sb = new StringBuilder();
            sb.append("Failed to delete Producer Address");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(producerAddressDTO.getShoppingCartID());
            sb.append("]");
            sb.append(" ");
            sb.append("from Producer with Email");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(producerByEmail.getEmail());
            sb.append("]");
            sb.append(" ");
            sb.append("because Address does not exist to producer addresses.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Address is not found to specific producer addresses.");
            throw appException;
        }

        producerHasAddressToBeDeleted.setProducerAddress(producerAddressForDeletion);
        return producerDAO.deleteProducerAddress(producerHasAddressToBeDeleted);*/

        return null;
    }




    private void checkAddressExistsElseThrowError(ProducerAddress producerAddressToBeCheckedIfExists)
    {
        if(producerAddressToBeCheckedIfExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Producer has not any address assigned already!");
            sb.append(" ");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Producer entity has not any address asigned yet!");
            throw appException;
        }
    }

    private void checkAddressNotExistsElseThrowError(ProducerAddress producerAddressToBeCheckedIfExists)
    {
        if(producerAddressToBeCheckedIfExists != null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Producer has already assigned address to profile!");
            sb.append(" ");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Producer has already assigned address to profile! Only one address is allowed per profile!");
            throw appException;
        }
    }


}
