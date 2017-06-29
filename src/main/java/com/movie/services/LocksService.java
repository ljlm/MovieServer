package com.movie.services;

import com.movie.dal.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.movie.tools.constants.DBConstants.UPDATE_;
import static com.movie.tools.constants.DBConstants._EOL;
import static com.movie.tools.constants.DBConstants._WHERE_;

/**
 * Created by lionelm on 6/28/2017.
 */
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
    public static boolean lockLine (String dbName, String whereStatement){
        return handleLock(true , dbName,whereStatement);
    }

    public static boolean handleLock (boolean lock, String dbName,  String whereStatement){
        StringBuilder handleLockLineQuery = new StringBuilder();
        String firstParam = lock ? LOCKED : UNLOCKED;
        String secondParam = lock ? UNLOCKED : LOCKED;

        handleLockLineQuery.append(UPDATE_).append(dbName).append(_SET_LOCKEDe+firstParam +_WHERE_)
                .append(whereStatement).append(_AND_LOCKEDe+secondParam +_EOL);
        if (dbManager.updateQuery(handleLockLineQuery.toString()) > 0){
            return true;
        }
        return false;
    }
}
