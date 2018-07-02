package com.movie.services;

import com.movie.dal.DBManager;
import com.movie.tools.errors.AlreadyExistentUserNameException;
import com.movie.tools.errors.UnauthorizedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import static com.movie.tools.constants.UsersDBConstants.SELECT_ALL_FROM_USERS_WHERE_;
import static java.awt.SystemColor.info;

/**
 * Created by lionelm on 6/28/2017.
 */
@Service
public class UserDataManager {
    private String inserQuery = "INSERT INTO users (user_name, password, first_name, last_name,credits) VALUES (?, ?, ?, ?,?) ";
    private int[] types = new int[] { Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR ,Types.INTEGER};

    @Autowired
    public DBManager dbManager;

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

    public  Integer insertUser (String userName , String pass,String fName ,String lName ){
        String inserQuery = "INSERT INTO users (user_name, password, first_name, last_name,role,credits,locked) VALUES (?, ?, ?, ?, ? , ?,?) ";
        int[] types = new int[] { Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR , Types.INTEGER, Types.INTEGER,Types.INTEGER};

        Object[] params = new Object[] { userName,pass,fName, lName , 1,0 , 0};
        dbManager.insertQuery(inserQuery, params, types);
        return 1;


    }

    public void addUser(String userName, String password, String firstName, String lastName, int role, int credits) throws AlreadyExistentUserNameException {
        List<Map<String,Object>> users = dbManager.queryForList(SELECT_ALL_FROM_USERS_WHERE_+"user_name=" + userName + ";");
        if (users.size() > 0){
            throw new AlreadyExistentUserNameException("The requested user " + userName + "is already listed in database.");
        }
        Object[] params = new Object[] { userName,password,firstName, lastName,role,credits,0};
        if (dbManager.insertQuery(inserQuery, params, types) != 1){
            throw new InternalError("Unable to add user to database");
        }
    }
}
