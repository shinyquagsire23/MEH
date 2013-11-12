package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.GBARom;

public class Sprites {
	  byte bNumNPC;
	  byte bNumExits;
	  byte bNumTraps;
	  byte bNumSigns;
	  long pNPC;
	  long pExits;
	  long pTraps;
	  long pSigns;
	  Sprites(GBARom rom){
		  
		  bNumNPC=rom.readByte();
		  bNumExits=rom.readByte();
		  bNumTraps=rom.readByte();
		  bNumSigns=rom.readByte();
		  pNPC=rom.getPointer();
		  pExits=rom.getPointer();
		  pTraps=rom.getPointer();
		  pSigns=rom.getPointer();
	  }	  
	  Sprites(GBARom rom, int offset){
		  
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
}
