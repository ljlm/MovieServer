package com.movie.controllers;


import com.movie.application.MovieApplication;
import com.movie.application.PurchaseApplication;
import com.movie.application.RantedMoviesApplication;
import com.movie.application.UserApplication;
import com.movie.services.DataPopulator;
import com.movie.services.RoleValidator;
import com.movie.tools.ActiveUser;
import com.movie.tools.DbDataEnums;
import com.movie.tools.JsonTools;
import com.movie.tools.SimpleResponse;
import com.movie.tools.errors.AlreadyExistentMovieException;
import com.movie.tools.errors.AlreadyExistentUserNameException;
import com.movie.tools.errors.InvalidRoleException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;

@EnableAutoConfiguration
@RestController

public class AdminController {

    @Autowired
    public RantedMoviesApplication rantedMoviesApplication;

    @Autowired
    public DataPopulator dataPopulator;

    @Autowired
    public MovieApplication movieApplication;

    @Autowired
    public UserApplication userApplication;

    @Autowired
    public PurchaseApplication purchaseApplication;

    @RequestMapping("/admin/createtables")
    public String createTables(ServletRequest servletRequest) throws InvalidRoleException {
        RoleValidator.validateAdmin(ActiveUser.getActiveUserData(servletRequest).getRole());
        dataPopulator.createTables();
        return new SimpleResponse().setResult(DbDataEnums.result.SUCCESS).toString();
    }

    @RequestMapping(value = "/admin/movie",  method = RequestMethod.POST)
    public String addMovie (ServletRequest servletRequest ) throws InvalidRoleException, AlreadyExistentMovieException {
        RoleValidator.validateAdmin(ActiveUser.getActiveUserData(servletRequest).getRole());
        String movieName = servletRequest.getParameter("movie_name");
        String picLink =  servletRequest.getParameter("pick_link");
        String year = servletRequest.getParameter("year");
        String category = servletRequest.getParameter("category");
        String info =  servletRequest.getParameter("info");
        String available = servletRequest.getParameter("available");
        return movieApplication.addMovie(movieName, picLink, year, category, info, available).toString();
    }

    @RequestMapping(value = "/admin/movie",  method = RequestMethod.PUT)
    public String editMovie (ServletRequest servletRequest ) throws InvalidRoleException, AlreadyExistentMovieException {
        RoleValidator.validateAdmin(ActiveUser.getActiveUserData(servletRequest).getRole());
        String movieId = servletRequest.getParameter("movie_id");
        String movieName = servletRequest.getParameter("movie_name");
        String picLink =  servletRequest.getParameter("pick_link");
        String year = servletRequest.getParameter("year");
        String category = servletRequest.getParameter("category");
        String info =  servletRequest.getParameter("info");
        String available = servletRequest.getParameter("available");
        return movieApplication.editMovie(movieId, movieName, picLink, year, category, info, available).toString();
    }

    @RequestMapping(value = "/admin/user",  method = RequestMethod.POST)
    public String addUser (ServletRequest servletRequest ) throws InvalidRoleException, AlreadyExistentUserNameException {
        RoleValidator.validateAdmin(ActiveUser.getActiveUserData(servletRequest).getRole());
        String userName = servletRequest.getParameter("user_name");
        String password =  servletRequest.getParameter("password");
        String firstName = servletRequest.getParameter("first_name");
        String lastName = servletRequest.getParameter("last_name");
        String role =  servletRequest.getParameter("role");
        String credits = servletRequest.getParameter("credits");
        String paymentToken = servletRequest.getParameter("payment_token");
        return userApplication.addUser(userName, password, firstName, lastName, paymentToken, role, credits).toString();
    }

    @RequestMapping(value = "/admin/user",  method = RequestMethod.PUT)
    public String editUser (ServletRequest servletRequest ) throws InvalidRoleException, AlreadyExistentUserNameException {
        RoleValidator.validateAdmin(ActiveUser.getActiveUserData(servletRequest).getRole());
        String userId = servletRequest.getParameter("user_id");
        String userName = servletRequest.getParameter("user_name");
        String password =  servletRequest.getParameter("password");
        String firstName = servletRequest.getParameter("first_name");
        String lastName = servletRequest.getParameter("last_name");
        String role =  servletRequest.getParameter("role");
        String credits = servletRequest.getParameter("credits");
        String paymentToken = servletRequest.getParameter("payment_token");
        return userApplication.editUser(ActiveUser.getActiveUserData(servletRequest), userId, userName, password, firstName, lastName, paymentToken, role, credits).toString();
    }

    @RequestMapping(value = "/admin/user",  method = RequestMethod.DELETE)
    public String removeUser (ServletRequest servletRequest ) throws InvalidRoleException, AlreadyExistentUserNameException {
        RoleValidator.validateAdmin(ActiveUser.getActiveUserData(servletRequest).getRole());
        String userID = servletRequest.getParameter("user_id");
        return userApplication.removeUser(userID).toString();
    }

    @RequestMapping(value = "/admin/users",  method = RequestMethod.GET)
    public String getAllUsers (ServletRequest servletRequest ) throws InvalidRoleException, AlreadyExistentUserNameException {
        RoleValidator.validateAdmin(ActiveUser.getActiveUserData(servletRequest).getRole());
        return JsonTools.convertToJson(userApplication.getAllUsers());
    }


        @RequestMapping(value = "/admin/purchases",  method = RequestMethod.GET)
    public String getAllUsersPurchaseHistory (ServletRequest servletRequest ) throws InvalidRoleException {
        RoleValidator.validateAdmin(ActiveUser.getActiveUserData(servletRequest).getRole());
        return JsonTools.convertToJson(purchaseApplication.getAllUsersPurchaseHistory());
    }

    @RequestMapping(value = "/admin/leaser",  method = RequestMethod.GET)
    public String getAllRentedHistory (ServletRequest servletRequest ) throws InvalidRoleException, AlreadyExistentUserNameException {
        RoleValidator.validateAdmin(ActiveUser.getActiveUserData(servletRequest).getRole());
        return JsonTools.convertToJson(rantedMoviesApplication.getAllRantedMovieLog());
    }

    @RequestMapping( value = "/admin/leaser/{movieID}/{userID}", method = RequestMethod.PUT)
    public String leaseMovieForUser(@PathVariable Integer movieID, @PathVariable Integer userID, ServletRequest servletRequest ) throws InvalidRoleException {
        RoleValidator.validateAdmin(ActiveUser.getActiveUserData(servletRequest).getRole());
        return movieApplication.leaseMovie(movieID, userID).toString();
    }

    @RequestMapping( value = "/admin/leaser/{movieID}/{userID}", method = RequestMethod.DELETE)
    public String unleaseMovieForUser(@PathVariable Integer movieID, @PathVariable Integer userID, ServletRequest servletRequest) throws InvalidRoleException {
        RoleValidator.validateAdmin(ActiveUser.getActiveUserData(servletRequest).getRole());
        return movieApplication.unleaseMovie(movieID, userID).toString();
    }








}