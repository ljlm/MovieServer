package com.movie.services;

import com.movie.dal.DBManager;
import com.movie.tools.Categories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.sql.Types;

/**
 * Created by lionelm on 2/25/2017.
 */
@EnableAutoConfiguration
@Service


public class DataPopulator {

    @Autowired
    private DBManager dbManager;
    private static final Logger logger = LoggerFactory.getLogger(DataPopulator.class);
//    TODO CONSTANTS AND LOGS
     public void createTables(){
      dbManager.executeQuery("drop table if exists categories ");
      dbManager.executeQuery("CREATE TABLE categories(id int NOT NULL  " +
              ",category_name VARCHAR(255) NOT NULL "+
              ",PRIMARY KEY (id))");
      String inserQuery = "INSERT INTO categories (id , category_name) VALUES (?, ?) ";
      int[] types = new int[] { Types.INTEGER,Types.VARCHAR};
      Object[] params = new Object[] { 9,"horror"};
      dbManager.insertQuery(inserQuery, params, types);

      params = new Object[] { 1,"crime"};
      dbManager.insertQuery(inserQuery, params, types);

      params = new Object[] { 2,"adventure"};
      dbManager.insertQuery(inserQuery, params, types);

      params = new Object[] { 3,"action"};
      dbManager.insertQuery(inserQuery, params, types);

      params = new Object[] { 4,"drama"};
      dbManager.insertQuery(inserQuery, params, types);


      params = new Object[] { 5,"documentary"};
      dbManager.insertQuery(inserQuery, params, types);

      params = new Object[] { 6,"animation"};
      dbManager.insertQuery(inserQuery, params, types);

      params = new Object[] { 7,"comedy"};
      dbManager.insertQuery(inserQuery, params, types);


      params = new Object[] { 8,"thriller"};
      dbManager.insertQuery(inserQuery, params, types);


        dbManager.executeQuery("drop table if exists users ");
        dbManager.executeQuery("CREATE TABLE users(id int NOT NULL AUTO_INCREMENT " +
                ",user_name VARCHAR(255) NOT NULL , password VARCHAR(255) NOT NULL  " +
                ", first_name VARCHAR(255), last_name VARCHAR(255),role int,credits int, locked int" +
                ",PRIMARY KEY (id))");
          inserQuery = "INSERT INTO users (user_name, password, first_name, last_name,role ,credits,locked) VALUES (?,?, ?, ?, ?,?,?) ";
          types = new int[] { Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.INTEGER ,Types.INTEGER,Types.INTEGER};

         params = new Object[] { "lionelmina","lionelmina","Lionel", "mina",0,3,0};
         dbManager.insertQuery(inserQuery, params, types);

         params = new Object[] { "elilevi","elilevi","eli", "levi",0,0,0};
         dbManager.insertQuery(inserQuery, params, types);

         params = new Object[] { "tigger","tigger","tiger", "mina",1,0,0};
         dbManager.insertQuery(inserQuery, params, types);

      params = new Object[] { "helenab","helenab","helena", "bogdzel",1,0,0};
      dbManager.insertQuery(inserQuery, params, types);

    //insert rated movies table
         
         
    	 dbManager.executeQuery("drop table if exists rented_movies ");
    	 dbManager.executeQuery("CREATE TABLE rented_movies(id int NOT NULL AUTO_INCREMENT " +
    	                ",user_id int  , movie_id int  " +
    	                ", rented_date DATE , return_date DATE ,PRIMARY KEY (id))");
    					
    					
//    					 inserQuery = "INSERT INTO rented_movies (user_id, movie_id, rented_date, return_date) VALUES (?, ?, ?, ?) ";
//    					  types = new int[] { Types.INTEGER,Types.INTEGER, Types.DATE, Types.DATE };
//    					   params = new Object[] { 1,1,"2014-01-28", "2014-01-28"};
//    					  dbManager.insertQuery(inserQuery, params, types);
         
         //end of rated movie table 
         
         
         
         
         
         
         
         
         
         
         
         dbManager.executeQuery("drop table if exists  movies");
         dbManager.executeQuery("CREATE TABLE movies(" +
                 "id int NOT NULL AUTO_INCREMENT ,movie_name VARCHAR(255) , year int, category int, info VARCHAR(1027) , rating FLOAT ,raters int,available int,locked int,PRIMARY KEY (id))");
         types = new int[] { Types.VARCHAR,Types.INTEGER, Types.INTEGER,Types.VARCHAR , Types.FLOAT ,Types.INTEGER,Types.INTEGER,Types.INTEGER};
         inserQuery = "INSERT INTO movies (movie_name, year, category, info,rating ,raters ,available,locked ) VALUES (?, ?, ?, ?,?,?,?,?) ";
         params = new Object[] { "City of God",2002, Categories.CRIME,"Two boys growing up in a violent neighborhood" +
                 "of Rio de Janeiro take different paths: one becomes a photographer, the other a drug dealer.",0,0,3,0};
         dbManager.insertQuery(inserQuery, params, types);

         params = new Object[] { "Requiem for a Dream",2000,Categories.DRAMA,"he drug-induced utopias of four Coney Island people are" +
                 " shattered when their addictions run deep.",0,0,3,0};
         dbManager.insertQuery(inserQuery, params, types);

         params = new Object[] { "The Big Lebowski",1998, Categories.ADVENTURE,"\"The Dude\" Lebowski, mistaken for a millionaire Lebowski," +
                 " seeks restitution for his ruined rug and enlists his bowling buddies to help get it.",0,0,3,0};
         dbManager.insertQuery(inserQuery, params, types);

         params = new Object[] { "Eternal Sunshine of the Spotless Mind",2004, Categories.DRAMA,"When their relationship turns sour, a couple undergoes" +
                 " a procedure to have each other erased from their memories. But it is only through the process of loss that they discover what they had " +
                 "to begin with.",0,0,3,0};
         dbManager.insertQuery(inserQuery, params, types);

         params = new Object[] { "Spirited Away",2004, Categories.ANIMATION,"During her family's move to the suburbs, a sullen 10-year-old girl wanders " +
                 "into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.",0,0,3,0};
         dbManager.insertQuery(inserQuery, params, types);

         params = new Object[] { "Donnie Darko",2001, Categories.DRAMA,"A troubled teenager is plagued by visions of a man in a large rabbit suit who " +
                 "manipulates him to commit a series of crimes, after he narrowly escapes a bizarre accident.",0,0,3,0};
         dbManager.insertQuery(inserQuery, params, types);

         params = new Object[] { "Memento",2000, Categories.THRILLER,"A man juggles searching for his wife's murderer and keeping his short-term memory" +
                 " loss from being an obstacle.",0,0,3,0};
         dbManager.insertQuery(inserQuery, params, types);

         params = new Object[] { "Catch Me If You Can",2002, Categories.ACCION,"The true story of Frank Abagnale Jr. who, before his 19th birthday, " +
                 "successfully conned millions of dollars' worth of checks as a Pan Am pilot, doctor, and legal prosecutor.",0,0,3,0};
         dbManager.insertQuery(inserQuery, params, types);


         dbManager.executeQuery("drop table if exists rating");
         dbManager.executeQuery("CREATE TABLE rating(" +
                 "id int NOT NULL AUTO_INCREMENT ,user_id int , movie_id int  , rating int, comment VARCHAR(255),locked int,PRIMARY KEY (id))");
         inserQuery = "INSERT INTO rating (user_id ,movie_id, rating, comment,locked) VALUES (?, ?,?,?,? ) ";
         types = new int[] { Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.INTEGER};
         params = new Object[] { 1,1 ,8,"City of God is a beautiful movie. I felt like a child.",0};
         dbManager.insertQuery(inserQuery, params, types);

    }
}
