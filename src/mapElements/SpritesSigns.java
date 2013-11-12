package mapElements;

import org.zzl.minegaming.GBAUtils.GBARom;

public class SpritesSigns {
	  public  byte bX;
	  public  byte b2;
	  public  byte bY;
	  public  byte b4;
	  public  byte b5;
	  public   byte b6;
	  public   byte b7;
	  public   byte b8;
	  public   long pScript;
	  public   SpritesSigns(GBARom rom){
			 
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
	  public 	 SpritesSigns(GBARom rom, int offset){
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
