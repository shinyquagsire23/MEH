package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class WildPokemonData implements ISaveable
{
	private long pData;
	private GBARom rom;
	public byte bRatio;
	public long pUnknown;
	public WildPokemon[] aWildPokemon = new WildPokemon[12];
	public WildPokemonData(GBARom rom, long offset)
	{
		this.rom = rom;
		this.pData = offset;
		
		rom.Seek((int)offset);
		bRatio = rom.readByte();
		rom.internalOffset += 0x3;
		pUnknown = rom.getPointer();
		for(int i = 0; i < 12; i++)
		{
			aWildPokemon[i] = new WildPokemon(rom);
		}
	}
	
	public void save()
	{
		rom.Seek((int)pData);
		rom.writeByte(bRatio);
		rom.internalOffset += 0x3;
		rom.writePointer(pUnknown);
		for(int i = 0; i < 12; i++)
		{
			aWildPokemon[i].save();
		}
	}
}
