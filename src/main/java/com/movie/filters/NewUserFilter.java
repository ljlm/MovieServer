package com.movie.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.movie.services.DataManager;
import com.movie.tools.ActiveUser;
import com.movie.tools.errors.AlreadyExistentUserNameException;
import com.movie.tools.errors.UnauthorizedException;

import java.io.IOException;
import java.util.Map;


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

        String userName= wrappedRequest.getParameter("user_name");
        String pass= wrappedRequest.getParameter("password");
        String fName = wrappedRequest.getParameter("first_name");
        String lName = wrappedRequest.getParameter("last_name");
        String paymentToken = wrappedRequest.getParameter("payment_token");

        if (DataManager.getUserDataManager().isUserNameRegistered(userName)){
            throw new SecurityException("Username already exist.");
        }
        Map user;
        try {
             DataManager.getUserDataManager().addUser(userName, pass, fName, lName,paymentToken,1,0);
            user = DataManager.getUserDataManager().getUserIdIfExists(userName, pass);
        } catch (Exception e) {
            throw new SecurityException(e.getCause());
        } catch (AlreadyExistentUserNameException e) {
            throw new SecurityException("Username already exist.");
        }
        ActiveUser activeUser = new ActiveUser((String) user.get("user_name"),(int)user.get("id"), (int)user.get("role"));
        session.setAttribute("activeUser", activeUser);
        filterChain.doFilter(wrappedRequest,servletResponse);
        
    }


    @Override
    public void destroy() {

    }
}
