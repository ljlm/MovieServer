package com.movie.dal;

import com.movie.dil.DataIntegrity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lionelm on 2/25/2017.
 */
@EnableAutoConfiguration
@Service
@Component


public class DataManager {

    @Autowired
    public DBManager dbManager;



    public List<Map<String,Object>> getMovieList (){
        List<Map<String,Object>> movies
                = dbManager.queryForList("SELECT * FROM movieserverdb.movies ;");
        return movies;
    }

    public  Map<String, Object> getMovieById (Integer id){
        List<Map<String,Object>> movies
                = dbManager.queryForList("SELECT * FROM movieserver.movies" + " WHERE id="+id+ ";");
        return movies.get(0);

    }

    public  List<Map<String, Object>> getMovieByCategory (Integer category){
        List<Map<String,Object>> movies = dbManager.queryForList("SELECT * FROM movieserverdb.movies WHERE category="+category+  ";");
        return movies;
    }

    public  Map<String, Object> getUserById (Integer id){
        List<Map<String,Object>> user = dbManager.queryForList("SELECT * FROM movieserverdb.users WHERE id="+id + ";");
        return user.get(0);
    }

    public List<Map<String, Object>> getMovieComments(Integer movieId){
        List<Map<String,Object>> response = new ArrayList<>();
        List<Map<String,Object>> comments = dbManager.queryForList("SELECT * FROM movieserverdb.rating WHERE movie_id="+ movieId + ";");
        for (Map<String,Object> comment:comments ) {
            Map<String, Object> userAndComment = new HashMap<>();
            Map <String, Object> user = getUserById((Integer) comment.get("user_id"));
            userAndComment.put("user",user);
            userAndComment.put("comment",comment);
            response.add(userAndComment);
        }
        return response;
    }

    public  void updateMovieRating (Integer movieId, Integer userId, Integer rating){

        StringBuilder selectLinequery = new StringBuilder();


        selectLinequery.append("SELECT * FROM movieserverdb.rating WHERE user_id=").append(userId).append(" && movie_id=").append(movieId).append(";");
        if (lineExists(selectLinequery.toString())){
            StringBuilder updateLineQuery = new StringBuilder();
            lockLine("movieserverdb.rating", "user_id="+userId + " && movie_id=" + movieId );
            updateLineQuery.append("UPDATE movieserverdb.rating SET rating=").append(rating).append(" WHERE user_id=").append(userId).append(" && movie_id=").append(movieId).append(" && locked=1;");
            dbManager.updateQuery(updateLineQuery.toString());
            unlockLine("movieserverdb.rating", "user_id="+userId + " && movie_id=" + movieId );

        }
        else{
            String insertQuery = "INSERT INTO rating (user_id ,movie_id, rating, comment,locked) VALUES (?, ?,?,?,? ) ";
            int[] types = new int[] { Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.INTEGER};
            Object[] params = new Object[] { userId, movieId ,rating, "",0};
            dbManager.insertQuery(insertQuery, params, types);
        }

    }

    private boolean lockLine (String dbName, String whereStatement){
        return handleLock(true , dbName,whereStatement);
    }



    private boolean handleLock (boolean lock, String dbName,  String whereStatement){
        StringBuilder handleLockLineQuery = new StringBuilder();
        String firstParam = lock ? "1" : "0";
        String secondParam = lock ? "0" : "1";

        handleLockLineQuery.append("UPDATE ").append(dbName).append(" SET locked="+firstParam +" WHERE ").append(whereStatement).append(" && locked="+secondParam +"  ; ");
        if (dbManager.updateQuery(handleLockLineQuery.toString()) > 0){
            return true;
        }
        return false;


    }


    private boolean unlockLine (String dbName, String whereStatement){
        return handleLock(false , dbName,whereStatement);

    }

    private boolean lineExists (String query){
        List list;
        if ( (list = dbManager.queryForList(query)) != null && list.size()>0) {
            return true;
        }
        return false;
    }


    public  Integer insertUser (String userName , String pass,String fName ,String lName ){
        String inserQuery = "INSERT INTO users (user_name, password, first_name, last_name,credits) VALUES (?, ?, ?, ?,?) ";
        int[] types = new int[] { Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR ,Types.INTEGER};

        Object[] params = new Object[] { userName,pass,fName, lName,0};
        dbManager.insertQuery(inserQuery, params, types);
        return 1;

    }

    public int getUserIdIfExists (String username, String password){
        String query = "SELECT * FROM movieserverdb.users WHERE user_name='"+username + "';";
        List<Map<String,Object>> users = dbManager.queryForList(query.toString());
        if (users!=null){
            Map<String, Object> user = users.get(0);
            if (user != null && user.get("password").equals(password)){
                return (Integer) user.get("id");
            }
        }
        return -1;
    }


    public void calculateMoviesRating () {
        List<Map<String,Object>> movies = getMovieList ();
        for (int i=0; i< movies.size() ; i++){
            movies.get(i)
        }


    }

}
