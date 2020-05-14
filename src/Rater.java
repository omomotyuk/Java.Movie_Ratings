
/*
 * Rater interface of movie ratings for one rater
 * 
 * @author: 	Alex Momotyuk 
 * @version: 	20.04
 */

import java.util.*;


public interface Rater
{
    
    public void addRating(String item, double rating);
    
    public boolean hasRating(String item);
    
    public String getID();
    
    public double getRating(String item);
    
    public int numRatings();
    
    public ArrayList<String> getItemsRated();
    
    public ArrayList<Rating> getRatings();
    
    public int getRatingsSize();
    
}


