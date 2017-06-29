package com.movie.controllers;

import com.movie.application.MovieApplication;
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
public class MovieController {
    @Autowired
    private MovieApplication movieApplication;


    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public List getMovies(){
        return movieApplication.getMovieList();
    }

    @RequestMapping(value = "/movies/{movieID}", method = RequestMethod.GET)
    public Map<String, Object> getMoviesById(@PathVariable Integer movieID){
        return movieApplication.getMovieById(movieID);
    }

    @RequestMapping(value = "/movies/categories", method = RequestMethod.GET)
    public List getCategories(){
        return movieApplication.getCategories();
    }

    @RequestMapping(value = "/movies/categories/{categoryId}", method = RequestMethod.GET)
    public List getMoviesByCategory(@PathVariable Integer categoryId){
        return movieApplication.getMovieByCategory(categoryId);
    }


    //TODO
    @RequestMapping(value = "/movies/topten", method = RequestMethod.GET)
    public String getTopTenMovies(){
        return "List of top 10 movies";
    }
    //TODO
    @RequestMapping( value = "/movies/{movieID}/{userID}", method = RequestMethod.GET)
    public List getMoviesLeasedByUser (Integer movieId, Integer userId){
        return null;
    }
    //TODO
    @RequestMapping( value = "/movies/{movieID}/{userID}", method = RequestMethod.PUT)
    public boolean leaseMovie(Integer movieId, Integer userId){
        return false;
    }
    //TODO
    @RequestMapping( value = "/movies/{movieID}/{userID}", method = RequestMethod.DELETE)
    public boolean unleaseMovie(Integer movieId, Integer userId){
        return false;
    }


}
