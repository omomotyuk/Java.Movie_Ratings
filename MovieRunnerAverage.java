
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MovieRunnerAverage
{
    public MovieRunnerAverage()
    {
    }

    
    public void printAverageRatings()
    {
        //SecondRatings movieRatings = new SecondRatings( "data/ratedmovies_short.csv","data/ratings_short.csv" );
        SecondRatings movieRatings = new SecondRatings( "data/ratedmoviesfull.csv","data/ratings.csv" );
        
        System.out.println( "Number of movies : " + movieRatings.getMovieSize() );
        System.out.println( "Number of raters : " + movieRatings.getRaterSize() );
        
        int minimalRaters = 12;
        ArrayList<Rating> ratingList = movieRatings.getAverageRatings( minimalRaters );
        
        System.out.println( "\nNumber of movies with minimal rater number " + minimalRaters + " is " + ratingList.size() + "\n" );
        
        for( Rating item : ratingList )
        {
            //System.out.println( "" + item.getValue() + "\t" + movieRatings.getTitle( item.getItem() ) );
            System.out.println( movieRatings.getTitle( item.getItem() ) + "\t \t \t" + item.getValue() );
            //System.out.format("%.2f \t %s \n", item.getValue(),movieRatings.getTitle( item.getItem() ) );
        }
    }
    
    
    public void getAverageRatingOneMovie()
    {
        //SecondRatings movieRatings = new SecondRatings( "data/ratedmovies_short.csv","data/ratings_short.csv" );
        SecondRatings movieRatings = new SecondRatings( "data/ratedmoviesfull.csv","data/ratings.csv" );
        
        int minimalRaters = 1;
        ArrayList<Rating> ratingList = movieRatings.getAverageRatings( minimalRaters );
        
        //String title = "The Godfather";
        //String title = "The Man with the Iron Fists";
        //String title = "The Maze Runner";
        //String title = "Moneyball";
        String title = "Vacation";
        
        for( Rating item : ratingList )
        {
            if( movieRatings.getTitle( item.getItem() ).equals( title ) )
            {
                System.out.println( "" + movieRatings.getTitle( item.getItem() ) + "\t" + item.getValue() );
            }
        }
    }

    
}
