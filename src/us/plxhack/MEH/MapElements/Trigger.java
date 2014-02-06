package us.plxhack.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class Trigger implements ISaveable
{
	public byte bX;
	public byte b2;
	public byte bY;
	public byte b4;
	public int h3;
	public int hFlagCheck;
	public int hFlagValue;
	public int h6;
	public long pScript;

	private GBARom rom;

	void LoadTriggers(GBARom rom)
	{
		this.rom = rom;

		bX = rom.readByte();
		b2 = rom.readByte();
		bY = rom.readByte();
		b4 = rom.readByte();
		h3 = rom.readWord();
		hFlagCheck = rom.readWord();
		hFlagValue = rom.readWord();
		h6 = rom.readWord();
		pScript = rom.getPointer();
	}

	public Trigger(GBARom rom, int offset)
	{
		rom.Seek(offset);
		LoadTriggers(rom);
	}

	public Trigger(GBARom rom)
	{
		LoadTriggers(rom);
	}

	public Trigger(GBARom rom, byte x, byte y)
	{
		this.rom = rom;

		bX = 0;
		b2 = 0;
		bY = 0;
		b4 = 0;
		h3 = 0;
		hFlagCheck = 0;
		hFlagValue = 0;
		h6 = 0;
		pScript = 0;
	}

	public static int getSize()
	{
		return 16;
	}

	public void save()
	{
		rom.writeByte(bX);
		rom.writeByte(b2);
		rom.writeByte(bY);
		rom.writeByte(b4);
		rom.writeWord(h3);
		rom.writeWord(hFlagCheck);
		rom.writeWord(hFlagValue);
		rom.writeWord(h6);
		rom.writePointer(pScript + (pScript == 0 ? 0 : 0x08000000));
	}
}
