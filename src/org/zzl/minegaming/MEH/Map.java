package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class Map implements ISaveable
{
	private MapData mapData;
	public Map(GBARom rom, int dataOffset)
	{
		mapData = new MapData(rom, rom.getPointerAsInt(dataOffset));
	}
	
	public MapData getMapData()
	{
		return mapData;
	}
	
	public void save()
	{
		mapData.save();
	}
}
