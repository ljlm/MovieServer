package com.movie.dal;

import com.movie.Tools.User;
import org.springframework.stereotype.Service;

import java.sql.Types;

/**
 * Created by lionelm on 1/7/2017.
 */
@Service
public class DBManager {
//    @Autowired
//    JdbcTemplate jdbcTemplate;

    public void createUser(User user){
        String inserQuery = "INSERT INTO users (user_name, first_name, last_name,credits) VALUES (?, ?, ?,?) ";
        Object[] params = new Object[] { "anniedadon","annie", "dadon",0};
        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR ,Types.INTEGER};
//        jdbcTemplate.update(inserQuery, params, types);


    }

    public boolean comparePassword (String typedPassword){
        return true;
    }
}
