package com.movie.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lionelm on 1/7/2017.
 */
@EnableAutoConfiguration
@Service
public class DBManager {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public  Map<String, Object> getMovieById (Integer id){
        List<Map<String,Object>> movies = jdbcTemplate.queryForList("SELECT * FROM movieserverdb.movies WHERE id="+id+";");
        return movies.get(0);
    }

    public List<Map<String,Object>> getMovieList (){
        List<Map<String,Object>> movies = jdbcTemplate.queryForList("SELECT * FROM movieserverdb.movies;");
        return movies;
    }

    public  List<Map<String, Object>> getMovieByCategory (Integer category){
        List<Map<String,Object>> movies = jdbcTemplate.queryForList("SELECT * FROM movieserverdb.movies WHERE category="+category+";");
        return movies;
    }



}