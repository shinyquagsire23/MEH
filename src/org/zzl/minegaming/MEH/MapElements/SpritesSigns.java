package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class SpritesSigns implements ISaveable {
	  public  byte bX;
	  public  byte b2;
	  public  byte bY;
	  public  byte b4;
	  public  byte b5;
	  public   byte b6;
	  public   byte b7;
	  public   byte b8;
	  public   long pScript;
	  private int pData;
	  private GBARom rom;
	  public   SpritesSigns(GBARom rom){
		   this(rom,rom.internalOffset);
		 }
	  public 	 SpritesSigns(GBARom rom, int offset){
			 rom.Seek(offset);
			 this.rom = rom;
			 pData = offset;
			 bX=rom.readByte();
			 b2=rom.readByte();
			 bY=rom.readByte();
			 b4=rom.readByte();
			 b5=rom.readByte();
			 b6=rom.readByte();
			 b7=rom.readByte();
			 b8=rom.readByte();
			 pScript=rom.getPointer();
		 }

	  public void save()
	  {
		   rom.Seek(pData);
		   rom.writeByte(bX);
		   rom.writeByte(b2);
		   rom.writeByte(bY);
		   rom.writeByte(b4);
		   rom.writeByte(b5);
		   rom.writeByte(b6);
		   rom.writeByte(b7);
		   rom.writeByte(b8);
		   rom.writePointer(pScript);
	  }
}
