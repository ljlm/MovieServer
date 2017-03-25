package com.movie.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;


/**
 * Created by lionelm on 3/15/2017.
 */
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpResettableServletRequest wrappedRequest = new HttpResettableServletRequest((HttpServletRequest) servletRequest);
        System.out.println(wrappedRequest);
        byte[] userpass = Base64.getDecoder().decode(wrappedRequest.getHeader("authorization").split(" ")[1].getBytes());
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        session.setAttribute("authenticated","true");
    }

    @Override
    public void destroy() {

    }
}
