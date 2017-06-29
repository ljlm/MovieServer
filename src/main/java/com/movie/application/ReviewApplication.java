package com.movie.application;

import com.movie.services.DataManager;
import com.movie.services.LocksService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lionelm on 6/28/2017.
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


}
