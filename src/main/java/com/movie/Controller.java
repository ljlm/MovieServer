package com.movie;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Controller {

    @RequestMapping("/help")
    public String index() {
        return "Greetings from Movie System";
    }
//function getMovie()
//    return array of array {Id of movie,name,png url,year,rating}
    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public String getMovies(){
        return "List of all movies";
    }
//function getRatingComment(movieID) return array of arrays String  {User ID,Commment ,rating} order by Rating
    @RequestMapping(value = "/comments/{movieID}", method = RequestMethod.GET)
    public String getMoviesComments(@PathVariable String movieID){
        return "List of comments for movie="+movieID;
    }

    @RequestMapping(value = "/movies/{movieID}/{rating}", method = RequestMethod.GET)
    public String AddMovieRating(@PathVariable String movieID,@PathVariable String rating){
        return "User rated movie "+movieID+" as "+rating;
    }

//    getTopTenMovies() return array of array list movies {Id of movie,name,png url,year,rating,}

    @RequestMapping(value = "/movies/topten", method = RequestMethod.GET)
    public String getTopTenMovies(){
        return "List of top 10 movies";
    }
//    function rentMovie(MovieId) {creditLeft}
    @RequestMapping(value = "/movies/{movieID}", method = RequestMethod.GET)
    public String returnMovie (@PathVariable String movieID){
        return movieID+" returned";
    }

//    function returnMovie(MovieId) {}

//

//    function acountinfo(userid){creditLeft,firstName,LastName,arrayofmovies}


}