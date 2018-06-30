package com.movie.services;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dal.DBManager;

@Service
public class RantedMoviesDataManager {

    @Autowired
    public DBManager dbManager;


    public List<Map<String,Object>> getUserLeaseHistory(int userId){
        String query = "SELECT * FROM movieserverdb.rented_movies a, movieserverdb.movies b WHERE a.user_id='"+userId + "' && a.return_date is NULL && a.movie_id = b.id ;";
        List<Map<String,Object>> userReviews = dbManager.queryForList(query);
        return userReviews;
    }
}
