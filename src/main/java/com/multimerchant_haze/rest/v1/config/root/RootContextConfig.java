package com.multimerchant_haze.rest.v1.config.root;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;

/**
 *
 * The root context configuration of the application - the beans in this context will be globally visible
 * in all servlet contexts.
 *
 * Created by zorzis on 3/1/2017.
 */
@Configuration
@ComponentScan({
                "com.multimerchant_haze.rest.v1.modules.emails.service",

                "com.multimerchant_haze.rest.v1.modules.users.client.service",
                "com.multimerchant_haze.rest.v1.modules.users.client.dao",
                "com.multimerchant_haze.rest.v1.modules.users.producer.service",
                "com.multimerchant_haze.rest.v1.modules.users.producer.dao",

                "com.multimerchant_haze.rest.v1.modules.products.service",
                "com.multimerchant_haze.rest.v1.modules.products.dao",

                "com.multimerchant_haze.rest.v1.modules.orders.service",
                "com.multimerchant_haze.rest.v1.modules.orders.dao",

                "com.multimerchant_haze.rest.v1.modules.payments.service",
                "com.multimerchant_haze.rest.v1.modules.payments.dao",

                "com.multimerchant_haze.rest.v1.modules.security"})
public class RootContextConfig
{

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory,
                                                         DriverManagerDataSource dataSource)
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

}
