package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

import PokemonClasses.MapHeader;
import PokemonClasses.Sprites;
import PokemonClasses.SpritesExitManager;
import PokemonClasses.SpritesNPCManager;
import PokemonClasses.SpritesSignManager;
import PokemonClasses.TriggerManager;


public class Map implements ISaveable
{
	private MapData mapData;
	private MapTileData mapTileData;
	public MapHeader mapHeader; 
	public Sprites mapSprites;
	public SpritesNPCManager mapNPCManager;
	public SpritesSignManager mapSignManager;
	public SpritesExitManager mapExitManager;
	public TriggerManager mapTriggerManager;
	public boolean isEdited;
	public Map(GBARom rom, int dataOffset)
	{
		mapHeader = new MapHeader(rom, dataOffset);
		mapSprites = new Sprites(rom, (int) mapHeader.pSprites);
		mapNPCManager=new SpritesNPCManager(rom, (int) mapSprites.pNPC, mapSprites.bNumNPC);
		mapTriggerManager = new TriggerManager(rom, (int) mapSprites.pTraps, mapSprites.bNumTraps);
		mapExitManager = new SpritesExitManager(rom, (int) mapSprites.pExits, mapSprites.bNumExits);
		mapData = new MapData(rom, (int)mapHeader.pMap);
		mapTileData = new MapTileData(rom, BitConverter.shortenPointer(mapData.mapTilesPtr),mapData);
	    isEdited=true;
	}
	
	public MapData getMapData()
	{
		return mapData;
	}
	
	public MapTileData getMapTileData()
	{
		return mapTileData;
	}
	
	public void save()
	{
		//mapData.save();
		mapTileData.save();
	}
}
