package com.movie.application;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.movie.services.DataManager;

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
