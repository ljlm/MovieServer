package com.movie.tools;

/**
 * Created by lionelm on 7/3/2017.
 */
public class DBRowUpdateData extends DBRowLockerData {
    private String setStatement;
    public DBRowUpdateData (String dbName, String whereStatement, String setStatement){
        super(dbName,whereStatement);
        this.setStatement= setStatement;
    }

    public String getDbName() {
        return super.getDbName();
    }

    public String getWhereStatement() {
        return super.getWhereStatement();
    }

    public String getSetStatement() {
        return setStatement;
    }

    public void setDbName(String dbName) {
        super.setDbName(dbName);
    }

    public void setWhereStatement(String whereStatement) {
        super.setWhereStatement(whereStatement);
    }

    public void setSetStatement(String setStatement) {
        this.setStatement = setStatement;
    }
}