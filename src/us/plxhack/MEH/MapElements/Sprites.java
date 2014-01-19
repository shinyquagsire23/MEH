package us.plxhack.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;

public class Sprites {
	public   byte bNumNPC;
	public   byte bNumExits;
	public   byte bNumTraps;
	public  byte bNumSigns;
	public  long pNPC;
	public  long pExits;
	public  long pTraps;
	public  long pSigns;
	private int pData;
	private GBARom rom;
	public Sprites(GBARom rom){

		this(rom,rom.internalOffset);
	}	  
	public Sprites(GBARom rom, int offset){

		pData = offset;
		this.rom = rom;
		rom.Seek(offset&0x1FFFFFF);
		bNumNPC=rom.readByte();
		bNumExits=rom.readByte();
		bNumTraps=rom.readByte();
		bNumSigns=rom.readByte();
		pNPC=rom.getPointer();
		pExits=rom.getPointer();
		pTraps=rom.getPointer();
		pSigns=rom.getPointer();
	}

	public void save()
	{
		rom.Seek(pData&0x1FFFFFF);
		rom.writeByte(bNumNPC);
		rom.writeByte(bNumExits);
		rom.writeByte(bNumTraps);
		rom.writeByte(bNumSigns);
		
		rom.writePointer((int)pNPC);
		rom.writePointer((int)pExits);
		rom.writePointer((int)pTraps);
		rom.writePointer((int)pSigns);
	}
}
