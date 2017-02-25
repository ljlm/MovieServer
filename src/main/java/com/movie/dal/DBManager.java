package com.movie.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
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
        List<Map<String,Object>> movies = jdbcTemplate.queryForList("SELECT * FROM movieserver.movies WHERE id="+id+";");
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

    public  Map<String, Object> getUserById (Integer id){
        List<Map<String,Object>> user = jdbcTemplate.queryForList("SELECT * FROM movieserver.users WHERE id="+id+";");
        return user.get(0);
//        SELECT * FROM movieserverdb.rating WHERE id=1
    }
    
    public  boolean updateMovieRating (Integer id){
    	 String inserQuery = "INSERT INTO movies (user_name, password, first_name, last_name,credits) VALUES (?, ?, ?, ?,?) ";
         int[] types = new int[] { Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR ,Types.INTEGER};

         Object[] params = new Object[] { "lionelmina","lionelmina","Lionel", "mina",0};
         jdbcTemplate.update(inserQuery, params, types);

        return true;
//        SELECT * FROM movieserverdb.rating WHERE id=1
    }
    
    
}