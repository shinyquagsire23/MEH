package org.zzl.minegaming.MEH;

import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ROMManager;
import org.zzl.minegaming.MEH.MapElements.Connection;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

public class ConnectionsEditorPanel extends JPanel
{
	private static final long serialVersionUID = 7594967420892677608L;

	//Map data
	private Map center;
	private Map up;
	private Map down;
	private Map left;
	private Map right;
	
	//Original map images
	private Image centerImg;
	private Image upImg;
	private Image downImg;
	private Image leftImg;
	private Image rightImg;
	
	//Scaled images
	private Image centerImgS;
	private Image upImgS;
	private Image downImgS;
	private Image leftImgS;
	private Image rightImgS;
	
	//Connection data
	private Connection upCon;
	private Connection downCon;
	private Connection leftCon;
	private Connection rightCon;
	
	//Rectangle locations of each connection
	private Rectangle upRect;
	private Rectangle downRect;
	private Rectangle leftRect;
	private Rectangle rightRect;
	private Rectangle centerRect;
	
	//Modified offsets
	private double upOfs;
	private double downOfs;
	private double leftOfs;
	private double rightOfs;
	
	private long lW =0, lH = 0, rW = 0, rH = 0, uW = 0, uH = 0, dW = 0, dH = 0;
	private int filter = Image.SCALE_SMOOTH;
	public double scale = 1;
	
	private static boolean dragging = false;
	private static ConnectionType connectionDragging = null;
	private Point startDrag = new Point(-1,-1);
	private Point mouseTracker = new Point(-1,-1);
	
	public ConnectionsEditorPanel() 
	{
		addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mousePressed(MouseEvent e) 
			{
				System.out.println(e.getModifiersEx());
				if(e.getModifiersEx() != 1024)
					return;
				
				System.out.println("Pressed");
				startDrag = e.getPoint();
				
				if(upRect.contains(e.getX(), e.getY()))
						connectionDragging = ConnectionType.UP;
				else if(downRect.contains(e.getX(), e.getY()))
					connectionDragging = ConnectionType.DOWN;
				else if(leftRect.contains(e.getX(), e.getY()))
					connectionDragging = ConnectionType.LEFT;
				else if(rightRect.contains(e.getX(), e.getY()))
					connectionDragging = ConnectionType.RIGHT;
				
				dragging = true;
			}
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				dragging = false;
				startDrag = new Point(-1,-1);
				ApplyEdits();
				RecalculateScaling(false, false);
				repaint();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) 
			{
				if(e.getModifiersEx() == 1024 && dragging)
				{
					switch(connectionDragging)
					{
						case UP:
							upOfs = (int)((startDrag.x - e.getX()) / (16 / scale));
							break;
						case DOWN:
							downOfs = (int)((startDrag.x - e.getX()) / (16 / scale));
							break;
						case LEFT:
							leftOfs = (int)((startDrag.y - e.getY()) / (16 / scale));
							break;
						case RIGHT:
							rightOfs = (int)((startDrag.y - e.getY()) / (16 / scale));
							break;
					}
					RecalculateScaling(false, false);
					repaint();
				}
				else
					dragging = false;
			}
			@Override
			public void mouseMoved(MouseEvent e) 
			{
				mouseTracker = e.getPoint();
			}
		});
	}
	
	public void loadConnections(Map map)
	{
		resetData();
		center = map;
		upImg = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		downImg = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		leftImg = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		rightImg = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		
		for(Connection c : map.mapConnections.aConnections)
		{
			if(c.lType == 0x1)
			{
				down = new Map(ROMManager.currentROM, c.bBank & 0xFF, c.bMap & 0xFF);
				downImg = Map.renderMap(down);
				downCon = c;
			}
			else if (c.lType == 0x2)
			{
				up = new Map(ROMManager.currentROM, c.bBank & 0xFF, c.bMap & 0xFF);
				upImg = Map.renderMap(up);
				upCon = c;
			}
			else if(c.lType == 0x3)
			{
				left = new Map(ROMManager.currentROM, c.bBank & 0xFF, c.bMap & 0xFF);
				leftImg = Map.renderMap(left);
				leftCon = c;
			}
			else if(c.lType == 0x4)
			{
				right = new Map(ROMManager.currentROM, c.bBank & 0xFF, c.bMap & 0xFF);
				rightImg = Map.renderMap(right);
				rightCon = c;
			}

			//TODO Diving maps!
		}	
		
		centerImg = Map.renderMap(center); //Render last to switch the tileset back to normal
		
		RescaleImages();
	}
	
	private void ApplyEdits()
	{
		try
		{
			upCon.lOffset -= upOfs;
			for(Connection c : up.mapConnections.aConnections)
				if((int)((long)BankLoader.maps[c.bBank].get(c.bMap)) == center.dataOffset)
					c.lOffset = -upCon.lOffset;
		}
		catch(Exception e){}
		
		try
		{
			downCon.lOffset -= downOfs;
			for(Connection c : down.mapConnections.aConnections)
				if((int)((long)BankLoader.maps[c.bBank].get(c.bMap)) == center.dataOffset)
					c.lOffset = -downCon.lOffset;
		}
		catch(Exception e){}
		
		try
		{
			leftCon.lOffset -= leftOfs;
			for(Connection c : left.mapConnections.aConnections)
				if((int)((long)BankLoader.maps[c.bBank].get(c.bMap)) == center.dataOffset)
					c.lOffset = -leftCon.lOffset;
		}
		catch(Exception e){}
		
		try
		{
			rightCon.lOffset -= rightOfs;
			for(Connection c : right.mapConnections.aConnections)
			{
				int mapOffs = (int)((long)BankLoader.maps[c.bBank].get(c.bMap));
				if(mapOffs == center.dataOffset)
					c.lOffset = -rightCon.lOffset;
			}
		}
		catch(Exception e){}
		upOfs = 0;
		downOfs = 0;
		leftOfs = 0;
		rightOfs = 0;
	}
	
	double lastScale = -1;
	private void RecalculateScaling(boolean resize, boolean scroll)
	{
		try
		{
			lW = left.getMapData().mapWidth * 16;
			lH = left.getMapData().mapHeight * 16;
		}
		catch(Exception e){}
		
		try
		{
			rW = right.getMapData().mapWidth * 16;
			rH = right.getMapData().mapHeight * 16;
		}
		catch(Exception e){}
		
		try
		{
			uW = up.getMapData().mapWidth * 16;
			uH = up.getMapData().mapHeight * 16;
		}
		catch(Exception e){}
		
		try
		{
			dW = down.getMapData().mapWidth * 16;
			dH = down.getMapData().mapHeight * 16;
		}
		catch(Exception e){}
		
		if(resize)
		{
			setPreferredSize(new Dimension((int)(((lW + (center.getMapData().mapWidth * 16) + rW)) / scale), ((int)(((uH + (center.getMapData().mapHeight * 16) + dH)) / scale))));
			setSize(this.getPreferredSize());
			
			//TODO scale around a point
			/*if(lastScale != scale && lastScale != -1)
			{
				double scalediff = (lastScale - scale);
				JViewport jsp = (JViewport)this.getParent();
				Rectangle toCenterMap = this.getBounds();
				toCenterMap.setLocation((int)((mouseTracker.x)*(scalediff-1)+toCenterMap.x*scalediff),(int)((toCenterMap.height - mouseTracker.y)*(scalediff-1)-toCenterMap.y*scalediff)); 	 
				jsp.scrollRectToVisible(toCenterMap);						
			}*/
		}
		if(scroll)
		{
			JViewport jsp = (JViewport)this.getParent();
			Rectangle toCenterMap = this.getBounds();
			toCenterMap.setLocation((int)(lW / scale), (int)(uH / scale)); 	//TODO center town instead of bringing it to the corner. 
			jsp.scrollRectToVisible(toCenterMap);						// Corner might be useful for a more seamless transition from the
																		// map editor though, so we'll have to see.
		}
		
		try
		{
			upRect = new Rectangle((int)((lW + (upCon.lOffset * 16)) / scale),0,(int)(uW / scale),(int)(uH / scale));
		}
		catch(Exception e){}
		try
		{
			centerRect = new Rectangle((int)(lW / scale), (int)(uH / scale),(int)((center.getMapData().mapWidth * 16) / scale),(int)((center.getMapData().mapHeight * 16) / scale));
		}
		catch(Exception e){}
		try
		{
			leftRect = new Rectangle(0,((int)((uH + (leftCon.lOffset * 16)) / scale)),(int)(lW / scale),(int)(lH / scale));
		}
		catch(Exception e){}
		try
		{
			rightRect = new Rectangle((int)((lW + ((int)center.getMapData().mapWidth * 16)) / scale), (int)((uH + (int)(rightCon.lOffset * 16)) / scale),(int)(rW / scale),(int)(rH / scale));
		}
		catch(Exception e){}
		try
		{
			downRect = new Rectangle((int)((lW + (int)(downCon.lOffset * 16)) / scale), (int)((uH + ((int)center.getMapData().mapHeight * 16)) / scale),(int)(dW / scale),(int)(dH / scale));
		}
		catch(Exception e){}
		lastScale = scale;
	}
	
	private double average(double a, double b)
	{
		return (a+b)/2;
	}
	
	public void RescaleImages()
	{
		RescaleImages(true);
	}
	
	public void RescaleImages(boolean movescroll)
	{
		RecalculateScaling(true, movescroll);
		
		try
		{
			upImgS = upImg.getScaledInstance((int)(upImg.getWidth(null) / scale), (int)(upImg.getHeight(null) / scale), filter);
		}
		catch(Exception e){}
		try
		{
			centerImgS = centerImg.getScaledInstance((int)(centerImg.getWidth(null) / scale), (int)(centerImg.getHeight(null) / scale), filter);
		}
		catch(Exception e){}
		try
		{
			leftImgS = leftImg.getScaledInstance((int)(leftImg.getWidth(null) / scale), (int)(leftImg.getHeight(null) / scale), filter);
		}
		catch(Exception e){}
		try
		{
			rightImgS = rightImg.getScaledInstance((int)(rightImg.getWidth(null) / scale), (int)(rightImg.getHeight(null) / scale), filter);
		}
		catch(Exception e){}
		try
		{
			downImgS = downImg.getScaledInstance((int)(downImg.getWidth(null) / scale), (int)(downImg.getHeight(null) / scale), filter);
		}
		catch(Exception e){}
	}
	
	private void resetData()
	{
		up = null;
		down = null;
		left = null;
		right = null;
		
		upImg = null;
		downImg = null;
		leftImg = null;
		rightImg = null;
		centerImg = null;
		
		upImgS = null;
		downImgS = null;
		leftImgS = null;
		rightImgS = null;
		centerImgS = null;
		
		upRect = new Rectangle(-1,-1,1,1);
		downRect = new Rectangle(-1,-1,1,1);
		leftRect = new Rectangle(-1,-1,1,1);
		rightRect = new Rectangle(-1,-1,1,1);
		centerRect = new Rectangle(-1,-1,1,1);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(center == null)
			return;
		
		//TODO Make rectangles optional just to be on the user-preference-safe side
		//TODO Draw rectangles for the visible range between tileset changes
		g.setColor(Color.green);
		try
		{
			g.drawImage(upImgS, upRect.x - (int)(upOfs * 16 / scale), upRect.y,this);
			drawRect(g, upRect);
		}
		catch(Exception e)
		{}
		try
		{
			g.drawImage(centerImgS,centerRect.x,centerRect.y,this);
		}
		catch(Exception e)
		{}
		try
		{
			g.drawImage(leftImgS,leftRect.x,leftRect.y - (int)(leftOfs * 16 / scale),this);
			drawRect(g, leftRect);
		}
		catch(Exception e)
		{}
		try
		{
			g.drawImage(rightImgS,rightRect.x,rightRect.y - (int)(rightOfs * 16 / scale),this);
			drawRect(g, rightRect);
		}
		catch(Exception e)
		{}
		try
		{
			g.drawImage(downImgS, downRect.x - (int)(downOfs * 16 / scale), downRect.y,this);
			drawRect(g, downRect);
		}
		catch(Exception e)
		{}
		g.setColor(Color.red);
		drawRect(g, centerRect);
	}
	
	private void drawRect(Graphics g, Rectangle r)
	{
		if(r != null)
			g.drawRect(r.x, r.y, r.width, r.height);
	}

	public void save()
	{
		try
		{
		up.save();
		}
		catch(Exception e){}
		try
		{
		down.save();
		}
		catch(Exception e){}
		try
		{
		left.save();
		}
		catch(Exception e){}
		try
		{
		right.save();
		}
		catch(Exception e){}
	}
}
