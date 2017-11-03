package com.movie.application;

import com.movie.dal.DBManager;
import com.movie.services.LocksService;
import com.movie.tools.DBRowUpdateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lionelm on 6/28/2017.
 */
@Component
public class UserApplication {

@Autowired
private DBManager dbManager;
private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public boolean decreaseUserCredits(int userID){
        return decreaseUserCredits(userID,true);
    }

    public boolean increaseUserCredits(int userID, int amount){
        return increaseUserCredits(userID,  amount, true);
    }



    public boolean decreaseUserCredits(int userID, boolean needToLockLine){
        String whereStatement = "id=" + userID +" && credits > 0";
        String setStatement = "credits = credits - 1";
        DBRowUpdateData rowBlockerData = new DBRowUpdateData("movieserverdb.users",whereStatement,setStatement);
        if (needToLockLine){
            return LocksService.setRow(rowBlockerData);
        }
        return LocksService.setLockedRow(rowBlockerData);
    }



    public boolean increaseUserCredits(int userID, int amount, boolean needToLockLine){
        String whereStatement = "id=" + userID ;
        String setStatement = "credits = credits + 1";
        DBRowUpdateData rowBlockerData = new DBRowUpdateData("movieserverdb.users",whereStatement,setStatement);
        if (needToLockLine){
            return LocksService.setRow(rowBlockerData);
        }
        return LocksService.setLockedRow(rowBlockerData);
    }

    public void createRantedMovieLog (int userId, int movieId){
        Calendar cal = Calendar.getInstance();
        String today = dateFormat.format(cal.getTime());
        String inserQuery = "INSERT INTO rented_movies (user_id, movie_id, rented_date, return_date) VALUES (?, ?, ?, ?) ";
        int[] types = new int[] { Types.INTEGER,Types.INTEGER, Types.DATE, Types.DATE };
        Object[] params = new Object[] { userId,movieId, today,null};
        dbManager.insertQuery(inserQuery, params, types);
    }

    public void updateRantedMovieLog (int userId, int movieId){
        Calendar cal = Calendar.getInstance();
        String today = dateFormat.format(cal.getTime());
        String query = "UPDATE movieserverdb.rented_movies SET return_date=\""+today +"\" WHERE user_id=" + userId +" && movie_id=" + movieId +";";
        dbManager.updateQuery(query);
//        DBRowUpdateData rowBlockerData = new DBRowUpdateData("movieserverdb.users",whereStatement,today);
//        LocksService.setRow(rowBlockerData);
//        String inserQuery = "INSERT INTO rented_movies (user_id, movie_id, rented_date, return_date) VALUES (?, ?, ?, ?) ";
//        int[] types = new int[] { Types.INTEGER,Types.INTEGER, Types.DATE, Types.DATE };
//        Object[] params = new Object[] { userId,movieId, today,null};
//        dbManager.insertQuery(inserQuery, params, types);

    }





}
