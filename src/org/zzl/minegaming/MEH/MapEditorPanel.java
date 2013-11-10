package org.zzl.minegaming.MEH;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MapEditorPanel extends JPanel
{

	private static final long serialVersionUID = -877213633894324075L;
	private Tileset globalTiles;
	private Tileset localTiles;
	private BlockRenderer blockRenderer = new BlockRenderer();

    public MapEditorPanel() 
    {
    	
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
        	g.drawImage(blockRenderer.renderBlock(0xE), 0, 0, null); 
        	g.drawImage(blockRenderer.renderBlock(0xF), 16, 0, null); 
        	g.drawImage(blockRenderer.renderBlock(0x1E), 0, 16, null); 
        	g.drawImage(blockRenderer.renderBlock(0x1F), 16, 16, null); 
        	g.drawImage(blockRenderer.renderBlock(0x26), 0, 32, null); 
        	g.drawImage(blockRenderer.renderBlock(0x27), 16, 32, null);
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
