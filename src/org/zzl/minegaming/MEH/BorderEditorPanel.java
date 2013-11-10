package org.zzl.minegaming.MEH;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BorderEditorPanel extends JPanel
{

	private static final long serialVersionUID = -877213633894324075L;
	private Tileset globalTiles;
	private Tileset localTiles;
	private BlockRenderer blockRenderer = new BlockRenderer();
	private BorderMap map;

	public BorderEditorPanel() 
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

	public void setMap(BorderMap m)
	{
		map = m;
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		if(globalTiles != null)
		{
			for(int y = 0; y < map.getMapData().borderHeight; y++)
			{
				for(int x = 0; x < map.getMapData().borderWidth; x++)
				{
					
						g.drawImage((Image)blockRenderer.renderBlock(map.getMapTileData().getTile(x, y).getID()), (this.getWidth() / 2) - (map.getMapData().borderWidth * 8) + x*16, 20+y*16, null); 
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
