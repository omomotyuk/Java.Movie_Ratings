
/*
 * description of class
 * 
 * @author: 	Alex Momotyuk 
 * @version: 	20.04
 */

import java.util.*;

public class DirectorsFilter implements Filter
{
	private String[] myDirectors;
	
// parameter Directors represents a list of directors separated by commas (Example: "Charles Chaplin,Michael Mann,Spike Jonze")
	public DirectorsFilter( String Directors )
	{
		myDirectors = Directors.split( "," );
	}
	
// satisfies method returns true if a movie has at least one of these directors as one of its directors
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


