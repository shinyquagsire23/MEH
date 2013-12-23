package org.zzl.minegaming.MEH;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;
import org.zzl.minegaming.GBAUtils.ROMManager;
import org.zzl.minegaming.MEH.MapElements.ConnectionData;
import org.zzl.minegaming.MEH.MapElements.MapData;
import org.zzl.minegaming.MEH.MapElements.MapHeader;
import org.zzl.minegaming.MEH.MapElements.MapTileData;
import org.zzl.minegaming.MEH.MapElements.OverworldSprites;
import org.zzl.minegaming.MEH.MapElements.OverworldSpritesManager;
import org.zzl.minegaming.MEH.MapElements.Sprites;
import org.zzl.minegaming.MEH.MapElements.SpritesExitManager;
import org.zzl.minegaming.MEH.MapElements.SpritesNPCManager;
import org.zzl.minegaming.MEH.MapElements.SpritesSignManager;
import org.zzl.minegaming.MEH.MapElements.Tileset;
import org.zzl.minegaming.MEH.MapElements.TilesetCache;
import org.zzl.minegaming.MEH.MapElements.TriggerManager;


public class Map implements ISaveable
{
	private MapData mapData;
	private MapTileData mapTileData;
	public MapHeader mapHeader; 
	public ConnectionData mapConnections;
	public Sprites mapSprites;
	
	public SpritesNPCManager mapNPCManager;
	public SpritesSignManager mapSignManager;
	public SpritesExitManager mapExitManager;
	public TriggerManager mapTriggerManager;
	public OverworldSpritesManager overworldSpritesManager;
	public int dataOffset = 0;
	public OverworldSprites[] eventSprites;
	public boolean isEdited;
	
	public Map(GBARom rom, int bank, int map)
	{
		this(rom,(int)(BitConverter.shortenPointer(BankLoader.maps[bank].get(map))));
	}
	
	public Map(GBARom rom, int dataOffset)
	{
		this.dataOffset = dataOffset;
		mapHeader = new MapHeader(rom, dataOffset);
		mapConnections = new ConnectionData(rom, mapHeader);
		mapSprites = new Sprites(rom, (int) mapHeader.pSprites);
		
		mapNPCManager=new SpritesNPCManager(rom, (int) mapSprites.pNPC, mapSprites.bNumNPC);
		mapSignManager = new SpritesSignManager(rom,(int) mapSprites.pSigns, mapSprites.bNumSigns);
		mapTriggerManager = new TriggerManager(rom, (int) mapSprites.pTraps, mapSprites.bNumTraps);
		mapExitManager = new SpritesExitManager(rom, (int) mapSprites.pExits, mapSprites.bNumExits);
		overworldSpritesManager= new OverworldSpritesManager(rom, mapNPCManager.mapNPCs);

		mapData = new MapData(rom, mapHeader);
		mapTileData = new MapTileData(rom ,mapData);
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
		mapConnections.save();
		mapSprites.save();
		mapNPCManager.save();
		mapSignManager.save();
		mapTriggerManager.save();
		mapExitManager.save();
		mapData.save();
		mapTileData.save();
	}

	public static Image renderMap(int bank, int map)
	{
		return renderMap(new Map(ROMManager.currentROM,bank,map), true);
	}
	
	public static Image renderMap(Map map, boolean full)
	{
		TilesetCache.switchTileset(map);
		MapEditorPanel.blockRenderer.setGlobalTileset(TilesetCache.get(map.getMapData().globalTileSetPtr));
		MapEditorPanel.blockRenderer.setLocalTileset(TilesetCache.get(map.getMapData().localTileSetPtr));
		
		
		BufferedImage imgBuffer = new BufferedImage(8,8, BufferedImage.TYPE_INT_ARGB);
		Image tiles;
		if(!full)
			tiles = MainGUI.tileEditorPanel.RerenderSecondary(MainGUI.tileEditorPanel.imgBuffer);
		else
			tiles = MainGUI.tileEditorPanel.RerenderTiles(MainGUI.tileEditorPanel.imgBuffer, 0);
		try
		{		
			imgBuffer = new BufferedImage((int) map.getMapData().mapWidth * 16,
					(int) map.getMapData().mapHeight * 16, BufferedImage.TYPE_INT_ARGB);
			Graphics gcBuff = imgBuffer.getGraphics();

			for (int y = 0; y < map.getMapData().mapHeight; y++)
			{
				for (int x = 0; x < map.getMapData().mapWidth; x++)
				{
					gcBuff = imgBuffer.getGraphics();
					int TileID=(map.getMapTileData().getTile(x, y).getID());
					int srcX=(TileID % TileEditorPanel.editorWidth) * 16;
					int srcY = (TileID / TileEditorPanel.editorWidth) * 16;
					gcBuff.drawImage(((BufferedImage)(tiles)).getSubimage(srcX, srcY, 16, 16), x * 16, y * 16, null);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("Error rendering map.");
			e.printStackTrace();
			imgBuffer.getGraphics().setColor(Color.RED);
			imgBuffer.getGraphics().fillRect(0, 0, 8, 8);
		}

		return imgBuffer;
	}
}
