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
    }
    
    public  boolean updateMovieRating (Integer movieId, Integer userId, Integer rating){
    	 String inserQuery = "INSERT INTO movies (user_name, password, first_name, last_name,credits) VALUES (?, ?, ?, ?,?) ";
         int[] types = new int[] { Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR ,Types.INTEGER};

         Object[] params = new Object[] { "lionelmina","lionelmina","Lionel", "mina",0};
         jdbcTemplate.update(inserQuery, params, types);

        return true;
//        SELECT * FROM movieserverdb.rating WHERE id=1
    }

    public String updateMovieRatingAndRaters(int movieId , Float rating, int raters, int userId){
//        verify if there is already an
        StringBuilder quary = new StringBuilder();
        quary.append("UPDATE movieserverdb.movies SET raters=").append(raters).append("  WHERE CustomerID=").append(movieId).append(";");
//        String quary = "UPDATE movieserverdb.movies SET raters="+raters +"  WHERE CustomerID="+movieId+";";
//        Object[] params = new Object[] { "Spirited Away",2004, Categories.ANIMATION,"During her family's move to the suburbs, a sullen 10-year-old girl wanders " +
//                "into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.",0,0,3};
        jdbcTemplate.update(quary.toString());
        return "";
    }

    public int getMovieRatingByUser(int movieId, int userId){
        StringBuilder quary = new StringBuilder();
        quary.append("SELECT * FROM movieserverdb.rating WHERE user_id=").append(userId).append(" && movie_id=").append(movieId).append(";");
        List<Map<String,Object>> movies = jdbcTemplate.queryForList(quary.toString());
        if (movies.size()>0){
            Integer rating = (Integer) movies.get(0).get("rating");
            return rating;
        }
        return 0;
    }
    
    
}