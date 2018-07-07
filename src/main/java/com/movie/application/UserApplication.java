package com.movie.application;

import com.movie.dal.DBManager;
import com.movie.services.DataManager;
import com.movie.services.LocksService;
import com.movie.tools.DBRowUpdateData;
import com.movie.tools.DbDataEnums;
import com.movie.tools.SimpleResponse;
import com.movie.tools.errors.AlreadyExistentUserNameException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lionelm on 6/28/2017.
 */
@Component
public class UserApplication {

    @Autowired
    private ReviewApplication reviewApplication;

@Autowired
private DBManager dbManager;

@Autowired
private PurchaseApplication purchaseApplication;

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


    public SimpleResponse addUser(String userName, String password, String firstName, String lastName, String paymentToken, String roleStr, String creditsStr) throws AlreadyExistentUserNameException {
        int role;
        try{
            role = Integer.parseInt(roleStr);
        }catch (Exception e){
            throw new InvalidParameterException("Parameter role=" + roleStr + " is invalid");
        }

        int credits;
        try{
            credits = Integer.parseInt(creditsStr);
        }catch (Exception e){
            throw new InvalidParameterException("Parameter credits=" + creditsStr + " is invalid");
        }

        return DataManager.getUserDataManager().addUser(userName, password, firstName, lastName, paymentToken, role, credits);


    }

    public SimpleResponse removeUser(String userIDStr) {
        int userId;
        try {
            userId = Integer.parseInt(userIDStr);
        }catch (Exception e){
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Invalid User id");
        }
        purchaseApplication.removePurchaseHistoryForUser(userId);
        reviewApplication.deleteAllUserReview(userId);
        return DataManager.getUserDataManager().removeUser(userIDStr);
    }

    public SimpleResponse addCreditsToUser(int userId, int creditsToAdd){
        StringBuilder whereStatement = new StringBuilder();
        whereStatement.append("id=").append(userId);
        String setStatement = "credits = credits + " + creditsToAdd;
        DBRowUpdateData rowUpdateData = new DBRowUpdateData(" movieserverdb.users", whereStatement.toString(),setStatement.toString());
        if (!LocksService.setRow(rowUpdateData)){
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("unable to update row");
        }
        return new SimpleResponse().setResult(DbDataEnums.result.SUCCESS);
    }
}
