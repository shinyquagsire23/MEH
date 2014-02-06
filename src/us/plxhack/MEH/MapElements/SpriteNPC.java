package us.plxhack.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class SpriteNPC implements ISaveable
{
	public byte b1;
	public int bSpriteSet;
	public byte b4;
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

	// Non struct vars
	private GBARom rom;

	public SpriteNPC(GBARom rom)
	{
		this(rom, rom.internalOffset);
	}

	public SpriteNPC(GBARom rom, int offset)
	{
		this.rom = rom;
		rom.Seek(offset);
		b1 = rom.readByte();
		bSpriteSet = rom.readWord();
		b4 = rom.readByte();
		bX = rom.readByte();
		b6 = rom.readByte();
		bY = rom.readByte();
		b8 = rom.readByte();
		b9 = rom.readByte();
		bBehavior1 = rom.readByte();
		b10 = rom.readByte();
		bBehavior2 = rom.readByte();
		bIsTrainer = rom.readByte();
		b14 = rom.readByte();
		bTrainerLOS = rom.readByte();
		b16 = rom.readByte();
		pScript = rom.getPointer();
		iFlag = rom.readWord();
		b23 = rom.readByte();
		b24 = rom.readByte();
	}
	
	public SpriteNPC(GBARom rom, byte x, byte y)
	{
		this.rom = rom;
		b1 = 0;
		bSpriteSet = 0;
		b4 = 0;
		bX = x;
		b6 = 0;
		bY = y;
		b8 = 0;
		b9 = 0;
		bBehavior1 = 0;
		b10 = 0;
		bBehavior2 = 0;
		bIsTrainer = 0;
		b14 = 0;
		bTrainerLOS = 0;
		b16 = 0;
		pScript = 0;
		iFlag = 0;
		b23 = 0;
		b24 = 0;
	}
	
	public static int getSize()
	{
		return 24;
	}

	public void save()
	{
		rom.writeByte(b1);
		rom.writeWord(bSpriteSet);
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
