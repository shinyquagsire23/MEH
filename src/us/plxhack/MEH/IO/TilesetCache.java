package us.plxhack.MEH.IO;

import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ROMManager;

import java.util.HashMap;

public class TilesetCache
{
	private static HashMap<Integer, Tileset> cache = new HashMap<Integer, Tileset>();
	private GBARom rom;
	
	private TilesetCache(){}
	
	public static void contains(int offset)
	{
		
	}
	
	/**
	 * Pulls a tileset from the tileset cache. Create a new tileset if one is not cached.
	 * @param offset Tileset data offset
	 * @return
	 */
	public static Tileset get(int offset)
	{
		if(cache.containsKey(offset))
		{
			Tileset t = cache.get(offset);
			if(t.modified)
			{
				t.loadData(offset);
				t.renderTiles(offset);
				t.modified = false;
			}
			return t;
		}
		else
		{
			Tileset t =  new Tileset(ROMManager.getActiveROM(), offset);
			cache.put(offset, t);
			return t;
		}
	}

	public static void clearCache()
	{
		cache = new HashMap<Integer, Tileset>();
	}

	public static void saveAllTilesets()
	{
		for(Tileset t : cache.values())
			t.save();
	}
	
	public static void switchTileset(Map loadedMap)
	{
		get(loadedMap.getMapData().globalTileSetPtr).resetPalettes();
		get(loadedMap.getMapData().localTileSetPtr).resetPalettes();
		for(int i = DataStore.MainTSPalCount-1; i < 13; i++)
			get(loadedMap.getMapData().globalTileSetPtr).getPalette()[i] = get(loadedMap.getMapData().localTileSetPtr).getROMPalette()[i];
		get(loadedMap.getMapData().localTileSetPtr).setPalette(get(loadedMap.getMapData().globalTileSetPtr).getPalette());
		get(loadedMap.getMapData().localTileSetPtr).renderPalettedTiles();
		get(loadedMap.getMapData().globalTileSetPtr).renderPalettedTiles();
		get(loadedMap.getMapData().localTileSetPtr).startTileThreads();
		get(loadedMap.getMapData().globalTileSetPtr).startTileThreads();
	}
}
