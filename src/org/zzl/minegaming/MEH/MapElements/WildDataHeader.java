package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class WildDataHeader implements ISaveable
{
	public byte bBank, bMap;
	public long pGrass, pWater, pTrees, pFishing;
	private int pData;
	private GBARom rom;
	public WildDataHeader(GBARom rom)
	{
		this(rom,rom.internalOffset);
	}
	
	public WildDataHeader(GBARom rom, int offset)
	{
		this.rom = rom;
		pData = offset;
		
		rom.Seek(offset);
		bBank = rom.readByte();
		bMap = rom.readByte();
		rom.internalOffset+=2; //Filler bytes
		pGrass = rom.getPointer();
		pWater = rom.getPointer();
		pTrees = rom.getPointer();
		pFishing = rom.getPointer();
	}
	
	@Override
	public void save()
	{
		rom.Seek(pData);
		rom.writeByte(bBank);
		rom.writeByte(bMap);
		rom.internalOffset+=2; //Filler bytes
		rom.writePointer(pGrass);
		rom.writePointer(pWater);
		rom.writePointer(pTrees);
		rom.writePointer(pFishing);
	}
}
