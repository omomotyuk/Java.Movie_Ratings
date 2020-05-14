
/*
 * description of class
 * 
 * @author: 	Alex Momotyuk 
 * @version: 	20.04
 */


import java.util.*;

public class ThirdRatings
{
    //private ArrayList<Movie> myMovies;
    
    
    
    
    private ArrayList<Rater> myRaters;
    //
    private FirstRatings myMovieRatings;
    private ArrayList<DataMap> myMovieRaterSort;
    
    
    /*public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }*/
    
    public ThirdRatings()
    {
        this("ratings.csv");
    }    

    
    //public ThirdRatings( String moviefile,String ratingsfile )
    public ThirdRatings( String ratingsfile )
    {
        FirstRatings myMovieRatings = new FirstRatings();
        
        //myMovies = myMovieRatings.loadMovies( moviefile );
        myRaters = myMovieRatings.loadRaters( ratingsfile );
        
        ArrayList<DataMap> movieRater = myMovieRatings.getMovieRaterMap( myRaters );
        myMovieRaterSort = myMovieRatings.sortByDataNumber( movieRater );
        
        //System.out.println( "DataMap has " + myMovieRaterSort.size() + " items." );

    }
    
    
    /*public int getMovieSize()
    {
        return myMovies.size();
    }*/
    
    
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
    
    
    private double getAverageByID( String id,int minimalRaters )
    {
        
        for( DataMap item : myMovieRaterSort )
        {
            if( item.getKey().equals( id ) )
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
    

//    
    
    public ArrayList<Rating> getAverageRatings( int minimalRaters )
    {
        ArrayList<Rating> ratingList = new ArrayList<Rating>();
        
        /*for( DataMap item : myMovieRaterSort )
        {
            double average = getAverageByID( item.getKey(),minimalRaters );
            
            if( average != 0. )
            {
                Rating r_item = new Rating( item.getKey(),average );
                ratingList.add( r_item );
            }
        }*/
        
        // new code :
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        
        for( String item : movies )
        {
            double average = getAverageByID( item,minimalRaters );
            
            //System.out.println( item + " has average rating " + average );
            
            if( average != 0. )
            {
                Rating r_item = new Rating( item,average );
                ratingList.add( r_item );
            }        
        }
        //

        ratingList = sortByAverage( ratingList );
        
        return ratingList;
    }

    

    public ArrayList<Rating> getAverageRatingsByFilter( int minimalRaters,Filter filterCriteria )
    {
        ArrayList<Rating> sorted = new ArrayList<Rating>();
        
        ArrayList<String> movies = MovieDatabase.filterBy( filterCriteria );
        
        for( String item : movies )
        {
            double average = getAverageByID( item,minimalRaters );
            
            //System.out.println( item + " has average rating " + average );
            
            if( average != 0. )
            {
                Rating r_item = new Rating( item,average );
                sorted.add( r_item );
            }        
        }
        //

        sorted = sortByAverage( sorted );        
        
        return sorted;
    }
    
    
    
    /*public String getTitle( String id )
    {
        for( Movie item : myMovies )
        {
            if( item.getID().equals( id ) )
            {
                return item.getTitle();
            }
        }
        
        return "NO SUCH ID";
    }*/

    
    /*public String getID( String title )
    {
        for( Movie item : myMovies )
        {
            if( item.getTitle().equals( title ) )
            {
                return item.getID();
            }
        }
        
        return "NO SUCH TITLE";
    }*/

}


