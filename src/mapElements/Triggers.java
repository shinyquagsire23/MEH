package mapElements;

import org.zzl.minegaming.GBAUtils.GBARom;

public class Triggers {
	  public   byte bX;
	  public   byte b2;
	  public   byte bY;
	  public   byte b4;
	  public   int h3;
	  public  int hFlagCheck;
	  public  int hFlagValue;
	  public  int h6;
	  public  long pScript;
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
	  public   Triggers(GBARom rom, int offset){
		  rom.Seek(offset);
		  LoadTriggers(rom);
	  }
	  public  Triggers(GBARom rom){
		  LoadTriggers(rom);
	  }
}
