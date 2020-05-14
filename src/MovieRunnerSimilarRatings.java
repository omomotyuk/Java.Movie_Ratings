
/*
 * description of class
 * 
 * @author: 	Alex Momotyuk 
 * @version: 	20.04
 */


import java.util.*;


public class MovieRunnerSimilarRatings
{
    private FourthRatings movieRatings;
    
    
    private void initialize()
    {
        //MovieDatabase.initialize( "ratedmovies_short.csv" );
        MovieDatabase.initialize( "ratedmoviesfull.csv" );
        
        //movieRatings = new ThirdRatings( "data/ratings_short.csv" );
        //movieRatings = new ThirdRatings( "data/ratings.csv" );
        RaterDatabase.initialize( "ratings.csv" );
        
        movieRatings = new FourthRatings();
        
        System.out.println( "\n\nNumber of movies : " + MovieDatabase.size() );
        System.out.println( "Number of raters : " + RaterDatabase.size() );        
    }

    
    /*public void printAverageRatings()
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
    }*/
    
    
    public void printSimilarRatings()
    {
        initialize();
        
        String id = "71";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        
        ArrayList<Rating> ratingList = movieRatings.getSimilarRatings( id,numSimilarRaters,minimalRaters );
        
        System.out.println( "Number of movies with minimal rater number " + minimalRaters + " and " + numSimilarRaters + " top similar raters for rater ID " + id + " is " + ratingList.size() + "\n" );
        
        for( Rating item : ratingList )
        {
            System.out.println( "" + MovieDatabase.getYear( item.getItem() ) + " " + MovieDatabase.getTitle( item.getItem() ) + " \t" + item.getValue() );
        }
        
    }

    
    public void printSimilarRatingsByGenre()
    {
        initialize();
        
        String id = "964";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        //String genre = "Crime";
        //String genre = "Comedy";
        //String genre = "Action";
        String genre = "Mystery";
        
        Filter genreFilter = new GenreFilter( genre );
        
        ArrayList<Rating> ratingList = movieRatings.getSimilarRatingsByFilter( id,numSimilarRaters,minimalRaters,genreFilter );
        
        //System.out.println( "Number of movies with minimal rater number " + minimalRaters + " and genre filter " + genre + " is " + ratingList.size() + "\n" );
        System.out.println( "Number of movies with minimal rater number " + minimalRaters + " and " + numSimilarRaters + " top similar raters for rater ID " + id + 
                            " and genre " + genre + " is " + ratingList.size() + "\n" );
        
        for( Rating item : ratingList )
        {
            System.out.println( "" + MovieDatabase.getYear( item.getItem() ) + " " + MovieDatabase.getTitle( item.getItem() ) + " \t" + item.getValue() );
            System.out.println( "\t" + MovieDatabase.getGenres( item.getItem() ) );
        }        
    }

    
    public void printSimilarRatingsByDirector()
    {
        initialize();
        
        String id = "120";
        int numSimilarRaters = 10;
        int minimalRaters = 2;
        String directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        
        Filter directorsFilter = new DirectorsFilter( directors );
        
        ArrayList<Rating> ratingList = movieRatings.getSimilarRatingsByFilter( id,numSimilarRaters,minimalRaters,directorsFilter );
        
        System.out.println( "Number of movies for " + numSimilarRaters + " top similar raters, " + minimalRaters + " minimal rater number " + " and rater ID " + id + 
                            "\nand directors " + directors + " is " + ratingList.size() + "\n" );
        
        for( Rating item : ratingList )
        {
            System.out.println( "" + MovieDatabase.getYear( item.getItem() ) + " " + MovieDatabase.getTitle( item.getItem() ) + " \t" + item.getValue() );
            System.out.println( "\t" + MovieDatabase.getGenres( item.getItem() ) );
        }        
    }

    
    public void printSimilarRatingsByGenreAndMinutes()
    {
        initialize();
        
        String id = "168";
        int numSimilarRaters = 10;
        int minimalRaters = 3;
        
        //String genre = "Crime";
        //String genre = "Comedy";
        //String genre = "Action";
        //String genre = "Adventure";
        String genre = "Drama";
        
        int minMinutes = 80;
        int maxMinutes = 160;
        
        Filter genreFilter = new GenreFilter( genre );
        Filter minutesFilter = new MinutesFilter( minMinutes,maxMinutes );

        AllFilters filters = new AllFilters();
        
        filters.addFilter( genreFilter );
        filters.addFilter( minutesFilter );
        
        ArrayList<Rating> ratingList = movieRatings.getSimilarRatingsByFilter( id,numSimilarRaters,minimalRaters,filters );
        
        System.out.println( "Number of movies for " + numSimilarRaters + " top similar raters, " + minimalRaters + " minimal rater number " + " and rater ID " + id + 
                            "\nand genre " + genre + " and minutes " + minMinutes + " - " + maxMinutes + " is " + ratingList.size() + "\n" );
        
        for( Rating item : ratingList )
        {
            System.out.println( "" + MovieDatabase.getYear( item.getItem() ) + " " + MovieDatabase.getTitle( item.getItem() ) + " " +
                                     MovieDatabase.getMinutes( item.getItem() ) + " \t" + item.getValue() );
            System.out.println( "\t" + MovieDatabase.getGenres( item.getItem() ) );
        }
    }

    
    public void printSimilarRatingsByYearAfterAndMinutes()
    {
        initialize();
        
        String id = "314";
        int numSimilarRaters = 10;
        int minimalRaters = 5;
        
        int year = 1975;
        
        int minMinutes = 70;
        int maxMinutes = 200;
        
        Filter yearAfter = new YearAfterFilter( year );
        Filter minutesFilter = new MinutesFilter( minMinutes,maxMinutes );

        AllFilters filters = new AllFilters();
        
        filters.addFilter( yearAfter );
        filters.addFilter( minutesFilter );
        
        ArrayList<Rating> ratingList = movieRatings.getSimilarRatingsByFilter( id,numSimilarRaters,minimalRaters,filters );
        
        System.out.println( "Number of movies for " + numSimilarRaters + " top similar raters, " + minimalRaters + " minimal rater number " + " and rater ID " + id + 
                            "\nand after year " + year + " and minutes " + minMinutes + " - " + maxMinutes + " is " + ratingList.size() + "\n" );
        
        for( Rating item : ratingList )
        {
            System.out.println( "" + MovieDatabase.getYear( item.getItem() ) + " " + MovieDatabase.getTitle( item.getItem() ) + " " +
                                     MovieDatabase.getMinutes( item.getItem() ) + " \t" + item.getValue() );
            System.out.println( "\t" + MovieDatabase.getGenres( item.getItem() ) );
        }
    }
    
}


