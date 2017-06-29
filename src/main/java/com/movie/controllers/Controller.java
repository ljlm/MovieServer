package com.movie.controllers;

import com.movie.services.DataManager;
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

@EnableAutoConfiguration
@RestController

//TODO handle errors


public class Controller {

    @Autowired
    public DataManager dataManager;




    //    DBManager.getMovieListByCategory

//    getTopTenMovies() return array of array list movies {Id of movie,name,png url,year,rating,}






    @RequestMapping( method = RequestMethod.GET)
    public void startSession(ServletRequest servletRequest, ServletResponse servletResponse){
        HttpResettableServletRequest wrappedRequest = new HttpResettableServletRequest((HttpServletRequest) servletRequest);
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        session.getAttribute("authenticated");
        session.getAttribute("username");

    }





    @RequestMapping( value = "/newuser",method = RequestMethod.POST)
    @ResponseBody
    public Integer insertUser(ServletRequest servletRequest, ServletResponse servletResponse){
       
   
        return 1;

    }

}