
/*
 * SecondRatings class used to calculate averages on movie ratings
 * 
 * @author: 	Alex Momotyuk 
 * @version: 	20.04
 */

import java.util.*;

public class SecondRatings
{
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    //
    private FirstRatings myMovieRatings;
    private ArrayList<DataMap> myMovieRaterSort;
    
    
        // default constructor
    public SecondRatings() {
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    
	// constructor 
    public SecondRatings( String moviefile,String ratingsfile )
    {
        FirstRatings myMovieRatings = new FirstRatings();
        
        myMovies = myMovieRatings.loadMovies( moviefile );
        myRaters = myMovieRatings.loadRaters( ratingsfile );
        
        ArrayList<DataMap> movieRater = myMovieRatings.getMovieRaterMap( myRaters );
        myMovieRaterSort = myMovieRatings.sortByDataNumber( movieRater );

    }
    
	//  getMovieSize returns the number of movies that were read in and stored in the ArrayList of type Movie    
    public int getMovieSize()
    {
        return myMovies.size();
    }
    

	// getRaterSize returns the number of raters that were read in and stored in the ArrayList of type Rater    
    public int getRaterSize()
    {
        return myRaters.size();
    }
    
    
    private double getRating( String id,String rater_id )
    {
        for( Rater item : myRaters )
        {
            if( item.getID().equals( rater_id ) )
            {
                ArrayList<Rating> ratingList = item.getRatings();
                
                for( Rating r_item : ratingList )
                {
                    if( r_item.getItem().equals( id ) )
                    {
                        return r_item.getValue();
                    }
                }
            }
        }
        
        return 0.;
    }
    
	// getAverageByID returns a double representing the average movie rating for this ID if there are at least minimalRaters ratings    
    private double getAverageByID( String id,int minimalRaters )
    {
        
        for( DataMap item : myMovieRaterSort )
        {
            if( item.getKey() == id )
            {
                if( item.getData().size() < minimalRaters )
                {
                    return 0.;
                }
                else
                {
                    double average = 0.;
                    ArrayList<String> raterList = item.getData();
                   
                    for( String rater : raterList )
                    {
                        average += getRating( id,rater );
                    }
                    
                    return average / raterList.size();
                }
            }
        }
        
        return 0.;
    }

    
    private int getSmallestByRating( ArrayList<Rating> list )
    {
        int smallest = 0;
        double number = list.get( smallest ).getValue(); // 
        
        for( Rating item : list )
        {
            if( number > item.getValue() )
            {
                number = item.getValue();
                smallest = list.indexOf( item );
            }
        }
        
        return smallest;        
    }

    
    private ArrayList<Rating> sortByAverage( ArrayList<Rating> in )
    {
        ArrayList<Rating> out = new ArrayList<Rating>();
        
        while( !in.isEmpty() )
        {
            int smallest = getSmallestByRating( in );
            
            Rating item = in.get( smallest );
            in.remove( smallest );
            out.add( item );
        }
        
        return out;        
    }
    

	// getAverageRatings returns an ArrayList of all the Rating objects for movies that have at least the minimal number of raters (minimalRaters) supplying a rating    
    public ArrayList<Rating> getAverageRatings( int minimalRaters )
    {
        ArrayList<Rating> ratingList = new ArrayList<Rating>();
        
        for( DataMap item : myMovieRaterSort )
        {
            double average = getAverageByID( item.getKey(),minimalRaters );
            
            if( average != 0. )
            {
                Rating r_item = new Rating( item.getKey(),average );
                ratingList.add( r_item );
            }
        }
        
        ratingList = sortByAverage( ratingList );
        
        return ratingList;
    }
    
    
	// getTitle returns the title of the movie with that ID
    public String getTitle( String id )
    {
        for( Movie item : myMovies )
        {
            if( item.getID().equals( id ) )
            {
                return item.getTitle();
            }
        }
        
        return "NO SUCH ID";
    }


	// getID returns the movie ID of the movie with given title    
    public String getID( String title )
    {
        for( Movie item : myMovies )
        {
            if( item.getTitle().equals( title ) )
            {
                return item.getID();
            }
        }
        
        return "NO SUCH TITLE";
    }

}


