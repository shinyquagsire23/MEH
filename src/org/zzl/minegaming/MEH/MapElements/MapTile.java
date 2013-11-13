package org.zzl.minegaming.MEH.MapElements;

public class MapTile
{
	private int ID; 
	private int Meta;//Interdpth - Now sure what this is for? flip and pal data? 
	public void SetID(int i){
		ID=i;
		ID=i;
	}
	public MapTile(int id, int meta)
	{
		ID = id;
		Meta = meta;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public int getMeta()
	{
		return Meta;
	}
}
