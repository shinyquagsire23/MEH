package us.plxhack.MEH.IO;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBARom;

import us.plxhack.MEH.Structures.MapTile;

public class BorderTileData
{
	private int originalPointer;
	private int originalSize;
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
		this.originalPointer = mData.borderTilePtr;
		this.originalSize = getSize();
	}
	
	public int getSize()
	{
		return (int) ((mData.borderWidth * mData.borderHeight) * 2);
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

	
	public MapTile[][] getTiles(int x, int y, int width, int height)
	{
		MapTile[][] m = new MapTile[width][height];
		for(int i = x; i < x + width; i++)
		{
			for(int j = y; j < y + width; j++)
			{
				m[i-x][j-y] = getTile(i,j);
			}
		}
		return m;
	}
	
	public void save()
	{
		rom.Seek(dataLoc);
		for(int x = 0; x < mData.borderWidth; x++)
		{
			for(int y = 0; y < mData.borderHeight; y++)
			{
				
				//int index = (int) ((y*mData.borderWidth) + x);
				rom.writeWord(mapTiles[y][x].getID() + ((mapTiles[y][x].getMeta() & 0x3F) << 10));
			}
		}
	}
	
	public void resize(long xSize, long ySize)
	{
		/*MapTile[][] newMapTiles = new MapTile[(int)xSize][(int)ySize];
		mData.borderWidth = (int) xSize;
		mData.borderHeight = (int) ySize;
		rom.floodBytes(originalPointer, rom.freeSpaceByte, originalSize);
		
		//TODO make this a setting, ie always repoint vs keep pointers
		if(originalSize < getSize())
		{
			mData.mapTilesPtr = rom.findFreespace(DataStore.FreespaceStart, getSize());
		}
		
		for(int x = 0; x < xSize; x++)
			for(int y = 0; y < ySize; y++)
			{
				try
				{
					newMapTiles[x][y] = mapTiles[x][y];
				}
				catch(Exception e)
				{
					newMapTiles[x][y] = new MapTile(0,0);
				}
			}
		
		mapTiles = newMapTiles;*/
	}
}
