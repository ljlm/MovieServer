package com.movie.services;

import com.movie.dal.DBManager;
import com.movie.tools.DBRowUpdateData;
import com.movie.tools.DbDataEnums;
import com.movie.tools.SimpleResponse;
import com.movie.tools.errors.AlreadyExistentUserNameException;
import com.movie.tools.errors.UnauthorizedException;
import com.mysql.jdbc.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import static com.movie.tools.constants.UsersDBConstants.SELECT_ALL_FROM_USERS_WHERE_;
import static java.awt.SystemColor.info;

/**
 * This service is resposable for creating the necessary queries
 * for user related requests to db
 */

@Service
public class UserDataManager {
    private String inserQuery = "INSERT INTO users (user_name, password, first_name, last_name,payment_token ,role ,credits,locked) VALUES (?,?,?, ?, ?, ?,?,?) ";
    private int[] types = new int[] { Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,Types.INTEGER,Types.INTEGER};

    @Autowired
    public DBManager dbManager;


    public List<Map<String, Object>> getAllUsers (){
        List<Map<String,Object>> users = dbManager.queryForList("SELECT * FROM movieserverdb.users ;");
        return users;
    }

    public Map<String, Object> getUserById (Integer id){
        List<Map<String,Object>> user = dbManager.queryForList(SELECT_ALL_FROM_USERS_WHERE_+" id="+id + ";");
        return user.get(0);
    }

    public Map<String, Object> getUserIdIfExists (String username, String password) throws UnauthorizedException {
        String query = "SELECT * FROM movieserverdb.users WHERE user_name='"+username + "';";
        List<Map<String,Object>> users = dbManager.queryForList(query.toString());
        if (users!=null){
            Map<String, Object> user = users.get(0);
            if (user != null && user.get("password").equals(password)){
                return user;
            }
        }

        throw new UnauthorizedException("invalid credentials");
//        return -1;
    }

    public boolean isUserNameRegistered(String username){
        String query = "SELECT * FROM movieserverdb.users WHERE user_name='"+username + "';";
        List<Map<String,Object>> users = dbManager.queryForList(query.toString());
        return users.size()>0;
    }

    public  Integer insertUser (String userName , String pass,String fName ,String lName ){
        String inserQuery = "INSERT INTO users (user_name, password, first_name, last_name,role,credits,locked) VALUES (?, ?, ?, ?, ? , ?,?) ";
        int[] types = new int[] { Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR , Types.INTEGER, Types.INTEGER,Types.INTEGER};

        Object[] params = new Object[] { userName,pass,fName, lName , 1,0 , 0};
        dbManager.insertQuery(inserQuery, params, types);
        return 1;


    }

    public SimpleResponse addUser(String userName, String password, String firstName, String lastName,String paymentToken, int role, int credits) throws AlreadyExistentUserNameException {
        List<Map<String,Object>> users = dbManager.queryForList(SELECT_ALL_FROM_USERS_WHERE_+"user_name='" + userName + "';");
        if (users.size() > 0){
            throw new AlreadyExistentUserNameException("The requested user " + userName + "is already listed in database.");
        }
        Object[] params = new Object[] { userName,password,firstName, lastName,paymentToken,role,credits,0};
        if (dbManager.insertQuery(inserQuery, params, types) != 1){
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Unable to add user to database");
        }
        return new SimpleResponse().setResult(DbDataEnums.result.SUCCESS);
    }

    public SimpleResponse removeUser(String userID) {
        List<Map<String,Object>> users = dbManager.queryForList(SELECT_ALL_FROM_USERS_WHERE_+"id='" + userID + "';");
        if (users.size() == 0){
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Unable to remove unexisting user.");
        }
        dbManager.executeQuery("DELETE FROM movieserverdb.users WHERE id="+userID+";");
        return new SimpleResponse().setResult(DbDataEnums.result.SUCCESS);


    }


    public SimpleResponse editUser(int userId, String userName, String password, String firstName, String lastName,
                                   String paymentToken, int role,  int credits) {
        StringBuilder whereStatement = new StringBuilder();
        whereStatement.append("id=").append(userId);
        StringBuilder setStatement = new StringBuilder();

        if (!StringUtils.isEmptyOrWhitespaceOnly(userName)){
            setStatement.append("user_name='").append(userName).append("' ");
        }

        if (!StringUtils.isEmptyOrWhitespaceOnly(password)){
            if(setStatement.length() != 0 && !setStatement.substring(setStatement.length()-2).equals(',')){
                setStatement.append(" , ");
            }
            setStatement.append("password='").append(password).append("' ");
        }

        if (!StringUtils.isEmptyOrWhitespaceOnly(firstName)){
            if(setStatement.length() != 0 && !setStatement.substring(setStatement.length()-2).equals(',')){
                setStatement.append(" , ");
            }
            setStatement.append("first_name='").append(firstName).append("' ");
        }

        if (!StringUtils.isEmptyOrWhitespaceOnly(lastName)){
            if(setStatement.length() != 0 && !setStatement.substring(setStatement.length()-2).equals(',')){
                setStatement.append(" , ");
            }
            setStatement.append("last_name='").append(lastName).append("' ");
        }

        if (!StringUtils.isEmptyOrWhitespaceOnly(paymentToken)){
            if(setStatement.length() != 0 && !setStatement.substring(setStatement.length()-2).equals(',')){
                setStatement.append(" , ");
            }
            setStatement.append("payment_token='").append(paymentToken).append("' ");
        }

        if (role != -1){
            if(setStatement.length() != 0 && !setStatement.substring(setStatement.length()-2).equals(',')){
                setStatement.append(" , ");
            }
            setStatement.append("role=").append(role).append(" ");
        }

        if (credits != -1){
            if(setStatement.length() != 0 && !setStatement.substring(setStatement.length()-2).equals(',')){
                setStatement.append(" , ");
            }
            setStatement.append("credits=").append(credits).append(" ");
        }

        if (setStatement.length() == 0){
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Nothing to update");
        }

        DBRowUpdateData rowUpdateData = new DBRowUpdateData(" movieserverdb.users", whereStatement.toString(),setStatement.toString());
        if (!LocksService.setRow(rowUpdateData)){
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Unable to update user");
        }
        return new SimpleResponse().setResult(DbDataEnums.result.SUCCESS);
    }
}
