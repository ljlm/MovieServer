package com.movie.services;

import com.movie.dal.DBManager;
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


    public void createMovieRating (int userId, int movieId, int rating , String review){
        String insertQuery = "INSERT INTO rating (user_id ,movie_id, rating, comment,locked) VALUES (?, ?,?,?,? ) ";
        int[] types = new int[] { Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.INTEGER};
        Object[] params = new Object[] { userId, movieId ,rating, review,0};
        dbManager.insertQuery(insertQuery, params, types);
    }

    public void deleteMovieRating (int userId, int movieId){
        dbManager.updateQuery("DELETE FROM movieserverdb.rating WHERE movie_id="+ movieId + "&& user_id="+userId+";");
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


}
