package com.movie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;


@EnableAutoConfiguration
@Service



public class DataManager {

    private static MovieDataManager movieDataManager;
    private static ReviewsDataManager  reviewsDataManager;
    private static UserDataManager userDataManager;
    private static RantedMoviesDataManager rantedMoviesDataManager;
    private static PricingDataManager pricingDataManager;
    private static PurchaseHistoryDataManager purchaseHistoryDataManager;

    @Autowired
    public DataManager (MovieDataManager movieDataManager,
                        ReviewsDataManager  reviewsDataManager,UserDataManager userDataManager,
                        RantedMoviesDataManager rantedMoviesDataManager ,PricingDataManager pricingDataManager,
                        PurchaseHistoryDataManager purchaseHistoryDataManager){
        DataManager.movieDataManager = movieDataManager;
        DataManager.reviewsDataManager=reviewsDataManager;
        DataManager.userDataManager=userDataManager;
        DataManager.rantedMoviesDataManager=rantedMoviesDataManager;
        DataManager.pricingDataManager=pricingDataManager;
        DataManager.purchaseHistoryDataManager=purchaseHistoryDataManager;
    }

    public static MovieDataManager getMovieDataManager(){
        return movieDataManager;
    }

    public static ReviewsDataManager getReviewsDataManager(){
        return reviewsDataManager;
    }

    public static UserDataManager getUserDataManager(){
        return userDataManager;
    }

    public static RantedMoviesDataManager getRantedMoviesDataManager(){
        return rantedMoviesDataManager;
    }

    public static PricingDataManager getPricingDataManager() {
        return pricingDataManager;
    }

    public static PurchaseHistoryDataManager getPurchaseHistoryDataManager() {
        return purchaseHistoryDataManager;
    }
}
