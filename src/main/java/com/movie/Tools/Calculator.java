package com.movie.Tools;

import java.util.List;

/**
 * Created by lionelm on 2/25/2017.
 */
public class Calculator {


    public static String movieRatingCalculator ( List<Integer> movieRatings){
        float avg = 0;
        for (Integer movieRating : movieRatings){
            avg = avg +movieRating;
        }
        avg= avg / (float) movieRatings.size();
        return avg == avg ? avg + "" : "0.0";

    }

}
