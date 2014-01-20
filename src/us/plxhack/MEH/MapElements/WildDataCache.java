package us.plxhack.MEH.MapElements;

import java.util.HashMap;

import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBARom;

public class WildDataCache extends Thread implements Runnable
{
	private static HashMap<Integer,WildData> dataCache = new HashMap<Integer,WildData>();
	private static GBARom rom;
	private static int initialNum;
	
	public WildDataCache(GBARom rom)
	{
		WildDataCache.rom = rom;
	}
	
	public static void gatherData()
	{
		long pData = DataStore.WildPokemon;
		int count = 0;
		while(true)
		{
			WildDataHeader h = new WildDataHeader(rom, (int)pData);
			if(h.bBank == (byte)0xFF && h.bMap == (byte)0xFF)
				break;
			
			WildData d = new WildData(rom,h);
			int num = (h.bBank & 0xFF) + ((h.bMap & 0xFF)<<8);
			dataCache.put(num,d);
			pData += (4 * 5);
			count++;
		}
		initialNum = count;
	}
	
	public static void save()
	{
		long pData = DataStore.WildPokemon;
		if(initialNum < dataCache.size())
		{
			pData = rom.findFreespace(DataStore.FreespaceStart, WildDataHeader.getSize() * dataCache.size());
			rom.repoint((int)DataStore.WildPokemon, (int)pData, 14); //TODO: Maybe make this configurable?
			rom.floodBytes((int)DataStore.WildPokemon, (byte)0xFF, initialNum * WildDataHeader.getSize()); //TODO Make configurable
		}
		
		for(WildData d : dataCache.values())
		{
			d.save((int) pData);
			pData += (4 * 5);
		}
	}
	
	public static WildData getWildData(int bank, int map)
	{
		int num = (bank & 0xFF) + ((map & 0xFF)<<8);
		return dataCache.get(num);
	}
	
	public static WildData createWildDataIfNotExists(int bank, int map)
	{
		if(dataCache.containsKey((bank & 0xFF) + ((map & 0xFF)<<8)))
			return getWildData(bank,map);
		else
		{
			WildData d = new WildData(rom, bank, map);
			dataCache.put((bank & 0xFF) + ((map & 0xFF)<<8), d);
			return d;
		}
	}
	
	@Override
	public void run()
	{
		gatherData();
	}
}
