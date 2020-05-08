
/**
 * Write a description of DataMap here.
 * 
 * @author: 	Alex Momotyuk 
 * @version: 	20.04
 */

import java.util.*;

public class DataMap
{
    private String key;
    private ArrayList<String> data;
    
    public DataMap()
    {
        key = "";
        data = new ArrayList<String>();
    }
    
    public DataMap( String pKey,ArrayList<String> pData )
    {
        key = pKey;
        data = pData;
    }
    
    public void put( String pKey,ArrayList<String> pData )
    {
        key = pKey;
        data = pData;
    }
    
    public void add( String pData )
    {
        data.add( pData );
    }
    
    public String getKey()
    {
        return key;
    }
    
    public ArrayList<String> getData()
    {
        return data;
    }
}
