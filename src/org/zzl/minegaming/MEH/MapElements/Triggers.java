package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class Triggers implements ISaveable {
	  public   byte bX;
	  public   byte b2;
	  public   byte bY;
	  public   byte b4;
	  public   int h3;
	  public  int hFlagCheck;
	  public  int hFlagValue;
	  public  int h6;
	  public  long pScript;
	  private int pData;
	  private GBARom rom;
	  void LoadTriggers(GBARom rom)
	  {
		  pData = rom.internalOffset;
		  this.rom = rom;
		  
		  bX=rom.readByte();
		  b2=rom.readByte();
		  bY=rom.readByte();
		  b4=rom.readByte();
		  h3=rom.readWord();
		  hFlagCheck=rom.readWord();
		  hFlagValue=rom.readWord();
		  h6=rom.readWord();
		  pScript=rom.getPointer();
	  }
	  public   Triggers(GBARom rom, int offset){
		  rom.Seek(offset);
		  LoadTriggers(rom);
	  }
	  public  Triggers(GBARom rom){
		  LoadTriggers(rom);
	  }
	  
	  @Override
	  public void save()
	  {
		  rom.Seek(pData);
		  rom.writeByte(bX);
		  rom.writeByte(b2);
		  rom.writeByte(bY);
		  rom.writeByte(b4);
		  rom.writeWord(h3);
		  rom.writeWord(hFlagCheck);
		  rom.writeWord(hFlagValue);
		  rom.writeWord(h6);
		  rom.writePointer(pScript + (pScript == 0 ? 0 : 0x08000000));
	  }
}
