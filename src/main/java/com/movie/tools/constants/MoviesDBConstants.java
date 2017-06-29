package com.movie.tools.constants;

import static com.movie.tools.constants.DBConstants.SELECT_ALL_FROM_;
import static com.movie.tools.constants.DBConstants._WHERE_;

/**
 * Created by lionelm on 6/28/2017.
 */
public class MoviesDBConstants {
    public static final String MOVIES =" movieserverdb.movies ";
    public static final String CATEGORIES = " movieserverdb.categories";
    public static final String SELECT_ALL_FROM_MOVIES = SELECT_ALL_FROM_+MOVIES;
    public static final String SELECT_ALL_FROM_CATEGORIES = SELECT_ALL_FROM_+CATEGORIES;
    public static final String SELECT_ALL_FROM_MOVIES_WHERE_ = SELECT_ALL_FROM_MOVIES+_WHERE_;




}
