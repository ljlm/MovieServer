package com.movie.controllers;

import com.movie.application.MovieApplication;
import com.movie.tools.ActiveUser;
import com.movie.tools.JsonTools;

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
 * Created by lionelm on 6/28/2017.
 */

@EnableAutoConfiguration
@RestController
public class MovieController {
    @Autowired
    private MovieApplication movieApplication;


    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public String getMovies(){
        return JsonTools.convertToJson(movieApplication.getMovieList());
    }

    @RequestMapping(value = "/movies/{movieID}", method = RequestMethod.GET)
    public String getMoviesById(@PathVariable Integer movieID){
        return JsonTools.convertToJson(movieApplication.getMovieById(movieID));
    }

    @RequestMapping(value = "/movies/categories", method = RequestMethod.GET)
    public String getCategories(){
        return JsonTools.convertToJson(movieApplication.getCategories());
    }

    @RequestMapping(value = "/movies/categories/{categoryId}", method = RequestMethod.GET)
    public String getMoviesByCategory(@PathVariable Integer categoryId){
        return JsonTools.convertToJson(movieApplication.getMovieByCategory(categoryId));
    }


    //TODO
    @RequestMapping(value = "/movies/topten", method = RequestMethod.GET)
    public String getTopTenMovies(){
        return "List of top 10 movies";
    }
    //TODO
    @RequestMapping( value = "/leaser", method = RequestMethod.GET)
    public String getMoviesLeasedByUser ( ServletRequest servletRequest){
        return null;
    }

    @RequestMapping( value = "/leaser/{movieID}", method = RequestMethod.PUT)
    public boolean leaseMovie(@PathVariable Integer movieID,ServletRequest servletRequest ){
        return movieApplication.leaseMovie(movieID, ActiveUser.getActiveUserData(servletRequest).getUserId());
    }
    
    @RequestMapping( value = "/leaser/{movieID}", method = RequestMethod.DELETE)
    public boolean unleaseMovie(@PathVariable Integer movieID, ServletRequest servletRequest){
        return movieApplication.unleaseMovie(movieID, ActiveUser.getActiveUserData(servletRequest).getUserId());
    }


}
