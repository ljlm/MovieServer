package com.movie.tools;


public class DBRowLockerData {
    private String dbName;
    private String whereStatement;
    public DBRowLockerData(String dbName, String whereStatement){
        this.dbName=dbName;
        this.whereStatement=whereStatement;
    }

    public String getDbName() {
        return dbName;
    }

    public String getWhereStatement() {
        return whereStatement;
    }


    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setWhereStatement(String whereStatement) {
        this.whereStatement = whereStatement;
    }

}