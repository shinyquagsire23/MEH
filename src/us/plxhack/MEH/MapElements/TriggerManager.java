package us.plxhack.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;
import us.plxhack.MEH.IO.Map;

import java.util.ArrayList;

public class TriggerManager implements ISaveable
{
	public ArrayList<Trigger> mapTriggers;
	private Map loadedMap;
	private int internalOffset = 0;
	private int originalSize;
	private GBARom rom;

	public int getSpriteIndexAt(int x, int y)
	{
		int i = 0;
		for (i = 0; i < mapTriggers.size(); i++)
		{
			if (mapTriggers.get(i).bX == x && mapTriggers.get(i).bY == y)
			{
				return i;
			}
		}

		return -1;
	}

	public void LoadTriggers(GBARom rom, Map m, int count)
	{
		internalOffset = rom.internalOffset;
		mapTriggers = new ArrayList<Trigger>();
		int i = 0;
		for (i = 0; i < count; i++)
		{
			mapTriggers.add(new Trigger(rom));
		}
		originalSize = getSize();
		this.rom = rom;
		this.loadedMap = m;
	}

	public TriggerManager(GBARom rom, Map m, int count)
	{
		LoadTriggers(rom, m, count);

	}

	public TriggerManager(GBARom rom, Map m, int offset, int count)
	{
		rom.Seek(offset);
		LoadTriggers(rom, m, count);
	}
	
	public int getSize()
	{
		return mapTriggers.size() * Trigger.getSize();
	}

	public void add(int x, int y)
	{
		mapTriggers.add(new Trigger(rom, (byte)x,(byte)y));
	}

	public void remove(int x, int y)
	{
		mapTriggers.remove(getSpriteIndexAt(x,y));
	}

	public void save()
	{
		rom.floodBytes(BitConverter.shortenPointer(internalOffset), rom.freeSpaceByte, originalSize);

		// TODO make this a setting, ie always repoint vs keep pointers
		int i = getSize();
		if (originalSize < getSize())
		{
			internalOffset = rom.findFreespace(DataStore.FreespaceStart, getSize());

			if (internalOffset < 0x08000000)
				internalOffset += 0x08000000;
		}

		loadedMap.mapSprites.pTraps = internalOffset & 0x1FFFFFF;
		loadedMap.mapSprites.bNumTraps = (byte) mapTriggers.size();

		rom.Seek(internalOffset);
		for (Trigger t : mapTriggers)
			t.save();
	}
}
