package com.movie.filters;

import com.movie.services.DataManager;
import com.movie.tools.ActiveUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;


/**
 * Created by lionelm on 3/15/2017.
 */

public class AuthenticationFilter implements Filter {



    public AuthenticationFilter(){
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
        byte[] userpassBase64 = getRequesAuthenticationHeader(wrappedRequest);
        try {
            validateUserCredentials(userpassBase64,session);
        } catch (Exception e) {
            throw new SecurityException(e.getCause());
        }
        filterChain.doFilter(wrappedRequest,servletResponse);


    }

    private byte[] getRequesAuthenticationHeader(HttpResettableServletRequest wrappedRequest) throws UnsupportedEncodingException {
        String requesHeader = wrappedRequest.getHeader("authorization");
        if (requesHeader!=null){
            return Base64.getDecoder().decode(requesHeader.split(" ")[1].getBytes("utf-8"));
        }
        throw new IllegalArgumentException("Unautorized");
    }

    private void validateUserCredentials (byte[] userpassBase64, HttpSession session) throws Exception {
        String userpass = new String(userpassBase64, "utf-8");
        String username = userpass.split(":")[0];
        String password = userpass.split(":")[1];
        Map user = DataManager.getUserDataManager().getUserIdIfExists(username, password);
        ActiveUser activeUser = new ActiveUser((String) user.get("username"),(int)user.get("id"), (int)user.get("role"));
        session.setAttribute("activeUser", activeUser);

    }


    @Override
    public void destroy() {

    }
}
