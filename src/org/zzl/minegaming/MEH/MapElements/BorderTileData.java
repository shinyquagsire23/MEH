package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;

public class BorderTileData
{
	private int dataLoc;
	private MapData mData;
	private GBARom rom;
	private MapTile[][] mapTiles;
	public BorderTileData(GBARom rom, int offset, MapData mData)
	{
		dataLoc = offset;
		this.mData = mData;
		this.rom = rom;
		mapTiles = new MapTile[mData.borderWidth][mData.borderHeight];
		for(int x = 0; x < mData.borderWidth; x++)
		{
			for(int y = 0; y < mData.borderHeight; y++)
			{
				mapTiles[x][y] = getTile(x,y);
			}
		}
	}
	
	public MapTile getTile(int x, int y)
	{
		if(mapTiles[x][y] != null)
			return mapTiles[x][y];
		else
		{
			int index = (y*mData.borderWidth) + x;
			int raw = rom.readWord(dataLoc + index*2);
			MapTile m = new MapTile((raw & 0x3FF),(raw&0xFC00) >> 10);
			mapTiles[x][y] = m;
			return m;
		}
	}
}
