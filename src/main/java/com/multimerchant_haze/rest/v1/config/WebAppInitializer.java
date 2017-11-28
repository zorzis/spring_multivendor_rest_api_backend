package com.multimerchant_haze.rest.v1.config;

import com.multimerchant_haze.rest.v1.config.root.MultipleEntryPointsSecurityConfig;
import com.multimerchant_haze.rest.v1.config.root.RootContextConfig;
import com.multimerchant_haze.rest.v1.config.root.PersistenceConfig;
import com.multimerchant_haze.rest.v1.config.servlet.ServletContextConfig;
import com.multimerchant_haze.rest.v1.modules.emails.config.EmailSenderConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


/**
 *
 * Replacement for most of the content of web.xml, sets up the root and the servlet context get_authentication_principal_interface.
 *
 * Created by zorzis on 3/1/2017.
 *
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class<?>[]
                {
                        RootContextConfig.class,
                        PersistenceConfig.class,
                        MultipleEntryPointsSecurityConfig.class,
                        EmailSenderConfig.class,
                };

    }

    @Override
    protected Class<?>[] getServletConfigClasses() {return new Class<?>[]{ServletContextConfig.class};}

    @Override
    protected String[] getServletMappings() {return new String[]{"/"};}


    // Just overiding default spring 404 no handler found exception
    // HELP FOUND HERE: http://stackoverflow.com/questions/23574869/404-error-redirect-in-spring-with-java-config/31436535#31436535
    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext)
    {
        final DispatcherServlet dispatcherServlet = (DispatcherServlet)super.createDispatcherServlet(servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }
    /*

        @Override
        protected void customizeRegistration(ServletRegistration.Dynamic registration)
        {
            registration.setInitParameter("throwExceptionIfNoHandlerFo‌​und", "true");
        }
    */




}