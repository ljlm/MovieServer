package com.movie;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
@RestController
public class Controller {

	@Autowired
    JdbcTemplate jdbcTemplate;
	
    @RequestMapping("/inserttest")
    public String index() {

       
        //jdbcTemplate.execute("CREATE TABLE customers(" +
                //"id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
    	
    	
    
    	
    	String inserQuery = "INSERT INTO users (user_name, first_name, last_name,credits) VALUES (?, ?, ?,?) ";
		Object[] params = new Object[] { "anniedadon","annie", "dadon",0};
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR ,Types.INTEGER};

		 jdbcTemplate.update(inserQuery, params, types);
		
		 String statment= "SELECT * FROM users";
		 System.out.println( ""+jdbcTemplate.queryForList(statment));
		 
		
		 
		 
    	
        return "table inserted";
        
        
        
    }
//function getMovie()
//    return array of array {Id of movie,name,png url,year,rating}
    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public String getMovies(){
        return "List of all movies";
    }
//function getRatingComment(movieID) return array of arrays String  {User ID,Commment ,rating} order by Rating
    @RequestMapping(value = "/comments/{movieID}", method = RequestMethod.GET)
    public String getMoviesComments(@PathVariable String movieID){
        return "List of comments for movie="+movieID;
    }

    @RequestMapping(value = "/movies/{movieID}/{rating}", method = RequestMethod.GET)
    public String AddMovieRating(@PathVariable String movieID,@PathVariable String rating){
        return "User rated movie "+movieID+" as "+rating;
    }

//    getTopTenMovies() return array of array list movies {Id of movie,name,png url,year,rating,}

    @RequestMapping(value = "/movies/topten", method = RequestMethod.GET)
    public String getTopTenMovies(){
        return "List of top 10 movies";
    }
//    function rentMovie(MovieId) {creditLeft}
    @RequestMapping(value = "/movies/{movieID}", method = RequestMethod.GET)
    public String returnMovie (@PathVariable String movieID){
        return movieID+" returned";
    }

//    function returnMovie(MovieId) {}

//

//    function acountinfo(userid){creditLeft,firstName,LastName,arrayofmovies}


}