package com.movie.services;

import com.movie.dal.DBManager;
import com.movie.tools.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.*;

import javax.annotation.PostConstruct;

/**
 * Created by lionelm on 2/25/2017.
 */
@EnableAutoConfiguration
@Service



public class DataManager {

    private static MovieDataService movieDataService;
    private static ReviewsDataManager  reviewsDataManager;
    private static UserDataManager userDataManager;
    private static RantedMoviesDataManager rantedMoviesDataManager;

    @Autowired
    public DataManager (MovieDataService movieDataService,
                        ReviewsDataManager  reviewsDataManager,UserDataManager userDataManager,
                        RantedMoviesDataManager rantedMoviesDataManager ){
        DataManager.movieDataService=movieDataService;
        DataManager.reviewsDataManager=reviewsDataManager;
        DataManager.userDataManager=userDataManager;
        DataManager.rantedMoviesDataManager=rantedMoviesDataManager;
    }

    public static MovieDataService getMovieDataService(){
        return movieDataService;
    }

    public static ReviewsDataManager getReviewsDataManager(){
        return reviewsDataManager;
    }

    public static UserDataManager getUserDataManager(){
        return userDataManager;
    }

    public static RantedMoviesDataManager getRantedMoviesDataManager(){
        return rantedMoviesDataManager;
    }
}
