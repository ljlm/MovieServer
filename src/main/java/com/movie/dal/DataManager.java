package com.movie.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lionelm on 2/25/2017.
 */
@EnableAutoConfiguration
@Service

public class DataManager {
    @Autowired
    public DBManager dbManager;

    public String updateMovieRating (Integer movieId, Integer userId, Integer rating){
        dbManager.updateMovieRating( movieId,  userId,  rating);
        Map<String, Object> movie =  dbManager.getMovieById(movieId);
        Float previewRating = Float.parseFloat((String) movie.get("rating"));
        Integer raters = Integer.parseInt((String) movie.get("raters"));
        Float newRating = (previewRating*raters + rating)/(raters+1);

        return null;
    }
}
