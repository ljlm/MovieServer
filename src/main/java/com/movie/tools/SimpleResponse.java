package com.movie.tools;

public class SimpleResponse {
    private DbDataEnums.result result;
    private String cause;

    public SimpleResponse setResult(DbDataEnums.result result) {
        this.result = result;
        return this;
    }

    public SimpleResponse setCause(String cause) {
        this.cause = cause;
        return this;
    }

    public String toString(){
        return "{ \"result\":\""+result+"\" , \"cause\":\""+cause+"\"}";
    }
}
