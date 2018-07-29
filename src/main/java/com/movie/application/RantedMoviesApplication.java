package com.movie.application;

import java.sql.Date;
import java.util.ArrayList;
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
        List<Map<String, Object>> sortedMovieLogs = new ArrayList<>();
        List<Map<String, Object>> unsortedMovieLogs = DataManager.getRantedMoviesDataManager().getAllLeaseHistory();
        for (Map<String, Object> unsortedMovieLog : unsortedMovieLogs){
            Date returnDate = (Date) unsortedMovieLog.get("return_date");
            if (returnDate==null){
                sortedMovieLogs.add(unsortedMovieLog);
            }
        }

        for (Map<String, Object> unsortedMovieLog : unsortedMovieLogs){
            Date returnDate = (Date) unsortedMovieLog.get("return_date");
            if (returnDate!=null){
                sortedMovieLogs.add(unsortedMovieLog);
            }
        }

        return sortedMovieLogs;
    }

    public boolean isMovieRantedByUser(int userId, int movieID){
        return DataManager.getRantedMoviesDataManager().isMovieRantedByUser(userId,movieID);
    }



}
