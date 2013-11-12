package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class Map implements ISaveable
{
	private MapData mapData;
	private MapTileData mapTileData;
	public MapHeader mapHeader; 
	public boolean isEdited;
	public Map(GBARom rom, int dataOffset)
	{
		mapHeader = new MapHeader(rom, dataOffset);
		mapData = new MapData(rom, (int)mapHeader.pMap);
		mapTileData = new MapTileData(rom, BitConverter.shortenPointer(mapData.mapTilesPtr),mapData);
	    isEdited=true;
	}
	
	public MapData getMapData()
	{
		return mapData;
	}
	
	public MapTileData getMapTileData()
	{
		return mapTileData;
	}
	
	public void save()
	{
		//mapData.save();
		mapTileData.save();
	}
}
