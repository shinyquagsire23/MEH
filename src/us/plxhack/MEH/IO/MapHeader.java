package us.plxhack.MEH.IO;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class MapHeader implements ISaveable {
	  public   long pMap;
	  public   long pSprites;
	  public   long pScript;
	  public   long pConnect;
	  public  int hSong;
	  public  int hMap;
	  public  byte bLabelID;
	  public  byte bFlash;
	  public  byte bWeather;
	  public  byte bType;
	  public  byte bUnused1;
	  public  byte bUnused2;
	  public  byte bLabelToggle;
	  public  byte bUnused3;
	  private int bOffset;
	  private GBARom rom;
	  int hdrSize;//This is internal and does not go into the ROM
	 public MapHeader(GBARom rom, int offset){
		  bOffset=offset&0x1FFFFFF;
		  this.rom = rom;
		  
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

	 
	public void save()
	 {
		  rom.Seek(bOffset);
  		  rom.writePointer((int)pMap);
		  rom.writePointer((int)pSprites);
		  rom.writePointer((int)pScript);
		  rom.writePointer((int)pConnect);
		  rom.writeWord(hSong);
		  rom.writeWord(hMap);

		  rom.writeByte(bLabelID);
		  rom.writeByte(bFlash);
		  rom.writeByte(bWeather);
		  rom.writeByte(bType);
		  rom.writeByte(bUnused1);
		  rom.writeByte(bUnused2);
		  rom.writeByte(bLabelToggle);
		  rom.writeByte(bUnused3);
	 }
}
