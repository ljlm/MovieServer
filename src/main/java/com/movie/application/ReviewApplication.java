package com.movie.application;

import com.movie.services.DataManager;
import com.movie.services.LocksService;
import com.movie.services.ReviewsDataManager;
import com.movie.tools.SimpleResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * this class provides the logic for the Reviews related operation
 * called by endpoints or another application that requires usage of reviews related methods
 */
@Component
public class ReviewApplication {


    public List<Map<String,Object>> getReviews (){
       return DataManager.getReviewsDataManager().getReviews();
    }


    public List<Map<String, Object>> getReviewsByMovieId(int movieId){
        List<Map<String,Object>> response = new ArrayList<>();
        List<Map<String,Object>> comments = DataManager.getReviewsDataManager().getReviewsByMovieId( movieId);
        for (Map<String,Object> comment:comments ) {
            Map<String, Object> userAndComment = new HashMap<>();
            Map <String, Object> user = DataManager.getUserDataManager().getUserById((Integer) comment.get("user_id"));
            userAndComment.put("user",user);
            userAndComment.put("comment",comment);
            response.add(userAndComment);
        }
        return response;
    }


    public SimpleResponse createUserReview (int userId, int movieId, int rating , String review){
        DataManager.getReviewsDataManager().deleteMovieRating(userId,movieId);
        return DataManager.getReviewsDataManager().createMovieRating( userId,  movieId,  rating ,  review);
    }

    public SimpleResponse deleteUserReview (int userId, int movieId){
        return DataManager.getReviewsDataManager().deleteMovieRating(userId,movieId);
    }

    public SimpleResponse deleteAllUserReview (int userId){
        return DataManager.getReviewsDataManager().deleteMovieRating(userId);
    }

}
