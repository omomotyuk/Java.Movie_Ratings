
/*
 * description of class
 * 
 * @author: 	Alex Momotyuk 
 * @version: 	20.04
 */


import java.util.*;


public class FourthRatings
{

    private ArrayList<Rater> myRaters;
    //
    private FirstRatings myMovieRatings;
    private ArrayList<DataMap> myMovieRaterSort;
    
    
    /*public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }*/
    
    /*public FourthRatings()
    {
        this("ratings.csv");
    }*/    

    
    //public ThirdRatings( String moviefile,String ratingsfile )
    //public FourthRatings( String ratingsfile )
    public FourthRatings()
    {
        
        //myMovies = myMovieRatings.loadMovies( moviefile );

        //myRaters = myMovieRatings.loadRaters( ratingsfile );
        myRaters = RaterDatabase.getRaters();
        
        FirstRatings myMovieRatings = new FirstRatings();
        ArrayList<DataMap> movieRater = myMovieRatings.getMovieRaterMap( myRaters );
        myMovieRaterSort = myMovieRatings.sortByDataNumber( movieRater );
        
        //System.out.println( "DataMap has " + myMovieRaterSort.size() + " items." );

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

        sorted = sortByAverage( sorted );        
        
        return sorted;
    }
    

    private double dotProduct( Rater me,Rater r )
    {
        ArrayList<String> list = r.getItemsRated();
        
        double dotProductSum = 0.;
        
        for( String id : list )
        {
            if( me.hasRating( id ) )
            {
                double meRating = me.getRating( id );
                double r_Rating =  r.getRating( id );
                
                dotProductSum += ( (meRating - 5.) * (r_Rating - 5.) );
            }
        }
        
        return dotProductSum;
    }
    
    
    private ArrayList<Rating> getSimilarities( String id )
    {
        ArrayList<Rating> list = new ArrayList<Rating>();
        
        Rater me = RaterDatabase.getRater( id );
        
        for( Rater r : RaterDatabase.getRaters() )
        {
            // add dot_product(r,me) to list if r != me
            if( ! r.getID().equals( me.getID() ) ) //
            {
                double dProduct = dotProduct( me,r );
                if( dProduct > 0. )
                {
                    list.add( new Rating( r.getID(),dProduct ) );
                }
            }
        }
        
        Collections.sort(list, Collections.reverseOrder());
        
        return list;
    }

    
    public ArrayList<Rating> getSimilarRatings( String id,int numSimilarRaters,int minimalRaters )
    {
        ArrayList<Rating> sorted = new ArrayList<Rating>();
        
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());

        ArrayList<Rating> list = getSimilarities( id );
        
        for( String item : movies )
        {
            int num = 0;
            double sum = 0.;
            
            for( int i = 0; i < numSimilarRaters; i++ )
            {
                String raterID = list.get( i ).getItem();
                double ratingV = list.get( i ).getValue();
                
                if( RaterDatabase.getRater( raterID ).hasRating( item ) )
                {
                    sum += ratingV * RaterDatabase.getRater( raterID ).getRating( item );
                    num ++;
                }
            }
            
            double average = sum / num;
            
            if( minimalRaters <= num )
            {
                sorted.add( new Rating( item,average ) );
            }
        }

        Collections.sort( sorted,Collections.reverseOrder() );
        
        return sorted;
    }


    public ArrayList<Rating> getSimilarRatingsByFilter( String id,int numSimilarRaters,int minimalRaters,Filter filterCriteria )
    {
        ArrayList<Rating> sorted = new ArrayList<Rating>();
        
        ArrayList<String> movies = MovieDatabase.filterBy( filterCriteria );
        
        ArrayList<Rating> list = getSimilarities( id );
        
        for( String item : movies )
        {
            int num = 0;
            double sum = 0.;
            
            for( int i = 0; i < numSimilarRaters; i++ )
            {
                String raterID = list.get( i ).getItem();
                double ratingV = list.get( i ).getValue();
                
                if( RaterDatabase.getRater( raterID ).hasRating( item ) )
                {
                    sum += ratingV * RaterDatabase.getRater( raterID ).getRating( item );
                    num ++;
                }
            }
            
            double average = sum / num;
            
            if( minimalRaters <= num )
            {
                sorted.add( new Rating( item,average ) );
            }
        }

        Collections.sort( sorted,Collections.reverseOrder() );
        
        return sorted;
    }
}



