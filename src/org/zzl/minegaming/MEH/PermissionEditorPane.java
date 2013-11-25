package org.zzl.minegaming.MEH;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.MapTile;
import org.zzl.minegaming.MEH.MapElements.SpritesExit;
import org.zzl.minegaming.MEH.MapElements.SpritesNPC;
import org.zzl.minegaming.MEH.MapElements.SpritesSigns;
import org.zzl.minegaming.MEH.MapElements.Tileset;
import org.zzl.minegaming.MEH.MapElements.Triggers;

public class PermissionEditorPane extends JPanel
{
	private static PermissionEditorPane instance = null;

	public static PermissionEditorPane getInstance()
	{
		if (instance == null)
		{
			instance = new PermissionEditorPane();
		}
		return instance;
	}

	private static final long serialVersionUID = -877213633894324075L;

	
	static Rectangle mouseTracker;
	public static boolean Redraw = true;
	public static boolean renderPalette = false;
	public static boolean renderTileset = false;
	public static MapTile[][] selectBuffer;
	public static int bufferWidth = 1;
	public static int bufferHeight = 1;
	public static Rectangle selectBox;
 
	public PermissionEditorPane()
	{
		mouseTracker=new Rectangle(0,0,16,16);
		selectBox = new Rectangle(0,0,16,16);
		this.addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseDragged(MouseEvent e)
			{
				int x = (mouseTracker.x / 16);
				int y = (mouseTracker.y / 16);
				if(x>MainGUI.mapEditorPanel.map.getMapData().mapWidth || y>MainGUI.mapEditorPanel.map.getMapData().mapHeight){
					return;
				}
				System.out.println(e.getButton());


				int b1 = InputEvent.BUTTON1_DOWN_MASK;
				int b2 = InputEvent.BUTTON2_DOWN_MASK;
				if ((e.getModifiersEx() & (b1 | b2)) == b1) 
				{
					for(int DrawX=0;DrawX<bufferWidth;DrawX++){
						for(int DrawY=0;DrawY<bufferHeight;DrawY++){
							//map.getMapTileData().getTile(x+DrawX, y+DrawY).SetID(selectBuffer[DrawX][DrawY].getID());
						}
					}


					MainGUI.mapEditorPanel.map.isEdited = true;
					DrawMap();
					repaint();
				}
				else
				{
					calculateSelectBox(e);
					repaint();
				}
			}

			@Override
			public void mouseMoved(MouseEvent e)
			{
				mouseTracker.x=e.getX() - (bufferWidth * 8);
				mouseTracker.y=e.getY() - (bufferHeight * 8);
				repaint();
			}

		});

		this.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				int x = (mouseTracker.x / 16);
				int y = (mouseTracker.y / 16);
				if(MainGUI.mapEditorPanel.map==null) return;

				if(x>MainGUI.mapEditorPanel.map.getMapData().mapWidth || y>MainGUI.mapEditorPanel.map.getMapData().mapHeight){
					return;
				}
				System.out.println(e.getButton());


				if(e.getButton() == 1)
				{
					for(int DrawX=0;DrawX<bufferWidth;DrawX++){
						for(int DrawY=0;DrawY<bufferHeight;DrawY++){
						//	map.getMapTileData().getTile(x+DrawX, y+DrawY).SetID(selectBuffer[DrawX][DrawY].getID());
						}
					}


					MainGUI.mapEditorPanel.map.isEdited = true;
					// myParent.mapEditorPanel.setMap(myParent.loadedMap);
					DrawMap();


					repaint();
				}
				else if(e.getButton() == 3)
				{
					//TileEditorPanel.baseSelectedTile = map.getMapTileData().getTile(x, y).getID();
					MainGUI.lblTileVal.setText("Current Tile: 0x" + BitConverter.toHexString(TileEditorPanel.baseSelectedTile));
					MainGUI.repaintTileEditorPanel();
				}
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				if(e.getButton() == 3)
				{
					selectBox = new Rectangle(e.getX(),e.getY(),0,0);
					bufferWidth = 1;
					bufferHeight = 1;
					mouseTracker.x=e.getX() - (bufferWidth * 8);
					mouseTracker.y=e.getY() - (bufferHeight * 8);
				}
			}

			@Override
			public void mouseExited(MouseEvent e)
			{

			}

			@Override
			public void mouseEntered(MouseEvent e)
			{

			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				if(e.getButton() == 3)
				{
					calculateSelectBox(e);

					//Fill the tile buffer
					selectBuffer = new MapTile[selectBox.width / 16][selectBox.height / 16];
					bufferWidth = selectBox.width / 16;
					bufferHeight = selectBox.height / 16;
				//	for(int x = 0; x < bufferWidth; x++)
			//			for(int y = 0; y < bufferHeight; y++)
						//	selectBuffer[x][y] = (MapTile)map.getMapTileData().getTile(selectBox.x / 16 + x, selectBox.y / 16 + y).clone();
				}
			}

		});
	}

	public static void calculateSelectBox(MouseEvent e)
	{
		//Get width/height
		selectBox.width = (e.getX() - selectBox.x);
		selectBox.height = (e.getY() - selectBox.y);

		//If our selection is negative, adjust it to be positive 
		//starting from the position the mouse was released
		if(selectBox.width < 0)
		{
			selectBox.x = e.getX();
			selectBox.width = Math.abs(selectBox.width);
		}
		if(selectBox.height < 0)
		{
			selectBox.y = e.getY();
			selectBox.height = Math.abs(selectBox.height);
		}

		//Round the values to multiples of 16
		selectBox.x = ((selectBox.x / 16) * 16);
		selectBox.y = ((selectBox.y / 16) * 16);
		selectBox.width = (selectBox.width / 16) * 16;
		selectBox.height = (selectBox.height / 16) * 16;
		
		//Minimum sizes
		if(selectBox.height == 0)
		{
			selectBox.height = 16;
		}
		if(selectBox.width == 0)
		{
			selectBox.width = 16;
		}
	}

	

	public static Graphics2D gcBuff;
	static  Image imgBuffer = null;
    
	public void DrawMap()
	{
		try
		{
		
			imgBuffer = createImage((int) MainGUI.mapEditorPanel.map.getMapData().mapWidth * 16,
					(int) MainGUI.mapEditorPanel.map.getMapData().mapHeight * 16);
			gcBuff = (Graphics2D) imgBuffer.getGraphics();
            gcBuff.drawImage(MapEditorPanel.imgBuffer, 0, 0, null);
            //Draw permissions
          
            
			for (int y = 0; y < MainGUI.mapEditorPanel.map.getMapData().mapHeight; y++)
			{
				for (int x = 0; x < MainGUI.mapEditorPanel.map.getMapData().mapWidth; x++)
				{
					int TileID=(MainGUI.mapEditorPanel.map.getMapTileData().getTile(x, y).getMeta());
					AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.50);
					gcBuff.setComposite(ac);
					
					gcBuff.drawImage(((BufferedImage)(PermissionTilePanel.imgPermissions)).getSubimage(TileID*16, 0, 16, 16), x * 16, y * 16, this);
				}
			}
			
			EventEditorPanel.Redraw=true;
		
			this.repaint();
		}
		catch (Exception e)
		{

			int a = 1;
			a = 1;

		}

	}
	
	public static Image getMapImage()
	{
		return imgBuffer;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
	
		if(PermissionEditorPane.Redraw==true){
			DrawMap();
			PermissionEditorPane.Redraw=false;
		}
		super.paintComponent(g);
		
			g.drawImage(imgBuffer, 0, 0, this);

		
			


		
		try
		{
			// g.drawImage(ImageIO.read(MainGUI.class.getResourceAsStream("/resources/smeargle.png")),
			// 100, 240, null);
		}
		catch (Exception e)
		{
			//e.printStackTrace();
		}
	}


}
