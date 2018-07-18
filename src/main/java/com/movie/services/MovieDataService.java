package com.movie.services;

import com.movie.dal.DBManager;
import com.movie.tools.DBRowUpdateData;
import com.movie.tools.DbDataEnums;
import com.movie.tools.Filter;
import com.movie.tools.SimpleResponse;
import com.movie.tools.errors.AlreadyExistentMovieException;
import com.mysql.jdbc.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.ArrayList;
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

    public SimpleResponse editMovie(int movieID, String movieName, String picLink, int year,
                                    int category, String info, int available) {
        StringBuilder whereStatement = new StringBuilder();
        whereStatement.append("id=").append(movieID);
        StringBuilder setStatement = new StringBuilder();

        if (!StringUtils.isEmptyOrWhitespaceOnly(movieName)){
            setStatement.append("movie_name='").append(movieName).append("' ");
        }

        if (!StringUtils.isEmptyOrWhitespaceOnly(picLink)){
            if(setStatement.length() != 0 && !setStatement.substring(setStatement.length()-2).equals(',')){
                setStatement.append(" , ");
            }
            setStatement.append("pic_link='").append(picLink).append("' ");
        }

        if (year != -1){
            if(setStatement.length() != 0 && !setStatement.substring(setStatement.length()-2).equals(',')){
                setStatement.append(" , ");
            }
            setStatement.append("year=").append(year).append(" ");
        }

        if (category != -1){
            if(setStatement.length() != 0 && !setStatement.substring(setStatement.length()-2).equals(',')){
                setStatement.append(" , ");
            }
            setStatement.append("category=").append(category).append(" ");
        }

        if (!StringUtils.isEmptyOrWhitespaceOnly(info)){
            if(setStatement.length() != 0 && !setStatement.substring(setStatement.length()-2).equals(',')){
                setStatement.append(" && ");
            }
            setStatement.append("info='").append(info).append("' ");
        }

        if (available != -1){
            if(setStatement.length() != 0 && !setStatement.substring(setStatement.length()-2).equals(',')){
                setStatement.append(" , ");
            }
            setStatement.append("available=").append(available).append(" ");
        }

        if (setStatement.length() == 0){
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Nothing to update");
        }

        DBRowUpdateData rowUpdateData = new DBRowUpdateData(" movieserverdb.movies", whereStatement.toString(),setStatement.toString());
        if (!LocksService.setRow(rowUpdateData)){
            return new SimpleResponse().setResult(DbDataEnums.result.FAILURE).setCause("Unable to update movie");
        }
        return new SimpleResponse().setResult(DbDataEnums.result.SUCCESS);
    }

    public List<Map<String,Object>> searchForMovies(List<String> keyWords) {
        List<Map<String,Object>> movies = new ArrayList<>();
        if (keyWords.size()==0){
            return getMovieList ();
        }

        movies.addAll(dbManager.queryForList(createWhereExactSearchQuery("SELECT * FROM movieserverdb.movies","movie_name", keyWords)));
        if (movies.isEmpty()) {
            List<String> filteredKeywords = Filter.purifyKeywords(keyWords);
            if (filteredKeywords.size()>0){
                movies.addAll(dbManager.queryForList(createWhereLoseSearchQuery("SELECT * FROM movieserverdb.movies", "movie_name", keyWords)));
                movies.addAll(dbManager.queryForList(createWhereLoseSearchQuery("SELECT * FROM movieserverdb.movies",
                        "info", filteredKeywords)));
            }

        }
        //        movies.addAll(dbManager.queryForList(createWhereSearchQuery("SELECT * FROM movieserverdb.rating","comment", keyWords)));
        return movies;
    }

    private String createWhereLoseSearchQuery(String selectStatement, String columnName, List<String> keyWords){
        return createWhereSearchQuery("or",selectStatement,columnName,keyWords);
    }

    private String createWhereExactSearchQuery(String selectStatement, String columnName, List<String> keyWords){
        return createWhereSearchQuery("and",selectStatement,columnName,keyWords);
    }

    private String createWhereSearchQuery(String operator, String selectStatement, String columnName, List<String> keyWords){
        StringBuilder query  = new StringBuilder();
        query.append(selectStatement).append(" WHERE ");
        boolean isFirst=true;
        for (String keyWord : keyWords){
            if (!isFirst){
                query.append(" " + operator + " ");
            }
            query.append(columnName + " LIKE '%"+keyWord).append("%'");
            isFirst=false;
        }
        query.append(";");
        return query.toString();
    }



}
