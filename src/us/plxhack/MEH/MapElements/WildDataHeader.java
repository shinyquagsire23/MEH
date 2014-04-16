package us.plxhack.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;

public class WildDataHeader implements Cloneable
{
	public byte bBank, bMap;
	public long pGrass, pWater, pTrees, pFishing;
	private GBARom rom;
	public WildDataHeader(GBARom rom)
	{
		this(rom,rom.internalOffset);
	}
	
	public WildDataHeader(GBARom rom, int offset)
	{
		loadWildData(rom, offset);
	}
	
	public WildDataHeader(GBARom rom, int bank, int map)
	{
			this.rom = rom;
			
			bBank = (byte)bank;
			bMap = (byte)map;
			pGrass = 0;
			pWater = 0;
			pTrees = 0;
			pFishing = 0;
	}
	
	public static int getSize()
	{
		return 20;
	}
	
	private void loadWildData(GBARom rom, int offset)
	{
		this.rom = rom;
		
		rom.Seek(offset);
		bBank = rom.readByte();
		bMap = rom.readByte();
		rom.internalOffset+=2; //Filler bytes
		pGrass = rom.getPointer();
		pWater = rom.getPointer();
		pTrees = rom.getPointer();
		pFishing = rom.getPointer();
	}
	
	public void save(int headerloc)
	{
		rom.Seek(headerloc);
		rom.writeByte(bBank);
		rom.writeByte(bMap);
		rom.internalOffset+=2; //Filler bytes
		
		if(pGrass != 0)
			rom.writePointer((int)pGrass);
		else
			rom.writePointer((long)0);
		
		if(pWater != 0)
			rom.writePointer((int)pWater);
		else
			rom.writePointer((long)0);
		
		if(pTrees != 0)
			rom.writePointer((int)pTrees);
		else
			rom.writePointer((long)0);
		
		if(pFishing != 0)
			rom.writePointer((int)pFishing);
		else
			rom.writePointer((long)0);
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
