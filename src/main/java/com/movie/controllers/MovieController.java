package com.movie.controllers;

import com.movie.application.MovieApplication;
import com.movie.application.RantedMoviesApplication;
import com.movie.services.RantedMoviesDataManager;
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

import java.util.List;
import java.util.Map;



/**
 * This class holds the endpoints for movie related requests.
 */


@EnableAutoConfiguration
@RestController
public class MovieController {
    @Autowired
    private MovieApplication movieApplication;

    @Autowired
    private RantedMoviesApplication rantedMoviesApplication;

// Get all movies
    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public String getMovies(){
        return JsonTools.convertToJson(movieApplication.getMovieList());
    }

//    Get movie by id
    @RequestMapping(value = "/movies/{movieID}", method = RequestMethod.GET)
    public String getMoviesById(@PathVariable Integer movieID){
        return JsonTools.convertToJson(movieApplication.getMovieById(movieID));
    }

//    Get movie's categories
    @RequestMapping(value = "/movies/categories", method = RequestMethod.GET)
    public String getCategories(){
        return JsonTools.convertToJson(movieApplication.getCategories());
    }

//    Get movie by category
    @RequestMapping(value = "/movies/categories/{categoryId}", method = RequestMethod.GET)
    public String getMoviesByCategory(@PathVariable Integer categoryId){
        return JsonTools.convertToJson(movieApplication.getMovieByCategory(categoryId));
    }


    @RequestMapping( value = "/leaser", method = RequestMethod.GET)
    public String getMoviesLeasedByUser ( ServletRequest servletRequest){
        return JsonTools.convertToJson(rantedMoviesApplication.getRantedMovieLogByUser(ActiveUser.getActiveUserData(servletRequest).getUserId()));
    }

    @RequestMapping( value = "/leaser/{movieID}", method = RequestMethod.PUT)
    public String leaseMovie(@PathVariable Integer movieID, ServletRequest servletRequest ){
        return movieApplication.leaseMovie(movieID, ActiveUser.getActiveUserData(servletRequest).getUserId()).toString();
    }
    
    @RequestMapping( value = "/leaser/{movieID}", method = RequestMethod.DELETE)
    public String unleaseMovie(@PathVariable Integer movieID, ServletRequest servletRequest){
        return movieApplication.unleaseMovie(movieID, ActiveUser.getActiveUserData(servletRequest).getUserId()).toString();
    }

    @RequestMapping(value = "/search/{searchPatern}", method = RequestMethod.GET)
    public String searchForMovies(@PathVariable String searchPatern){
        return JsonTools.convertToJson(movieApplication.searchForMovies(searchPatern));
    }


}
