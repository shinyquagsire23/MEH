package org.zzl.minegaming.MEH.MapElements;

public class MapTile implements Cloneable
{
	private int ID; 
	private int Meta;
	public void SetID(int i){
		ID=i;
		
	}
	public MapTile(int id, int meta)
	{
		ID = id;
		Meta = meta;
	}
	public void SetMeta(int meta){
		Meta=meta;
	}
	public int getID()
	{
		return ID;
	}
	
	public int getMeta()
	{
		return Meta;
	}

	@Override
	public Object clone()
	{
		return new MapTile(ID,Meta);
	}
}
