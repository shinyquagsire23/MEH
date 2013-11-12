package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.GBARom;

public class SpritesExit {
	  byte bX;
	  byte b2;
	  byte bY;
	  byte b4;
	  byte b5;
	  byte b6;
	  int hLevel;
	  SpritesExit(GBARom rom){

		  
		  
		   bX=rom.readByte();
		   b2=rom.readByte();
		   bY=rom.readByte();
		   b4=rom.readByte();
		   b5=rom.readByte();
		   b6=rom.readByte();
		   hLevel=rom.readWord();
		 }
		 SpritesExit(GBARom rom, int offset){
			 rom.Seek(offset);
			 
			   bX=rom.readByte();
			   b2=rom.readByte();
			   bY=rom.readByte();
			   b4=rom.readByte();
			   b5=rom.readByte();
			   b6=rom.readByte();
			   hLevel=rom.readWord();
		 }
}
