package us.plxhack.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class SpriteExit implements ISaveable {
	public  byte bX;
	public byte b2;
	public byte bY;
	public byte b4;
	public byte b5;
	public byte b6;
	public byte bMap;
	public byte bBank;
	private GBARom rom;
	
	public  SpriteExit(GBARom rom){
		this(rom,rom.internalOffset);
	}
	public SpriteExit(GBARom rom, int offset)
	{
		this.rom = rom; 
		rom.Seek(offset);

		bX=rom.readByte();
		b2=rom.readByte();
		bY=rom.readByte();
		b4=rom.readByte();
		b5=rom.readByte();
		b6=rom.readByte();
		bMap=rom.readByte();
		bBank=rom.readByte();
	}
	
	public SpriteExit(GBARom rom, byte x, byte y)
	{
		this.rom = rom; 
		
		bX = x;
		bY = y;
		b2 = 0;
		b4 = 0;
		b5 = 0;
		b6 = 0;
		bMap = 0;
		bBank = 0;
	}
	
	public static int getSize()
	{
		return 8;
	}
	
	public void save()
	{
		rom.writeByte(bX);
		rom.writeByte(b2);
		rom.writeByte(bY);
		rom.writeByte(b4);
		rom.writeByte(b5);
		rom.writeByte(b6);
		rom.writeByte(bMap);
		rom.writeByte(bBank);
	}
}
