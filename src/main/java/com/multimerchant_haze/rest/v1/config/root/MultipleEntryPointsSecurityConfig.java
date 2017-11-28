package com.multimerchant_haze.rest.v1.config.root;


import com.multimerchant_haze.rest.v1.app.errorHandling.CustomAccessDeniedHandler;
import com.multimerchant_haze.rest.v1.app.errorHandling.FiltersExceptionHandler;
import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.config.JwtAuthenticationEntryPoint;
import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.config.JwtAuthenticationTokenFilterForClients;
import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.config.JwtAuthenticationTokenFilterForProducers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * The Spring Security configuration for the Application
 *
 * HELP for MultipleEntryPoints found here: http://www.baeldung.com/spring-security-multiple-entry-points
 *
 * Created by zorzis on 3/1/2017.
 */


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MultipleEntryPointsSecurityConfig
{



    @Configuration
    @Order(1)
    public static class ClientsConfigurationAdapter extends WebSecurityConfigurerAdapter
    {
        @Autowired
        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPointForClients;

        @Bean
        public JwtAuthenticationTokenFilterForClients jwtAuthenticationTokenFilterBeanForClients() throws Exception
        {
            return new JwtAuthenticationTokenFilterForClients();
        }

        @Bean
        public FiltersExceptionHandler filtersExceptionHandlerBeanForClients()
        {
            return new FiltersExceptionHandler();
        }

        @Bean
        public CustomAccessDeniedHandler customAccessDeniedHandlerForClients()
        {
            return new CustomAccessDeniedHandler();
        }

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception
        {

            httpSecurity
                    // we don't need CSRF because our token is invulnerable
                    .csrf().disable()

                    // Call our errorHandler if authentication/authorisation fails
                    .exceptionHandling()
                    .accessDeniedHandler(customAccessDeniedHandlerForClients())
                    .authenticationEntryPoint(jwtAuthenticationEntryPointForClients).and()

                    // don't create session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                    .antMatcher("/client/**")
                    .authorizeRequests()


                    // Authorization/Registration
                    .antMatchers("/client/auth/**").permitAll()
                    .antMatchers("/client/registration/**").permitAll()

                    // Profile
                    .antMatchers("/client/get_profile/**").permitAll()
                    .antMatchers("/client/update_profile/**").permitAll()

                    // Addresses
                    .antMatchers("/client/get_addresses/**").permitAll()
                    .antMatchers("/client/add_address/**").permitAll()
                    .antMatchers("/client/delete_address/**").permitAll()


                    // Just Simple Guests
                    .antMatchers("/client/get_producer/**").permitAll()
                    .antMatchers("/client/get_producers/**").permitAll()


                    // Products
                    .antMatchers("/client/get_products/**").permitAll()

                    // Orders
                    .antMatchers("/client/get_orders/**").permitAll()
                    .antMatchers("/client/get_single_order/**").permitAll()

                    //### Paypal Checkout for Order payment system
                    .antMatchers("/client/order-checkout/paypal/**").permitAll()
                    .antMatchers("/client/payment-checkout/paypal/create/{shoppingCartID}/**").permitAll()
                    .antMatchers("/client/payment-checkout/paypal/execute/**").permitAll()

                    //### OnDeivery Checkout for Order payment system
                    .antMatchers("/client/order-checkout/ondelivery/**").permitAll()


                    // THEY ARE HERE JUST FOR NOW- THEY GONNA BE ADMIN CONTROLED
                    .antMatchers("/client/get_payment_methods/**").permitAll()
                    .antMatchers("/client/create_new_payment_method/**").permitAll()
                    .antMatchers("/client/delete_payment_method/**").permitAll()


                    .antMatchers("/client/get_products_categories/**").permitAll()

                    .antMatchers("/client/search_producers/**").permitAll()

                    .anyRequest().authenticated();



            // CORS filtering
            httpSecurity
                    .addFilterAfter(new CORSFilter(), ChannelProcessingFilter.class);

            // Custom JWT based security filter
            httpSecurity
                    .addFilterBefore(jwtAuthenticationTokenFilterBeanForClients(), UsernamePasswordAuthenticationFilter.class);

            // Added custom filter error handling in json format
            httpSecurity
                    .addFilterAfter(filtersExceptionHandlerBeanForClients(), ChannelProcessingFilter.class);

            // disable page caching
            httpSecurity.headers().cacheControl();

        }
    }


    @Configuration
    @Order(2)
    public static class ProducersConfigurationAdapter extends WebSecurityConfigurerAdapter
    {
        @Autowired
        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPointForProducers;

        @Bean
        public JwtAuthenticationTokenFilterForProducers jwtAuthenticationTokenFilterBeanForProducers() throws Exception
        {
            return new JwtAuthenticationTokenFilterForProducers();
        }

        @Bean
        public FiltersExceptionHandler filtersExceptionHandlerBeanForProducers()
        {
            return new FiltersExceptionHandler();
        }

        @Bean
        public CustomAccessDeniedHandler customAccessDeniedHandlerForProducers()
        {
            return new CustomAccessDeniedHandler();
        }


        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception
        {

            httpSecurity
                    // we don't need CSRF because our token is invulnerable
                    .csrf().disable()

                    // Call our errorHandler if authentication/authorisation fails
                    .exceptionHandling()
                    .accessDeniedHandler(customAccessDeniedHandlerForProducers())
                    .authenticationEntryPoint(jwtAuthenticationEntryPointForProducers).and()

                    // don't create session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                    .antMatcher("/producer/**")
                    .authorizeRequests()

                    //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                    // Authorization/Registration
                    .antMatchers("/producer/auth/**").permitAll()
                    .antMatchers("/producer/registration/**").permitAll()

                    // Profile
                    .antMatchers("/producer/get_profile/**").permitAll()
                    .antMatchers("/producer/update_profile/**").permitAll()

                    // Addresses
                    .antMatchers("/producer/get_addresses/**").permitAll()
                    .antMatchers("/producer/add_address/**").permitAll()
                    .antMatchers("/producer/delete_address/**").permitAll()
                    .antMatchers("/producer/update_address/**").permitAll()

                    // Spirits
                    .antMatchers("/producer/get_spirits/**").permitAll()
                    .antMatchers("/producer/add_spirit/**").permitAll()
                    .antMatchers("/producer/get_producer_spirit/**").permitAll()
                    .antMatchers("/producer/update_spirit/**").permitAll()
                    .antMatchers("/producer/delete_spirit/**").permitAll()

                    // Orders
                    .antMatchers("/producer/get_orders/**").permitAll()

                    // PaymentMethods
                    .antMatchers("/producer/get_original_payment_methods/**").permitAll()
                    .antMatchers("/producer/get_payment_methods/**").permitAll()
                    .antMatchers("/producer/add_payment_method/**").permitAll()

                    .anyRequest().authenticated();


            // CORS filtering
            httpSecurity
                    .addFilterAfter(new CORSFilter(), ChannelProcessingFilter.class);

            // Custom JWT based security filter
            httpSecurity
                    .addFilterBefore(jwtAuthenticationTokenFilterBeanForProducers(), UsernamePasswordAuthenticationFilter.class);

            // Added custom filter error handling in json format
            httpSecurity
                    .addFilterAfter(filtersExceptionHandlerBeanForProducers(), ChannelProcessingFilter.class);

            // disable page caching
            httpSecurity.headers().cacheControl();
        }

    }


}

