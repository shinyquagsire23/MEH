package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.GBARom;

public class SpritesSigns {
	  byte bX;
	  byte b2;
	  byte bY;
	  byte b4;
	  byte b5;
	  byte b6;
	  byte b7;
	  byte b8;
	  long pScript;
	  SpritesSigns(GBARom rom){
			 
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
		 SpritesSigns(GBARom rom, int offset){
			 rom.Seek(offset);
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
}
