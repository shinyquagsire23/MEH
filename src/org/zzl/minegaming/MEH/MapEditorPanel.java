package org.zzl.minegaming.MEH;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
	private BlockRenderer blockRenderer = new BlockRenderer();
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
                int tile=TileEditorPanel.getInstance().baseSelectedTile;
              
        		map.getMapTileData().getTile(x, y).SetID(tile);
                
              
        		//myParent.mapEditorPanel.setMap(myParent.loadedMap);
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

	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		if(globalTiles != null)
		{
			for(int y = 0; y < map.getMapData().mapHeight; y++)
			{
				for(int x = 0; x < map.getMapData().mapWidth; x++)
				{
					g.drawImage(blockRenderer.renderBlock(map.getMapTileData().getTile(x, y).getID()), x*16, y*16, null); 
				}
			}
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
