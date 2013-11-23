package org.zzl.minegaming.MEH;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PermissionEditorPanel extends JPanel
{
	private static PermissionEditorPanel instance = null;

	public static PermissionEditorPanel getInstance()
	{
		if (instance == null)
		{
			instance = new PermissionEditorPanel();
		}
		return instance;
	}

	private static final long serialVersionUID = -877213633894324075L;


    public static boolean Redraw = true;


	public static Color[] PermissionColors;
    Point pointEvent;
    int moveSrcX;
    int moveSrcY;
    int selectedEvent;//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger
	int IndexNPC;
	int IndexSign;
	int IndexExit;
	int IndexTriggers;
	public void GrabSelectedEvent(MouseEvent e){
		int x = (e.getX() / 16);
		int y = (e.getY() / 16);
		
	}
	public PermissionEditorPanel()
	{
		
		selectedEvent=-1;
		PermissionColors= new Color[0x3F];
		int i=0;
		for(i=0;i<0x3F;i++){
			PermissionColors[i]= new Color((255-(i/4)+1 )& 254, 200 - i/8 + 23 ,128 -  i/16 + 34, 80);
		}

		this.addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseDragged(MouseEvent e)
			{
				
			}

			@Override
			public void mouseMoved(MouseEvent e)
			{
				

			}

		});
        
		this.addMouseListener(new MouseListener(){

			@SuppressWarnings("unused")
			@Override
			public void mouseClicked(MouseEvent e)
			{
				int x = (e.getX() / 16);
				int y = (e.getY() / 16);
				
				if(x>MainGUI.mapEditorPanel.map.getMapData().mapWidth || y>MainGUI.mapEditorPanel.map.getMapData().mapHeight){
					return;
				}
				
				if(e.getButton() == MouseEvent.BUTTON1)
				{
			  
					MainGUI.mapEditorPanel.map.getMapTileData().getTile(x, y).SetMeta(PermissionTilesPanel.baseSelectedTile);
				  PermissionEditorPanel.Redraw=true;
				  repaint();
				}
				else if(e.getButton()==3)
				{
						
				}
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				int x = (e.getX() / 16);
				int y = (e.getY() / 16);
				
			
			
				GrabSelectedEvent(e);
				
				
						if(e.getButton() == MouseEvent.BUTTON1)
						{
						
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
				int x = (e.getX() / 16);
				int y = (e.getY() / 16);
				int srcX=moveSrcX;
				int srcY=moveSrcY;
				int s=selectedEvent;
			
				
			}

		});
	}

	
	private static Graphics gcBuff;
	private Image imgBuffer = null;

	
	static void DrawPermissions(){
		for (int y = 0; y <= MainGUI.mapEditorPanel.map.getMapData().mapHeight; y++)
		{
			for (int x = 0; x < MainGUI.mapEditorPanel.map.getMapData().mapWidth; x++)
			{
				int TileID=(MainGUI.mapEditorPanel.map.getMapTileData().getTile(x, y).getMeta());
				int srcX=(TileID % TileEditorPanel.editorWidth) * 16;
				int srcY = (TileID / TileEditorPanel.editorWidth) * 16;
				gcBuff.setColor(Color.black);
				gcBuff.drawRect(x*16, y*16, 16, 16);
				gcBuff.setColor(PermissionColors[TileID]);
				
			    gcBuff.fillRect(x*16, y*16, 16, 16);
			    gcBuff.setColor(Color.RED);
			    gcBuff.drawString(Integer.toHexString(TileID), x*16 + 1, y*16 + 16);
			}
		}
		
	}
	public void DrawMap()
	{
		try
		{
 
			imgBuffer = new BufferedImage((int) MainGUI.mapEditorPanel.map.getMapData().mapWidth * 16,
					(int) MainGUI.mapEditorPanel.map.getMapData().mapHeight * 16,BufferedImage.TYPE_INT_ARGB);
			gcBuff = imgBuffer.getGraphics();
		
			gcBuff.drawImage(MapEditorPanel.imgBuffer, 0, 0, this);
			DrawPermissions();
			this.setSize(MainGUI.mapEditorPanel.getSize());
			this.setPreferredSize(MainGUI.mapEditorPanel.getSize());
		}
		catch (Exception e)
		{

			int a = 1;
			a = 1;

		}

	}
	void DrawText(String Text, int x, int y){
		 gcBuff.drawRect(x , y, 16, 16);
		 gcBuff.drawString(Text,x,y+16);
	}
    
	@Override
	protected void paintComponent(Graphics g)
	{
	
		super.paintComponent(g);
		 if(PermissionEditorPanel.Redraw){
			 DrawMap();
			 PermissionEditorPanel.Redraw=false;
		 }
		g.drawImage(imgBuffer, 0, 0, this);
           
			
		
		try
		{
			// g.drawImage(ImageIO.read(MainGUI.class.getResourceAsStream("/resources/smeargle.png")),
			// 100, 240, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
