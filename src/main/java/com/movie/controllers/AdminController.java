package com.movie.controllers;

import com.movie.services.DataPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController

public class AdminController {



    @Autowired
    public DataPopulator dataPopulator;

    @RequestMapping("/createtables")
    public void createTables(){
        dataPopulator.createTables();
    }

}