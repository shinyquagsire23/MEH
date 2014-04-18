package us.plxhack.MEH.UI;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.DataStore;
import us.plxhack.MEH.IO.Map;
import us.plxhack.MEH.IO.MapIO;
import us.plxhack.MEH.IO.Render.BlockRenderer;
import us.plxhack.MEH.IO.Tileset;
import us.plxhack.MEH.Structures.EditMode;
import us.plxhack.MEH.Structures.MapTile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class MapEditorPanel extends JPanel {
	private static MapEditorPanel instance = null;

	public static MapEditorPanel getInstance() {
		if (instance == null) {
			instance = new MapEditorPanel();
		}
		return instance;
	}

	private static final long serialVersionUID = -877213633894324075L;
	private Tileset globalTiles;
	private Tileset localTiles;
	public static BlockRenderer blockRenderer = new BlockRenderer();
	public Map map;
	static Rectangle mouseTracker;
	public static boolean Redraw = true;
	public static boolean renderPalette = false;
	public static boolean renderTileset = false;
	public static MapTile[][] selectBuffer;
	public static int bufferWidth = 1;
	public static int bufferHeight = 1;
	public static Rectangle selectBox;
	private static EditMode currentMode = EditMode.TILES;
 
	public MapEditorPanel() {
		mouseTracker = new Rectangle(0,0,16,16);
		selectBox = new Rectangle(0,0,16,16);
		selectBuffer = new MapTile[1][1];
		selectBuffer[0][0] = new MapTile(0,0xC);
		
		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
				if (e.getModifiersEx() == 1024)  {
					mouseTracker.x = e.getX();
					mouseTracker.y = e.getY();
				}
				int x = (mouseTracker.x / 16);
				int y = (mouseTracker.y / 16);
				if(x > map.getMapData().mapWidth || y > map.getMapData().mapHeight) {
					return;
				}
				System.out.println(x + " " + y);

				if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK)  {
					for(int DrawX=0; DrawX < bufferWidth; DrawX++) {
						for(int DrawY = 0; DrawY < bufferHeight; DrawY++) {
							//Tiles multi-select will grab both the tiles and the meta, 
							//while movement editing will only select metas.
							if(currentMode == EditMode.TILES) {
								map.getMapTileData().getTile(x + DrawX, y + DrawY).SetID(selectBuffer[DrawX][DrawY].getID());
								if(selectBuffer[DrawX][DrawY].getMeta() >= 0)
									map.getMapTileData().getTile(x + DrawX, y + DrawY).SetMeta(selectBuffer[DrawX][DrawY].getMeta()); //TODO Allow for tile-only selection. Hotkeys?
								drawTile(x + DrawX,y + DrawY);
							}
							else if(currentMode == EditMode.MOVEMENT) {
								map.getMapTileData().getTile(x+DrawX, y+DrawY).SetMeta(selectBuffer[DrawX][DrawY].getMeta());
								drawTile(x+DrawX,y+DrawY);
							}
						}
					}

					map.isEdited = true;
					MapIO.repaintTileEditorPanel();
				}
				else {
					calculateSelectBox(e);
				}
                MainGUI.setMouseCoordinates(mouseTracker.x / 16, mouseTracker.y / 16);
                repaint();
			}

			public void mouseMoved(MouseEvent e) {
				if(map == null)
					return;
				mouseTracker.x = e.getX();
				mouseTracker.y = e.getY();

				if(mouseTracker.x > map.getMapData().mapWidth * 16)
					mouseTracker.x = (int)(map.getMapData().mapWidth * 16) - (bufferWidth * 8);
				if(mouseTracker.y > map.getMapData().mapHeight * 16)
					mouseTracker.y = (int)(map.getMapData().mapHeight * 16) - (bufferHeight * 8);
                MainGUI.setMouseCoordinates(mouseTracker.x / 16, mouseTracker.y / 16);
                
                selectBox.x = ((mouseTracker.x / 16) * 16);
                selectBox.y = ((mouseTracker.y / 16) * 16);
                
                if(selectBox.width > 16)
                	selectBox.x -= selectBox.width / 2;
                if(selectBox.height > 16)
                	selectBox.y -= selectBox.height / 2;
				repaint();
			}
		});

		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				int x = selectBox.x / 16;
				int y = selectBox.y / 16;
				if (map == null)
                    return;

				if (x>map.getMapData().mapWidth || y>map.getMapData().mapHeight) {
					return;
				}
				System.out.println(e.getButton());

				if(e.getButton() == 1) {
					for(int DrawX=0;DrawX<bufferWidth;DrawX++) {
						for(int DrawY=0;DrawY<bufferHeight;DrawY++) {
							//Tiles multi-select will grab both the tiles and the meta, 
							//while movement editing will only select metas.
							if(currentMode == EditMode.TILES) {
								map.getMapTileData().getTile(x+DrawX, y+DrawY).SetID(selectBuffer[DrawX][DrawY].getID());
								if(selectBuffer[DrawX][DrawY].getMeta() >= 0)
									map.getMapTileData().getTile(x+DrawX, y+DrawY).SetMeta(selectBuffer[DrawX][DrawY].getMeta()); //TODO Allow for tile-only selection. Hotkeys?
								drawTile(x + DrawX, y + DrawY);
							}
							else if(currentMode == EditMode.MOVEMENT) {
								map.getMapTileData().getTile(x + DrawX, y + DrawY).SetMeta(selectBuffer[DrawX][DrawY].getMeta());
								drawTile(x + DrawX,y + DrawY);
							}
						}
					}
					map.isEdited = true;
					//DrawMap();
				}
				else if(e.getButton() == 3) {
					if(currentMode == EditMode.TILES) {
						TileEditorPanel.baseSelectedTile = map.getMapTileData().getTile(x, y).getID();
						MainGUI.lblTileVal.setText("Current Tile: 0x" + BitConverter.toHexString(TileEditorPanel.baseSelectedTile));
					}
					else if(currentMode == EditMode.MOVEMENT) {
						PermissionTilePanel.baseSelectedTile = map.getMapTileData().getTile(x, y).getMeta();
						MainGUI.lblTileVal.setText("Current Perm: 0x" + BitConverter.toHexString(TileEditorPanel.baseSelectedTile));
					}
					
					MapIO.repaintTileEditorPanel();
				}
                MainGUI.setMouseCoordinates(mouseTracker.x / 16, mouseTracker.y / 16);
                repaint();
			}

			public void mousePressed(MouseEvent e) {
				if(e.getButton() == 3) {
					selectBox = new Rectangle(e.getX(),e.getY(),0,0);
					bufferWidth = 1;
					bufferHeight = 1;
					mouseTracker.x=e.getX() - (bufferWidth * 8);
					mouseTracker.y=e.getY() - (bufferHeight * 8);
				}
                MainGUI.setMouseCoordinates(mouseTracker.x / 16, mouseTracker.y / 16);
                repaint();
			}

			public void mouseExited(MouseEvent e) {
                MainGUI.setMouseCoordinates(mouseTracker.x / 16, mouseTracker.y / 16);
                repaint();
			}

			
			public void mouseEntered(MouseEvent e) {
                MainGUI.setMouseCoordinates(mouseTracker.x / 16, mouseTracker.y / 16);
                repaint();
			}

			
			public void mouseReleased(MouseEvent e) {
				if(e.getButton() == 3) {
					calculateSelectBox(e);
					//Fill the tile buffer
					selectBuffer = new MapTile[selectBox.width / 16][selectBox.height / 16];
					bufferWidth = selectBox.width / 16;
					bufferHeight = selectBox.height / 16;
					for(int x = 0; x < bufferWidth; x++)
						for(int y = 0; y < bufferHeight; y++)
							selectBuffer[x][y] = (MapTile)map.getMapTileData().getTile(selectBox.x / 16 + x, selectBox.y / 16 + y).clone();
				}
                MainGUI.setMouseCoordinates(mouseTracker.x / 16, mouseTracker.y / 16);
                repaint();
			}
		});
	}

	public static void calculateSelectBox(MouseEvent e) {
		//Get width/height
		selectBox.width = (e.getX() - selectBox.x);
		selectBox.height = (e.getY() - selectBox.y);

		//If our selection is negative, adjust it to be positive 
		//starting from the position the mouse was released
		if (selectBox.width < 0) {
			selectBox.x = e.getX();
			selectBox.width = Math.abs(selectBox.width);
		}
		if (selectBox.height < 0) {
			selectBox.y = e.getY();
			selectBox.height = Math.abs(selectBox.height);
		}

		//Round the values to multiples of 16
		selectBox.x = ((selectBox.x / 16) * 16);
		selectBox.y = ((selectBox.y / 16) * 16);
		selectBox.width = (selectBox.width / 16) * 16;
		selectBox.height = (selectBox.height / 16) * 16;
		
		//Minimum sizes
		if(selectBox.height == 0) {
			selectBox.height = 16;
		}
		if(selectBox.width == 0) {
			selectBox.width = 16;
		}
	}

	public void setGlobalTileset(Tileset global) {
		globalTiles = global;
		blockRenderer.setGlobalTileset(global);
	}

	public void setLocalTileset(Tileset local) {
		localTiles = local;
		blockRenderer.setLocalTileset(local);
	}

	public void setMap(Map m) {
		map = m;
		Dimension size = new Dimension();
		size.setSize((int) (m.getMapData().mapWidth + 1) * 16,
				(int) (m.getMapData().mapHeight + 1) * 16);
		setPreferredSize(size);
		this.setSize(size);
	}

	public static Graphics gcBuff;
	static  Image imgBuffer = null;
	static Image permImgBuffer = null;

	public void DrawMap() {
		imgBuffer = Map.renderMap(map, true);
	}
	
	public void DrawMovementPerms() {
		try {
			permImgBuffer = createImage((int) map.getMapData().mapWidth * 16,
					(int) map.getMapData().mapHeight * 16);
			for (int y = 0; y < map.getMapData().mapHeight; y++) {
				for (int x = 0; x < map.getMapData().mapWidth; x++) {
					drawTile(x,y,EditMode.MOVEMENT);
				}
			}
		}
		catch (Exception e) {

		}
	}
	
	void drawTile(int x, int y) {
		drawTile(x,y,currentMode);
	}
	
	void drawTile(int x, int y, EditMode m) {
		if(m == EditMode.TILES) {
			gcBuff = imgBuffer.getGraphics();
			int TileID=(map.getMapTileData().getTile(x, y).getID());
			int srcX=(TileID % TileEditorPanel.editorWidth) * 16;
			int srcY = (TileID / TileEditorPanel.editorWidth) * 16;
			gcBuff.drawImage(((BufferedImage)(TileEditorPanel.imgBuffer)).getSubimage(srcX, srcY, 16, 16), x * 16, y * 16, this);
		}
		else if(m == EditMode.MOVEMENT) {
			gcBuff = permImgBuffer.getGraphics();
			int TileMeta=(MainGUI.mapEditorPanel.map.getMapTileData().getTile(x, y).getMeta());
			
			//Clear the rectangle since transparency can draw ontop of itself
			((Graphics2D)gcBuff).setBackground(new Color(255,255,255,0));
			((Graphics2D)gcBuff).clearRect(x * 16, y * 16, 16, 16);
			((Graphics2D)gcBuff).drawImage(((BufferedImage)(PermissionTilePanel.imgPermissions)).getSubimage(TileMeta*16, 0, 16, 16), x * 16, y * 16, this);
		}
		gcBuff.finalize();
        repaint();
	}
	
	public static Image getMapImage() {
		return imgBuffer;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (globalTiles != null) {
			if(MapEditorPanel.Redraw==true) {
				try {
					DrawMap();
				}
				catch (Exception e) {
                    e.printStackTrace();
                }
				DrawMovementPerms();
				MapEditorPanel.Redraw=false;
			}
			if(currentMode != EditMode.TILES) {
				Graphics2D g2 = (Graphics2D)g;
				g2.drawImage(imgBuffer, 0, 0, this);
				
				AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, DataStore.mehPermissionTranslucency);
				g2.setComposite(ac);
				g2.drawImage(permImgBuffer, 0, 0, this);
			}
			else
				g.drawImage(imgBuffer, 0, 0, this);

			if(renderPalette) {
				int x = 0;
				for(int i = 0; i < 12; i++) {
					while(x < 16) {
						try {
							g.setColor(globalTiles.getPalette()[i].getIndex(x));
							g.fillRect(x*8, i*8, 8, 8);
						}
						catch (Exception e) {
                            e.printStackTrace();
                        }
						x++;
					}
					x = 0;
				}

				x = 0;
				for(int i = 0; i < 12; i++) {
					while(x < 16) {
						try {
							g.setColor(localTiles.getPalette()[i].getIndex(x));
							g.fillRect(128+x*8, i*8, 8, 8);
						}
						catch(Exception e) {
                            e.printStackTrace();
                        }
						x++;
					}
					x = 0;
				}
			}
			
			if(renderTileset)
			{
				for(int i = 0; i < 13; i++)
				{
					g.drawImage(globalTiles.getTileSet(i),i*128,0,this);
					g.drawImage(localTiles.getTileSet(i),i*128,DataStore.MainTSHeight + 8,this);
				}
			}

			g.setColor(MainGUI.uiSettings.cursorColor);
			if (mouseTracker.width < 0)
				mouseTracker.x -= Math.abs(mouseTracker.width);
			if (mouseTracker.height < 0)
				mouseTracker.y -= Math.abs(mouseTracker.height);
			try 
			{
				//g.drawRect((int)(((mouseTracker.x / 16) % map.getMapData().mapWidth) * 16),(mouseTracker.y / 16) * 16,selectBox.width-1,selectBox.height-1);
				g.drawRect(selectBox.x,selectBox.y,selectBox.width-1,selectBox.height-1);
			}
            catch(Exception e) 
            {
                e.printStackTrace();
            }
		}
		try {
			// g.drawImage(ImageIO.read(MapIO.class.getResourceAsStream("/resources/smeargle.png")),
			// 100, 240, null);
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public void reset() {
		globalTiles = null;
		localTiles = null;
		map = null;
	}

	public static void setMode(EditMode tiles) {
		currentMode = tiles;
	}

	public static EditMode getMode() {
		return currentMode;
	}
}
