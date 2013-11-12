package PokemonClasses;

import org.zzl.minegaming.GBAUtils.GBARom;

public class Sprites {
	  public   byte bNumNPC;
	  public   byte bNumExits;
	  public   byte bNumTraps;
	  public  byte bNumSigns;
	  public  long pNPC;
	  public  long pExits;
	  public  long pTraps;
	  public  long pSigns;
	  public Sprites(GBARom rom){
		  
		  bNumNPC=rom.readByte();
		  bNumExits=rom.readByte();
		  bNumTraps=rom.readByte();
		  bNumSigns=rom.readByte();
		  pNPC=rom.getPointer();
		  pExits=rom.getPointer();
		  pTraps=rom.getPointer();
		  pSigns=rom.getPointer();
	  }	  
	  public Sprites(GBARom rom, int offset){
		  
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
