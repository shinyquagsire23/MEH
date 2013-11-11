package org.zzl.minegaming.MEH;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
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
	public static int baseSelectedTile;	// Called it base in case of multiple tile
										// selection in the future.
	public static int editorWidth = 8; //Editor width in 16x16 tiles
	private Tileset globalTiles;
	private Tileset localTiles;
	private boolean isMouseDown = true;
	private BlockRenderer blockRenderer = MapEditorPanel.blockRenderer;

	public TileEditorPanel()
	{

		this.addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseDragged(MouseEvent arg0)
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
				int x = 0;
				int y = 0;

				x = (e.getX() / 16);
				y = (e.getY() / 16);
				baseSelectedTile = x + (y * editorWidth);
				String k = "Current Tile: ";
				k += String.format("0x%8s",
						Integer.toHexString(baseSelectedTile))
						.replace(' ', '0');
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
				isMouseDown = true;
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				isMouseDown = false;
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
	private Graphics gcBuff;
	private Image imgBuffer = null;
	public void DrawTileset(){
		imgBuffer = createImage(512, 512);
		gcBuff=imgBuffer.getGraphics();
		for(int i = 0; i < globalTiles.numBlocks+localTiles.numBlocks; i++)
		{
			int x = (i % editorWidth) * 16;
			int y = (i / editorWidth) * 16;
			if((y + 1)*16 > this.getVisibleRect().y)
				gcBuff.drawImage(blockRenderer.renderBlock(i), x, y, this);
			if(baseSelectedTile == i)
			{
				gcBuff.setColor(Color.red);
				gcBuff.drawRect(x, y, 15, 15);
			}

		}
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		System.out.println(this.getVisibleRect());
		super.paintComponent(g);
		if (globalTiles != null)
		{
			 g.drawImage(imgBuffer, 0, 0, this);
			MainGUI.lblInfo.setText("Done!");
		}
		try
		{
			//best error image.
			//I'll always remember you Smeargle <3
			//g.drawImage(ImageIO.read(MainGUI.class.getResourceAsStream("/resources/smeargle.png")), 100, 240,null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
