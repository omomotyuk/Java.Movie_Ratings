
/*
 * description of class
 * 
 * @author: 	Alex Momotyuk 
 * @version: 	20.04
 */


public class MinutesFilter implements Filter
{
	private int myMinMinutes;
	private int myMaxMinutes;
	
	public MinutesFilter( int minMinutes,int maxMinutes )
	{
		myMinMinutes = minMinutes;
		myMaxMinutes = maxMinutes;
	}
	
	@Override
	public boolean satisfies( String id )
	{
		return ( (myMinMinutes <= MovieDatabase.getMinutes( id )) && (MovieDatabase.getMinutes( id ) <= myMaxMinutes) );
	}
}


