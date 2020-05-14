
/*
 * description of class
 * 
 * @author: 	Alex Momotyuk 
 * @version: 	20.04
 */


public class GenreFilter implements Filter
{
	private String myGenre;
	
	public GenreFilter( String genre )
	{
		myGenre = genre;
	}
	
	@Override
	public boolean satisfies( String id )
	{
		return ( MovieDatabase.getGenres( id ).indexOf( myGenre ) != -1 );
	}

}


