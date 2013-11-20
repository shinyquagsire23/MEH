package org.zzl.minegaming.MEH;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.MapTile;
import org.zzl.minegaming.MEH.MapElements.Tileset;

import java.awt.event.InputEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import org.zzl.minegaming.MEH.PermissionEditorPanel;
public class PermissionTilesPanel extends JPanel
{
	
	public static int baseSelectedTile;	// Called it base in case of multiple tile
	// selection in the future.
	public static int editorWidth = 8; //Editor width in 16x16 tiles
    
	private boolean isMouseDown = true;

	static Rectangle mouseTracker;
	void DrawTiles(){
		
	}
	public void SetRect(int width, int heigh){

		if(heigh>16) heigh=16;
		if(width>16) width=16;
		mouseTracker.height=heigh;
		mouseTracker.width=width;
	}
	public void SetRect(){
		mouseTracker.height=16;
		mouseTracker.width=16;
	}
	int srcX;
	int srcY;

	public PermissionTilesPanel()
	{
		mouseTracker=new Rectangle(0,0,16,16);

		this.addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseDragged(MouseEvent e)
			{
				int b1 = InputEvent.BUTTON1_DOWN_MASK;
				int b2 = InputEvent.BUTTON2_DOWN_MASK;
				if ((e.getModifiersEx() & (b1 | b2)) != b1) 
				{
					MapEditorPanel.calculateSelectBox(e);
					repaint();
				}
			}

			@Override
			public void mouseMoved(MouseEvent e)
			{
				mouseTracker.x=e.getX();
				mouseTracker.y=e.getY();

				repaint();

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
				if (e.getClickCount() == 2 && e.getButton()==3){
					SetRect();//Reset tile rectangle
				}
				else{
					srcX=x;
					srcY=y;
					baseSelectedTile = y;

					String k = "Current Permission: ";
					k += String.format("0x%8s",
							Integer.toHexString(baseSelectedTile))
							.replace(' ', '0');
					MainGUI.lblTileVal.setText("Current Tile: 0x" + BitConverter.toHexString(PermissionTilesPanel.baseSelectedTile));
					repaint();
				}

			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				if(e.getButton() == 3)
				{
					MapEditorPanel.selectBox = new Rectangle(e.getX(),e.getY(),0,0);
				}
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
				if(e.getButton() == 3)
				{
					MapEditorPanel.calculateSelectBox(e);

					
				}
			}

		});

	}


	public static Graphics gcBuff;
	static Image imgBuffer = null;
	
	
	public static void DrawPermissionTiles(){
		Dimension d = new Dimension(16,0x3F*16);//#hexswag

		imgBuffer = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_ARGB);
		
		gcBuff=imgBuffer.getGraphics();
		for(int i = 0; i < PermissionEditorPanel.PermissionColors.length; i++) //TODO readd ini support here
		{

			gcBuff.setColor(Color.black);
			gcBuff.drawRect(0, i*16, 16, 16);
			gcBuff.setColor(PermissionEditorPanel.PermissionColors[i]);
			
		    gcBuff.fillRect(0, i*16, 16, 16);
		    gcBuff.setColor(Color.RED);
		    gcBuff.drawString(Integer.toHexString(i), 1, i*16);
		}
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
			g.drawImage(imgBuffer, 0, 0, this);
	
		
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
