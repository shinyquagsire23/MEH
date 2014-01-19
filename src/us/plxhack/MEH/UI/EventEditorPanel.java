package us.plxhack.MEH.UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.ROMManager;

import us.plxhack.MEH.IO.Map;
import us.plxhack.MEH.IO.Tileset;
import us.plxhack.MEH.IO.Render.BlockRenderer;
import us.plxhack.MEH.MapElements.OverworldSpritesManager;
import us.plxhack.MEH.MapElements.SpritesExit;
import us.plxhack.MEH.MapElements.SpritesNPC;
import us.plxhack.MEH.MapElements.SpritesSigns;
import us.plxhack.MEH.MapElements.Triggers;

public class EventEditorPanel extends JPanel
{
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
	public void GrabSelectedEvent(MouseEvent e){
		int x = (e.getX() / 16);
		int y = (e.getY() / 16);
		 IndexNPC=map.mapNPCManager.IsPresent(x,y);
			if(IndexNPC!=-1){
				
				selectedEvent=0;//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger
				//return;
			}
			 IndexSign=map.mapSignManager.IsPresent(x,y);
			if(IndexSign!=-1){
				
				selectedEvent=1;//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger
				return;
			}
			IndexExit=map.mapExitManager.IsPresent(x,y);
			if(IndexExit!=-1){
			
				selectedEvent=2;//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger
				return;
			}
			 IndexTriggers=map.mapTriggerManager.IsPresent(x,y);
			if(IndexTriggers!=-1){
				
				selectedEvent=3;//-1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is Trigger
				return;
			}
	}
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

			
			public void mouseDragged(MouseEvent e)
			{
				
			}

			
			public void mouseMoved(MouseEvent e)
			{
				

			}

		});
        
		this.addMouseListener(new MouseListener(){

			@SuppressWarnings("unused")
			
			public void mouseClicked(MouseEvent e)
			{
				int x = (e.getX() / 16);
				int y = (e.getY() / 16);
				
				if(x>map.getMapData().mapWidth || y>map.getMapData().mapHeight){
					return;
				}
				GrabSelectedEvent(e);
			
				System.out.println(e.getButton());
				if(e.getButton() == MouseEvent.BUTTON1)
				{
			
					MainGUI.panel_5.removeAll();
				//If there's two events on tile, we'll handle that later with some kind of picker 
					switch(selectedEvent){
					case 0:
						MainGUI.panel_5.add(new NPCPane(map.mapNPCManager,IndexNPC));
						
						break;
					case 1:
						
						MainGUI.panel_5.add(new SignPanel(map.mapSignManager, IndexSign));
						break;
					case 2:
						if(e.getClickCount() > 1)
						{
							//Load map number
							MainGUI.loadMap(map.mapExitManager.mapExits[IndexExit].bBank & 0xFF, map.mapExitManager.mapExits[IndexExit].bMap & 0xFF);
						}
						else
							MainGUI.panel_5.add(new ExitPanel(map.mapExitManager, IndexExit));
						break;
					case 3:
						
						MainGUI.panel_5.add(new TriggerPanel(map.mapTriggerManager, IndexTriggers));
						break;
					
						
						
					}
				 
					MainGUI.panel_5.revalidate();
					MainGUI.panel_5.repaint();
					Redraw = true;
					repaint();
				}
				else if(e.getButton()==3)
				{
					
						//Execute script editor if any
						int offset=0;
						int tmp=0;
						switch(selectedEvent){
						case 0:
							tmp=IndexNPC;
							offset =(int) map.mapNPCManager.mapNPCs[tmp].pScript;
							
							break;
						case 1:
							tmp=IndexSign;
							offset =(int) map.mapSignManager.mapSigns[tmp].pScript;
							break;
					
						case 3:
							tmp=IndexTriggers;
							offset =(int) map.mapTriggerManager.mapTriggers[tmp].pScript;
							break;
						
							
							
						}
						
						if(offset!=0){
							try {
								String[] Params=new String[]{ROMManager.currentROM.input_filepath,Long.toHexString(offset)};
								Process p = Runtime.getRuntime().exec(DataStore.mehSettingCallScriptEditor+" "+ROMManager.currentROM.input_filepath+":"+Long.toHexString(offset));//",Params);  Elitemap is dumb//ScriptEd is for testing, not versatile enough
							
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								MainGUI.lblInfo.setText("Script failed to load, please check to see if " + DataStore.mehSettingCallScriptEditor + " exists");
							}
						}
						
				}
			}

			
			public void mousePressed(MouseEvent e)
			{
				int x = (e.getX() / 16);
				int y = (e.getY() / 16);
				
				if(x>map.getMapData().mapWidth || y>map.getMapData().mapHeight){
					return;
				}
			
				GrabSelectedEvent(e);
				
				
						if(e.getButton() == MouseEvent.BUTTON1)
						{
							moveSrcX=x;
							moveSrcY=y;
						
						}
					
					
							
						
				
			}

			
			public void mouseExited(MouseEvent e)
			{

			}

			
			public void mouseEntered(MouseEvent e)
			{

			}

			
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
    	
    	for(i=0;i<map.mapNPCManager.mapNPCs.length;i++){
    		SpritesNPC n=map.mapNPCManager.mapNPCs[i];
    		if(DataStore.mehSettingShowSprites==1){
	    	   
	    		Image imgNPCs=OverworldSpritesManager.GetImage(n.bSpriteSet & 0xFF);
	    		int adjX = (OverworldSpritesManager.GetSprite(n.bSpriteSet & 0xFF).mSpriteSize == 2 ? 8 : 0);
	    		 int dstX=(n.bX*16) - adjX;
	    		 int dstY=(n.bY*16) - (OverworldSpritesManager.GetSprite(n.bSpriteSet & 0xFF).mSpriteSize == 1 ? 16 : 0) - adjX*2;
	    		gcBuff.drawImage(imgNPCs, dstX , dstY, this);
    		}else{
    			gcBuff.drawImage(imgNPC, n.bX*16, n.bY*16, this);
    		}
    	}
    }
    void DrawTriggers(){
    	
    	int i=0;
    	for(i=0;i<map.mapTriggerManager.mapTriggers.length;i++){
    		Triggers n=map.mapTriggerManager.mapTriggers[i];
    		
    		 gcBuff.drawImage(imgTrigger, n.bX*16, n.bY*16,n.bX*16+ 16, n.bY*16 + 16, 0, 0, 64, 64, this);
    	}
    }
    void DrawSigns(){
    	
    	int i=0;
    	for(i=0;i<map.mapSignManager.mapSigns.length;i++){
    		SpritesSigns n=map.mapSignManager.mapSigns[i];
    		
    		 gcBuff.drawImage(imgSign, n.bX*16, n.bY*16,n.bX*16+ 16, n.bY*16 + 16, 0, 0, 64, 64, this);
    	}
    }
    void DrawExits(){
    	
    	int i=0;
    	SpritesExit[] tmp=map.mapExitManager.mapExits;
    	for(i=0;i<tmp.length;i++){
    		SpritesExit n=tmp[i];
    		 gcBuff.drawImage(imgWarp, n.bX*16, n.bY*16,n.bX*16+ 16, n.bY*16 + 16, 0, 0, 64, 64, this);
    	}
    }
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (globalTiles != null)
		{
			if(EventEditorPanel.Redraw){
				DrawMap();
				EventEditorPanel.Redraw=false;
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
