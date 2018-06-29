package com.movie.tools.errors;

public class AlreadyExistentMovieException extends Exception {
    public AlreadyExistentMovieException(String s) {
        super(s);
    }
}
