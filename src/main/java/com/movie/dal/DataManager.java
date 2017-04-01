package com.movie.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.*;

/**
 * Created by lionelm on 2/25/2017.
 */
@EnableAutoConfiguration
@Service


public class DataManager {

    @Autowired
    public DBManager dbManager;

    private static final String SHARED_LOCK =" LOCK IN SHARE MODE;";
    private static final String PRIVATE_LOCK=" FOR UPDATE;";

    public List<Map<String,Object>> getMovieList (){
        List<Map<String,Object>> movies
                = dbManager.queryForList("SELECT * FROM movieserverdb.movies"+ SHARED_LOCK);
        return movies;
    }

    public  Map<String, Object> getMovieById (Integer id){
        List<Map<String,Object>> movies
                = dbManager.queryForList("SELECT * FROM movieserver.movies" + " WHERE id="+id+ SHARED_LOCK);
        return movies.get(0);
    }

    public  List<Map<String, Object>> getMovieByCategory (Integer category){
        List<Map<String,Object>> movies = dbManager.queryForList("SELECT * FROM movieserverdb.movies WHERE category="+category+ SHARED_LOCK);
        return movies;
    }

    public  Map<String, Object> getUserById (Integer id){
        List<Map<String,Object>> user = dbManager.queryForList("SELECT * FROM movieserverdb.users WHERE id="+id + SHARED_LOCK);
        return user.get(0);
    }

    public List<Map<String, Object>> getMovieComments(Integer movieId){
        List<Map<String,Object>> response = new ArrayList<>();
        List<Map<String,Object>> comments = dbManager.queryForList("SELECT * FROM movieserverdb.rating WHERE movie_id="+ movieId + SHARED_LOCK);
        for (Map<String,Object> comment:comments ) {
            Map<String, Object> userAndComment = new HashMap<>();
            Map <String, Object> user = getUserById((Integer) comment.get("user_id"));
            userAndComment.put("user",user);
            userAndComment.put("comment",comment);
            response.add(userAndComment);
        }
        return response;
    }
//TODO fix it
    public  boolean updateMovieRating (Integer movieId, Integer userId, Integer rating){
        String inserQuery = "INSERT INTO movies (user_name, password, first_name, last_name,credits) VALUES (?, ?, ?, ?,?) ";
        int[] types = new int[] { Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR ,Types.INTEGER};

        Object[] params = new Object[] { "lionelmina","lionelmina","Lionel", "mina",0};
        dbManager.insertQuery(inserQuery, params, types);

        return true;
//        SELECT * FROM movieserverdb.rating WHERE id=1
    }

    //TODO fix it
    public String updateMovieRatingAndRaters(int movieId , Float rating, int raters, int userId){

//        verify if there is already an
        StringBuilder quary = new StringBuilder();
        quary.append("UPDATE movieserverdb.movies SET raters=").append(raters).append("  WHERE CustomerID=").append(movieId).append(";");
//        String quary = "UPDATE movieserverdb.movies SET raters="+raters +"  WHERE CustomerID="+movieId+";";
//        Object[] params = new Object[] { "Spirited Away",2004, Categories.ANIMATION,"During her family's move to the suburbs, a sullen 10-year-old girl wanders " +
//                "into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.",0,0,3};
        dbManager.updateQuery(quary.toString());
        return "";
    }

    //TODO fix it
    public int getMovieRatingByUser(int movieId, int userId){
        StringBuilder quary = new StringBuilder();
        quary.append("SELECT * FROM movieserverdb.rating WHERE user_id=").append(userId).append(" && movie_id=").append(movieId).append(";");

        List<Map<String,Object>> movies = dbManager.queryForList(quary.toString());
        if (movies.size()>0){
            Integer rating = (Integer) movies.get(0).get("rating");
            return rating;
        }
        return 0;
    }

    public int rateMovie (int movieId, int userId, int rating){
        return 0;
    }

////TODO fix it
//    public Map<String, Object> updateMovieRating (int movieId,  int user_id ,int rating ) throws Exception {
//        if (attemptToLockLine("movieserverdb.movies", movieId )){
//            if (attemptToLockLine("movieserverdb.movies", movieId)
//        }
//
//        try {
//            String query = ("UPDATE movieserverdb.movies SET locked=1 WHERE id=" + movieId + " && locked=0" + PRIVATE_LOCK);
//            if (dbManager.updateQuery(query) != 1) {
//                query = ("SELECT * movieserverdb.movies WHERE id=" + movieId + " " + SHARED_LOCK);
//                if (dbManager.updateQuery(query) == 1) {
//                    Thread.sleep(new Random().nextInt(150)+150);
//                    return updateMovieRating ( movieId,   user_id , rating );
//                }
////                no such line. need to create
//
//            }
//            else{
//
//            }
//            String query2 = ("SELECT * FROM movieserverdb.movies WHERE id=" + movieId + " ;");
//            List<Map<String, Object>> movies = jdbcTemplate.queryForList(query2);
//            if (movies.size() == 1) {
//                int raters = (Integer) movies.get(0).get("raters") + 1;
//                String query3 = ("UPDATE movieserverdb.movies SET raters=" + raters + " && rating= " + rating + " WHERE id=" + movieId + PRIVATE_LOCK);
//                int res2 = jdbcTemplate.update(query);
//                System.out.println(res2);
//                String query4 = ("UPDATE movieserverdb.movies SET locked=0 WHERE id=" + movieId + " && locked=1" + PRIVATE_LOCK);
//                int res3 = jdbcTemplate.update(query);
//
//
//            } else {
////            TODO retry
//            }
//
//            return null;
//        }
//        catch (Exception ex){
//            throw new Exception ();
//        }
//    }

    private boolean attemptToLockLine (String db, int id){
        String updateQuery = ("UPDATE "+ db +" SET locked=1 WHERE id=" + id + " && locked=0" + PRIVATE_LOCK);
        String selectQuery= ("SELECT * FROM " + db + " WHERE id=" + id + " ;");
        return handleLock(updateQuery, selectQuery);
    }

    private boolean attempTofreeLine (String db, int id){
        String updateQuery = ("UPDATE "+ db +" SET locked=0 WHERE id=" + id + " && locked=1" + PRIVATE_LOCK);
        String selectQuery= ("SELECT * FROM " + db + " WHERE id=" + id + " ;");
        return handleLock(updateQuery, selectQuery);
    }

    private boolean handleLock (String updateQuery, String selectQuery){
        if (dbManager.updateQuery(updateQuery) == 1){
            return true;
        }
        else {
            List list;
            if ( (list = dbManager.queryForList(selectQuery)) != null && list.size()>0) {
                return true;
            }
            else {
                try{
                    Thread.sleep(new Random().nextInt(150)+150);
                }catch (Exception e)
                {

                }
            }
        }
        return false;
    }


    public void unrateMovie(int movieId, int userId){
        StringBuilder quary = new StringBuilder();
        quary.append("UPDATE  movieserverdb.rating SET rating=").append("0").append("  WHERE movie_id=").append(movieId).append(" && user_id=").append(userId).append(PRIVATE_LOCK);
        dbManager.updateQuery(quary.toString());
        getRatersForMovie(movieId);

    }

    private  Integer getRatersForMovie (int movieId){
        StringBuilder quary = new StringBuilder();
        quary.append("SELECT * FROM movieserverdb.movies WHERE id=").append(movieId).append(";");
        List<Map<String,Object>> raters =  dbManager.queryForList(quary.toString());
        raters.get(0).get("raters");
        return (Integer) raters.get(0).get("raters");

    }
    
    public  Integer insertUser (String userName , String pass,String fName ,String lName ){
    	  String inserQuery = "INSERT INTO users (user_name, password, first_name, last_name,credits) VALUES (?, ?, ?, ?,?) ";
          int[] types = new int[] { Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR ,Types.INTEGER};

          Object[] params = new Object[] { userName,pass,fName, lName,0};
          dbManager.insertQuery(inserQuery, params, types);
        return (1);

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

}
