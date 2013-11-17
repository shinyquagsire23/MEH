package org.zzl.minegaming.MEH;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.Tileset;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseMotionListener;

public class TileEditorPanel extends JPanel
{
	private static final long serialVersionUID = -877213633894324075L;
	public static int baseSelectedTile;	// Called it base in case of multiple tile
										// selection in the future.
	public static int editorWidth = 8; //Editor width in 16x16 tiles
	private Tileset globalTiles;
	private Tileset localTiles;
	private boolean isMouseDown = true;
    public static Rectangle mouseTracker;
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

			@Override
			public void mouseDragged(MouseEvent arg0)
			{
				
			}

			@Override
			public void mouseMoved(MouseEvent e)
			{
				mouseTracker.x=e.getX();
				mouseTracker.y=e.getY();
				
				repaint();
				
			}

		});

		this.addMouseListener(new MouseListener()
		{

			@Override
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
						String k = "Current Tile: ";
						k += String.format("0x%8s",
								Integer.toHexString(baseSelectedTile))
								.replace(' ', '0');
						MainGUI.lblTileVal.setText("Current Tile: 0x" + BitConverter.toHexString(TileEditorPanel.baseSelectedTile));
						repaint();
				 }
				
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				isMouseDown = true;
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				isMouseDown = false;
				if(e.getButton()==3){
					int x=e.getX()/16;
					int y=e.getY()/16;
					if(x<0){
						x=0;
					}
					if(x>editorWidth){
						x=editorWidth;
					}
					if(y<0){
						y=0;
					}
					if(y>(DataStore.LocalTSHeight+DataStore.MainTSHeight)){
						y=DataStore.LocalTSHeight+DataStore.MainTSHeight;
					}
					SetRect((x-srcX)*16, (y-srcY)*16);
					
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
		Dimension d = new Dimension(16*editorWidth,(DataStore.MainTSBlocks / editorWidth)*(DataStore.LocalTSBlocks / editorWidth) *16);
		if(DataStore.EngineVersion == 0)
			d.height = 3048;
		imgBuffer = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_ARGB);
		setSize(d);
		gcBuff=imgBuffer.getGraphics();
		for(int i = 0; i < DataStore.MainTSBlocks+(DataStore.EngineVersion == 1 ? 0x11D : 1024); i++) //TODO readd ini support here
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
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (globalTiles != null)
		{
			g.drawImage(imgBuffer, 0, 0, this);
			g.setColor(Color.red);
			g.drawRect((baseSelectedTile % editorWidth) * 16, (baseSelectedTile / editorWidth) * 16, 15, 15);
			g.setColor(Color.GREEN);
		     
			if( mouseTracker.width <0)
		    	   mouseTracker.x-=Math.abs( mouseTracker.width);
		        if( mouseTracker.height <0)
		        	mouseTracker.y-=Math.abs( mouseTracker.height);

		        g.drawRect(mouseTracker.x,mouseTracker.y,Math.abs(mouseTracker.width), Math.abs(mouseTracker.height));
			
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
