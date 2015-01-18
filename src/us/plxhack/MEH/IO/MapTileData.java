package us.plxhack.MEH.IO;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;
import us.plxhack.MEH.Structures.MapTile;

public class MapTileData implements ISaveable
{
	private int originalPointer;
	private int originalSize;
	private MapData mData;
	private GBARom rom;
	private MapTile[][] mapTiles;
	public MapTileData(GBARom rom, MapData mData)
	{
		this.mData = mData;
		this.rom = rom;
		mapTiles = new MapTile[(int)mData.mapWidth][(int) mData.mapHeight];
		for(int x = 0; x < mData.mapWidth; x++)
		{
			for(int y = 0; y < mData.mapHeight; y++)
			{
				
				int index = (int) ((y*mData.mapWidth) + x);
				int raw = rom.readWord(BitConverter.shortenPointer(mData.mapTilesPtr) + index*2);
				MapTile m = new MapTile((raw & 0x3FF),(raw&0xFC00) >> 10);
				mapTiles[x][y] = m;
				
			}
		}
		this.originalPointer = mData.mapTilesPtr;
		this.originalSize = getSize();
	}
	
	public MapTile getTile(int x, int y)
	{
		if(x < 0 && y < 0)
			return mapTiles[0][0];
		else if(x < 0)
			return mapTiles[0][y];
		else if(y < 0)
			return mapTiles[x][0];
		
		if(x > mData.mapWidth && y > mData.mapHeight)
			return mapTiles[(int)mData.mapWidth][(int)mData.mapHeight];
		else if(x > mData.mapWidth)
			return mapTiles[(int)mData.mapWidth][y];
		else if(y > mData.mapHeight)
			return mapTiles[x][(int)mData.mapHeight];
		
		return mapTiles[x][y];
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
	
	public int getSize()
	{
		return (int) ((mData.mapWidth * mData.mapHeight) * 2);
	}
	
	public void save()
	{
		for(int x = 0; x < mData.mapWidth; x++)
		{
			for(int y = 0; y < mData.mapHeight; y++)
			{
				
				int index = (int) ((y*mData.mapWidth) + x);
				rom.writeWord(BitConverter.shortenPointer(mData.mapTilesPtr) + index*2, mapTiles[x][y].getID() + ((mapTiles[x][y].getMeta() & 0x3F) << 10));
			}
		}
	}
	
	public void resize(long xSize, long ySize)
	{
		MapTile[][] newMapTiles = new MapTile[(int)xSize][(int)ySize];
		mData.mapWidth = xSize;
		mData.mapHeight = ySize;
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
		
		mapTiles = newMapTiles;
	}
}
