package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class SpritesExit implements ISaveable {
	public  byte bX;
	public byte b2;
	public byte bY;
	public byte b4;
	public byte b5;
	public byte b6;
	public byte bMap;
	public byte bBank;
	private int pData;
	private GBARom rom;
	public  SpritesExit(GBARom rom){
		this(rom,rom.internalOffset);
	}
	public SpritesExit(GBARom rom, int offset){
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
		pData = offset;
	}
	@Override
	public void save()
	{
		rom.Seek(pData);
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
