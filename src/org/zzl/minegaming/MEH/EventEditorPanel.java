package org.zzl.minegaming.MEH;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.SpritesExit;
import org.zzl.minegaming.MEH.MapElements.SpritesNPC;
import org.zzl.minegaming.MEH.MapElements.SpritesSigns;
import org.zzl.minegaming.MEH.MapElements.Triggers;

public class EventEditorPanel extends JPanel
{
	private static EventEditorPanel instance = null;

	public static EventEditorPanel getInstance()
	{
		if (instance == null)
		{
			instance = new EventEditorPanel();
		}
		return instance;
	}

	private static final long serialVersionUID = -877213633894324075L;
	private Tileset globalTiles;
	private Tileset localTiles;
	public static BlockRenderer blockRenderer = new BlockRenderer();
	private Map map;
    public static boolean Redraw = true;
	public static boolean renderPalette = false;
	public static boolean renderTileset = false;
    Point pointEvent;
    int moveSrcX;
    int moveSrcY;
    int selectedEvent;//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger
	int IndexNPC;
	int IndexSign;
	int IndexExit;
	int IndexTriggers;
	public EventEditorPanel()
	{
		selectedEvent=-1;
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
        
		this.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				int x = (e.getX() / 16);
				int y = (e.getY() / 16);
				moveSrcX=x;
				moveSrcY=y;
				if(x>map.getMapData().mapWidth || y>map.getMapData().mapHeight){
					return;
				}
			
			
				System.out.println(e.getButton());
				if(e.getButton() == e.BUTTON1)
				{
			
				//If there's two events on tile, we'll handle that later with some kind of picker 
				
					 IndexNPC=map.mapNPCManager.IsPresent(x,y);
					if(IndexNPC!=-1){
						MainGUI.panel_5.removeAll();
						MainGUI.panel_5.add(new NPCPane().paneNPCs);
						MainGUI.panel_5.revalidate();
						MainGUI.panel_5.repaint();
						selectedEvent=0;//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger
						return;
					}
					 IndexSign=map.mapSignManager.IsPresent(x,y);
					if(IndexSign!=-1){
						MainGUI.panel_5.removeAll();
						MainGUI.panel_5.add(new SignPanel());
						MainGUI.panel_5.revalidate();
						MainGUI.panel_5.repaint();
						selectedEvent=1;//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger
						return;
					}
					IndexExit=map.mapExitManager.IsPresent(x,y);
					if(IndexExit!=-1){
						MainGUI.panel_5.removeAll();
						MainGUI.panel_5.add(new ExitPanel());
						MainGUI.panel_5.revalidate();
						MainGUI.panel_5.repaint();
						selectedEvent=2;//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger
						return;
					}
					 IndexTriggers=map.mapTriggerManager.IsPresent(x,y);
					if(IndexTriggers!=-1){
						MainGUI.panel_5.removeAll();
						MainGUI.panel_5.add(new TriggerPanel());
						MainGUI.panel_5.revalidate();
						MainGUI.panel_5.repaint();
						selectedEvent=3;//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger
						return;
					}
					
				}
				else if(e.getButton() == 3)
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
				if(srcX!=x||srcY!=y){
					
					switch(s){//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger)
					case 0:
						map.mapNPCManager.mapNPCs[IndexNPC].bX=(byte) x;
						map.mapNPCManager.mapNPCs[IndexNPC].bY=(byte) y;
						break;
					case 1:
						map.mapSignManager.mapSigns[IndexSign].bX=(byte) x;
						map.mapSignManager.mapSigns[IndexSign].bY=(byte) y;
						break;
					case 2:
						map.mapExitManager.mapExits[IndexExit].bX=(byte) x;
						map.mapExitManager.mapExits[IndexExit].bY=(byte) y;
						break;
					case 3:
						map.mapTriggerManager.mapTriggers[IndexTriggers].bX=(byte) x;
						map.mapTriggerManager.mapTriggers[IndexTriggers].bY=(byte) y;
						break;
					};
					selectedEvent=-1;
					DrawMap();
					repaint();
				}
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

	public void setMap(Map m)
	{
		map = m;
		Dimension size = new Dimension();
		size.setSize((int) (m.getMapData().mapWidth + 1) * 16,
				(int) (m.getMapData().mapHeight + 1) * 16);
		setPreferredSize(size);
		this.setSize(size);
	}

	private Graphics gcBuff;
	private Image imgBuffer = null;

	public void DrawMap()
	{
		try
		{

			imgBuffer = createImage((int) map.getMapData().mapWidth * 16,
					(int) map.getMapData().mapHeight * 16);
			gcBuff = imgBuffer.getGraphics();
		
			gcBuff.drawImage(MapEditorPanel.imgBuffer, 0, 0, this);
			DrawSigns();
			DrawExits();
			DrawNPCs();
			DrawTriggers();
			
			
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
    void DrawNPCs(){
    	
    	int i=0;
    	for(i=0;i<map.mapNPCManager.mapNPCs.length;i++){
    		SpritesNPC n=map.mapNPCManager.mapNPCs[i];
    	     DrawText("N", n.bX*16 , n.bY*16);
    	}
    }
    void DrawTriggers(){
    	
    	int i=0;
    	for(i=0;i<map.mapTriggerManager.mapTriggers.length;i++){
    		Triggers n=map.mapTriggerManager.mapTriggers[i];
    		
    		 DrawText("T", n.bX*16 , n.bY*16);
    	}
    }
    void DrawSigns(){
    	
    	int i=0;
    	for(i=0;i<map.mapSignManager.mapSigns.length;i++){
    		SpritesSigns n=map.mapSignManager.mapSigns[i];
    		
    		DrawText("S", n.bX*16 , n.bY*16);
    	}
    }
    void DrawExits(){
    	
    	int i=0;
    	SpritesExit[] tmp=map.mapExitManager.mapExits;
    	for(i=0;i<tmp.length;i++){
    		SpritesExit n=tmp[i];
    		DrawText("E", n.bX*16 , n.bY*16);
    	}
    }
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (globalTiles != null)
		{
			if(Redraw){
				DrawMap();
				Redraw=false;
			}
			g.drawImage(imgBuffer, 0, 0, this);
           
			
		}
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

	public void reset()
	{
		globalTiles = null;
		localTiles = null;
		map = null;
	}
}
