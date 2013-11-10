package org.zzl.minegaming.MEH;

public class MapTile
{
	private int ID;
	private int Meta;
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
