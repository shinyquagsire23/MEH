package org.zzl.minegaming.MEH;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class MapEditorPanel extends JPanel
{
	private static MapEditorPanel instance = null;
	  
	  public static MapEditorPanel getInstance(){
	    if(instance==null){
	       instance = new MapEditorPanel();
	      }
	      return instance;
	  }
	private static final long serialVersionUID = -877213633894324075L;
	private Tileset globalTiles;
	private Tileset localTiles;
	public static BlockRenderer blockRenderer = new BlockRenderer();
	private Map map;
	
     
	public MapEditorPanel() 
	{
		
		this.addMouseListener(new MouseListener()
        {
           
            @Override
            public void mouseClicked(MouseEvent e)
            {
              
                //X+(Y*mapSize);
                
                
              
                int x=(e.getX()/16);
                int y=(e.getY()/16);
                int tile=TileEditorPanel.baseSelectedTile;
        		map.getMapTileData().getTile(x, y).SetID(tile);
                
        		map.isEdited=true;
        		//myParent.mapEditorPanel.setMap(myParent.loadedMap);
        		DrawMap();
        		repaint();
        	
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
                
               
            }
            
            @Override
            public void mouseReleased(MouseEvent e)
            {
                
               
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
		size.setSize((int)(m.getMapData().mapWidth + 1) * 16, (int)(m.getMapData().mapHeight + 1) * 16);
		setPreferredSize(size);
		this.setSize(size);
	}
	private Graphics gcBuff;
	private Image imgBuffer = null;
    public void DrawMap(){
    	try{
    	
    		imgBuffer = createImage((int)map.getMapData().mapWidth*16,(int)map.getMapData().mapHeight*16);
    		gcBuff=imgBuffer.getGraphics();
    	int rX=this.getVisibleRect().x;
		int rW = this.getVisibleRect().width;
		int rY = this.getVisibleRect().y;
		int rH = this.getVisibleRect().height;
		for(int y = 0; y < map.getMapData().mapHeight; y++)
		{
			for(int x = 0; x < map.getMapData().mapWidth; x++)
			{
				
				if((x - 1)*16 < rX + rW  && (x + 1)*16 > rX && (y - 1)*16 < rY + rH && (y + 1)*16 > rY)
					gcBuff.drawImage((Image)blockRenderer.renderBlock(map.getMapTileData().getTile(x, y).getID()), x*16, y*16, null); 
			}
		}
    	}
    	catch(Exception e){
    		
    		int a=1;
    		a=1;
    		
    	}
    
    }
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		if(globalTiles != null)
		{
			g.drawImage(imgBuffer, 0, 0, this);
			MainGUI.lblInfo.setText("Done!");
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

}
