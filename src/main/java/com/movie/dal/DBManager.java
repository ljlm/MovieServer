package com.movie.dal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * this class interacts directly with the db
 */

@Repository

public class DBManager {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int updateQuery (String query){
        System.out.println("Attempting to " + query);
        int lines;
        try {
            lines = jdbcTemplate.update(query);
        }catch (Exception e){
            return 0;
        }
        return lines;
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