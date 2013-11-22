package org.zzl.minegaming.MEH.MapElements;

import java.util.HashMap;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.MEH.DataStore;
import org.zzl.minegaming.MEH.MapID;

public class WildDataCache extends Thread implements Runnable
{
	private static HashMap<MapID,WildData> dataCache = new HashMap<MapID,WildData>();
	private static GBARom rom;
	
	public WildDataCache(GBARom rom)
	{
		WildDataCache.rom = rom;
	}
	
	public static void gatherData()
	{
		long pData = DataStore.WildPokemon;
		while(true)
		{
			WildDataHeader h = new WildDataHeader(rom, (int)pData);
			if(h.bBank == (byte)0xFF && h.bMap == (byte)0xFF)
				break;
			
			WildData d = new WildData(rom,h);
			dataCache.put(new MapID(h.bBank & 0xFF,h.bMap & 0xFF),d);
			pData += (4 * 5);
		}
	}
	
	public static WildData getWildData(int bank, int map)
	{
		return dataCache.get(new MapID(bank,map));
	}
	
	@Override
	public void run()
	{
		gatherData();
	}
}
