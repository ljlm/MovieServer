package com.movie.services;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dal.DBManager;
/**
 * This service is resposable for creating the necessary queries
 * for movie lease related requests to db
 */
@Service
public class RantedMoviesDataManager {

    @Autowired
    public DBManager dbManager;

    public List<Map<String,Object>> getAllLeaseHistory(){
        String query = "SELECT * FROM movieserverdb.rented_movies r, movieserverdb.movies m, movieserverdb.users u WHERE r.user_id=u.id && r.movie_id=m.id ;";
        List<Map<String,Object>> userReviews = dbManager.queryForList(query);
        return userReviews;
    }


    public List<Map<String,Object>> getUserLeaseHistory(int userId){
        String query = "SELECT * FROM movieserverdb.rented_movies a, movieserverdb.movies b WHERE a.user_id='"+userId + "' && a.return_date is NULL && a.movie_id = b.id ;";
        List<Map<String,Object>> userReviews = dbManager.queryForList(query);
        return userReviews;
    }

    public boolean isMovieRantedByUser(int userId, int movieID){
        String query = "SELECT * FROM movieserverdb.rented_movies  WHERE user_id='"+userId + "' && movie_id='" +movieID +"' && return_date is NULL ;";
        List<Map<String,Object>> userReviews = dbManager.queryForList(query);
        return userReviews.size()!=0;
    }
}
