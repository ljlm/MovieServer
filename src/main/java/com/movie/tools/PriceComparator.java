package com.movie.tools;

import java.util.Comparator;

/**
 * this tool implements the Comparator to sort prices
 */

public class PriceComparator implements Comparator<Price>{
    @Override
    public int compare(Price o1, Price o2) {
        return o2.getUnits() - o1.getUnits();
    }
}
