package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;
import org.zzl.minegaming.MEH.MapElements.MapData;
import org.zzl.minegaming.MEH.MapElements.MapHeader;
import org.zzl.minegaming.MEH.MapElements.MapTileData;
import org.zzl.minegaming.MEH.MapElements.OverworldSprites;
import org.zzl.minegaming.MEH.MapElements.OverworldSpritesManager;
import org.zzl.minegaming.MEH.MapElements.Sprites;
import org.zzl.minegaming.MEH.MapElements.SpritesExitManager;
import org.zzl.minegaming.MEH.MapElements.SpritesNPCManager;
import org.zzl.minegaming.MEH.MapElements.SpritesSignManager;
import org.zzl.minegaming.MEH.MapElements.TriggerManager;


public class Map implements ISaveable
{
	private MapData mapData;
	private MapTileData mapTileData;
	public MapHeader mapHeader; 
	public Sprites mapSprites;
	public static SpritesNPCManager mapNPCManager;
	public static SpritesSignManager mapSignManager;
	public static SpritesExitManager mapExitManager;
	public static TriggerManager mapTriggerManager;
	public static OverworldSpritesManager overworldSpritesManager;
	public OverworldSprites[] eventSprites;
	public boolean isEdited;
	public Map(GBARom rom, int dataOffset)
	{
		mapHeader = new MapHeader(rom, dataOffset);
		mapSprites = new Sprites(rom, (int) mapHeader.pSprites);
		mapNPCManager=new SpritesNPCManager(rom, (int) mapSprites.pNPC, mapSprites.bNumNPC);
		mapSignManager = new SpritesSignManager(rom,(int) mapSprites.pSigns, mapSprites.bNumSigns);
		mapTriggerManager = new TriggerManager(rom, (int) mapSprites.pTraps, mapSprites.bNumTraps);
		mapExitManager = new SpritesExitManager(rom, (int) mapSprites.pExits, mapSprites.bNumExits);
		overworldSpritesManager= new OverworldSpritesManager(rom, mapNPCManager.mapNPCs);

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
		mapHeader.save();
		mapSprites.save();
		mapNPCManager.save();
		mapSignManager.save();
		mapTriggerManager.save();
		mapExitManager.save();
		mapData.save();
		mapTileData.save();
	}
}
