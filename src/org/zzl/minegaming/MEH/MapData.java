package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class MapData implements ISaveable
{
	private GBARom rom;
	private int dataLoc;
	public long mapWidth, mapHeight;
	public int borderTilePtr, mapTilesPtr, globalTileSetPtr, localTileSetPtr;
	public int borderWidth, borderHeight;
	public int secondarySize;
	
	public MapData(GBARom rom, int mapDataLoc)
	{
		this.rom = rom;
		dataLoc = mapDataLoc;
		load();
	}
	
	public void load()
	{
		mapWidth = rom.getPointer(dataLoc,true);
		mapHeight = rom.getPointer(dataLoc+0x4,true);
		borderTilePtr = rom.getPointerAsInt(dataLoc+0x8);
		mapTilesPtr = rom.getPointerAsInt(dataLoc+0xC);
		globalTileSetPtr = rom.getPointerAsInt(dataLoc+0x10);
		localTileSetPtr = rom.getPointerAsInt(dataLoc+0x14);
		borderWidth = BitConverter.ToInts(rom.readBytes(dataLoc+0x18, 2))[0];
		borderHeight = BitConverter.ToInts(rom.readBytes(dataLoc+0x18, 2))[1];
		secondarySize = borderWidth + 0xA0;
		System.out.println(borderWidth + " " + borderHeight);
		if(rom.getGameCode().startsWith("AX")) //If this is a RSE game...
		{
			borderWidth = 2;
			borderHeight = 2;
			DataStore.LocalTSBlocks = secondarySize;
		}
		else
		{
			secondarySize = DataStore.LocalTSSize;
		}
	}
	
	public void save()
	{
		rom.writePointer(mapWidth,dataLoc);
		rom.writePointer(mapHeight,dataLoc+0x4);
		rom.writePointer(borderTilePtr,dataLoc+0x8);
		rom.writePointer(mapTilesPtr,dataLoc+0xC);
		rom.writePointer(globalTileSetPtr,dataLoc+0x10);
		rom.writePointer(localTileSetPtr,dataLoc+0x14);
		rom.writeBytes(dataLoc+0x18, new byte[]{(byte)(borderWidth), (byte)(borderHeight)});
	}
}
