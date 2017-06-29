package com.movie.controllers;

import com.movie.application.ReviewApplication;
import com.movie.services.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by lionelm on 6/28/2017.
 */

@EnableAutoConfiguration
@RestController
public class ReviewController {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private ReviewApplication reviewApplication;


    @RequestMapping(value = "/reviews", method = RequestMethod.GET)
    public List<Map<String,Object>> getReviews(){
        return reviewApplication.getReviews();
    }

    @RequestMapping(value = "/reviews/{movieID}", method = RequestMethod.GET)
    public List getReviewsByMovieID(@PathVariable Integer movieID){
        return reviewApplication.getReviewsByMovieId(movieID);
    }

    @RequestMapping(value = "/ratings/{userId}/{movieId}/{rating}", method = RequestMethod.PUT)
    public void updateMovieRating (@PathVariable Integer movieId, @PathVariable Integer userId, @PathVariable Integer rating){
//        reviewApplication.updateMovieRating(movieId,userId,rating);
    }


}
