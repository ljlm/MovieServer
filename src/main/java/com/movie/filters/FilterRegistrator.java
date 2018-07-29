package com.movie.filters;

import com.movie.services.DataManager;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * this class creates the filters as beans, set their path and order of operation
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
        filterRegBean.setOrder(1);
        return filterRegBean;
    }

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

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory(){
        return new TomcatEmbeddedServletContainerFactory(){
            @Override
            protected void customizeConnector(Connector connector){
                super.customizeConnector(connector);
                connector.setParseBodyMethods("POST, PUT, DELETE");
            }
        };
    }



}
