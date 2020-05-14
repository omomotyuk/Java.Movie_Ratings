
/*
 * MovieDatabase class stores movie information. Allows fast lookup of movie information given a movie ID, and also allows filtering movies based on queries. 
 * 
 * @author: 	Alex Momotyuk 
 * @version: 	20.04
 */

import java.util.*;
import org.apache.commons.csv.*;
import edu.duke.FileResource;

public class MovieDatabase {

// ourMovies maps a movie ID String to a Movie object with all the information about that movie
    private static HashMap<String, Movie> ourMovies;

// A method used to initialize the movie database
    public static void initialize(String moviefile) {
        if (ourMovies == null) {
            ourMovies = new HashMap<String,Movie>();
            loadMovies("data/" + moviefile);
        }
    }

    private static void initialize() {
        if (ourMovies == null) {
            ourMovies = new HashMap<String,Movie>();
            loadMovies("data/ratedmoviesfull.csv");
        }
    }	

	
// A method to build the HashMap ourMovies
    private static void loadMovies(String filename) {
        FirstRatings fr = new FirstRatings();
        ArrayList<Movie> list = fr.loadMovies(filename);
        for (Movie m : list) {
            ourMovies.put(m.getID(), m);
        }
    }

// This method returns true if the id is a movie in the database, and false otherwise
    public static boolean containsID(String id) {
        initialize();
        return ourMovies.containsKey(id);
    }

//
// Several getter methods to return information about movie:
//

    public static int getYear(String id) {
        initialize();
        return ourMovies.get(id).getYear();
    }

    public static String getGenres(String id) {
        initialize();
        return ourMovies.get(id).getGenres();
    }

    public static String getTitle(String id) {
        initialize();
        return ourMovies.get(id).getTitle();
    }

    public static Movie getMovie(String id) {
        initialize();
        return ourMovies.get(id);
    }

    public static String getPoster(String id) {
        initialize();
        return ourMovies.get(id).getPoster();
    }

    public static int getMinutes(String id) {
        initialize();
        return ourMovies.get(id).getMinutes();
    }

    public static String getCountry(String id) {
        initialize();
        return ourMovies.get(id).getCountry();
    }

    public static String getDirector(String id) {
        initialize();
        return ourMovies.get(id).getDirector();
    }

// Returns the number of movies in the database
    public static int size() {
        return ourMovies.size();
    }

// Returns an ArrayList of movie IDs that match the filtering criteria
    public static ArrayList<String> filterBy(Filter f) {
        initialize();
        ArrayList<String> list = new ArrayList<String>();
        for(String id : ourMovies.keySet()) {
            if (f.satisfies(id)) {
                list.add(id);
            }
        }
        
        return list;
    }

}


