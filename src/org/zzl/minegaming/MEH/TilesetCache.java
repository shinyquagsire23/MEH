package org.zzl.minegaming.MEH;

import java.util.HashMap;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ROMManager;

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
			return cache.get(offset);
		else
		{
			Tileset t =  new Tileset(ROMManager.getActiveROM(), offset);
			cache.put(offset, t);
			return t;
		}
	}
}
