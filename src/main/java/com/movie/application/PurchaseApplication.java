package com.movie.application;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.movie.services.DataManager;
import com.movie.tools.Calculator;
import com.movie.tools.DbDataEnums;
import com.movie.tools.SimpleResponse;

/**
 * Created by lionelm on 6/28/2017.
 */
@Component
public class PurchaseApplication {
    @Autowired
    private UserApplication userApplication;


    public SimpleResponse purchaseCredits(int userId, String amountStr) {
        int amount;
        try{
            amount = Integer.parseInt(amountStr);
        }catch (Exception e){
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Invalid amount");
        }
        DataManager.getPurchaseHistoryDataManager().createPurchaseRecord(userId
                ,amount, Calculator.calculateMoviePurchasePrice(amount,
                        DataManager.getPricingDataManager().getPrices()));

        return userApplication.addCreditsToUser(userId, amount);

    }

    public void removePurchaseHistoryForUser(int userId){
        DataManager.getPurchaseHistoryDataManager().removePurchaseHistoryForUser(userId);
    }

    public List<Map<String,Object>> getAllUsersPurchaseHistory (){
        return DataManager.getPurchaseHistoryDataManager().getAllUsersPurchaseHistory();
    }

    public List<Map<String,Object>> getUserPurchaseHistory (int userId){
        return DataManager.getPurchaseHistoryDataManager().getUserPurchaseHistory(userId);
    }

}
