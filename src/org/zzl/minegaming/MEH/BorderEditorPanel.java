package org.zzl.minegaming.MEH;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.Tileset;

public class BorderEditorPanel extends JPanel
{

	
	private Tileset globalTiles;
	private Tileset localTiles;
	private BlockRenderer blockRenderer = new BlockRenderer();
	private BorderMap map;

	public BorderEditorPanel() 
	{

		this.addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseDragged(MouseEvent e)
			{
				int x = ((e.getX() - ((getWidth() / 2) - (map.getMapData().borderWidth * 8))) / 16);
				int y = ((e.getY() - 20) / 16);

				if(e.getButton() == 0)
				{
					int tile = TileEditorPanel.baseSelectedTile;
					map.getMapTileData().getTile(x, y).SetID(tile);

					map.isEdited = true;
					repaint();
				}
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
				int x = ((e.getX() - ((getWidth() / 2) - (map.getMapData().borderWidth * 8))) / 16);
				int y = ((e.getY() - 20) / 16);
				System.out.println(x + " " + y);
				if(e.getButton() == e.BUTTON1)
				{
					int tile = TileEditorPanel.baseSelectedTile;
					try
					{
						map.getMapTileData().getTile(x, y).SetID(tile);
					}
					catch(Exception ex){}

					map.isEdited = true;
					repaint();
				}
				else if(e.getButton() == 3)
				{
					TileEditorPanel.baseSelectedTile = map.getMapTileData().getTile(x, y).getID();
					MainGUI.lblTileVal.setText("Current Tile: 0x" + BitConverter.toHexString(TileEditorPanel.baseSelectedTile));
					MainGUI.repaintTileEditorPanel();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				
			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
				
			}

			@Override
			public void mousePressed(MouseEvent arg0)
			{
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0)
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

	public void reset()
	{
		globalTiles = null;
		localTiles = null;
		map = null;
	}
}
