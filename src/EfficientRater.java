
/*
 * description of class
 * 
 * @author: 	Alex Momotyuk 
 * @version: 	20.04
 */

import java.util.*;


public class EfficientRater implements Rater
{
    private String myID;
    //private ArrayList<Rating> myRatings;
    private HashMap<String,Rating> myRatings;


    public EfficientRater(String id)
    {
        myID = id;
        //myRatings = new ArrayList<Rating>();
        myRatings = new HashMap<String,Rating>();
    }

    public void addRating(String item, double rating)
    {
        //myRatings.add( new Rating(item,rating) );
        
        if( !myRatings.containsKey( item ) )
        {
            myRatings.put( item,new Rating(item,rating) );
        }
    }

    public boolean hasRating(String item)
    {
        /*for(int k=0; k < myRatings.size(); k++)
        {
            if (myRatings.get(k).getItem().equals(item))
            {
                return true;
            }
        }*/
        
        if( myRatings.containsKey( item ) )
        {
            return true;
        }
        
        return false;
    }

    
    
    
    public String getID() {
        return myID;
    }

    public double getRating(String item)
    {
        /*for(int k=0; k < myRatings.size(); k++)
        {
            if (myRatings.get(k).getItem().equals(item))
            {
                return myRatings.get(k).getValue();
            }
        }*/
        
        for( String rcd : myRatings.keySet() )
        {
            if( myRatings.get( rcd ).getItem().equals( item ) )
            {
                return myRatings.get( rcd ).getValue();
            }
        }
        
        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated()
    {
        ArrayList<String> list = new ArrayList<String>();


        
        
        /*for(int k=0; k < myRatings.size(); k++)
        {
            
        System.out.println( "k : " + k + " item : " + myRatings.get(k).getItem() );

            list.add( myRatings.get(k).getItem() );
        }*/

        
        for( String item : myRatings.keySet() )
        {
            list.add( myRatings.get( item ).getItem() );
        }
        
        
        
        
        return list;
    }
    
    
    public ArrayList<Rating> getRatings()
    {
        //return myRatings;
        
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        
        for( Rating item : myRatings.values() )
        {
            ratings.add( item );
        }
        
        return ratings;
    }

    public int getRatingsSize()
    {
        return myRatings.size();
    }
    
}


