package org.zzl.minegaming.MEH;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.MapTile;
import org.zzl.minegaming.MEH.MapElements.Tileset;

import java.awt.event.InputEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

//TODO Make this a base class for *any* tileset, ie movement perms
public class TileEditorPanel extends JPanel
{
	private static final long serialVersionUID = -877213633894324075L;
	public static int baseSelectedTile;	// Called it base in case of multiple tile
	// selection in the future.
	public static int editorWidth = 8; //Editor width in 16x16 tiles
	public Tileset globalTiles;
	public Tileset localTiles;
	private boolean isMouseDown = true;
    private static boolean Redraw = true;
	static Rectangle mouseTracker;
	public void SetRect(int width, int heigh){

		if(heigh>16) heigh=16;
		if(width>16) width=16;
		mouseTracker.height=heigh;
		mouseTracker.width=width;
	}
	public void SetRect(){
		mouseTracker.height=16;
		mouseTracker.width=16;
	}
	int srcX;
	int srcY;

	public TileEditorPanel()
	{
		mouseTracker=new Rectangle(0,0,16,16);

		this.addMouseMotionListener(new MouseMotionListener()
		{

			
			public void mouseDragged(MouseEvent e)
			{
				int b1 = InputEvent.BUTTON1_DOWN_MASK;
				int b2 = InputEvent.BUTTON2_DOWN_MASK;
				if ((e.getModifiersEx() & (b1 | b2)) != b1) 
				{
					MapEditorPanel.calculateSelectBox(e);
					repaint();
				}
			}

			
			public void mouseMoved(MouseEvent e)
			{
				mouseTracker.x=e.getX();
				mouseTracker.y=e.getY();

				repaint();

			}

		});

		this.addMouseListener(new MouseListener()
		{

			
			public void mouseClicked(MouseEvent e)
			{
				int x = 0;
				int y = 0;

				x = (e.getX() / 16);
				y = (e.getY() / 16);
				if (e.getClickCount() == 2 && e.getButton()==3){
					SetRect();//Reset tile rectangle
				}
				else{
					srcX=x;
					srcY=y;
					baseSelectedTile = x + (y * editorWidth);
					MapEditorPanel.selectBuffer = new MapTile[1][1];
					MapEditorPanel.selectBuffer[0][0] = new MapTile(baseSelectedTile,-1); //TODO Default movement perms
					MapEditorPanel.bufferWidth = 1;
					MapEditorPanel.bufferHeight = 1;
					MapEditorPanel.selectBox.width = 16;
					MapEditorPanel.selectBox.height = 16;
					String k = "Current Tile: ";
					k += String.format("0x%8s",
							Integer.toHexString(baseSelectedTile))
							.replace(' ', '0');
					MainGUI.lblTileVal.setText("Current Tile: 0x" + BitConverter.toHexString(TileEditorPanel.baseSelectedTile));
					repaint();
				}

			}

			
			public void mousePressed(MouseEvent e)
			{
				if(e.getButton() == 3)
				{
					MapEditorPanel.selectBox = new Rectangle(e.getX(),e.getY(),0,0);
				}
			}

			
			public void mouseExited(MouseEvent e)
			{

			}

			
			public void mouseEntered(MouseEvent e)
			{
				isMouseDown = true;
			}

			
			public void mouseReleased(MouseEvent e)
			{
				if(e.getButton() == 3)
				{
					MapEditorPanel.calculateSelectBox(e);

					//Fill the tile buffer
					MapEditorPanel.selectBuffer = new MapTile[MapEditorPanel.selectBox.width / 16][MapEditorPanel.selectBox.height / 16];
					MapEditorPanel.bufferWidth = MapEditorPanel.selectBox.width / 16;
					MapEditorPanel.bufferHeight = MapEditorPanel.selectBox.height / 16;
					for(int x = 0; x < MapEditorPanel.bufferWidth; x++)
						for(int y = 0; y < MapEditorPanel.bufferHeight; y++)
							MapEditorPanel.selectBuffer[x][y] = new MapTile(baseSelectedTile = x + (y * editorWidth), 0xC); //TODO implement movement perms
				}
			}

		});

	}

	public void setGlobalTileset(Tileset global)
	{
		globalTiles = global;
		//blockRenderer.setGlobalTileset(global);
	}

	public void setLocalTileset(Tileset local)
	{
		localTiles = local;
		//blockRenderer.setLocalTileset(local);
	}
	public static Graphics gcBuff;
	static Image imgBuffer = null;
	public void DrawTileset()
	{
		imgBuffer = RerenderTiles(imgBuffer, 0, DataStore.MainTSBlocks+(DataStore.EngineVersion == 1 ? 0x11D : 1024), true);
	}
	
	public Image RerenderSecondary(Image i)
	{
		return RerenderTiles(i, DataStore.MainTSBlocks);
	}
	
	public Image RerenderTiles(Image i, int startBlock)
	{
		return RerenderTiles(i, startBlock, DataStore.MainTSBlocks+(DataStore.EngineVersion == 1 ? 0x11D : 1024), false);
	}
	
	public Image RerenderTiles(Image b, int startBlock, int endBlock, boolean completeRender)
	{
		Dimension d = new Dimension(16*editorWidth,(DataStore.MainTSBlocks / editorWidth)*(DataStore.LocalTSBlocks / editorWidth) *16);
		if(completeRender)
		{
			if(DataStore.EngineVersion == 0)
				d.height = 3048;
			b = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_ARGB);
		}
		setSize(d);
		gcBuff=b.getGraphics();
		for(int i = startBlock; i < endBlock; i++)
		{

			int x = (i % editorWidth) * 16;
			int y = (i / editorWidth) * 16;

			try{
				gcBuff.drawImage(MapEditorPanel.blockRenderer.renderBlock(i,true), x, y, this);
			}
			catch(Exception e){
				e.printStackTrace();
			}

		}
		return b;
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (globalTiles != null)
		{
			if(TileEditorPanel.Redraw==true){
				DrawTileset();
				TileEditorPanel.Redraw=false;
			}
			g.drawImage(imgBuffer, 0, 0, this);
			g.setColor(Color.red);
			g.drawRect((baseSelectedTile % editorWidth) * 16, (baseSelectedTile / editorWidth) * 16, 15, 15);
			
			g.setColor(Color.GREEN);
			if( mouseTracker.width <0)
				mouseTracker.x-=Math.abs( mouseTracker.width);
			if( mouseTracker.height <0)
				mouseTracker.y-=Math.abs( mouseTracker.height);
			g.drawRect(((mouseTracker.x / 16) % editorWidth) * 16,(mouseTracker.y / 16) * 16,MapEditorPanel.selectBox.width-1,MapEditorPanel.selectBox.height-1);

		}
		try
		{
			//best error image.
			//I'll always remember you Smeargle <3
			//g.drawImage(ImageIO.read(MainGUI.class.getResourceAsStream("/resources/smeargle.png")), 100, 240,null);
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
	}
}
