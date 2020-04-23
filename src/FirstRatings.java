
/**
 * Write a description of FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;


public class FirstRatings
{
    //private ArrayList<Movie> myMovie;
    //private ArrayList<Rater>   myRater;
    
    public FirstRatings()
    {
        //myMovie = new ArrayList<Movie>();
        //myRater = new ArrayList<Rater>  ();
    }


    // Movie methods 
    
    public ArrayList<Movie> loadMovies( String filename )
    {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        
        FileResource file = new FileResource( filename );
        CSVParser parser = file.getCSVParser();
        
        for( CSVRecord record : parser )
        {
            Movie item = new Movie( record.get( "id" ),record.get( "title" ),record.get( "year" ),record.get( "genre" ),record.get( "director" ),
                                    record.get( "country" ),record.get( "poster" ),Integer.parseInt( record.get( "minutes" )) );
            movieList.add( item );
        }
        
        return movieList;
    }
   
    
    public ArrayList<Movie> howManyMovies( ArrayList<Movie> list,String criteria,String text )
    {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        
        for( Movie item : list )
        {
            String info = "";
            
            switch ( criteria.toLowerCase() )
            {
            case "id": 
                info = item.getID(); break;
            case "title": 
                info = item.getTitle(); break;
            //case "year": 
                //info = item.getYear(); break;
            case "genre": 
                info = item.getGenres(); break;
            case "director": 
                info = item.getDirector(); break;
            case "country": 
                info = item.getCountry(); break;
            //case "poster": 
                //info = item.getPoster(); break;
            }

            if( info.toLowerCase().indexOf( text.toLowerCase() ) != -1 ) //
            {
                movieList.add( item );
            }
        }
        
        return movieList;
    }
    
    
    public int greaterThan( ArrayList<Movie> list,int length )
    {
        int number = 0;
        
        for( Movie item : list )
        {
            if( item.getMinutes() > length )
            {
                number++;
            }
        }
        
        return number;
    }    
    

    // Rating methods 
    
    private int containsItem( ArrayList<Rater>   list,String id )
    {
        if( list.size() == 0 ){ return -1; }
        
        for( Rater  item : list )
        {
            String itemID = item.getID();
            if( itemID.equalsIgnoreCase( id ) )
            {
                return list.indexOf( item );
            }
        }
        
        return -1;
    }

    
    public ArrayList<Rater> loadRaters( String filename )
    {
        ArrayList<Rater> list = new ArrayList<Rater>  ();
        
        FileResource file = new FileResource( filename );
        CSVParser parser = file.getCSVParser();
        
        for( CSVRecord record : parser )
        {
            int index = containsItem( list,record.get( "rater_id" ) );
            
            if( index != -1 )
            {
                Rater item = list.get( index );
                item.addRating( record.get( "movie_id" ),Double.parseDouble( record.get( "rating" ) ) );
                list.set( index,item );
            }
            else
            {
                Rater item = new EfficientRater( record.get( "rater_id" ) );
                item.addRating( record.get( "movie_id" ),Double.parseDouble( record.get( "rating" ) ) );
                list.add( item );
            }

        }
        
        return list;
    }

    
    public int numberOfRatings( ArrayList<Rater>   list,int id )
    {
        for( Rater  item : list )
        {
            if( Integer.parseInt( item.getID() ) == id )
            {
                //return( item.getRatings().size() );
                return( item.getRatingsSize() );
            }
        }
        
        return 0;
    }
    
    
    /*private ArrayList<String> getDirectorList( ArrayList<Movie> list )
    {
        ArrayList<String> directorList = new ArrayList<String>();
        
        for( Movie item : list )
        {
            String[] directors = item.getDirector().split( ",");
            
            for( int i = 0; i < directors.length; i++ )
            {
                String name = directors[i].trim();
                
                if( !directorList.contains( name ) )
                {
                    directorList.add( name );
                }
            }
        }
        
        return directorList;
    }*/

    
    private boolean dataMapContainsKey( ArrayList<DataMap> list,String key )
    {
        for( DataMap item : list )
        {
            if( item.getKey().equals( key ) )
            {
                return true;
            }
        }
        
        return false;
    }

    
    private int getIndexOf( ArrayList<DataMap> list,String key )
    {
        for( int i = 0; i < list.size(); i++ )
        {
            if( list.get( i ).getKey().equals( key ) )
            {
                return i;
            }
        }
        
        return -1;
    }

    
    private int numberOfMovieRatings( ArrayList<DataMap> list,String key )
    {
        return list.get( getIndexOf( list,key ) ).getData().size();
    }
    
    
    private ArrayList<DataMap> getDirectorMovieMap( ArrayList<Movie> movieList )
    {
        ArrayList<DataMap> directorMovie = new ArrayList<DataMap>();
        
        for( Movie item : movieList )
        {
            String title = item.getTitle();
            String[] directors = item.getDirector().split( ",");
            
            for( int i = 0; i < directors.length; i++ )
            {
                String name = directors[i].trim();
                
                if( !dataMapContainsKey( directorMovie,name ) )
                {
                    ArrayList<String> movies = new ArrayList<String>();
                    movies.add( title );
                    DataMap data = new DataMap( name,movies );
                    directorMovie.add( data );
                }
                else
                {
                    int index = getIndexOf( directorMovie,name );
                    
                    DataMap data = directorMovie.get( index );
                    
                    if( !data.getData().contains( title ) )
                    {
                        data.add( title );
                        directorMovie.set( index,data );
                    }
                }
            }
        }
        
        return directorMovie;        
    }
    

    private ArrayList<DataMap> getRaterMovieMap( ArrayList<Rater>   list )
    {
        ArrayList<DataMap> raterMovie = new ArrayList<DataMap>();
        
        for( Rater  item : list )
        {
            String rater_id = item.getID();
            
            ArrayList<Rating> ratings = item.getRatings(); 
            ArrayList<String> movie = new ArrayList<String>();
                
            for( Rating item_r : ratings )
            {
                movie.add( item_r.getItem() );
            }
            
            DataMap data = new DataMap( rater_id,movie );
            
            if( !raterMovie.contains( data ) )
            {
                raterMovie.add( data );
            }
        }
        
        return raterMovie;
    }
    
    
    public ArrayList<DataMap> getMovieRaterMap( ArrayList<Rater> list )
    {
        ArrayList<DataMap> movieRater = new ArrayList<DataMap>();
        
        for( Rater item : list )
        {
            String rater_id = item.getID();
            
            ArrayList<Rating> movies = item.getRatings();
            
            
            for( int i = 0; i < movies.size(); i++ )
            {
                String title = movies.get( i ).getItem();
                
                if( !dataMapContainsKey( movieRater,title ) )
                {
                    ArrayList<String> raters = new ArrayList<String>();
                    raters.add( rater_id );
                    DataMap data = new DataMap( title,raters );
                    movieRater.add( data );
                }
                else
                {
                    int index = getIndexOf( movieRater,title );
                    
                    DataMap data = movieRater.get( index );
                    
                    if( !data.getData().contains( rater_id ) )
                    {
                        data.add( rater_id );
                        movieRater.set( index,data );
                    }
                }
            }            
        }
        
        return movieRater;        
    }

    
    private DataMap getBiggestByDataNumber( ArrayList<DataMap> list )
    {
        DataMap biggest = new DataMap();
        int number = 0; // 
        
        for( DataMap item : list )
        {
            if( number < item.getData().size() )
            {
                number = item.getData().size();
                biggest = item;
            }
        }
        
        return biggest;        
    }

    
    public ArrayList<DataMap> sortByDataNumber( ArrayList<DataMap> in )
    {
        ArrayList<DataMap> out = new ArrayList<DataMap>(); 
        
        while( !in.isEmpty() )
        {
            DataMap biggest = getBiggestByDataNumber( in );
            
            in.remove( biggest );
            out.add( biggest );
        }
        
        return out;
    }
    
    
    // tests methods 
    
    public void testLoadMovies()
    {
        //ArrayList<Movie> movieList = loadMovies( "data/ratedmovies_short.csv" );
        ArrayList<Movie> movieList = loadMovies( "data/ratedmoviesfull.csv" );
        
        System.out.println( "Number of movies : " + movieList.size() );
        /*for( Movie item : movieList )
        {
            System.out.println( "Movie's title : " + item.getTitle() );
        }*/

        
        ArrayList selectedList = howManyMovies( movieList,"genre","Comedy" );
        System.out.println( "\n" + selectedList.size() + " movies include the Comedy genre."  );

        
        int length = 150;
        System.out.println( "\n" + greaterThan( movieList,length ) + " movies are greater than " + length + " minutes in length." );


        ArrayList<DataMap> directorMovie = getDirectorMovieMap( movieList );
        /*System.out.println( "" + directorMovie.size() + " directors found in movie list : " );
        for( DataMap item : directorMovie )
        {
            System.out.println( "" + item.getKey() + "\t is a director of " + item.getData().size() + " movies : \t" + item.getData() );
        }*/

        ArrayList<DataMap> directorMovieSort = sortByDataNumber( directorMovie );
        System.out.println( "\nNumber of directors in sorted list : " + directorMovieSort.size() + "\n" );
        for( DataMap item : directorMovieSort )
        {
            System.out.println( "" + item.getKey() + "\t" + item.getData().size() + "\t" + item.getData() );
        }
    }

    
    public void testLoadRaters()
    {
        //ArrayList<Rater> raterList = loadRaters( "data/ratings_short.csv" );
        ArrayList<Rater> raterList = loadRaters( "data/ratings.csv" );
        
        System.out.println( "\nNumber of raters : " + raterList.size() );
        /*for( Rater  item : raterList )
        {
            System.out.println( "Rater’s ID : " + item.getID() + "\tNumber of ratings : " + item.numRatings() );
            System.out.println( "" + item.getRatings() );
        }*/

        
        int id = 193;
        System.out.println( "\nNumber of ratings for a Rater  with ID " + id + " : " + numberOfRatings( raterList,id ) );

        
        // Rater  - movie
        
        ArrayList<DataMap> raterMovie = getRaterMovieMap( raterList );
        ArrayList<DataMap> raterMovieSort = sortByDataNumber( raterMovie );
        
        System.out.println( "\nNumber of raters in sorted list : " + raterMovieSort.size() + "\n" );
        for( DataMap item : raterMovieSort )
        {
            System.out.println( "Rater " + item.getKey() + "\t has " + item.getData().size() + "\t ratings for movies : " + item.getData() );
        }

       
        // movie - rater
        
        ArrayList<DataMap> movieRater = getMovieRaterMap( raterList );
        ArrayList<DataMap> movieRaterSort = sortByDataNumber( movieRater );
        
        System.out.println( "\nNumber of movies in sorted list : " + movieRaterSort.size() + "\n" );
        for( DataMap item : movieRaterSort )
        {
            System.out.println( "Movie " + item.getKey() + "\t has " + item.getData().size() + "\t ratings from raters : " + item.getData() );
        }
        
        
        String title = "1798709";
        System.out.println( "\nNumber of ratings of movie " + title + " is : " + numberOfMovieRatings( movieRaterSort,title ) );
    
    }
}


