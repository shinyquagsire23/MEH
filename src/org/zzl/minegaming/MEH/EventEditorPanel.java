package org.zzl.minegaming.MEH;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ROMManager;

import org.zzl.minegaming.MEH.MapElements.OverworldSprites;
import org.zzl.minegaming.MEH.MapElements.SpritesExit;
import org.zzl.minegaming.MEH.MapElements.SpritesNPC;
import org.zzl.minegaming.MEH.MapElements.SpritesSigns;
import org.zzl.minegaming.MEH.MapElements.Tileset;
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
	public static NPCPane paneNPC;
	public Image  imgTrigger;
	public Image  imgWarp;
	public Image  imgSign;
	public Image imgNPC;
	
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
		
		
		try {
			imgTrigger=ImageIO.read(MainGUI.class.getResource("/resources/trigger.png").openStream());
			imgWarp=ImageIO.read(MainGUI.class.getResource("/resources/warp.png").openStream());
			imgSign=ImageIO.read(MainGUI.class.getResource("/resources/sign.png").openStream());
			imgNPC=ImageIO.read(MainGUI.class.getResource("/resources/npc.png").openStream());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
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
        
		this.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				int x = (e.getX() / 16);
				int y = (e.getY() / 16);
				
				if(x>map.getMapData().mapWidth || y>map.getMapData().mapHeight){
					return;
				}
			
			
				System.out.println(e.getButton());
				if(e.getButton() == MouseEvent.BUTTON1)
				{
			
				//If there's two events on tile, we'll handle that later with some kind of picker 
				
					 IndexNPC=Map.mapNPCManager.IsPresent(x,y);
					 MainGUI.panel_5.removeAll();
					if(IndexNPC!=-1){
						
						MainGUI.panel_5.add(new NPCPane(Map.mapNPCManager,IndexNPC));
						
						
					}
					 IndexSign=Map.mapSignManager.IsPresent(x,y);
					if(IndexSign!=-1){
						
						MainGUI.panel_5.add(new SignPanel(Map.mapSignManager, IndexSign));
						
						
					}
					IndexExit=Map.mapExitManager.IsPresent(x,y);
					if(IndexExit!=-1){
						
						MainGUI.panel_5.add(new ExitPanel(Map.mapExitManager, IndexExit));
					
						
					}
					 IndexTriggers=Map.mapTriggerManager.IsPresent(x,y);
					if(IndexTriggers!=-1){
						
						MainGUI.panel_5.add(new TriggerPanel(Map.mapTriggerManager, IndexTriggers));
					
						
					}
					MainGUI.panel_5.revalidate();
					MainGUI.panel_5.repaint();
				}
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
				if(e.getButton() == MouseEvent.BUTTON1)
				{
			
				//If there's two events on tile, we'll handle that later with some kind of picker 
				
					 IndexNPC=Map.mapNPCManager.IsPresent(x,y);
					if(IndexNPC!=-1){
						
						selectedEvent=0;//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger
						//return;
					}
					 IndexSign=Map.mapSignManager.IsPresent(x,y);
					if(IndexSign!=-1){
						
						selectedEvent=1;//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger
						return;
					}
					IndexExit=Map.mapExitManager.IsPresent(x,y);
					if(IndexExit!=-1){
					
						selectedEvent=2;//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger
						return;
					}
					 IndexTriggers=Map.mapTriggerManager.IsPresent(x,y);
					if(IndexTriggers!=-1){
						
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
					try{
					switch(s){//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger)
					case 0:
						Map.mapNPCManager.mapNPCs[IndexNPC].bX=(byte) x;
						Map.mapNPCManager.mapNPCs[IndexNPC].bY=(byte) y;
						break;
					case 1:
						Map.mapSignManager.mapSigns[IndexSign].bX=(byte) x;
						Map.mapSignManager.mapSigns[IndexSign].bY=(byte) y;
						break;
					case 2:
						Map.mapExitManager.mapExits[IndexExit].bX=(byte) x;
						Map.mapExitManager.mapExits[IndexExit].bY=(byte) y;
						break;
					case 3:
						Map.mapTriggerManager.mapTriggers[IndexTriggers].bX=(byte) x;
						Map.mapTriggerManager.mapTriggers[IndexTriggers].bY=(byte) y;
						break;
					};
					}catch(Exception e1){
						System.out.println(e1.getMessage());
					}
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
    	
    	for(i=0;i<Map.mapNPCManager.mapNPCs.length;i++){
    		SpritesNPC n=Map.mapNPCManager.mapNPCs[i];
    		if(DataStore.mehSettingShowSprites==1){
	    	
	    		
	    		Image imgNPCs=Map.overworldSpritesManager.GetImage(i);
	    	    
	    		gcBuff.drawImage(imgNPCs, n.bX*16, n.bY*16,n.bX*16+ 64, n.bY*16 + 64, 0, 0, 64, 64, this);
    		}else{
    			gcBuff.drawImage(imgNPC, n.bX*16, n.bY*16,n.bX*16+ 16, n.bY*16 + 16, 0, 0, 64,64, this);
    		}
    	}
    }
    void DrawTriggers(){
    	
    	int i=0;
    	for(i=0;i<Map.mapTriggerManager.mapTriggers.length;i++){
    		Triggers n=Map.mapTriggerManager.mapTriggers[i];
    		
    		 gcBuff.drawImage(imgTrigger, n.bX*16, n.bY*16,n.bX*16+ 16, n.bY*16 + 16, 0, 0, 64, 64, this);
    	}
    }
    void DrawSigns(){
    	
    	int i=0;
    	for(i=0;i<Map.mapSignManager.mapSigns.length;i++){
    		SpritesSigns n=Map.mapSignManager.mapSigns[i];
    		
    		 gcBuff.drawImage(imgSign, n.bX*16, n.bY*16,n.bX*16+ 16, n.bY*16 + 16, 0, 0, 64, 64, this);
    	}
    }
    void DrawExits(){
    	
    	int i=0;
    	SpritesExit[] tmp=Map.mapExitManager.mapExits;
    	for(i=0;i<tmp.length;i++){
    		SpritesExit n=tmp[i];
    		 gcBuff.drawImage(imgWarp, n.bX*16, n.bY*16,n.bX*16+ 16, n.bY*16 + 16, 0, 0, 64, 64, this);
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
