package com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.utils.JwtUserFactoryUtil;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by zorzis on 5/24/2017.
 */
@Service
public class JwtClientDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private ClientDAO clientDAO;

    // We load user based on email that is unique
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException
    {

        Client userEntityFromDatabase = clientDAO.getClientByEmailFetchingProfileFetchingAuthoritiesFetchingAddresses(userEmail);

        this.checkIfUserObjectIsNull(userEntityFromDatabase, userEmail);

        return JwtUserFactoryUtil.create(userEntityFromDatabase);
    }

    private void checkIfUserObjectIsNull(Object userEntityFromDatabase, String userEmail) throws AppException
    {
        if(userEntityFromDatabase == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Authentication Error parsing Client Email from Token!");
            sb.append(" ");
            sb.append("No Client found with email: [");
            sb.append(userEmail);
            sb.append("].");
            sb.append("Provided data not sufficient for authentication");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.FORBIDDEN);
            appException.setAppErrorCode(HttpStatus.FORBIDDEN.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Client Email provided by JWT token seems not to belong to our system or it has been deleted");
            throw appException;
        }
    }


}
