package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;
import org.zzl.minegaming.MEH.DataStore;

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
	
	@Override
	public void save()
	{
		rom.Seek(dataLoc);
		rom.writePointer(mapWidth);
		rom.writePointer(mapHeight);
		rom.writePointer(borderTilePtr);
		rom.writePointer(mapTilesPtr);
		rom.writePointer(globalTileSetPtr);
		rom.writePointer(localTileSetPtr);
		//rom.writeBytes(dataLoc, new byte[]{(byte)(borderWidth), (byte)(borderHeight)}); //Isn't quite working yet :/
	}
}
