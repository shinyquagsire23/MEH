package PokemonClasses;

import org.zzl.minegaming.GBAUtils.GBARom;

public class SpritesNPC {
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
	  public SpritesNPC(GBARom rom){
			 
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
	  public  SpritesNPC(GBARom rom, int offset){
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
