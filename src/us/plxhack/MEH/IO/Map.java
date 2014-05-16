package us.plxhack.MEH.IO;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;
import org.zzl.minegaming.GBAUtils.ROMManager;
import us.plxhack.MEH.IO.Render.OverworldSprites;
import us.plxhack.MEH.IO.Render.OverworldSpritesManager;
import us.plxhack.MEH.MapElements.*;
import us.plxhack.MEH.UI.MainGUI;
import us.plxhack.MEH.UI.TileEditorPanel;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Map implements ISaveable
{
	private MapData mapData;
	private MapTileData mapTileData;
	public MapHeader mapHeader; 
	public ConnectionData mapConnections;
	public HeaderSprites mapSprites;
	
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
		mapSprites = new HeaderSprites(rom, (int) mapHeader.pSprites);
		
		mapNPCManager=new SpritesNPCManager(rom, this, (int) mapSprites.pNPC, mapSprites.bNumNPC);
		mapSignManager = new SpritesSignManager(rom, this, (int)mapSprites.pSigns, mapSprites.bNumSigns);
		mapTriggerManager = new TriggerManager(rom, this, (int) mapSprites.pTraps, mapSprites.bNumTraps);
		mapExitManager = new SpritesExitManager(rom, this, (int) mapSprites.pExits, mapSprites.bNumExits);
		overworldSpritesManager= new OverworldSpritesManager(rom);

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
		//Save in reverse order in case we have repointing to do first.
		mapTileData.save();
		mapData.save();

		mapNPCManager.save();
		mapSignManager.save();
		mapTriggerManager.save();
		mapExitManager.save();
		mapSprites.save();
		
		mapConnections.save();
		mapHeader.save();
	}

	public static Image renderMap(int bank, int map)
	{
		return renderMap(new Map(ROMManager.currentROM,bank,map), true);
	}
	
	public static Image renderMap(Map map, boolean full)
	{
		TilesetCache.switchTileset(map);
		MapIO.blockRenderer.setGlobalTileset(TilesetCache.get(map.getMapData().globalTileSetPtr));
		MapIO.blockRenderer.setLocalTileset(TilesetCache.get(map.getMapData().localTileSetPtr));
		
		
		BufferedImage imgBuffer = new BufferedImage(8,8, BufferedImage.TYPE_INT_ARGB);
		Image tiles;
		if(!full)
			tiles = MainGUI.tileEditorPanel.RerenderSecondary(TileEditorPanel.imgBuffer);
		else
			tiles = MainGUI.tileEditorPanel.RerenderTiles(TileEditorPanel.imgBuffer, 0);
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
