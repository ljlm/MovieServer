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



}
