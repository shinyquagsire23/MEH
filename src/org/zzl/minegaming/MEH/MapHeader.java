package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.GBARom;

public class MapHeader {
	  long pMap;
	  long pSprites;
	  long pScript;
	  long pConnect;
	  int hSong;
	  int hMap;
	  byte bLabelID;
	  byte bFlash;
	  byte bWeather;
	  byte bType;
	  byte bUnused1;
	  byte bUnused2;
	  byte bLabelToggle;
	  byte bUnused3;
	  int hdrSize;//This is internal and does not go into the ROM
	 public MapHeader(GBARom rom, int offset){
		  int bOffset=offset-0x8000000;
		/*  pMap = rom.getPointer(bOffset);bOffset+=4;
		  pSprites =rom.getPointer(bOffset);bOffset+=4;
		  pScript = rom.getPointer(bOffset);bOffset+=4;
		  pConnect = rom.getPointer(bOffset);bOffset+=4;
		  hSong = rom.readWord(bOffset);bOffset+=2;
		  hMap = rom.readWord(bOffset);bOffset+=2;

		  bLabelID= rom.readByte(bOffset);bOffset+=1;
		  bFlash= rom.readByte(bOffset);bOffset+=1;
		  bWeather= rom.readByte(bOffset);bOffset+=1;
		  bType= rom.readByte(bOffset);bOffset+=1;
		  bUnused1= rom.readByte(bOffset);bOffset+=1;
		  bUnused2= rom.readByte(bOffset);bOffset+=1;
		  bLabelToggle= rom.readByte(bOffset);bOffset+=1;
		  bUnused3= rom.readByte(bOffset);bOffset+=1;*/
		  rom.Seek(bOffset);
  		  pMap = rom.getPointer();
		  pSprites =rom.getPointer();
		  pScript = rom.getPointer();
		  pConnect = rom.getPointer();
		  hSong = rom.readWord();
		  hMap = rom.readWord();

		  bLabelID= rom.readByte();
		  bFlash= rom.readByte();
		  bWeather= rom.readByte();
		  bType= rom.readByte();
		  bUnused1= rom.readByte();
		  bUnused2= rom.readByte();
		  bLabelToggle= rom.readByte();
		  bUnused3= rom.readByte();
		  hdrSize=rom.internalOffset-bOffset-0x8000000;
	  }
}
