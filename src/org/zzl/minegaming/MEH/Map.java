package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class Map implements ISaveable
{
	private MapData mapData;
	private MapTileData mapTileData;
	public Map(GBARom rom, int dataOffset)
	{
		mapData = new MapData(rom, rom.getPointerAsInt(dataOffset));
		mapTileData = new MapTileData(rom, BitConverter.shortenPointer(mapData.mapTilesPtr),mapData);
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
		mapData.save();
	}
}
