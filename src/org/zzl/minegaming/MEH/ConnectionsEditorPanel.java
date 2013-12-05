package org.zzl.minegaming.MEH;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ROMManager;
import org.zzl.minegaming.MEH.MapElements.Connection;

public class ConnectionsEditorPanel extends JPanel
{
	private static final long serialVersionUID = 7594967420892677608L;

	private Map center;
	private Map up;
	private Map down;
	private Map left;
	private Map right;
	
	private Image centerImg;
	private Image upImg;
	private Image downImg;
	private Image leftImg;
	private Image rightImg;
	
	private Connection upCon;
	private Connection downCon;
	private Connection leftCon;
	private Connection rightCon;
	
	private long lW =0, lH = 0, rW = 0, rH = 0, uW = 0, uH = 0, dW = 0, dH = 0;
	
	public int scale = 2;
	
	public void loadConnections(Map map)
	{
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
		
		setPreferredSize(new Dimension(((int)(lW + (center.getMapData().mapWidth * 16) + rW)) / scale, ((int)(uH + (center.getMapData().mapHeight * 16) + dH)) / scale));
		setSize(this.getPreferredSize());
		JViewport jsp = (JViewport)this.getParent();
		Rectangle toCenterMap = this.getBounds();
		toCenterMap.setLocation((int)lW / scale, (int)uH / scale); 	//TODO center town instead of bringing it to the corner. 
		jsp.scrollRectToVisible(toCenterMap);						// Corner might be useful for a more seamless transition from the
																	// map editor though, so we'll have to see.
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(center == null)
			return;
		//TODO Offsetting
		int filter = Image.SCALE_SMOOTH;
		try
		{
			g.drawImage(upImg.getScaledInstance(upImg.getWidth(null) / scale, upImg.getHeight(null) / scale, filter), ((int)lW + (int)(upCon.lOffset * 16)) / scale,0,this);
		}
		catch(Exception e)
		{}
		try
		{
			g.drawImage(centerImg.getScaledInstance(centerImg.getWidth(null) / scale, centerImg.getHeight(null) / scale, filter),(int)lW / scale, (int)uH / scale,this);
		}
		catch(Exception e)
		{}
		try
		{
			g.drawImage(leftImg.getScaledInstance(leftImg.getWidth(null) / scale, leftImg.getHeight(null) / scale, filter),0,((int)uH + (int)(leftCon.lOffset * 16)) / scale,this);
		}
		catch(Exception e)
		{}
		try
		{
			g.drawImage(rightImg.getScaledInstance(rightImg.getWidth(null) / scale, rightImg.getHeight(null) / scale, filter),((int)lW + ((int)center.getMapData().mapWidth * 16)) / scale, ((int)uH + (int)(rightCon.lOffset * 16)) / scale,this);
		}
		catch(Exception e)
		{}
		try
		{
			g.drawImage(downImg.getScaledInstance(downImg.getWidth(null) / scale, downImg.getHeight(null) / scale, filter), ((int)lW + (int)(downCon.lOffset * 16)) / scale, ((int)uH + ((int)center.getMapData().mapHeight * 16)) / scale,this);

		}
		catch(Exception e)
		{}
	}
}
