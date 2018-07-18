package com.movie.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Filter {
    private static String [] wordsToFilterArr = {"and", "furthermore", "moreover", "besides", "also", "too", "another", "as", "then", "to", "in"
    , "the", "that", "at" , "but", "a" , "my", "for"};

    private static List<String> wordsToFilter = Arrays.asList(wordsToFilterArr);

    public static List<String> purifyKeywords (List<String> unfilteredKeywords){

        List<String> filteredKeyWords = new ArrayList<>();

        for (String unfilteredKeyword : unfilteredKeywords){
            if (!wordsToFilter.contains(unfilteredKeyword)){
                filteredKeyWords.add(unfilteredKeyword);
            }
        }
        return filteredKeyWords;
    }


}
