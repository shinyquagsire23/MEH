package org.zzl.minegaming.MEH;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.MapTile;
import org.zzl.minegaming.MEH.MapElements.Tileset;

import java.awt.event.InputEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class PermissionTilePanel extends JPanel
{
	private static final long serialVersionUID = -877213633894324075L;
	public static int baseSelectedTile;	// Called it base in case of multiple tile
	// selection in the future.
	public static int editorWidth = 8; //Editor width in 16x16 tiles
	private Tileset globalTiles;
	private Tileset localTiles;
	private boolean isMouseDown = true;
    private static boolean Redraw = true;
	static Rectangle mouseTracker;
	public static Image imgPermissions;
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

	public PermissionTilePanel()
	{
		mouseTracker=new Rectangle(0,0,16,16);
		try {
			imgPermissions=ImageIO.read(MainGUI.class.getResourceAsStream("/resources/permissions.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//auto generate image on fail.
			e1.printStackTrace();
		}
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
				
				}

			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				if(e.getButton() == 3)
				{
				
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
				}
			}

		});

	}


	public static Graphics gcBuff;
	static Image imgBuffer = null;
	public void DrawTileset()
	{
		Dimension d = new Dimension(256,512);//4 tiles per level, 16 levels total 

		imgBuffer = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_ARGB);
		setSize(d);
		gcBuff=imgBuffer.getGraphics();
		int x=0;
		int y=0;
		int i=0;
					
		for(y=0;y<16;y++){
			for(x=0;x<4;x++){
				
				gcBuff.drawImage(((BufferedImage)(PermissionTilePanel.imgPermissions)).getSubimage((x+(y*4)) * 16, 0, 16, 16), x * 16, y * 16, this);
			      
				
			}
		}
		

		
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
			if(PermissionTilePanel.Redraw==true){
				DrawTileset();
				PermissionTilePanel.Redraw=false;
			}
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
