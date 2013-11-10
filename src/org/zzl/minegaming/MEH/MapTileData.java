package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.GBARom;

public class MapTileData
{
	private int dataLoc;
	private MapData mData;
	private GBARom rom;
	public MapTileData(GBARom rom, int offset, MapData mData)
	{
		dataLoc = offset;
		this.mData = mData;
		this.rom = rom;
	}
	
	public MapTile getTile(int x, int y)
	{
		int index = (int) ((y*mData.mapWidth) + x);
		int raw = rom.readWord(dataLoc + index*2);
		return new MapTile((raw & 0x3FF),(raw&0xFC00) >> 10);
	}
}
