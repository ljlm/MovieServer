package com.movie.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lionelm on 1/7/2017.
 */

@Service
public class DBManager {
    @Autowired
    private static JdbcTemplate jdbcTemplate;
    public static Map<String, Object> getMovieById (Integer id){
        List<Map<String,Object>> movies =jdbcTemplate.queryForList("SELECT * FROM movieserverdb.movies WHERE id={};",id);
        return movies.get(0);
//        SELECT * FROM movieserverdb.rating WHERE id=1
    }



}