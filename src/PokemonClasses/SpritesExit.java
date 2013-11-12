package PokemonClasses;

import org.zzl.minegaming.GBAUtils.GBARom;

public class SpritesExit {
	  public  byte bX;
	  public byte b2;
	  public byte bY;
	  public byte b4;
	  public byte b5;
	  public byte b6;
	  public int hLevel;
	  public  SpritesExit(GBARom rom){

		  
		  
		   bX=rom.readByte();
		   b2=rom.readByte();
		   bY=rom.readByte();
		   b4=rom.readByte();
		   b5=rom.readByte();
		   b6=rom.readByte();
		   hLevel=rom.readWord();
		 }
	  public SpritesExit(GBARom rom, int offset){
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
