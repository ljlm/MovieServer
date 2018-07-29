package com.movie.services;

import com.movie.dal.DBManager;
import com.movie.tools.DBRowLockerData;
import com.movie.tools.DBRowUpdateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.movie.tools.constants.DBConstants.*;



@Service
public class LocksService {


    private static DBManager dbManager;

    private static final String _SET_LOCKEDe= " SET locked=";
    private static final String _AND_LOCKEDe=" && locked=";
    private static final String LOCKED = "1";
    private static final String UNLOCKED = "0";
    private static final Logger logger = LoggerFactory.getLogger(DataPopulator.class);

    @Autowired
    public void init (DBManager dbManager){
        LocksService.dbManager=dbManager;
    }

    public static boolean unlockLine (String dbName, String whereStatement){
        return handleLock(false , dbName,whereStatement);
    }

    public static boolean lineExists (String query){
        List list;
        if ( (list = dbManager.queryForList(query)) != null && list.size()>0) {
            return true;
        }
        return false;
    }

    public static boolean lineExists (String dbName, String whereStatement){
        String query = SELECT_ALL_FROM_ + dbName + _WHERE_ +whereStatement +_EOL;
                List list;
        if ( (list = dbManager.queryForList(query)) != null && list.size()>0) {
            return true;
        }
        return false;
    }


    public static boolean isLineLocked (String dbName, String whereStatement){
        String query = SELECT_ALL_FROM_ + dbName + _WHERE_ +whereStatement + _AND_LOCKEDe+"1"+ _EOL;
        List list;
        if ( (list = dbManager.queryForList(query)) != null && list.size()>0) {
            return true;
        }
        return false;
    }

    public static boolean lockMultipleRowsAndSetRow(List<DBRowLockerData> rowsToLock , DBRowUpdateData rowToUpdate ){
        if (lockMultipleRows(rowsToLock)){
            if (setRow(rowToUpdate)){
                unlockMultipleRows(rowsToLock);
                return true;
            }
            unlockMultipleRows(rowsToLock);
            return false;
        }
        return false;

    }


// this method is in charge of set the data in a row in a secured way. it first
    // locks the row and then changes the value

    public static boolean setRow (DBRowUpdateData rowBlockerData){
        int retryCounter=0;
        boolean lineLocked ;
        if (lineExists(rowBlockerData.getDbName(),rowBlockerData.getWhereStatement())) {
            while (retryCounter < 3) {
                lineLocked = lockLine(rowBlockerData.getDbName(), rowBlockerData.getWhereStatement());
                retryCounter++;
                if (lineLocked) {
                    String setQuery = "UPDATE " + rowBlockerData.getDbName() + " SET " + rowBlockerData.getSetStatement() + " WHERE " + rowBlockerData.getWhereStatement() + ";";
                    if (dbManager.updateQuery(setQuery) > 0) {
                        unlockLine(rowBlockerData.getDbName(), rowBlockerData.getWhereStatement());
                        return true;

                    }
                    unlockLine(rowBlockerData.getDbName(), rowBlockerData.getWhereStatement());
                    return false;
                }
                try {
                    Thread.sleep(100 + (new Random().nextInt(15) * 10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean setLockedRow (DBRowUpdateData rowBlockerData){
        if (isLineLocked(rowBlockerData.getDbName(),rowBlockerData.getWhereStatement())){
            String setQuery = "UPDATE " + rowBlockerData.getDbName() + " SET " + rowBlockerData.getSetStatement() + " WHERE " + rowBlockerData.getWhereStatement() + ";";
            if (dbManager.updateQuery(setQuery) > 0) {
                return true;
            }
        }
        return false;
    }

// this method tries to lock multiple rows. if one of them is unsuccessful then it releases
    //all the rows
    public static boolean lockMultipleRows(List<DBRowLockerData> rows){
        List<DBRowLockerData> lockedByMeRows = new ArrayList<>();
        boolean isSucessful= true;
        for (DBRowLockerData rowData : rows){
            if (lineExists(rowData.getDbName(),rowData.getWhereStatement()) && lockLine(rowData.getDbName(),rowData.getWhereStatement() )){
                isSucessful = isSucessful && true;
                lockedByMeRows.add(rowData);
            }
            else {
                isSucessful = isSucessful && false;
            }
        }
        if (!isSucessful){
            handleLockFailure(lockedByMeRows);
        }
        return isSucessful;
    }



    private static void handleLockFailure (List<DBRowLockerData> rows){
        logger.error("Fail to lock multiple rows.");
        unlockMultipleRows( rows);
    }


    public static void unlockMultipleRows(List<DBRowLockerData> rows){
        for (DBRowLockerData rowData : rows){
            if (lineExists(rowData.getDbName(),rowData.getWhereStatement()) ){
                unlockLine(rowData.getDbName(),rowData.getWhereStatement() );
            }
        }
    }


    public static boolean lockLine (String dbName, String whereStatement){
        return handleLock(true , dbName,whereStatement);
    }



    public static boolean handleLock (boolean lock, String dbName,  String whereStatement){
        StringBuilder handleLockLineQuery = new StringBuilder();
        String firstParam = lock ? LOCKED : UNLOCKED;
        String secondParam = lock ? UNLOCKED : LOCKED;

        for (int i=0 ; i<3 ; i++) {
            handleLockLineQuery.append(UPDATE_).append(dbName).append(_SET_LOCKEDe + firstParam + _WHERE_)
                    .append(whereStatement).append(_AND_LOCKEDe + secondParam + _EOL);
            if (dbManager.updateQuery(handleLockLineQuery.toString()) > 0) {
                return true;
            }
            try {
                Thread.sleep(100 + (new Random().nextInt(15) * 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        return false;

    }


}
