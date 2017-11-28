package com.multimerchant_haze.rest.v1.modules.users.client.service.client_authorities;

import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientAuthorityDAO;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientAuthorityDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zorzis on 5/8/2017.
 */
@Service
public class ClientAuthorityServiceImplementation implements ClientAuthorityService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientAuthorityServiceImplementation.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    ClientAuthorityDAO clientAuthorityDAO;


    @Override
    @Transactional("transactionManager")
    public ClientAuthorityDTO getAuthorityByAuthorityName(String authorityName) throws AppException
    {
        ClientAuthority clientAuthorityByAuthorityName = clientAuthorityDAO.getAuthorityByAuthorityName(authorityName);
        ClientAuthorityDTO clientAuthorityDTO = verifyAuthorityExists(clientAuthorityByAuthorityName,
                "AuthorityRole", authorityName);
        return clientAuthorityDTO;
    }



    @Override
    public ClientAuthorityDTO verifyAuthorityExists(ClientAuthority authorityToBeCheckedIfExists, String dataKeyToBeChecked, String dataValue) throws AppException
    {
        if(authorityToBeCheckedIfExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Authority Not Found!");
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
            appException.setDevelopersMessageExtraInfoAsSingleReason("Authority entity with specific credentials not found at the Database");
            throw appException;
        }
        else
        {
            // create a user model object using Client(UserEntity userEntity) constructor
            return new ClientAuthorityDTO(authorityToBeCheckedIfExists);
        }
    }
}
