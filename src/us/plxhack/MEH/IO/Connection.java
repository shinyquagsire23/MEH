package us.plxhack.MEH.IO;

import org.zzl.minegaming.GBAUtils.GBARom;
import us.plxhack.MEH.Structures.ConnectionType;

public class Connection
{
	private GBARom rom;
	public long lType, lOffset;
	public byte bBank, bMap;
	public int wFiller;
	
	public Connection(GBARom rom)
	{
		this.rom = rom;
		load();
	}
	
	public Connection(GBARom rom, ConnectionType c, byte bank, byte map)
	{
		this.rom = rom;
		lType = c.ordinal();
		lOffset = 0;
		bBank = bank;
		bMap = map;
		wFiller = 0;
	}
	
	public void load()
	{
		lType = rom.getPointer(true);
		lOffset = rom.getSignedLong(true);
		bBank = rom.readByte();
		bMap = rom.readByte();
		wFiller = rom.readWord();
	}
	
	public void save()
	{
		rom.writePointer(lType);
		rom.writeSignedPointer(lOffset);
		rom.writeByte(bBank);
		rom.writeByte(bMap);
		rom.writeWord(wFiller);
	}
}
