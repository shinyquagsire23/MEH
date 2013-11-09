package org.zzl.minegaming.MEH;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MapEditorPanel extends JPanel
{

	private static final long serialVersionUID = -877213633894324075L;
	private Tileset globalTiles;
	private Tileset localTiles;

    public MapEditorPanel() 
    {
    }
    
    public void setGlobalTileset(Tileset global)
    {
    	globalTiles = global;
    }
    
    public void setLocalTileset(Tileset local)
    {
    	localTiles = local;
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        if(globalTiles != null)
        	g.drawImage(globalTiles.getTileSet(0), 0, 0, null); // see javadoc for more info on the parameters 
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
