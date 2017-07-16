package com.movie.controllers;

import com.movie.services.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by lionelm on 6/28/2017.
 */
@EnableAutoConfiguration
@RestController
public class UserController {

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public Map<String, Object> getUserById(@PathVariable Integer userId){
        return DataManager.getUserDataManager().getUserById(userId);
    }
}
