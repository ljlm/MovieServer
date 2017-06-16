package com.movie.dal;

import com.movie.Tools.Categories;
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
    public DBManager dbManager;

     public void createTables(){
     
        dbManager.executeQuery("drop table if exists users ");
        dbManager.executeQuery("CREATE TABLE users(id int NOT NULL AUTO_INCREMENT " +
                ",user_name VARCHAR(255) NOT NULL , password VARCHAR(255) NOT NULL  " +
                ", first_name VARCHAR(255), last_name VARCHAR(255),credits int, locked int" +
                ",PRIMARY KEY (id))");
         String inserQuery = "INSERT INTO users (user_name, password, first_name, last_name,credits,locked) VALUES (?, ?, ?, ?,?,?) ";
         int[] types = new int[] { Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR ,Types.INTEGER,Types.INTEGER};

         Object[] params = new Object[] { "lionelmina","lionelmina","Lionel", "mina",0,0};
         dbManager.insertQuery(inserQuery, params, types);

         params = new Object[] { "elilevi","elilevi","eli", "levi",0,0};
         dbManager.insertQuery(inserQuery, params, types);

         params = new Object[] { "tiggermina","tiggermina","tiger", "mina",0,0};
         dbManager.insertQuery(inserQuery, params, types);

    //insert rated movies table
         
         
    	 dbManager.executeQuery("drop table if exists rented_movies ");
    	 dbManager.executeQuery("CREATE TABLE users(id int NOT NULL AUTO_INCREMENT " +
    	                ",user_id int NOT NULL , movie_id int NOT NULL  " +
    	                ", rented_date DATE NOT NULL, return_date DATE NOT NULL,PRIMARY KEY (id))");
    					
    					
    					 inserQuery = "INSERT INTO users (user_id, movie_id, rented_date, return_date) VALUES (?, ?, ?, ?) ";
    					  types = new int[] { Types.INTEGER,Types.INTEGER, Types.DATE, Types.DATE };
    					   params = new Object[] { 1,1,"2014-01-28", "2014-01-28"};
    					  dbManager.insertQuery(inserQuery, params, types);
         
         //end of rated movie table 
         
         
         
         
         
         
         
         
         
         
         
         dbManager.executeQuery("drop table if exists  movies");
         dbManager.executeQuery("CREATE TABLE movies(" +
                 "id int NOT NULL AUTO_INCREMENT ,movie_name VARCHAR(255) , year int, category int, info VARCHAR(1027) , rating FLOAT ,raters int,available int,locked int,PRIMARY KEY (id))");
         types = new int[] { Types.VARCHAR,Types.INTEGER, Types.INTEGER,Types.VARCHAR , Types.FLOAT ,Types.INTEGER,Types.INTEGER,Types.INTEGER};
         inserQuery = "INSERT INTO movies (movie_name, year, category, info,rating ,raters ,available,locked ) VALUES (?, ?, ?, ?,?,?,?,?) ";
         params = new Object[] { "City of God",2002, Categories.CRIME,"Two boys growing up in a violent neighborhood" +
                 "of Rio de Janeiro take different paths: one becomes a photographer, the other a drug dealer.",8.0,1,3,0};
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
