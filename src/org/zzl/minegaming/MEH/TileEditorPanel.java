package org.zzl.minegaming.MEH;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseMotionListener;

public class TileEditorPanel extends JPanel
{

	private static final long serialVersionUID = -877213633894324075L;
	public int baseSelectedTile;//Called it base in case of multiple tile selection in the future.
	private Tileset globalTiles;
	private Tileset localTiles;
	private BlockRenderer blockRenderer = new BlockRenderer();
	
    public TileEditorPanel() 
    {
    	this.addMouseListener(new MouseListener()
        {
            
            @Override
            public void mouseClicked(MouseEvent e)
            {
                int x=0;
                int y=0;
                
                		x=(e.getX()/8);
        		y=(e.getY()/8);
        		baseSelectedTile=x+( y*32);
        	
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

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        if(globalTiles != null)
        {
        	int i=0;
        	for(i=0;i<globalTiles.numBlocks;i++){
        		
	        	g.drawImage(blockRenderer.renderBlock(i),      (i % 16)*16,(i/16)*16, null); 
	    
	        	
        	}
        	MainGUI.lblInfo.setText("Done!");
        }
        try
		{
			g.drawImage(ImageIO.read(MainGUI.class.getResourceAsStream("/resources/smeargle.png")), 100, 240, null);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
    }

}
