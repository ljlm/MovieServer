package com.movie.services;

import com.movie.dal.DBManager;
import com.movie.tools.DbDataEnums;
import com.movie.tools.SimpleResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lionelm on 6/28/2017.
 */
@Service
public class ReviewsDataManager {
    @Autowired
    public DBManager dbManager;


    public SimpleResponse createMovieRating (int userId, int movieId, int rating , String review){
        String insertQuery = "INSERT INTO rating (user_id ,movie_id, rating, comment,locked) VALUES (?, ?,?,?,? ) ";
        int[] types = new int[] { Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.INTEGER};
        Object[] params = new Object[] { userId, movieId ,rating, review,0};
        if (dbManager.insertQuery(insertQuery, params, types)==1){
            return new SimpleResponse().setResult(DbDataEnums.result.SUCCESS);
        }
        return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Unable to update the movie ranting and review.");

    }

    public SimpleResponse deleteMovieRating (int userId, int movieId){
        if (dbManager.updateQuery("DELETE FROM movieserverdb.rating WHERE movie_id="+ movieId + "&& user_id="+userId+";") ==1){
            return new SimpleResponse().setResult(DbDataEnums.result.SUCCESS);
        }
        return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Unable to delete review.");
    }

    public SimpleResponse deleteMovieRating (int userId){
        if (dbManager.updateQuery("DELETE FROM movieserverdb.rating WHERE user_id="+userId+";") ==1){
            return new SimpleResponse().setResult(DbDataEnums.result.SUCCESS);
        }
        return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Unable to delete review.");
    }

    public  Map<String,Object> getUserReviewsByMovieId(int userId, int movieId){
        List<Map<String,Object>> review = dbManager.queryForList("SELECT * FROM movieserverdb.rating WHERE movie_id="+ movieId + "&& user_id="+userId+";");
        return review.get(0);
    }


    public  void setMovieRating (Integer movieId, Integer userId, Integer rating){
        StringBuilder selectLinequery = new StringBuilder();
        selectLinequery.append("SELECT * FROM movieserverdb.rating WHERE user_id=").append(userId).append(" && movie_id=").append(movieId).append(";");
        if (LocksService.lineExists(selectLinequery.toString())){
            StringBuilder updateLineQuery = new StringBuilder();
            LocksService.lockLine("movieserverdb.rating", "user_id="+userId + " && movie_id=" + movieId );
            updateLineQuery.append("UPDATE movieserverdb.rating SET rating=").append(rating).append(" WHERE user_id=").append(userId).append(" && movie_id=").append(movieId).append(" && locked=1;");
            dbManager.updateQuery(updateLineQuery.toString());
            LocksService.unlockLine("movieserverdb.rating", "user_id="+userId + " && movie_id=" + movieId );

        }
        else{
            String insertQuery = "INSERT INTO rating (user_id ,movie_id, rating, comment,locked) VALUES (?, ?,?,?,? ) ";
            int[] types = new int[] { Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.INTEGER};
            Object[] params = new Object[] { userId, movieId ,rating, "",0};
            dbManager.insertQuery(insertQuery, params, types);
        }
    }

    public  List<Map<String,Object>> getReviews(){
        List<Map<String,Object>> reviews = dbManager.queryForList("SELECT * FROM movieserverdb.rating ;");
        return reviews;
    }

    public  List<Map<String,Object>> getReviewsByMovieId(int movieId){
        List<Map<String,Object>> reviews = dbManager.queryForList("SELECT * FROM movieserverdb.rating WHERE movie_id="+ movieId + ";");
        return reviews;
    }


    public List<Map<String,Object>> getReviewsByUserId(int userId) {
        List<Map<String,Object>> reviews = dbManager.queryForList("SELECT * FROM movieserverdb.rating WHERE user_id="+ userId + ";");
        return reviews;
    }
}
