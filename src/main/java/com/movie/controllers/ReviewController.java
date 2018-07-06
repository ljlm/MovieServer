package com.movie.controllers;

import com.movie.application.ReviewApplication;
import com.movie.services.DataManager;
import com.movie.tools.ActiveUser;
import com.movie.tools.JsonTools;
import com.movie.tools.SimpleResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String getReviews(){
        return JsonTools.convertToJson(reviewApplication.getReviews());
    }

    @RequestMapping(value = "/reviews/{movieID}", method = RequestMethod.GET)
    public String getReviewsByMovieID(@PathVariable Integer movieID){
        return JsonTools.convertToJson(reviewApplication.getReviewsByMovieId(movieID));
    }

    @RequestMapping(value = "/reviews/{userId}/{movieId}/{rating}", method = RequestMethod.PUT)
    public void updateMovieRating (@PathVariable Integer movieId, @PathVariable Integer userId, @PathVariable Integer rating){
//        reviewApplication.updateMovieRating(movieId,userId,rating);
    }

    @RequestMapping(value = "/reviews/{movieId}", method = RequestMethod.POST)
    public SimpleResponse createMovieReview(@PathVariable Integer movieId, ServletRequest servletRequest){
        ActiveUser activeUserData = ActiveUser.getActiveUserData(servletRequest);
        String comment  =  servletRequest.getParameter("comment");
        String rating  =  servletRequest.getParameter("rating");
        return reviewApplication.createUserReview(activeUserData.getUserId(),movieId,Integer.parseInt(rating),comment);
    }

    @RequestMapping(value = "/reviews/{movieId}", method = RequestMethod.DELETE)
    public SimpleResponse deleteMovieReview(@PathVariable Integer movieId, ServletRequest servletRequest){
        ActiveUser activeUserData = ActiveUser.getActiveUserData(servletRequest);
        return reviewApplication.deleteUserReview(activeUserData.getUserId(),movieId);
    }




}
