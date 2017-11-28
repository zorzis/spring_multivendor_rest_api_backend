package com.multimerchant_haze.rest.v1.config.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by zorzis on 3/1/2017
 *
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.multimerchant_haze.rest.v1.config.root" })
@PropertySource(value = {"classpath:application.properties"})
public class PersistenceConfig
{
    @Autowired
    private Environment environment;

    @Bean(name = "datasource")
    public DriverManagerDataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));

        return dataSource;
    }




    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DriverManagerDataSource dataSource)
    {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan(new String[]
                {
                        "com.multimerchant_haze.rest.v1.modules.users.producer.model",
                        "com.multimerchant_haze.rest.v1.modules.users.client.model",
                        "com.multimerchant_haze.rest.v1.modules.products.model",
                        "com.multimerchant_haze.rest.v1.modules.orders.model",
                        "com.multimerchant_haze.rest.v1.modules.payments.model",
                });
        entityManagerFactoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());


        Map<String, Object> jpaProperties = new HashMap<String, Object>();
        jpaProperties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        jpaProperties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        jpaProperties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        //jpaProperties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.use_sql_comments", environment.getRequiredProperty("hibernate.use_sql_comments"));
        jpaProperties.put("hibernate.characterEncoding",environment.getRequiredProperty("hibernate.characterEncoding"));
        jpaProperties.put("hibernate.charSet", environment.getRequiredProperty("hibernate.charSet"));
        jpaProperties.put("hibernate.useUnicode", environment.getRequiredProperty("hibernate.useUnicode"));
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);

        return entityManagerFactoryBean;
    }


}
