package com.movie.tools;

import java.util.Comparator;

public class PriceComparator implements Comparator<Price>{
    @Override
    public int compare(Price o1, Price o2) {
        return o2.getUnits() - o1.getUnits();
    }
}
