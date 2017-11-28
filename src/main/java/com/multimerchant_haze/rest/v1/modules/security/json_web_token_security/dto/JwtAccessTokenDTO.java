package com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.dto;

/**
 * Created by zorzis on 3/2/2017.
 */
public class JwtAccessTokenDTO
{
    private String accessToken;

    public JwtAccessTokenDTO(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getAccessToken()
    {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String toString()
    {
        return "Access Token: " + this.accessToken;
    }
}
