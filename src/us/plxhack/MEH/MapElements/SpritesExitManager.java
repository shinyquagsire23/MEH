package us.plxhack.MEH.MapElements;

import java.util.ArrayList;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

import us.plxhack.MEH.IO.Map;

public class SpritesExitManager implements ISaveable
{
	public ArrayList<SpriteExit> mapExits;
	private Map loadedMap;
	private int internalOffset = 0;
	private int originalSize;
	private GBARom rom;

	public int getSpriteIndexAt(int x, int y)
	{
		int i = 0;
		for (SpriteExit exit : mapExits)
		{
			if (exit.bX == x && exit.bY == y)
			{
				return i;
			}
			i++;
		}

		return -1;

	}

	public SpritesExitManager(GBARom rom, Map m, int offset, int count)
	{
		rom.Seek(offset);
		mapExits = new ArrayList<SpriteExit>();
		int i = 0;
		for (i = 0; i < count; i++)
		{
			mapExits.add(new SpriteExit(rom));

		}
		originalSize = getSize();
		internalOffset = offset;
		this.rom = rom;
		this.loadedMap = m;
	}
	
	public int getSize()
	{
		return mapExits.size() * SpriteExit.getSize();
	}

	public void add(int x, int y)
	{
		mapExits.add(new SpriteExit(rom, (byte)x,(byte)y));
	}

	public void remove(int x, int y)
	{
		mapExits.remove(getSpriteIndexAt(x,y));
	}
	
	public void save()
	{
		rom.floodBytes(BitConverter.shortenPointer(internalOffset), rom.freeSpaceByte, originalSize);
		
		//TODO make this a setting, ie always repoint vs keep pointers
		int i = getSize();
		if(originalSize < getSize())
		{
			internalOffset = rom.findFreespace(DataStore.FreespaceStart, getSize());
			
			if(internalOffset < 0x08000000)
				internalOffset += 0x08000000;
		}
		
		loadedMap.mapSprites.pExits = internalOffset & 0x1FFFFFF;
		loadedMap.mapSprites.bNumExits = (byte)mapExits.size();

		rom.Seek(internalOffset);
		for (SpriteExit e : mapExits)
			e.save();
	}
}
