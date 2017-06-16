package com.movie.filters;

import com.movie.dal.DataManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;


/**
 * Created by lionelm on 3/15/2017.
 */

public class AuthenticationFilter implements Filter {


    public DataManager dataManager;

    public AuthenticationFilter(DataManager dataManager){
        this.dataManager=dataManager;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    			
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpResettableServletRequest wrappedRequest = new HttpResettableServletRequest((HttpServletRequest) servletRequest);
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        if (session.getAttribute("newUser") != null){
            filterChain.doFilter(servletRequest,servletResponse);
        }
        byte[] userpassBase64 = Base64.getDecoder().decode(wrappedRequest.getHeader("authorization").split(" ")[1].getBytes("utf-8"));
        String userpass = new String(userpassBase64, "utf-8");
        String username = userpass.split(":")[0];
        String password = userpass.split(":")[1];
        int userId = dataManager.getUserIdIfExists(username, password);
        if (userId > -1){
            session.setAttribute("userName",username);
            session.setAttribute("userId",userId);
            filterChain.doFilter(wrappedRequest,servletResponse);
        }
        else{
            throw new IllegalArgumentException("Unautorized");
        }

    }

    @Override
    public void destroy() {

    }
}
