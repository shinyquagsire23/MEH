package us.plxhack.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBARom;

public class WildData
{
	public WildPokemonData[] aWildPokemon = new WildPokemonData[4];
	public WildDataHeader wildDataHeader;
	private GBARom rom;
	
	public WildData(GBARom rom, WildDataHeader h)
	{
		this.rom = rom;
		wildDataHeader = h;
		
		rom.Seek((int)h.pGrass);
		if(h.pGrass != 0)
			aWildPokemon[0] = new WildPokemonData(rom, WildDataType.GRASS);
		
		rom.Seek((int)h.pWater);
		if(h.pWater != 0)
			aWildPokemon[1] = new WildPokemonData(rom, WildDataType.WATER);
		
		rom.Seek((int)h.pTrees);
		if(h.pTrees != 0)
			aWildPokemon[2] = new WildPokemonData(rom, WildDataType.TREE);
		
		rom.Seek((int)h.pFishing);
		if(h.pFishing != 0)
			aWildPokemon[3] = new WildPokemonData(rom, WildDataType.FISHING);
	}
	
	public WildData(GBARom rom, int bank, int map)
	{
		this.rom = rom;
		wildDataHeader = new WildDataHeader(rom, bank, map);
	}
	
	public void addWildData(WildDataType t)
	{
		addWildData(t, (byte)0x15);
	}
	
	public void removeWildData(WildDataType t)
	{
		WildPokemonData d = null;
		if(aWildPokemon[t.ordinal()] == null)
			return;
		int size;
		int pkmnData;
		switch(t)
		{
			case WATER:
				d = aWildPokemon[1];
				size = d.getWildDataSize();
				pkmnData = (int)d.pPokemonData;
				rom.floodBytes((int)wildDataHeader.pWater, DataStore.FreespaceByte, WildPokemonData.getSize());
				wildDataHeader.pWater = 0;
				aWildPokemon[1] = null;
				break;
			case TREE:
				d = aWildPokemon[2];
				size = d.getWildDataSize();
				pkmnData = (int)d.pPokemonData;
				rom.floodBytes((int)wildDataHeader.pTrees, DataStore.FreespaceByte, WildPokemonData.getSize());
				wildDataHeader.pTrees = 0;
				aWildPokemon[2] = null;
				break;
			case FISHING:
				d = aWildPokemon[3];
				size = d.getWildDataSize();
				pkmnData = (int)d.pPokemonData;
				rom.floodBytes((int)wildDataHeader.pFishing, DataStore.FreespaceByte, WildPokemonData.getSize());
				wildDataHeader.pFishing = 0;
				aWildPokemon[3] = null;
				break;
			case GRASS:
			default:
				d = aWildPokemon[0];
				size = d.getWildDataSize();
				pkmnData = (int)d.pPokemonData;
				rom.floodBytes((int)wildDataHeader.pGrass, DataStore.FreespaceByte, WildPokemonData.getSize());
				wildDataHeader.pGrass = 0;
				aWildPokemon[0] = null;
				break;
		}
		rom.floodBytes(pkmnData, DataStore.FreespaceByte, size);
	}
	
	public void addWildData(WildDataType t, byte ratio)
	{
		WildPokemonData d = new WildPokemonData(rom, t, ratio);
		switch(t)
		{
			case GRASS:
				aWildPokemon[0] = d;
				break;
			case WATER:
				aWildPokemon[1] = d;
				break;
			case TREE:
				aWildPokemon[2] = d;
				break;
			case FISHING:
				aWildPokemon[3] = d;
				break;
		}
	}
	
	public void save(int headerloc)
	{
		if(aWildPokemon[0] != null)
		{
			if(wildDataHeader.pGrass == 0)
				wildDataHeader.pGrass = rom.findFreespace(DataStore.FreespaceStart, 8);
			rom.floodBytes((int)wildDataHeader.pGrass, (byte)0, 8); //Prevent these bytes from being used by wild data
			rom.Seek((int) wildDataHeader.pGrass);
			aWildPokemon[0].save();
		}
		if(aWildPokemon[1] != null)
		{
			if(wildDataHeader.pWater == 0)
				wildDataHeader.pWater = rom.findFreespace(DataStore.FreespaceStart, 8);
			rom.floodBytes((int)wildDataHeader.pWater, (byte)0, 8); //Prevent these bytes from being used by wild data
			rom.Seek((int) wildDataHeader.pWater);
			aWildPokemon[1].save();
		}
		if(aWildPokemon[2] != null)
		{
			if(wildDataHeader.pTrees == 0)
				wildDataHeader.pTrees = rom.findFreespace(DataStore.FreespaceStart, 8);
			rom.floodBytes((int)wildDataHeader.pTrees, (byte)0, 8); //Prevent these bytes from being used by wild data
			rom.Seek((int) wildDataHeader.pTrees);
			aWildPokemon[2].save();
		}
		if(aWildPokemon[3] != null)
		{
			if(wildDataHeader.pFishing == 0)
				wildDataHeader.pFishing = rom.findFreespace(DataStore.FreespaceStart, 8);
			rom.floodBytes((int)wildDataHeader.pFishing, (byte)0, 8); //Prevent these bytes from being used by wild data
			rom.Seek((int) wildDataHeader.pFishing);
			aWildPokemon[3].save();
		}
		wildDataHeader.save(headerloc);
	}
}
