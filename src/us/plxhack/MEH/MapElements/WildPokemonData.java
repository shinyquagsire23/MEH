package us.plxhack.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class WildPokemonData implements ISaveable
{
	private WildDataType type;
	private GBARom rom;
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
		type = t;
		bRatio = ratio;
		pPokemonData = rom.findFreespace(0);
		//TODO rest of this stuff
	}
	
	public WildDataType getType()
	{
		return type;
	}
	
	public void save()
	{
		rom.writeByte(bRatio);
		rom.internalOffset += 0x3;
		rom.writePointer(pPokemonData);
		rom.Seek((int)pPokemonData);
		for(int i = 0; i < numPokemon[type.ordinal()]; i++)
		{
			aWildPokemon[i].save();
		}
	}
}
