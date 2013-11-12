package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.GBARom;

public class SpritesNPC {
	  byte b1;
	  byte bSpriteSet;
	  byte b3;
	  byte b4;
	  byte bX;
	  byte b6;
	  byte bY;
	  byte b8;
	  byte b9;
	  byte bBehavior1;
	  byte b10;
	  byte bBehavior2;
	  byte bIsTrainer;
	  byte b14;
	  byte bTrainerLOS;
	  byte b16;
	  long pScript;
	  int iFlag;
	  byte b23;
	  byte b24;
	  SpritesNPC(GBARom rom){
			 
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
		 SpritesNPC(GBARom rom, int offset){
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
}
