package com.movie.dal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lionelm on 1/7/2017.
 */
@EnableAutoConfiguration
@Service
public class DBManager {
    private static final String SHARED_LOCK =" LOCK IN SHARE MODE;";
    private static final String PRIVATE_LOCK=" FOR UPDATE;";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int updateQuery (String query){
        return jdbcTemplate.update(query);
    }

    public List queryForList(String query){
        return jdbcTemplate.queryForList(query);
    }

    public int insertQuery (String inserQuery, Object[] params, int[] types){
        return jdbcTemplate.update(inserQuery, params, types);
    }

    public void executeQuery (String query){
        jdbcTemplate.execute(query);
    }



}