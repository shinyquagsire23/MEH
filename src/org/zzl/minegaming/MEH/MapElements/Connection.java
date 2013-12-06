package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;

public class Connection
{
	private GBARom rom;
	public int dataLoc;
	public long lType, lOffset;
	public byte bBank, bMap;
	public int wFiller;
	
	public Connection(GBARom rom, int pointer)
	{
		this.rom = rom;
		dataLoc = pointer;
		load();
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
		rom.Seek(dataLoc);
		rom.writePointer(lType);
		rom.writeSignedPointer(lOffset);
		int i = 0;
		rom.writeByte(bBank);
		rom.writeByte(bMap);
		rom.writeWord(wFiller);
	}
}
