package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class BorderMap implements ISaveable
{
	private Map map;
	private MapData mapData;
	private BorderTileData mapTileData;
	public boolean isEdited = false;
	public BorderMap(GBARom rom, Map m)
	{
		map = m;
		mapData = map.getMapData();
		mapTileData = new BorderTileData(rom, BitConverter.shortenPointer(mapData.borderTilePtr),mapData);
	}
	
	public MapData getMapData()
	{
		return mapData;
	}
	
	public BorderTileData getMapTileData()
	{
		return mapTileData;
	}
	
	public void save()
	{
		mapData.save();
	}
}
