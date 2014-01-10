package org.zzl.minegaming.MEH;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.MEH.MapElements.MapTile;
import org.zzl.minegaming.MEH.MapElements.Tileset;

public class BorderEditorPanel extends JPanel
{

	
	private Tileset globalTiles;
	private Tileset localTiles;
	private BlockRenderer blockRenderer = new BlockRenderer();
	private BorderMap map;
	private int mouseX = 0;
	private int mouseY = 0;

	public BorderEditorPanel() 
	{

		this.addMouseMotionListener(new MouseMotionListener()
		{

			public void mouseDragged(MouseEvent e)
			{
				int x = ((e.getX() - ((getWidth() / 2) - (map.getMapData().borderWidth * 8))) / 16);
				int y = ((e.getY() - 20) / 16);

				if (e.getModifiersEx() == 1024) 
				{
					int bufWidth = (MapEditorPanel.bufferWidth > map.getMapData().borderWidth ? map.getMapData().borderWidth : MapEditorPanel.bufferWidth);
					int bufHeight = (MapEditorPanel.bufferHeight > map.getMapData().borderHeight ? map.getMapData().borderHeight : MapEditorPanel.bufferHeight);
					for(int DrawX=0; DrawX < bufWidth; DrawX++)
					{
						for(int DrawY=0; DrawY < bufHeight; DrawY++)
						{
							//Tiles multi-select will grab both the tiles and the meta, 
							//while movement editing will only select metas.
							if(MapEditorPanel.getMode() == EditMode.TILES)
							{
								try
								{
									map.getMapTileData().getTile(x+DrawX, y+DrawY).SetID(MapEditorPanel.selectBuffer[DrawX][DrawY].getID());
									if(MapEditorPanel.selectBuffer[DrawX][DrawY].getMeta() >= 0)
										map.getMapTileData().getTile(x+DrawX, y+DrawY).SetMeta(MapEditorPanel.selectBuffer[DrawX][DrawY].getMeta()); //TODO Allow for tile-only selection. Hotkeys?
								}
								catch(Exception ex){}
							}
							else if(MapEditorPanel.getMode() == EditMode.MOVEMENT)
							{
								try
								{
									map.getMapTileData().getTile(x+DrawX, y+DrawY).SetMeta(MapEditorPanel.selectBuffer[DrawX][DrawY].getMeta());
								}
								catch(Exception ex){}
							}
						}
					}


					map.isEdited = true;
					repaint();
				}
				else
				{
					MapEditorPanel.calculateSelectBox(e);
					
					if(MapEditorPanel.selectBox.width > map.getMapData().borderWidth * 16)
						MapEditorPanel.selectBox.width = map.getMapData().borderWidth * 16;
					if(MapEditorPanel.selectBox.height > map.getMapData().borderHeight * 16)
						MapEditorPanel.selectBox.height = map.getMapData().borderHeight * 16;
					
					repaint();
				}
			}

			public void mouseMoved(MouseEvent e)
			{
				if(map==null) return;
				mouseX = ((e.getX() - ((getWidth() / 2) - (map.getMapData().borderWidth * 8))) / 16);
				mouseY = ((e.getY() - 20) / 16);
				
				if(mouseX > map.getMapData().borderWidth - 1)
					mouseX = map.getMapData().borderWidth - 1;
				if(mouseY > map.getMapData().borderHeight - 1)
					mouseY = map.getMapData().borderHeight - 1;
				
				repaint();
			}

		});

		this.addMouseListener(new MouseListener()
		{

			public void mouseClicked(MouseEvent e)
			{
				int x = ((e.getX() - ((getWidth() / 2) - (map.getMapData().borderWidth * 8))) / 16);
				int y = ((e.getY() - 20) / 16);
				//System.out.println(x + " " + y);
				if(e.getButton() == MouseEvent.BUTTON1)
				{
					int bufWidth = (MapEditorPanel.bufferWidth > map.getMapData().borderWidth ? map.getMapData().borderWidth : MapEditorPanel.bufferWidth);
					int bufHeight = (MapEditorPanel.bufferHeight > map.getMapData().borderHeight ? map.getMapData().borderHeight : MapEditorPanel.bufferHeight);
					for(int DrawX=0; DrawX < bufWidth; DrawX++)
					{
						for(int DrawY=0; DrawY < bufHeight; DrawY++)
						{
							//Tiles multi-select will grab both the tiles and the meta, 
							//while movement editing will only select metas.
							if(MapEditorPanel.getMode() == EditMode.TILES)
							{
								try
								{
									map.getMapTileData().getTile(x+DrawX, y+DrawY).SetID(MapEditorPanel.selectBuffer[DrawX][DrawY].getID());
									if(MapEditorPanel.selectBuffer[DrawX][DrawY].getMeta() >= 0)
										map.getMapTileData().getTile(x+DrawX, y+DrawY).SetMeta(MapEditorPanel.selectBuffer[DrawX][DrawY].getMeta()); //TODO Allow for tile-only selection. Hotkeys?
								}
								catch(Exception ex){}
							}
							else if(MapEditorPanel.getMode() == EditMode.MOVEMENT)
							{
								try
								{
									map.getMapTileData().getTile(x+DrawX, y+DrawY).SetMeta(MapEditorPanel.selectBuffer[DrawX][DrawY].getMeta());
								}
								catch(Exception ex){}
							}
						}
					}

					map.isEdited = true;
					repaint();
				}
				else if(e.getButton() == 3)
				{
					TileEditorPanel.baseSelectedTile = map.getMapTileData().getTile(x, y).getID();
					MainGUI.lblTileVal.setText("Current Tile: 0x" + BitConverter.toHexString(TileEditorPanel.baseSelectedTile));
					MainGUI.repaintTileEditorPanel();
				}
			}

			public void mouseReleased(MouseEvent e)
			{
				if(e.getButton() == 3)
				{
					MapEditorPanel.calculateSelectBox(e);

					//Fill the tile buffer
					MapEditorPanel.selectBuffer = new MapTile[MapEditorPanel.selectBox.width / 16][MapEditorPanel.selectBox.height / 16];
					
					if(MapEditorPanel.selectBox.width > map.getMapData().borderWidth * 16)
						MapEditorPanel.selectBox.width = map.getMapData().borderWidth * 16;
					if(MapEditorPanel.selectBox.height > map.getMapData().borderHeight * 16)
						MapEditorPanel.selectBox.height = map.getMapData().borderHeight * 16;
					
					MapEditorPanel.bufferWidth = MapEditorPanel.selectBox.width / 16;
					MapEditorPanel.bufferHeight = MapEditorPanel.selectBox.height / 16;
					
					for(int x = 0; x < MapEditorPanel.bufferWidth; x++)
						for(int y = 0; y < MapEditorPanel.bufferHeight; y++)
							MapEditorPanel.selectBuffer[x][y] = (MapTile)map.getMapTileData().getTile(MapEditorPanel.selectBox.x / 16 + x, MapEditorPanel.selectBox.y / 16 + y).clone();
				}
			}

			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void setGlobalTileset(Tileset global)
	{
		globalTiles = global;
		blockRenderer.setGlobalTileset(global);
	}

	public void setLocalTileset(Tileset local)
	{
		localTiles = local;
		blockRenderer.setLocalTileset(local);
	}

	public void setMap(BorderMap m)
	{
		map = m;
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		if(globalTiles != null)
		{
			for(int y = 0; y < map.getMapData().borderHeight; y++)
			{
				for(int x = 0; x < map.getMapData().borderWidth; x++)
				{
					
						g.drawImage(blockRenderer.renderBlock(map.getMapTileData().getTile(x, y).getID()), (this.getWidth() / 2) - (map.getMapData().borderWidth * 8) + x*16, 20+y*16, null); 
				}
			}
			
			if(MapEditorPanel.getMode() == EditMode.MOVEMENT)
			{
				for(int y = 0; y < map.getMapData().borderHeight; y++)
				{
					for(int x = 0; x < map.getMapData().borderWidth; x++)
					{

						int TileMeta=(MainGUI.mapEditorPanel.map.getMapTileData().getTile(x, y).getMeta());
						AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, DataStore.mehPermissionTranslucency);
						((Graphics2D)g).setComposite(ac);
						((Graphics2D)g).drawImage(((BufferedImage)(PermissionTilePanel.imgPermissions)).getSubimage(TileMeta*16, 0, 16, 16), (this.getWidth() / 2) - (map.getMapData().borderWidth * 8) + x*16, 20+y*16, this);


					}
				}
			}
			
			g.setColor(Color.GREEN);
			try
			{
				g.drawRect((this.getWidth() / 2) - (map.getMapData().borderWidth * 8) + (int)(((mouseX) % map.getMapData().mapWidth) * 16), 20+(mouseY * 16),MapEditorPanel.selectBox.width-1,MapEditorPanel.selectBox.height-1);
			}catch(Exception e){}
		}
		try
		{
			//g.drawImage(ImageIO.read(MainGUI.class.getResourceAsStream("/resources/smeargle.png")), 100, 240, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void reset()
	{
		globalTiles = null;
		localTiles = null;
		map = null;
	}
}
