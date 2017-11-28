package com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.utils;

import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.model.JwtUser;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientAuthority;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by zorzis on 3/9/2017.
 */
public final class JwtUserFactoryUtil
{

    public static JwtUser create(Producer producer)
    {
        return new JwtUser(
                producer.getId(),
                producer.getProducerProfile().getFirstName(),
                producer.getProducerProfile().getLastName(),
                producer.getEmail(),
                producer.getProducerProfile().getProducerID(),
                producer.getPassword(),
                mapToProducerGrantedAuthorities(producer.getProducerAuthoritiesEntities()),
                producer.getIsEnabled(),
                producer.getLastPasswordResetDate()
        );
    }

    public static JwtUser create(Client client)
    {
        return new JwtUser(
                client.getId(),
                client.getClientProfile().getFirstName(),
                client.getClientProfile().getLastName(),
                client.getEmail(),
                client.getClientProfile().getClientID(),
                client.getPassword(),
                mapToClientGrantedAuthorities(client.getClientAuthoritiesEntities()),
                client.getIsEnabled(),
                client.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToProducerGrantedAuthorities(Set<ProducerAuthority> authorities)
    {
        return authorities.stream()
                .map(new Function<ProducerAuthority, SimpleGrantedAuthority>()
                {
                    @Override
                    public SimpleGrantedAuthority apply(ProducerAuthority userAuthority)
                    {
                        return new SimpleGrantedAuthority(userAuthority.getRole());
                    }
                })
                .collect(Collectors.toList());
    }

    private static List<GrantedAuthority> mapToClientGrantedAuthorities(Set<ClientAuthority> authorities)
    {
        return authorities.stream()
                .map(new Function<ClientAuthority, SimpleGrantedAuthority>()
                {
                    @Override
                    public SimpleGrantedAuthority apply(ClientAuthority userAuthority)
                    {
                        return new SimpleGrantedAuthority(userAuthority.getRole());
                    }
                })
                .collect(Collectors.toList());
    }
}