package com.multimerchant_haze.rest.v1.modules.users.client.service.client_profile;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.service.UserServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by zorzis on 5/27/2017.
 */
@Service
public class ClientProfileServiceImpl implements ClientProfileService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientProfileServiceImpl.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    ClientDAO clientDAO;

    @Override
    public ClientDTO getClientAccountProfileByEmail(String email) throws AppException
    {
        Client clientByEmail = clientDAO.getClientByClientEmailFetchingProfileFetchVerificationToken(email);
        ClientDTO clientDTO = UserServiceHelper.createClientDTOIfClientEntityExists(clientByEmail, "Email", email);
        return clientDTO;
    }


    @Override
    @Transactional("transactionManager")
    public String updateClientProfile(ClientDTO clientDTO) throws AppException
    {
        //verify existence of controller in the db (email must be unique)
        Client clientByEmail = clientDAO.getClientByClientEmailFetchingProfileFetchVerificationToken(clientDTO.getEmail());

        // Check if user already exists else throw AppException and stop process
        ClientDTO clientDTOafterSuccessfullVerificationForExistence = UserServiceHelper.createClientDTOIfClientEntityExists(clientByEmail,"Email", clientDTO.getEmail());


        // set the properties to be changed
        clientByEmail.getClientProfile().setFirstName(clientDTO.getFirstName());
        clientByEmail.getClientProfile().setLastName(clientDTO.getLastName());
        clientByEmail.getClientProfile().setBirthDate(clientDTO.getBirthDate());
        clientByEmail.getClientProfile().setGender(clientDTO.getGender());
        // clientByEmail.setIsEnabled(clientDTO.getEnabled());
        clientByEmail.setUpdatedAt(new Date());

        return clientDAO.updateClient(clientByEmail);
    }



}
