package com.movie.dil;

import com.movie.dal.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by lionelm on 6/15/2017.
 */



@Component
public class DataIntegrity {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final Logger log = LoggerFactory.getLogger(DataIntegrity.class);


    @Autowired
    public DataManager dbManager;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        dbManager.calculateMoviesRating ();
    }









}