package com.movie.controllers;

import com.movie.dal.DataManager;
import com.movie.filters.HttpResettableServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
@RestController

public class Controller {

    @Autowired
    public DataManager dataManager;


    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public List getMovies(ServletRequest servletRequest){
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        System.out.println(session.getAttributeNames().toString());
        return dataManager.getMovieList();
    }

    @RequestMapping(value = "/movies/{movieID}", method = RequestMethod.GET)
    public Map<String, Object> getMoviesById(@PathVariable Integer movieID){
        return dataManager.getMovieById(movieID);
    }



    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public Map<String, Object> getUserById(@PathVariable Integer userId){
        return dataManager.getUserById(userId);
    }


    @RequestMapping(value = "/comments/{movieID}", method = RequestMethod.GET)
    public List getMoviesComments(@PathVariable Integer movieID){
        return dataManager.getMovieComments(movieID);
    }

//    TODO FIX
    @RequestMapping(value = "/movies/{movieID}/{rating}", method = RequestMethod.GET)
    public String AddMovieRating(@PathVariable Integer movieID,@PathVariable Integer rating){
        return "User rated movie "+movieID+" as "+rating;
    }

    //    DBManager.getMovieListByCategory
    @RequestMapping(value = "/movies/categories/{categoryId}", method = RequestMethod.GET)
    public List getMoviesByCategory(@PathVariable Integer categoryId){
        return dataManager.getMovieByCategory(categoryId);
    }
//    getTopTenMovies() return array of array list movies {Id of movie,name,png url,year,rating,}

    @RequestMapping(value = "/movies/topten", method = RequestMethod.GET)
    public String getTopTenMovies(){
        return "List of top 10 movies";
    }


    @RequestMapping(value = "/ratings/{userId}/{movieId}/{rating}", method = RequestMethod.PUT)
    public String updateMovieRating (@PathVariable Integer movieId, @PathVariable Integer userId, @PathVariable Integer rating){
//        dbManager.getMovieRatingByUser(movieId, userId);
//        dataManager.unrateMovie(movieId,userId);
//        return dbManager.updateMovieRating( movieId,  userId,  rating)+"";
//        dataManager.rateMovie(movieId, userId);

        return "SOMETHING";
    }

    @RequestMapping( method = RequestMethod.GET)
    public void startSession(ServletRequest servletRequest, ServletResponse servletResponse){
        HttpResettableServletRequest wrappedRequest = new HttpResettableServletRequest((HttpServletRequest) servletRequest);
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        session.getAttribute("authenticated");
        session.getAttribute("username");

    }
    
    @RequestMapping( value = "/newuser",method = RequestMethod.PUT)
    @ResponseBody
    public Integer insertUser(ServletRequest servletRequest, ServletResponse servletResponse){
    	
        HttpResettableServletRequest wrappedRequest = new HttpResettableServletRequest((HttpServletRequest) servletRequest);
        
       String userName= wrappedRequest.getParameter("userName");
       String pass= wrappedRequest.getParameter("pass");
       String fName = wrappedRequest.getParameter("fName");
       String lName = wrappedRequest.getParameter("lName");
       dataManager.insertUser(userName, pass, fName, lName);
       return 1;
        
    }
    
}