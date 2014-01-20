package us.plxhack.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class WildPokemonData implements ISaveable
{
	private WildDataType type;
	private GBARom rom;
	private long pData;
	public byte bRatio;
	public long pPokemonData;
	public WildPokemon[] aWildPokemon;
	private static int[] numPokemon = new int[] {12,5,5,10};
	
	
	public WildPokemonData(GBARom rom, WildDataType t)
	{
		this.rom = rom;
		type = t;
		bRatio = rom.readByte();
		rom.internalOffset += 0x3;
		pPokemonData = rom.getPointer();
		aWildPokemon = new WildPokemon[numPokemon[type.ordinal()]];
		
		rom.Seek((int)pPokemonData);
		for(int i = 0; i < numPokemon[type.ordinal()]; i++)
		{
			aWildPokemon[i] = new WildPokemon(rom);
		}
	}
	
	public WildPokemonData(GBARom rom, WildDataType t, byte ratio)
	{
		this.rom = rom;
		type = t;
		bRatio = ratio;
		pPokemonData = -1;
		aWildPokemon = new WildPokemon[numPokemon[type.ordinal()]];
		
		rom.Seek((int)pPokemonData);
		for(int i = 0; i < numPokemon[type.ordinal()]; i++)
		{
			aWildPokemon[i] = new WildPokemon(rom,1,1,0);
		}
	}
	
	public static int getSize()
	{
		return 8;
	}
	
	public int getWildDataSize()
	{
		return numPokemon[type.ordinal()] * WildPokemon.getSize();
	}
	
	public WildDataType getType()
	{
		return type;
	}
	
	public void save()
	{
		rom.writeByte(bRatio);
		rom.internalOffset += 0x3;
		
		if(pPokemonData == -1)
			pPokemonData = rom.findFreespace(DataStore.FreespaceStart, getWildDataSize());
		
		rom.writePointer((int)pPokemonData);
		rom.Seek((int)pPokemonData);
		for(int i = 0; i < numPokemon[type.ordinal()]; i++)
		{
			aWildPokemon[i].save();
		}
	}
}
