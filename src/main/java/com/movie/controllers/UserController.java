package com.movie.controllers;

import com.movie.application.PurchaseApplication;
import com.movie.services.DataManager;
import com.movie.services.RoleValidator;
import com.movie.tools.ActiveUser;
import com.movie.tools.DbDataEnums;
import com.movie.tools.JsonTools;
import com.movie.tools.SimpleResponse;
import com.movie.tools.errors.InvalidRoleException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.servlet.ServletRequest;
/**
 * This class holds the endpoints for user requests.
 */

@EnableAutoConfiguration
@RestController
public class UserController {
    @Autowired
    private PurchaseApplication purchaseApplication;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUserById( ServletRequest servletRequest){
        return JsonTools.convertToJson(DataManager.getUserDataManager().getUserById(ActiveUser.getActiveUserData(servletRequest).getUserId()));
    }

    @RequestMapping(value = "/users/reviews", method = RequestMethod.GET)
    public String getReviewsByUser( ServletRequest servletRequest){
        return JsonTools.convertToJson(DataManager.getReviewsDataManager().getReviewsByUserId(ActiveUser.getActiveUserData(servletRequest).getUserId()));
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String newUser( ServletRequest servletRequest){
        return new SimpleResponse().setResult(DbDataEnums.result.SUCCESS).toString();
    }

    @RequestMapping(value = "/credits", method = RequestMethod.POST)
    public String purchaseCredits( ServletRequest servletRequest){
        String amount  =  servletRequest.getParameter("amount");
        return purchaseApplication.purchaseCredits(ActiveUser.getActiveUserData(servletRequest).getUserId(),amount).toString();
    }

    @RequestMapping(value = "/credits",  method = RequestMethod.GET)
    public String getUserPurchaseHistory (ServletRequest servletRequest ) throws InvalidRoleException {
        return purchaseApplication.getUserPurchaseHistory(ActiveUser.getActiveUserData(servletRequest).getUserId()).toString();
    }






}
