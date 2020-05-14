
/*
 * Class MovieRunnerWithFilters used to find the average rating of movies using different filters
 * 
 * @author: 	Alex Momotyuk 
 * @version: 	20.04
 */

import java.util.*;

public class MovieRunnerWithFilters
{
    private ThirdRatings movieRatings;
    
    
    private void initialize()
    {
        //MovieDatabase.initialize( "ratedmovies_short.csv" );
        MovieDatabase.initialize( "ratedmoviesfull.csv" );
        
        //movieRatings = new ThirdRatings( "data/ratings_short.csv" );
        movieRatings = new ThirdRatings( "data/ratings.csv" );
        
        System.out.println( "\n\nNumber of movies : " + MovieDatabase.size() );
        System.out.println( "Number of raters : " + movieRatings.getRaterSize() );        
    }

    
    public void printAverageRatings()
    {
        initialize();
        
        int minimalRaters = 35;
        ArrayList<Rating> ratingList = movieRatings.getAverageRatings( minimalRaters );
        
        System.out.println( "Number of movies with minimal rater number " + minimalRaters + " is " + ratingList.size() + "\n" );
        
        for( Rating item : ratingList )
        {
            System.out.println( MovieDatabase.getTitle( item.getItem() ) + "\t \t \t" + item.getValue() );
            //System.out.format("%.2f \t %s \n", item.getValue(),movieRatings.getTitle( item.getItem() ) );
        }
    }

    
    public void printAverageRatingsByYear()
    {
        initialize();
       
        int minimalRaters = 20;
        int year = 2000;
        
        Filter yearAfter = new YearAfterFilter( year );
        ArrayList<Rating> ratingList = movieRatings.getAverageRatingsByFilter( minimalRaters,yearAfter );        

        System.out.println( "Number of movies with minimal rater number " + minimalRaters + " and year filter " + year + " is " + ratingList.size() + "\n" );
        
        for( Rating item : ratingList )
        {
            System.out.println( "" + MovieDatabase.getYear( item.getItem() ) + " " + MovieDatabase.getTitle( item.getItem() ) + " \t" + item.getValue() );
        }
    }

    
    public void printAverageRatingsByGenre()
    {
        initialize();
        
        int minimalRaters = 20;
        //String genre = "Crime";
        String genre = "Comedy";
        
        Filter genreFilter = new GenreFilter( genre );
        ArrayList<Rating> ratingList = movieRatings.getAverageRatingsByFilter( minimalRaters,genreFilter );
        
        System.out.println( "Number of movies with minimal rater number " + minimalRaters + " and genre filter " + genre + " is " + ratingList.size() + "\n" );
        
        for( Rating item : ratingList )
        {
            System.out.println( "" + MovieDatabase.getYear( item.getItem() ) + " " + MovieDatabase.getTitle( item.getItem() ) + " \t" + item.getValue() );
            System.out.println( "\t" + MovieDatabase.getGenres( item.getItem() ) );
        }
    }

    
    public void printAverageRatingsByMinutes()
    {
        initialize();
        
        int minimalRaters = 5;
        int minMinutes = 105;
        int maxMinutes = 135;
        
        Filter minutesFilter = new MinutesFilter( minMinutes,maxMinutes );
        ArrayList<Rating> ratingList = movieRatings.getAverageRatingsByFilter( minimalRaters,minutesFilter );
        
        System.out.println( "Number of movies with minimal rater number " + minimalRaters + " and minutes " + minMinutes + " - " + maxMinutes + " is " + ratingList.size() + "\n" );
        
        for( Rating item : ratingList )
        {
            System.out.println( "" + MovieDatabase.getYear( item.getItem() ) + " " + MovieDatabase.getTitle( item.getItem() ) + " " +
                                     MovieDatabase.getMinutes( item.getItem() ) + " \t" + item.getValue() );
        }
    }

    
    public void printAverageRatingsByDirectors()
    {
        initialize();
        
        int minimalRaters = 4;
        //String directors = "Charles Chaplin,Michael Mann,Spike Jonze";
        String directors = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        
        Filter directorsFilter = new DirectorsFilter( directors );
        ArrayList<Rating> ratingList = movieRatings.getAverageRatingsByFilter( minimalRaters,directorsFilter );
        
        System.out.println( "Number of movies with minimal rater number " + minimalRaters + " and directors " + directors + " is " + ratingList.size() + "\n" );
        
        for( Rating item : ratingList )
        {
            System.out.println( "" + MovieDatabase.getYear( item.getItem() ) + " " + MovieDatabase.getTitle( item.getItem() ) + " \t" + item.getValue() );
            System.out.println( "\t" + MovieDatabase.getDirector( item.getItem() ) );
        }
    }
    

    public void printAverageRatingsByYearAfterAndGenre()
    {
        initialize();
        
        int minimalRaters = 8;
        int year = 1990;
        //String genre = "Romance";
        String genre = "Drama";
        
        Filter yearAfter = new YearAfterFilter( year );
        Filter genreFilter = new GenreFilter( genre );
        
        AllFilters filters = new AllFilters();
        
        filters.addFilter( yearAfter );
        filters.addFilter( genreFilter );
        
        ArrayList<Rating> ratingList = movieRatings.getAverageRatingsByFilter( minimalRaters,filters );
        
        System.out.println( "Number of movies with minimal rater number " + minimalRaters + " and year " + year + " and genre " + genre + " is " + ratingList.size() + "\n" );
        
        for( Rating item : ratingList )
        {
            System.out.println( "" + MovieDatabase.getYear( item.getItem() ) + " " + MovieDatabase.getTitle( item.getItem() ) + " \t" + item.getValue() );
            System.out.println( "\t" + MovieDatabase.getGenres( item.getItem() ) );
        }
    }

    
    public void printAverageRatingsByDirectorsAndMinutes()
    {
        initialize();
        
        int minimalRaters = 3;
        int minMinutes = 90;
        int maxMinutes = 180;
        //String directors = "Spike Jonze,Michael Mann,Charles Chaplin,Francis Ford Coppola";
        String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
        
        Filter minutesFilter = new MinutesFilter( minMinutes,maxMinutes );
        Filter directorsFilter = new DirectorsFilter( directors );
        
        AllFilters filters = new AllFilters();
        
        filters.addFilter( minutesFilter );
        filters.addFilter( directorsFilter );
        
        ArrayList<Rating> ratingList = movieRatings.getAverageRatingsByFilter( minimalRaters,filters );
        
        System.out.println( "Number of movies with minimal rater number " + minimalRaters + " and minutes " + minMinutes + " - " + maxMinutes + " and directors " + directors + " is " + ratingList.size() + "\n" );
        
        for( Rating item : ratingList )
        {
            System.out.println( "" + MovieDatabase.getYear( item.getItem() ) + " " + MovieDatabase.getTitle( item.getItem() ) + " " +
                                     MovieDatabase.getMinutes( item.getItem() ) + " \t" + item.getValue() );
            System.out.println( "\t" + MovieDatabase.getDirector( item.getItem() ) );
        }
    }

}
