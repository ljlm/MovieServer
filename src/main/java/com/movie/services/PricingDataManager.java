package com.movie.services;

import static com.movie.tools.constants.DBConstants._EOL;
import static com.movie.tools.constants.MoviesDBConstants.SELECT_ALL_FROM_MOVIES;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dal.DBManager;
import com.movie.tools.DbDataEnums;
import com.movie.tools.SimpleResponse;

/**
 * Created by lionelm on 6/28/2017.
 */
@Service
public class PricingDataManager {
    private String insertQuery = "INSERT INTO pricing (units ,price) VALUES (?, ? ) ";
    private int [] types = new int[] { Types.INTEGER,Types.INTEGER};

    @Autowired
    public DBManager dbManager;

    public List<Map<String,Object>> getPrices(){
        List<Map<String,Object>> prices  = dbManager.queryForList("SELECT * FROM movieserverdb.pricing");
        return prices;
    }


}
