package com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.utils.JwtUserFactoryUtil;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerDAO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by zorzis on 3/9/2017.
 */
@Service
public class JwtProducerDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private ProducerDAO producerDAO;
    private static final String USER_TYPE_PRODUCER = "PRODUCER";
    private String userTypeToBeAuthenticated;


    public void setUserType(String userType)
    {
        this.userTypeToBeAuthenticated = userType;
    }

    // We load user based on email that is unique
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException
    {
        Producer userEntityFromDatabase = producerDAO.getProducerByEmailFetchingProfileFetchingAuthoritiesFetchingAddress(userEmail);

        this.checkIfUserObjectIsNull(userEntityFromDatabase, userEmail);

        return JwtUserFactoryUtil.create(userEntityFromDatabase);
    }

    private void checkIfUserObjectIsNull(Object userEntityFromDatabase, String userEmail) throws AppException
    {
        if(userEntityFromDatabase == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Authentication Error Parsing Producer Email From Token!");
            sb.append(" ");
            sb.append("No Producer found with email: [");
            sb.append(userEmail);
            sb.append("].");
            sb.append("Provided data not sufficient for authentication");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.FORBIDDEN);
            appException.setAppErrorCode(HttpStatus.FORBIDDEN.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Producer Email provided by JWT token seems not to belong to our system or it has been deleted");
            throw appException;
        }
    }
}
