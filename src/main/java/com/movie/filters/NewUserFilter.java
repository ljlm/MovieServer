package com.movie.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.movie.services.DataManager;

import java.io.IOException;


/**
 * Created by lionelm on 3/15/2017.
 */


public class NewUserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpResettableServletRequest wrappedRequest = new HttpResettableServletRequest((HttpServletRequest) servletRequest);
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        session.setAttribute("newuser",true);
        filterChain.doFilter(wrappedRequest,servletResponse);
        String userName= wrappedRequest.getParameter("username");
        String pass= wrappedRequest.getParameter("password");
        String fName = wrappedRequest.getParameter("firstname");
        String lName = wrappedRequest.getParameter("lastname");
        DataManager.getUserDataManager().insertUser(userName, pass, fName, lName);
        
    }


    @Override
    public void destroy() {

    }
}
