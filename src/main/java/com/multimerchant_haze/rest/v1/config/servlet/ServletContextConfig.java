package com.multimerchant_haze.rest.v1.config.servlet;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 *
 * Spring MVC get_authentication_principal_interface for the servlet context in the application.
 *
 * The beans of this context are only visible inside the servlet context.
 *
 * Created by zorzis on 3/1/2017.
 */
@Configuration
@EnableWebMvc
@ComponentScan({
                "com.multimerchant_haze.rest.v1.modules.users.admin.controller",
                "com.multimerchant_haze.rest.v1.modules.users.producer.controller",
                "com.multimerchant_haze.rest.v1.modules.users.client.controller",
                "com.multimerchant_haze.rest.v1.modules.products.controller",
                "com.multimerchant_haze.rest.v1.app.controllers",
                "com.multimerchant_haze.rest.v1.app.errorHandling",
                "com.multimerchant_haze.rest.v1.config.root",
                "com.multimerchant_haze.rest.v1.modules.emails.config",


})
public class ServletContextConfig extends WebMvcConfigurerAdapter
{

    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addViewController("/").setViewName("home");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");

    }



    /*@Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("*")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(false).maxAge(3600);
    }*/
}