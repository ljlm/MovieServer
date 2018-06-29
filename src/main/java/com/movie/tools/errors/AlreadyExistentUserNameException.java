package com.movie.tools.errors;

public class AlreadyExistentUserNameException extends Throwable {
    public AlreadyExistentUserNameException(String s) {
        super(s);
    }
}
