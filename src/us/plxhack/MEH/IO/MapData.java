package us.plxhack.MEH.IO;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class MapData implements ISaveable
{
	private GBARom rom;
	private MapHeader mapHeader;
	public long mapWidth, mapHeight;
	public int borderTilePtr, mapTilesPtr, globalTileSetPtr, localTileSetPtr;
	public int borderWidth, borderHeight;
	public int secondarySize;
	
	public MapData(GBARom rom, MapHeader mHeader)
	{
		this.rom = rom;
		mapHeader = mHeader;
		load();
	}
	
	public void load()
	{
		mapWidth = rom.getPointer(((int)mapHeader.pMap),true);
		mapHeight = rom.getPointer(((int)mapHeader.pMap)+0x4,true);
		borderTilePtr = rom.getPointerAsInt(((int)mapHeader.pMap)+0x8);
		mapTilesPtr = rom.getPointerAsInt(((int)mapHeader.pMap)+0xC);
		globalTileSetPtr = rom.getPointerAsInt(((int)mapHeader.pMap)+0x10);
		localTileSetPtr = rom.getPointerAsInt(((int)mapHeader.pMap)+0x14);
		borderWidth = BitConverter.ToInts(rom.readBytes(((int)mapHeader.pMap)+0x18, 2))[0];
		borderHeight = BitConverter.ToInts(rom.readBytes(((int)mapHeader.pMap)+0x18, 2))[1];
		secondarySize = borderWidth + 0xA0;
		//System.out.println(borderWidth + " " + borderHeight);
		if(DataStore.EngineVersion==0) //If this is a RSE game...
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
		rom.Seek(((int)mapHeader.pMap));
		rom.writePointer(mapWidth);
		rom.writePointer(mapHeight);
		rom.writePointer(borderTilePtr);
		rom.writePointer(mapTilesPtr);
		rom.writePointer(globalTileSetPtr);
		rom.writePointer(localTileSetPtr);
		//rom.writeBytes(((int)mapHeader.pMap), new byte[]{(byte)(borderWidth), (byte)(borderHeight)}); //Isn't quite working yet :/
	}
}
