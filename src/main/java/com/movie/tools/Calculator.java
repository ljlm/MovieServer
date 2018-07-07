package com.movie.tools;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

/**
 * Created by lionelm on 2/25/2017.
 */
public class Calculator {


    public static String movieRatingCalculator ( List<Integer> movieRatings){
        float avg = 0;
        for (Integer movieRating : movieRatings){
            avg = avg +movieRating;
        }
        avg= avg / (float) movieRatings.size();
        return avg == avg ? avg + "" : "0.0";

    }

    public static int calculateMoviePurchasePrice(int amount, List<Map<String,Object>> prices){

        List<Price> parcedPrices = new ArrayList<>();
        for (Map<String,Object> price: prices){
            int myPrice,myUnits;
            try{
                myPrice= ((Integer)price.get("price"));
                myUnits = ((Integer)price.get("units"));
            }catch(Exception e){
                throw new InvalidParameterException("Prices and units should be ints");
            }
            parcedPrices.add(new Price(myUnits,myPrice));
        }
        Collections.sort(parcedPrices,new PriceComparator());
        int totalPrice=0;
        int index=0;
        while(amount>0){
            if (parcedPrices.get(index).getUnits() > amount){
                index++;
                continue;
            }
            amount = amount - parcedPrices.get(index).getUnits();
            totalPrice = totalPrice + parcedPrices.get(index).getPrice();
        }
        return totalPrice;
    }

}
