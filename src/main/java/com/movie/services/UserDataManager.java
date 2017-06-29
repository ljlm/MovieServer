package com.movie.services;

import com.movie.dal.DBManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import static com.movie.tools.constants.RatingDBConstants.SELECT_ALL_FROM_RATINGS_WHERE_;
import static com.movie.tools.constants.UsersDBConstants.SELECT_ALL_FROM_USERS_WHERE_;

/**
 * Created by lionelm on 6/28/2017.
 */
@Service
public class UserDataManager {
    @Autowired
    public DBManager dbManager;

    public Map<String, Object> getUserById (Integer id){
        List<Map<String,Object>> user = dbManager.queryForList(SELECT_ALL_FROM_USERS_WHERE_+" id="+id + ";");
        return user.get(0);
    }

    public int getUserIdIfExists (String username, String password){
        String query = "SELECT * FROM movieserverdb.users WHERE user_name='"+username + "';";
        List<Map<String,Object>> users = dbManager.queryForList(query.toString());
        if (users!=null){
            Map<String, Object> user = users.get(0);
            if (user != null && user.get("password").equals(password)){
                return (Integer) user.get("id");
            }
        }

        return -1;
    }

    public  Integer insertUser (String userName , String pass,String fName ,String lName ){
        String inserQuery = "INSERT INTO users (user_name, password, first_name, last_name,credits) VALUES (?, ?, ?, ?,?) ";
        int[] types = new int[] { Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR ,Types.INTEGER};

        Object[] params = new Object[] { userName,pass,fName, lName,0};
        dbManager.insertQuery(inserQuery, params, types);
        return 1;


    }
}
