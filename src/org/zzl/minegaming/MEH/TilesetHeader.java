package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.GBARom;

public class TilesetHeader {
	  byte bCompressed;
	  boolean isPrimary;
	  byte b2;
	  byte b3;
	  long pGFX;
	  long pPalettes;
	  long pBlocks;
	  long pBehavior;
	  long pAnimation;
	  long hdrSize;//This is internal and does not go into the ROM
	  TilesetHeader(GBARom rom, int offset){
		  int bOffset=offset;
		  rom.Seek(bOffset);
		  bCompressed=rom.readByte();
		  isPrimary=(rom.readByte() == 0);//Reflect this when saving
		  b2=rom.readByte();
		  b3=rom.readByte();
		  
		  pGFX = rom.getPointer();
		  pPalettes = rom.getPointer();
		  pBlocks = rom.getPointer();
		  pBehavior = rom.getPointer();
		  pAnimation = rom.getPointer();
		  hdrSize=rom.internalOffset-offset;
		  
	  }
}
