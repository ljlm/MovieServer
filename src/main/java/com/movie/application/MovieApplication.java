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

    private static final Logger logger = LoggerFactory.getLogger(MovieApplication.class);

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
        logger.debug("Starting rating calculation and updating process");
        List<String> movieIds = getMovieIds ();
        try {
            for (String id : movieIds) {
                List<Integer> movieRatings = getMovieRatings(Integer.parseInt(id));
                String rating = Calculator.movieRatingCalculator(movieRatings);
                System.out.println("rating of movieid=" + id + " is [" + rating + "]");
                logger.debug("Rating of  movieid=" + id + " is [" + rating + "]");
                updateMovieRating(Integer.parseInt(id), rating,movieRatings.size());
            }
        }
        catch (Exception e){
            System.out.println("unable to update the ratings " + e);
        }
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

    public boolean decreaseMovieCopiesCounter(int movieId){
        return decreaseMovieCopiesCounter(movieId,true);
    }

    public boolean increaseMovieCopiesCounter(int movieId){
        return increaseMovieCopiesCounter(movieId, true);
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

}
