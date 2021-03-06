package com.movie.services;

import com.movie.application.MovieApplication;
import com.movie.tools.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * This service automaticly starts the calculation of movies rating process.
 */




@Component
public class DataIntegrity {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(DataIntegrity.class);


    @Autowired
    public MovieApplication movieApplication;

    @Autowired
    private DataPopulator dp;

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
        try {
            movieApplication.calculateMoviesRating();
        }catch(BadSqlGrammarException e){
            dp.createTables();
        }
        }











}
