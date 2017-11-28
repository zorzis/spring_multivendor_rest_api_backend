package com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.config;

import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.model.JwtUser;
import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.service.JwtClientDetailsServiceImpl;
import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zorzis on 5/24/2017.
 */
public class JwtAuthenticationTokenFilterForClients extends OncePerRequestFilter
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilterForProducers.class);
    private String className =  this.getClass().getSimpleName();


    @Autowired
    private JwtClientDetailsServiceImpl jwtClientDetailsService;

    @Autowired
    private JwtAuthenticationFilterHelper jwtAuthenticationFilterHelper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        LOGGER.debug("Hello from " + className +"::doFilterInternal ---> Starting Custom Security Filtering For CLIENT...");



        if (!jwtAuthenticationFilterHelper.isAuthorizationHeaderNull(request))
        {

            String authToken = jwtAuthenticationFilterHelper.parseTokenFromAuthorizationHeader(request);

            // Just check the existence of a Token at Request Header
            if(authToken != null)
            {
                // if token is expired throw exception
                jwtAuthenticationFilterHelper.isTokenExpired(authToken);

                // Extract the userEmail from Token
                String userEmail = jwtTokenUtil.getUserEmailFromToken(authToken);
                LOGGER.info("Checking authentication for CLIENT: [" + userEmail + "]");

                // In case of Producer Email Existence proceed to validation and creation of Producer Object as Authenticated Principal
                if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)
                {
                    // Get the JwtUser Object from Token Claims
                    //UserDetails userDetails = jwtTokenUtil.getJwtUserFromToken(authToken);

                    // Validate the token using Client Validation
                    if (jwtTokenUtil.validateTokenForClient(authToken))
                    {
                        // Get the JwtUser Object from database based on email retrieved from Token
                        UserDetails userDetails = jwtClientDetailsService.loadUserByUsername(userEmail);

                        // set the authentication principal
                        this.setAuthentication(request, userDetails);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);

    }

    private void setAuthentication(HttpServletRequest request, UserDetails userDetails)
    {
        // In case of successful validation of token use the extracted JwtUser Object as Authenticated Producer
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        LOGGER.info("Authenticated Client " + ((JwtUser)userDetails).getEmail()+ ", setting security context");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
