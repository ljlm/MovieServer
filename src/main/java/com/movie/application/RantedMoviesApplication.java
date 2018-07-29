package com.movie.application;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.movie.services.DataManager;

/**
 * this class provides the logic for the Movie rented history related operation
 * called by endpoints or another application that requires usage of movie rented history related methods
 */

@Component
public class RantedMoviesApplication {


    public List<Map<String, Object>> getRantedMovieLogByUser(int userId){
        return DataManager.getRantedMoviesDataManager().getUserLeaseHistory(userId);
    }

    public List<Map<String, Object>> getAllRantedMovieLog(){
        return DataManager.getRantedMoviesDataManager().getAllLeaseHistory();
    }

    public boolean isMovieRantedByUser(int userId, int movieID){
        return DataManager.getRantedMoviesDataManager().isMovieRantedByUser(userId,movieID);
    }



}
