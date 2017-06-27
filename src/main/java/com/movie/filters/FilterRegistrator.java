package com.movie.filters;

import com.movie.dal.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public DataManager dataManager;

//    @Bean
//    public FilterRegistrationBean authenticationFilter(){
//        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
//        filterRegBean.setFilter(new AuthenticationFilter(dataManager));
//        List<String> urlPatterns = new ArrayList<>();
//        urlPatterns.add( "/*");
//        filterRegBean.setUrlPatterns(urlPatterns);
//        filterRegBean.setOrder(1);
//        return filterRegBean;
//    }
//
    @Bean
    public FilterRegistrationBean newUserFilter(){
        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        filterRegBean.setFilter(new NewUserFilter());
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/newuser");
        filterRegBean.setUrlPatterns(urlPatterns);
        filterRegBean.setOrder(0);
        return filterRegBean;
    }

}
