package com.multimerchant_haze.rest.v1.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by zorzis on 3/7/2017.
 */
@Controller
public class HomeController
{

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homeController()
    {

        return "home";
    }


}
