package com.movie.controllers;

import com.movie.Tools.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
@RestController

public class Controller {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/createtables")
    public void createTables(){
        jdbcTemplate.execute("drop table if exists users ");

        jdbcTemplate.execute("CREATE TABLE users(" +
                "id int NOT NULL AUTO_INCREMENT ,user_name VARCHAR(255) NOT NULL , password VARCHAR(255) NOT NULL  , first_name VARCHAR(255), last_name VARCHAR(255),credits int,PRIMARY KEY (id))");

        String inserQuery = "INSERT INTO users (user_name, password, first_name, last_name,credits) VALUES (?, ?, ?, ?,?) ";
        int[] types = new int[] { Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR ,Types.INTEGER};

        Object[] params = new Object[] { "lionelmina","lionelmina","Lionel", "mina",0};
        jdbcTemplate.update(inserQuery, params, types);

        params = new Object[] { "elilevi","elilevi","eli", "levi",0};
        jdbcTemplate.update(inserQuery, params, types);

        params = new Object[] { "tiggermina","tiggermina","tiger", "mina",0};
        jdbcTemplate.update(inserQuery, params, types);

        jdbcTemplate.execute("drop table if exists  movies");
        jdbcTemplate.execute("CREATE TABLE movies(" +
                "id int NOT NULL AUTO_INCREMENT ,movie_name VARCHAR(255) , year int, category int, info VARCHAR(1027) , rating FLOAT ,available int,PRIMARY KEY (id))");
        types = new int[] { Types.VARCHAR,Types.INTEGER, Types.INTEGER,Types.VARCHAR , Types.FLOAT ,Types.INTEGER};
        inserQuery = "INSERT INTO movies (movie_name, year, category, info,rating ,available ) VALUES (?, ?, ?, ?,?,?) ";
        params = new Object[] { "City of God",2002, Categories.CRIME,"Two boys growing up in a violent neighborhood" +
                "of Rio de Janeiro take different paths: one becomes a photographer, the other a drug dealer.",8.7,3};
        jdbcTemplate.update(inserQuery, params, types);

        params = new Object[] { "Requiem for a Dream",2000,Categories.DRAMA,"he drug-induced utopias of four Coney Island people are" +
                " shattered when their addictions run deep.",8.4,3};
        jdbcTemplate.update(inserQuery, params, types);

        params = new Object[] { "The Big Lebowski",1998, Categories.ADVENTURE,"\"The Dude\" Lebowski, mistaken for a millionaire Lebowski," +
                " seeks restitution for his ruined rug and enlists his bowling buddies to help get it.",8.2,3};
        jdbcTemplate.update(inserQuery, params, types);

        params = new Object[] { "Eternal Sunshine of the Spotless Mind",2004, Categories.DRAMA,"When their relationship turns sour, a couple undergoes" +
                " a procedure to have each other erased from their memories. But it is only through the process of loss that they discover what they had " +
                "to begin with.",8.3,3};
        jdbcTemplate.update(inserQuery, params, types);

        params = new Object[] { "Spirited Away",2004, Categories.ANIMATION,"During her family's move to the suburbs, a sullen 10-year-old girl wanders " +
                "into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.",8.6,3};
        jdbcTemplate.update(inserQuery, params, types);

        params = new Object[] { "Donnie Darko",2001, Categories.DRAMA,"A troubled teenager is plagued by visions of a man in a large rabbit suit who " +
                "manipulates him to commit a series of crimes, after he narrowly escapes a bizarre accident.",8.1,3};
        jdbcTemplate.update(inserQuery, params, types);

        params = new Object[] { "Memento",2000, Categories.THRILLER,"A man juggles searching for his wife's murderer and keeping his short-term memory" +
                " loss from being an obstacle.",8.5,3};
        jdbcTemplate.update(inserQuery, params, types);

        params = new Object[] { "Catch Me If You Can",2002, Categories.ACCION,"The true story of Frank Abagnale Jr. who, before his 19th birthday, " +
                "successfully conned millions of dollars' worth of checks as a Pan Am pilot, doctor, and legal prosecutor.",8.0,3};
        jdbcTemplate.update(inserQuery, params, types);


        jdbcTemplate.execute("drop table if exists rating");

//        jdbcTemplate.execute("CREATE TABLE rating(" +
//                "id int NOT NULL AUTO_INCREMENT, user_id int, movie_id int, rating int ,comment VARCHAR(255),PRIMARY KEY (id)");
        jdbcTemplate.execute("CREATE TABLE rating(" +
                "id int NOT NULL AUTO_INCREMENT ,user_id int , movie_id int  , rating int, comment VARCHAR(255),PRIMARY KEY (id))");
         inserQuery = "INSERT INTO rating (user_id ,movie_id, rating, comment) VALUES (?, ?,?,? ) ";
         types = new int[] { Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.VARCHAR};
          params = new Object[] { 1,1 ,8,"City of God is a beautiful movie. I felt like a child."};
        jdbcTemplate.update(inserQuery, params, types);


    }


//    @RequestMapping("/inserttest")
//    public String index() {
//
////        jdbcTemplate.execute("CREATE TABLE users(" +
////                "id int NOT NULL AUTO_INCREMENT ,user_name VARCHAR(255), first_name VARCHAR(255), last_name VARCHAR(255),credits int,PRIMARY KEY (id))");
//
//
//        String inserQuery = "INSERT INTO users (user_name, first_name, last_name,credits) VALUES (?, ?, ?,?) ";
//        Object[] params = new Object[] { "lionelmina","Lionel", "mina",0};
//        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR ,Types.INTEGER};
//        jdbcTemplate.update(inserQuery, params, types);
//
//        String statment= "SELECT * FROM users";
//        System.out.println( ""+jdbcTemplate.queryForList(statment));
//
//        return "table inserted";
//
//
//
//    }
    //function getMovie()
//    return array of array {Id of movie,name,png url,year,rating}
    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public String getMovies(){
        System.out.println(jdbcTemplate.queryForList("SELECT * FROM movieserverdb.movies;"));
        List<Map<String,Object>> movies =jdbcTemplate.queryForList("SELECT * FROM movieserverdb.movies;");
        List<String> movieList= new ArrayList<>();
        for (Map<String,Object> movie: movies) {
            String value = (String) movie.get("movie_name");
            movieList.add(value);

        }
        return movieList.toString();
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