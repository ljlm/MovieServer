package com.movie.controllers;

import java.security.InvalidParameterException;

import com.movie.application.MovieApplication;
import com.movie.application.UserApplication;
import com.movie.services.DataPopulator;
import com.movie.services.RoleValidator;
import com.movie.tools.ActiveUser;
import com.movie.tools.DbDataEnums;
import com.movie.tools.errors.AlreadyExistentMovieException;
import com.movie.tools.errors.AlreadyExistentUserNameException;
import com.movie.tools.errors.InvalidRoleException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@EnableAutoConfiguration
@RestController

public class AdminController {



    @Autowired
    public DataPopulator dataPopulator;

    @Autowired
    public MovieApplication movieApplication;

    @Autowired
    public UserApplication userApplication;



    @RequestMapping("/createtables")
    public void createTables(ServletRequest servletRequest) throws InvalidRoleException {
        RoleValidator.validateAdmin(ActiveUser.getActiveUserData(servletRequest).getRole());
        dataPopulator.createTables();
    }

    @RequestMapping(value = "/addmovie",  method = RequestMethod.POST)
    public void addMovie (ServletRequest servletRequest ) throws InvalidRoleException, AlreadyExistentMovieException {
        RoleValidator.validateAdmin(ActiveUser.getActiveUserData(servletRequest).getRole());
        String movieName = servletRequest.getParameter("movie_name");
        String picLink =  servletRequest.getParameter("pick_link");
        String year = servletRequest.getParameter("year");
        String category = servletRequest.getParameter("category");
        String info =  servletRequest.getParameter("info");
        String available = servletRequest.getParameter("available");
        movieApplication.addMovie(movieName, picLink, year, category, info, available);
    }

    @RequestMapping(value = "/adduser",  method = RequestMethod.POST)
    public void addUser (ServletRequest servletRequest ) throws InvalidRoleException, AlreadyExistentUserNameException {
        RoleValidator.validateAdmin(ActiveUser.getActiveUserData(servletRequest).getRole());
        String userName = servletRequest.getParameter("user_name");
        String password =  servletRequest.getParameter("password");
        String firstName = servletRequest.getParameter("first_name");
        String lastName = servletRequest.getParameter("last_name");
        String role =  servletRequest.getParameter("role");
        String credits = servletRequest.getParameter("credits");
        userApplication.addUser(userName, password, firstName, lastName, role, credits);
    }




}