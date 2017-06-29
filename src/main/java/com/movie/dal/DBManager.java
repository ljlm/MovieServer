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
 * Created by lionelm on 1/7/2017.
 */

@Repository

public class DBManager {


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




    public static Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName("jdbc:mysql:@localhost:3306:movieserverdb");

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }
//spring.datasource.url= jdbc:mysql://localhost:3306/movieserverdb
//        spring.datasource.username=root
//        spring.datasource.password=root
        try {

            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieserverdb", "root", "root");
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }


}