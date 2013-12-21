package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class SpritesNPC implements ISaveable {
	  public  byte b1;
	  public  byte bSpriteSet;
	  public  byte b3;
	  public  byte b4;
	  public byte bX;
	  public byte b6;
	  public byte bY;
	  public byte b8;
	  public byte b9;
	  public byte bBehavior1;
	  public byte b10;
	  public byte bBehavior2;
	  public byte bIsTrainer;
	  public byte b14;
	  public byte bTrainerLOS;
	  public byte b16;
	  public long pScript;
	  public int iFlag;
	  public byte b23;
	  public byte b24;
	  
	  
	  //Non struct vars
	  private int pData;
	  private GBARom rom;
	  public SpritesNPC(GBARom rom){
			 this(rom,rom.internalOffset);
		 }
	  public  SpritesNPC(GBARom rom, int offset){
			 pData = offset;
			 this.rom = rom;
			 rom.Seek(offset);
			 b1= rom.readByte();
			 bSpriteSet= rom.readByte();
			 b3= rom.readByte();
			 b4= rom.readByte();
			 bX= rom.readByte();
			 b6= rom.readByte();
			 bY= rom.readByte();
			 b8= rom.readByte();
			 b9= rom.readByte();
			 bBehavior1= rom.readByte();
			 b10= rom.readByte();
			 bBehavior2= rom.readByte();
			 bIsTrainer= rom.readByte();
			 b14= rom.readByte();
			 bTrainerLOS= rom.readByte();
			 b16= rom.readByte();
			 pScript= rom.getPointer();
			 iFlag= rom.readWord();
			 b23= rom.readByte();
			 b24= rom.readByte();
		 }

	  
	  public void save()
	  {
		  	 rom.Seek(pData);
			 rom.writeByte(b1);
			 rom.writeByte(bSpriteSet);
			 rom.writeByte(b3);
			 rom.writeByte(b4);
			 rom.writeByte(bX);
			 rom.writeByte(b6);
			 rom.writeByte(bY);
			 rom.writeByte(b8);
			 rom.writeByte(b9);
			 rom.writeByte(bBehavior1);
			 rom.writeByte(b10);
			 rom.writeByte(bBehavior2);
			 rom.writeByte(bIsTrainer);
			 rom.writeByte(b14);
			 rom.writeByte(bTrainerLOS);
			 rom.writeByte(b16);
			 rom.writePointer(pScript + (pScript == 0 ? 0 : 0x08000000));
			 rom.writeWord(iFlag);
			 rom.writeByte(b23);
			 rom.writeByte(b24);
	  }
}
