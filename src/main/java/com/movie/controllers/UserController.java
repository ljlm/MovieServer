package com.movie.controllers;

import com.movie.services.DataManager;
import com.movie.tools.ActiveUser;
import com.movie.tools.JsonTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.servlet.ServletRequest;

/**
 * Created by lionelm on 6/28/2017.
 */
@EnableAutoConfiguration
@RestController
public class UserController {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUserById( ServletRequest servletRequest){
        return JsonTools.convertToJson(DataManager.getUserDataManager().getUserById(ActiveUser.getActiveUserData(servletRequest).getUserId()));
    }

    @RequestMapping(value = "/users/reviews", method = RequestMethod.GET)
    public String getReviewsByUser( ServletRequest servletRequest){
        return JsonTools.convertToJson(DataManager.getReviewsDataManager().getReviewsByUserId(ActiveUser.getActiveUserData(servletRequest).getUserId()));
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public boolean newUser( ServletRequest servletRequest){
        return true;
    }






}
