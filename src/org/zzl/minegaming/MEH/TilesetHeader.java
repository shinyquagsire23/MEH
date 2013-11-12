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
		  bCompressed=rom.readByte(bOffset);bOffset+=1;
		  isPrimary=(rom.readByte(offset+1) == 0);bOffset+=1;//Reflect this when saving
		  b2=rom.readByte(bOffset);bOffset+=1;
		  b3=rom.readByte(bOffset);bOffset+=1;
		  
		  pGFX = rom.getPointer(bOffset);bOffset+=4;
		  pPalettes = rom.getPointer(bOffset);bOffset+=4;
		  pBlocks = rom.getPointer(bOffset);bOffset+=4;
		  pBehavior = rom.getPointer(bOffset);bOffset+=4;
		  pAnimation = rom.getPointer(bOffset);bOffset+=4;
		  hdrSize=bOffset-offset;
		  
	  }
}
