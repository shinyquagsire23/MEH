package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.MEH.ConnectionType;

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
		int i = 0;
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
