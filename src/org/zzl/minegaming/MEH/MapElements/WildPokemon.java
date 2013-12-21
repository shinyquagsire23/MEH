package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class WildPokemon implements ISaveable
{
	private GBARom rom;
	private int pData;
	public byte bMinLV, bMaxLV;
	public int wNum;
	public WildPokemon(GBARom rom)
	{
		this.rom = rom;
		this.pData = rom.internalOffset;
		bMinLV = rom.readByte();
		bMaxLV = rom.readByte();
		wNum = rom.readWord();
	}
	
	
	public void save()
	{
		rom.writeByte(bMinLV);
		rom.writeByte(bMaxLV);
		rom.writeWord(wNum);
	}
}
