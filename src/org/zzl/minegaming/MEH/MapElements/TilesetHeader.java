package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class TilesetHeader implements ISaveable {
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
	  private int bOffset;
	  private GBARom rom;
	  public TilesetHeader(GBARom rom, int offset){
		  this.rom = rom;
		  bOffset=offset;
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
	  
	  
	  public void save()
	  {
		  rom.Seek(bOffset);
		  rom.writeByte(bCompressed);
		  rom.writeByte((byte) (isPrimary ? 0x0 : 0x1));
		  rom.writeByte(b2);
		  rom.writeByte(b3);
		  
		  rom.writePointer(pGFX);
		  rom.writePointer((int)pPalettes);
		  rom.writePointer((int)pBlocks);
		  rom.writePointer((int)pBehavior);
		  rom.writePointer((int)pAnimation);
	  }
}
