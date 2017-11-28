package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerAuthorityDAO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerDAO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerAuthorityDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerHasAuthority;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zorzis on 3/2/2017.
 */
@Service
public class ProducerServiceImplementation implements ProducerService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    ProducerDAO producerDAO;

    @Autowired
    ProducerAuthorityDAO producerAuthorityDAO;




    /********************* CREATE related methods implementation ***********************/



    @Override
    @Transactional("transactionManager")
    public String addAuthorityToProducerByProducerEmailAndAuthorityName(ProducerDTO producerDTO, ProducerAuthorityDTO producerAuthorityDTO) throws AppException
    {
        //verify existence of controller in the db (email must be unique)
        Producer userByEmail = producerDAO.getProducerByEmailFetchingProfileFetchingAuthoritiesFetchingAddress(producerDTO.getEmail());
        // Check if user already exists else throw AppException and stop process
        ProducerDTO producerDTOafterSuccessfullVerificationForExistence = verifyProducerExists(userByEmail,"Email", producerDTO.getEmail());

        // Check if authority exists at database
        ProducerAuthority userAuthorityToBeAdded = producerAuthorityDAO.getAuthorityByAuthorityName(producerAuthorityDTO.getRole());
        // TODO: 3/30/2017 In Production don't create new Authority If not already Exists
        // todo we don't want to assign new Authorities If Not Already Exist in Database
        if(userAuthorityToBeAdded == null)
        {
            LOGGER.debug("ProducerAuthority [" + producerAuthorityDTO.getRole() + "]  to be assigned to user " + userByEmail.getEmail() + " Does Not Exists at Database Authorities Table");

            StringBuilder sb = new StringBuilder();
            sb.append("Failed to assign Authority");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(producerAuthorityDTO.getRole());
            sb.append("]");
            sb.append(" ");
            sb.append("to Producer with Email");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(userByEmail.getEmail());
            sb.append("]");
            sb.append(" ");
            sb.append("because Authority does not exists to our system.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Authority does not exists to our system.System cannot complete authority assignment");
            throw appException;
        }



        // Check if user has already assigned the authority
        ProducerHasAuthority userHasAuthorityToBeAdded = this.checkIfProducerHasAuthorityByAuthorityName(userByEmail, producerAuthorityDTO);
        if(userHasAuthorityToBeAdded != null)
        {
            LOGGER.debug("ProducerAuthority [" + userAuthorityToBeAdded.getRole() + "] is already assigned to user [" + userByEmail.getEmail() + "].");

            StringBuilder sb = new StringBuilder();
            sb.append("Failed to assign Authority");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(producerAuthorityDTO.getRole());
            sb.append("]");
            sb.append(" ");
            sb.append("to Producer with Email");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(userByEmail.getEmail());
            sb.append("]");
            sb.append(" ");
            sb.append("because Authority is already assigned to user.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Authority is already assigned to specific user.");
            throw appException;
        }


        userHasAuthorityToBeAdded = new ProducerHasAuthority();
        userHasAuthorityToBeAdded.setProducer(userByEmail);
        userHasAuthorityToBeAdded.setProducerAuthority(userAuthorityToBeAdded);


        return producerDAO.addProducerHasAuthority(userHasAuthorityToBeAdded);
    }




    /********************* DELETE related methods implementation ***********************/
    @Override
    @Transactional("transactionManager")
    public String deleteProducerByProducerEmail(ProducerDTO producerDTO) throws AppException
    {
        //verify existence of controller in the db (email must be unique)
        Producer producerByEmail = producerDAO.getProducerByEmailOnlyFromProducersTable(producerDTO.getEmail());
        // Check if user already exists else throw AppException and stop process
        ProducerDTO producerDTOafterSuccessfullVerificationForExistence = verifyProducerExists(producerByEmail,"Email", producerDTO.getEmail());
        return producerDAO.deleteProducer(producerByEmail);
    }


    @Override
    @Transactional("transactionManager")
    public String deleteProducerAuthorityByProducerEmailAndAuthorityID(ProducerDTO producerDTO, ProducerAuthorityDTO producerAuthorityDTO) throws AppException
    {
        //verify existence of controller in the db (email must be unique)
        Producer producerByEmail = producerDAO.getProducerByEmailFetchingProfileFetchingAuthoritiesFetchingAddress(producerDTO.getEmail());
        // Check if user already exists else throw AppException and stop process
        ProducerDTO producerDTOafterSuccessfullVerificationForExistence = verifyProducerExists(producerByEmail,"Email", producerDTO.getEmail());

        ProducerHasAuthority producerHasAuthorityToBeDeleted = this.checkIfProducerHasSpecificAuthorityByAuthorityID(producerByEmail, producerAuthorityDTO);

        if(producerHasAuthorityToBeDeleted == null)
        {
            LOGGER.debug("ProducerAuthority [" + producerAuthorityDTO.getAuthorityID() + "] is not assigned to user [" + producerByEmail.getEmail() + "].");

            StringBuilder sb = new StringBuilder();
            sb.append("Failed to delete Authority");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(producerAuthorityDTO.getAuthorityID());
            sb.append("]");
            sb.append(" ");
            sb.append("from Producer with Email");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(producerByEmail.getEmail());
            sb.append("]");
            sb.append(" ");
            sb.append("because Authority is not assigned to user.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Authority is not assigned to specific user.");
            throw appException;
        }

        return producerDAO.deleteProducerHasAuthority(producerHasAuthorityToBeDeleted);
    }






    /********************* helper public methods implementation ***********************/



    // Verify if user already exists in database by user email
    // Email is UNIQUE for each user
    // Check only at Users_Accounts Table
    @Override
    public ProducerDTO verifyProducerExists(Producer producerToBeCheckedIfExists, String dataKeyToBeChecked, String dataValue) throws AppException
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
            // create a user model object using Producer(UserEntity userEntity) constructor
            return new ProducerDTO(producerToBeCheckedIfExists);
        }
    }




    /********************* helper private methods implementation ***********************/





    // Check if UserEntity has an Authority by Authority name/role
    private ProducerHasAuthority checkIfProducerHasAuthorityByAuthorityName(Producer producer, ProducerAuthorityDTO producerAuthorityDTO)
    {
        ProducerHasAuthority producerHasAuthorityToBeVerifiedThatExists = null;

        // Verify ProducerAuthority does not exist to Producer Authorities
        for(ProducerHasAuthority producerHasAuthority : producer.getProducerHasAuthorities())
        {
            if(producerHasAuthority.getProducerAuthority().getRole().equals(producerAuthorityDTO.getRole()))
            {
                producerHasAuthorityToBeVerifiedThatExists = producerHasAuthority;
            }
        }

        return producerHasAuthorityToBeVerifiedThatExists;
    }

    private ProducerHasAuthority checkIfProducerHasSpecificAuthorityByAuthorityID(Producer producer, ProducerAuthorityDTO producerAuthorityDTO)
    {
        ProducerHasAuthority producerHasAuthorityToBeVerifiedThatExists = null;

        // Verify ProducerAuthority does not exist to Producer Authorities
        for(ProducerHasAuthority producerHasAuthority : producer.getProducerHasAuthorities())
        {
            if(producerHasAuthority.getProducerAuthority().getAuthorityID().equals(producerAuthorityDTO.getAuthorityID()))
            {
                producerHasAuthorityToBeVerifiedThatExists = producerHasAuthority;
            }
        }

        return producerHasAuthorityToBeVerifiedThatExists;
    }
}
