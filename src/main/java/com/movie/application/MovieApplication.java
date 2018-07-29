package com.movie.application;

import com.movie.services.DataManager;
import com.movie.services.DataPopulator;
import com.movie.services.LocksService;
import com.movie.tools.Calculator;
import com.movie.tools.DBRowLockerData;
import com.movie.tools.DBRowUpdateData;
import com.movie.tools.DbDataEnums;
import com.movie.tools.SimpleResponse;
import com.movie.tools.errors.AlreadyExistentMovieException;
import com.mysql.jdbc.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by lionelm on 6/28/2017.
 */

@Component
public class MovieApplication {
    @Autowired
    private ReviewApplication reviewApplication;

    @Autowired
    private RantedMoviesApplication rantedMoviesApplication;

    @Autowired
    private UserApplication userApplication;


    public List<Map<String,Object>> getMovieList (){
        return DataManager.getMovieDataService().getMovieList();
    }

    public  Map<String, Object> getMovieById (int id){
        return DataManager.getMovieDataService().getMovieById(id);
    }

    public  List<Map<String, Object>> getMovieByCategory (int category){
      return DataManager.getMovieDataService().getMovieByCategory(category);
    }

    public  List<String> getCategories (){
        return DataManager.getMovieDataService().getCategories();
    }

    public List<String> getMovieIds (){
        List<Map<String,Object>> movies = getMovieList ();
        List<String> movieIds = new ArrayList<>();
        for ( Map <String,Object> movie : movies  ){
            movieIds.add ("" + movie.get("id"));
        }
        return movieIds;
    }



    public void calculateMoviesRating(){
        System.out.println("Starting rating calculation and updating process");
        List<String> movieIds = getMovieIds ();
        try {
            for (String id : movieIds) {
                List<Integer> movieRatings = getMovieRatings(Integer.parseInt(id));
                String rating = Calculator.movieRatingCalculator(movieRatings);
                System.out.println("rating of movieid=" + id + " is [" + rating + "]");
                System.out.println("Rating of  movieid=" + id + " is [" + rating + "]");
                updateMovieRating(Integer.parseInt(id), rating,movieRatings.size());
            }
        }
        catch (Exception e){
            System.out.println("unable to update the ratings " + e);
        }
        System.out.println("Calculation and updating process ended successfully");
    }

    public List<Integer> getMovieRatings(Integer movieID){
        List<Map<String, Object>> movieComments =  reviewApplication.getReviewsByMovieId(movieID );
        List<Integer> movieRatings = new ArrayList<>();
        for (Map<String, Object> comment : movieComments){
            Integer rating = (Integer) ((Map) comment.get("comment")).get("rating");
            movieRatings.add(rating);
        }
        return movieRatings;
    }

    public void updateMovieRating (Integer movieID, String rating, int raters) throws Exception {
        DataManager.getMovieDataService().updateMovieRating(movieID,rating,raters);

    }



    public boolean decreaseMovieCopiesCounter(int movieId, boolean needToLockLine){
        String whereStatement = "id=" + movieId +" && available > 0";
        String setStatement = "available = available - 1";
        DBRowUpdateData rowBlockerData = new DBRowUpdateData("movieserverdb.movies",whereStatement,setStatement);
        if (needToLockLine){
            return LocksService.setRow(rowBlockerData);
        }
        return LocksService.setLockedRow(rowBlockerData);
    }



    public boolean increaseMovieCopiesCounter(int movieId, boolean needToLockLine){
        String whereStatement = "id=" + movieId ;
        String setStatement = "available = available + 1";
        DBRowUpdateData rowBlockerData = new DBRowUpdateData("movieserverdb.movies",whereStatement,setStatement);
        if (needToLockLine){
            return LocksService.setRow(rowBlockerData);
        }
        return LocksService.setLockedRow(rowBlockerData);
    }


    public SimpleResponse leaseMovie (int movieId, int userId){
        DBRowLockerData movieRow = new DBRowLockerData("movieserverdb.movies" ,"id="+movieId);
        DBRowLockerData userRow = new DBRowLockerData("movieserverdb.users" ,"id="+userId);



        List<DBRowLockerData> rowsToLock = Arrays.asList(movieRow,userRow);
        if (!LocksService.lockMultipleRows(rowsToLock)){
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Unable to lock database rows");
        }
        if (!decreaseMovieCopiesCounter(movieId,false)){
            LocksService.unlockMultipleRows(rowsToLock);
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Unsufficient movies");
        }
        if ( rantedMoviesApplication.isMovieRantedByUser(userId,movieId)){
            increaseMovieCopiesCounter(movieId,false);
            LocksService.unlockMultipleRows(rowsToLock);
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("User already have an active lease for this movie");
        }

        if (  !userApplication.decreaseUserCredits(userId,false)){
            increaseMovieCopiesCounter( movieId, false);
            LocksService.unlockMultipleRows(rowsToLock);
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("No credits left to lease movies");
        }
        userApplication.createRantedMovieLog(userId,movieId);
        LocksService.unlockMultipleRows(rowsToLock);
        return new SimpleResponse().setResult(DbDataEnums.result.SUCCESS);


    }

    public  SimpleResponse unleaseMovie (int movieId, int userId){
        if (!rantedMoviesApplication.isMovieRantedByUser(userId, movieId)){
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("User has no active lease for this movie");
        }
        if (increaseMovieCopiesCounter(movieId,true)){
            userApplication.updateRantedMovieLog(userId,movieId);
            return new SimpleResponse().setResult(DbDataEnums.result.SUCCESS);
        }
        return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Unable to update the copies quantity of this movie");
    }


    public SimpleResponse addMovie(String movieName, String picLink, String yearStr, String categoryStr, String info, String availableStr) throws AlreadyExistentMovieException {

        int year;
        try{
            year = Integer.parseInt( yearStr);
        }catch (Exception e){
            throw new InvalidParameterException("Parameter year=" + yearStr + " is invalid");
        }


        int category;
        try {
            category = Integer.parseInt(categoryStr);
        }catch (Exception e){
            throw new InvalidParameterException("Parameter category=" + categoryStr + " is invalid");
        }

        int available;
        try{
            available = Integer.parseInt(availableStr);
        }catch (Exception e){
            throw new InvalidParameterException("Parameter available=" + availableStr + " is invalid");
        }

        return DataManager.getMovieDataService().addMovie(movieName,picLink,year,category,info,available);
    }

    public SimpleResponse editMovie(String movieIdStr, String movieName, String picLink, String yearStr, String categoryStr, String info, String availableStr) {
        int movieId;
        try{
            movieId = Integer.parseInt( movieIdStr);
        }catch (Exception e){
            return new SimpleResponse().setCause("Parameter year=" + yearStr + " is invalid").setResult(DbDataEnums.result.FAILURE);
        }

        int year =-1;
        if (!StringUtils.isEmptyOrWhitespaceOnly(yearStr)) {
            try {
                year = Integer.parseInt(yearStr);
            } catch (Exception e) {
                return new SimpleResponse().setCause("Parameter year=" + yearStr + " is invalid").setResult(DbDataEnums.result.FAILURE);
            }
        }


        int category=-1;
        if (!StringUtils.isEmptyOrWhitespaceOnly(categoryStr)) {
            try {
                category = Integer.parseInt(categoryStr);
            } catch (Exception e) {
                return new SimpleResponse().setCause("Parameter category=" + categoryStr + " is invalid").setResult(DbDataEnums.result.FAILURE);
            }
        }

        int available=-1;
        if (!StringUtils.isEmptyOrWhitespaceOnly(availableStr)) {
            try {
                available = Integer.parseInt(availableStr);
            } catch (Exception e) {
                return new SimpleResponse().setCause("Parameter available=" + availableStr + " is invalid").setResult(DbDataEnums.result.FAILURE);
            }
        }
        return DataManager.getMovieDataService().editMovie(movieId ,movieName,picLink,year,category,info,available);
    }

    public List<Map<String,Object>> searchForMovies(String searchPatern) {
        List<String> keyWords = Arrays.asList(searchPatern.split(","));
        return DataManager.getMovieDataService().searchForMovies(keyWords);
    }
}
