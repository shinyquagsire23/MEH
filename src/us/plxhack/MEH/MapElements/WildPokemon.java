package us.plxhack.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class WildPokemon implements ISaveable
{
	private GBARom rom;
	public byte bMinLV, bMaxLV;
	public int wNum;
	public WildPokemon(GBARom rom)
	{
		this.rom = rom;
		bMinLV = rom.readByte();
		bMaxLV = rom.readByte();
		wNum = rom.readWord();
	}
	
	public WildPokemon(GBARom rom, int minLV, int maxLV, int pokemon)
	{
		this.rom = rom;
		bMinLV = (byte)minLV;
		bMaxLV = (byte)maxLV;
		wNum   = pokemon;
	}
	
	public static int getSize()
	{
		return 4;
	}
	
	public void save()
	{
		rom.writeByte(bMinLV);
		rom.writeByte(bMaxLV);
		rom.writeWord(wNum);
	}
}
