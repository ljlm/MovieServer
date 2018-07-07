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
public class PurchaseHistoryDataManager {
    private String insertQuery = "INSERT INTO purchase_history (user_id ,amount, price, date,locked) VALUES (?, ?,?,?,? ) ";
    private int [] types = new int[] { Types.INTEGER,Types.INTEGER,Types.FLOAT,Types.DATE,Types.INTEGER};
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public DBManager dbManager;

    public List<Map<String,Object>> getAllUsersPurchaseHistory (){
        List<Map<String,Object>> purchaseHistory  = dbManager.queryForList("SELECT * from movieserverdb.purchase_history;");
        return purchaseHistory;
    }

    public List<Map<String,Object>> getUserPurchaseHistory (int userId){
        List<Map<String,Object>> purchaseHistory  = dbManager.queryForList("SELECT * from movieserverdb.purchase_history WHERE user_id='" + userId + " ';");
        return purchaseHistory;
    }


    public SimpleResponse createPurchaseRecord (int userId, int amount, int price ){
        Calendar cal = Calendar.getInstance();
        String today = dateFormat.format(cal.getTime());
        Object[] params = new Object[] { userId, amount ,price, today,0};
        if (dbManager.insertQuery(insertQuery, params, types)==1){
            return new SimpleResponse().setResult(DbDataEnums.result.SUCCESS);
        }
        return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Unable to purchase movie credits.");

    }


    public void removePurchaseHistoryForUser(int userId) {
        dbManager.updateQuery("DELETE FROM movieserverdb.purchase_history WHERE user_id="+userId+";") ;
    }
}
