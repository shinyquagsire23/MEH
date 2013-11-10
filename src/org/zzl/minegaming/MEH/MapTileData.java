package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.GBARom;

public class MapTileData
{
	private int dataLoc;
	private MapData mData;
	private GBARom rom;
	private MapTile[][] mapTiles;
	public MapTileData(GBARom rom, int offset, MapData mData)
	{
		dataLoc = offset;
		this.mData = mData;
		this.rom = rom;
		mapTiles = new MapTile[(int)mData.mapWidth][(int) mData.mapHeight];
		for(int x = 0; x < mData.mapWidth; x++)
		{
			for(int y = 0; y < mData.mapHeight; y++)
			{
				
				int index = (int) ((y*mData.mapWidth) + x);
				int raw = rom.readWord(dataLoc + index*2);
				MapTile m = new MapTile((raw & 0x3FF),(raw&0xFC00) >> 10);
				mapTiles[x][y] = m;
				
			}
		}
	}
	
	public MapTile getTile(int x, int y)
	{
			return mapTiles[x][y];
	}
}
