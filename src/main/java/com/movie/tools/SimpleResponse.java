package com.movie.tools;

/**
 * this class holds the format of simple response. In case that the response of a request
 * is not data then the response will be a SimpleResponse type
 */
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
