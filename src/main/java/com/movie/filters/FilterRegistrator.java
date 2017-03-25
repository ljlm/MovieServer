package com.movie.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lionelm on 3/15/2017.
 */
@Component
public class FilterRegistrator {


    @Bean
    public FilterRegistrationBean authenticationFilter(){
        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        filterRegBean.setFilter(new AuthenticationFilter());
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add( "/*");
        filterRegBean.setUrlPatterns(urlPatterns);
        filterRegBean.setOrder(0);
        return filterRegBean;
    }

    @Bean
    public FilterRegistrationBean securityFilter(){
        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        filterRegBean.setFilter(new SecurityFilter());
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/session/*");
        filterRegBean.setUrlPatterns(urlPatterns);
        filterRegBean.setOrder(1);
        return filterRegBean;
    }

}
