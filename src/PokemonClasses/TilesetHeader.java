package PokemonClasses;

import org.zzl.minegaming.GBAUtils.GBARom;

public class TilesetHeader {
	  public   byte bCompressed;
	  public   boolean isPrimary;
	  public  byte b2;
	  public  byte b3;
	  public  long pGFX;
	  public  long pPalettes;
	  public  long pBlocks;
	  public long pBehavior;
	  public long pAnimation;
	  public long hdrSize;//This is internal and does not go into the ROM
	  public TilesetHeader(GBARom rom, int offset){
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
