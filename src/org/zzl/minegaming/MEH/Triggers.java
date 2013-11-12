package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.GBARom;

public class Triggers {
	  byte bX;
	  byte b2;
	  byte bY;
	  byte b4;
	  int h3;
	  int hFlagCheck;
	  int hFlagValue;
	  int h6;
	  long pScript;
	  void LoadTriggers(GBARom rom)
	  {
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
	  Triggers(GBARom rom, int offset){
		  rom.Seek(offset);
		  LoadTriggers(rom);
	  }
	  Triggers(GBARom rom){
		  LoadTriggers(rom);
	  }
}
