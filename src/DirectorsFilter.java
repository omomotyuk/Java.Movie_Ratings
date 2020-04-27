
/**
 * Write a description of DirectorsFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class DirectorsFilter implements Filter
{
	private String[] myDirectors;
	
	public DirectorsFilter( String Directors )
	{
		myDirectors = Directors.split( "," );
	}
	
	@Override
	public boolean satisfies( String id )
	{
		for( int i = 0; i < myDirectors.length; i++ )
		{
		    if( MovieDatabase.getDirector( id ).indexOf( myDirectors[i].trim() ) != -1 )
		    {
		        return true;
		    }
		}
		
		return false;
	}
}
