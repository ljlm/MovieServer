package com.movie.services;

import com.movie.dal.DBManager;
import com.movie.tools.DBRowUpdateData;
import com.movie.tools.DbDataEnums;
import com.movie.tools.SimpleResponse;
import com.movie.tools.errors.AlreadyExistentMovieException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import static com.movie.tools.constants.DBConstants._EOL;
import static com.movie.tools.constants.DBConstants._IDe;
import static com.movie.tools.constants.MoviesDBConstants.SELECT_ALL_FROM_CATEGORIES;
import static com.movie.tools.constants.MoviesDBConstants.SELECT_ALL_FROM_MOVIES;
import static com.movie.tools.constants.MoviesDBConstants.SELECT_ALL_FROM_MOVIES_WHERE_;

/**
 * Created by lionelm on 6/28/2017.
 */
@Service
public class MovieDataService {

    private int[] types = new int[] { Types.VARCHAR,Types.VARCHAR, Types.INTEGER,Types.INTEGER , Types.VARCHAR ,Types.FLOAT,Types.INTEGER,Types.INTEGER,Types.INTEGER};
    private String inserQuery = "INSERT INTO movies (movie_name, pic_link , year, category, info,rating ,raters ,available,locked ) VALUES (?, ?,?,?,?,?,?,?,?) ";
    @Autowired
    public DBManager dbManager;


    public List<Map<String,Object>> getMovieList (){
        List<Map<String,Object>> movies  = dbManager.queryForList(SELECT_ALL_FROM_MOVIES+_EOL);
        return movies;
    }


    public  Map<String, Object> getMovieById (Integer id){
        List<Map<String,Object>> movies = dbManager.queryForList(SELECT_ALL_FROM_MOVIES_WHERE_+_IDe+id+_EOL);
        return movies.get(0);
    }

    public  List<Map<String, Object>> getMovieByCategory (Integer category){
        List<Map<String,Object>> movies = dbManager.queryForList(SELECT_ALL_FROM_MOVIES_WHERE_+"category="+category+  ";");
        return movies;
    }

//    public void updateMovieRating (Integer movieID, String rating, int raters) throws Exception {
//        StringBuilder selectLinequery = new StringBuilder();
//        selectLinequery.append("SELECT * FROM movieserverdb.movies WHERE id=").append(movieID).append(";");
//        if (LocksService.lineExists(selectLinequery.toString()) &&
//                LocksService.lockLine("movieserverdb.movies", "id="+movieID   )  ){
//            StringBuilder updateLineQuery = new StringBuilder();
////            LocksService.lockLine("movieserverdb.movies", "id="+movieID   );
//            updateLineQuery.append("UPDATE movieserverdb.movies SET rating=").append(rating).append(" , raters=").append(raters).append(" WHERE id=").append(movieID).append(" && locked=1;");
//            dbManager.updateQuery(updateLineQuery.toString());
//            LocksService.unlockLine("movieserverdb.movies", "id=" + movieID );
//            return;
//        }
//        throw new Exception("Line was not found on DB");
//
//    }

    public void updateMovieRating (Integer movieID, String rating, int raters) throws Exception {
        StringBuilder whereStatement = new StringBuilder();
        whereStatement.append("id=").append(movieID);
        StringBuilder setStatement = new StringBuilder();
        setStatement.append("rating=").append(rating).append(" , raters=").append(raters);
        DBRowUpdateData rowUpdateData = new DBRowUpdateData(" movieserverdb.movies", whereStatement.toString(),setStatement.toString());
        if (!LocksService.setRow(rowUpdateData)){
            throw new  Exception("unable to update row");
        }

    }


    public List <String> getCategories(){
        return dbManager.queryForList(SELECT_ALL_FROM_CATEGORIES+  ";");
    }

    public SimpleResponse addMovie(String movieName, String picLink, int year, int category, String info, int available) throws AlreadyExistentMovieException {
        List<Map<String,Object>> movies = dbManager.queryForList(SELECT_ALL_FROM_MOVIES_WHERE_+"movie_name='" + movieName + "' && year='"+year+   "';");
        if (movies.size() > 0){
            throw new AlreadyExistentMovieException("The requested movie " + movieName + "is already listed in database.");
        }
        Object[] params = new Object[] { movieName,picLink,year, category,info,0,0,available,0};
        if (dbManager.insertQuery(inserQuery, params, types) != 1){
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Unable to add movie.");
        }
        return new SimpleResponse().setResult(DbDataEnums.result.SUCCESS);
    }

}
