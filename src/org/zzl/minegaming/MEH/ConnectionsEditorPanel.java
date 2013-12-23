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
	private Map[] up;
	private Map[] down;
	private Map[] left;
	private Map[] right;
	
	//Original map images
	private Image centerImg;
	private Image[] upImg;
	private Image[] downImg;
	private Image[] leftImg;
	private Image[] rightImg;
	
	//Scaled images
	private Image centerImgS;
	private Image[] upImgS;
	private Image[] downImgS;
	private Image[] leftImgS;
	private Image[] rightImgS;
	
	//Connection data
	private Connection[] upCon;
	private Connection[] downCon;
	private Connection[] leftCon;
	private Connection[] rightCon;
	
	//Rectangle locations of each connection
	private Rectangle[] upRect;
	private Rectangle[] downRect;
	private Rectangle[] leftRect;
	private Rectangle[] rightRect;
	private Rectangle centerRect;
	
	//Modified offsets
	private double[] upOfs;
	private double[] downOfs;
	private double[] leftOfs;
	private double[] rightOfs;
	
	private long[] lW, lH, rW, rH, uW, uH, dW, dH; //Map Widths and Heights
	private long lWB = 0, lHB = 0, rWB = 0, rHB = 0, uWB = 0, uHB = 0, dWB = 0, dHB = 0; //Largest Map Widths and Heights
	private long xAdj, wAdj, yAdj, hAdj;
	private int filter = Image.SCALE_SMOOTH;
	public double scale = 1.8;
	
	private static boolean dragging = false;
	private static ConnectionType connectionDragging = null;
	private Point startDrag = new Point(-1,-1);
	private Point mouseTracker = new Point(-1,-1);
	
	public ConnectionsEditorPanel() 
	{
		addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount() > 1)
				{
					if (upRect[0].contains(e.getX(), e.getY()))
						MainGUI.loadMap(upCon[0].bBank & 0xFF, upCon[0].bMap & 0xFF);
					else if (downRect[0].contains(e.getX(), e.getY()))
						MainGUI.loadMap(downCon[0].bBank & 0xFF, downCon[0].bMap & 0xFF);
					else if (leftRect[0].contains(e.getX(), e.getY()))
						MainGUI.loadMap(leftCon[0].bBank & 0xFF, leftCon[0].bMap & 0xFF);
					else if (rightRect[0].contains(e.getX(), e.getY()))
						MainGUI.loadMap(rightCon[0].bBank & 0xFF, rightCon[0].bMap & 0xFF);
				}
			}
			@Override
			public void mousePressed(MouseEvent e) 
			{
				System.out.println(e.getModifiersEx());
				if(e.getModifiersEx() != 1024)
					return;
				
				System.out.println("Pressed");
				startDrag = e.getPoint();
				
				if(upRect[0].contains(e.getX(), e.getY()))
						connectionDragging = ConnectionType.UP;
				else if(downRect[0].contains(e.getX(), e.getY()))
					connectionDragging = ConnectionType.DOWN;
				else if(leftRect[0].contains(e.getX(), e.getY()))
					connectionDragging = ConnectionType.LEFT;
				else if(rightRect[0].contains(e.getX(), e.getY()))
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
							upOfs[0] = (int)((startDrag.x - e.getX()) / (16 / scale));
							break;
						case DOWN:
							downOfs[0] = (int)((startDrag.x - e.getX()) / (16 / scale));
							break;
						case LEFT:
							leftOfs[0] = (int)((startDrag.y - e.getY()) / (16 / scale));
							break;
						case RIGHT:
							rightOfs[0] = (int)((startDrag.y - e.getY()) / (16 / scale));
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
		int dC = 0, uC = 0, lC = 0, rC = 0;
		
		for(Connection c : map.mapConnections.aConnections)
		{
			if(c == null)
				continue;
			if(c.lType == 0x1)
				dC++;
			else if (c.lType == 0x2)
				uC++;
			else if(c.lType == 0x3)
				lC++;
			else if(c.lType == 0x4)
				rC++;

			//TODO Diving maps!
		}	
		
		up = new Map[uC];
		down = new Map[dC];
		left = new Map[lC];
		right = new Map[rC];
		
		upImg = new BufferedImage[uC];
		downImg = new BufferedImage[dC];
		leftImg = new BufferedImage[lC];
		rightImg = new BufferedImage[rC];
		
		upImgS = new Image[uC];
		downImgS = new Image[dC];
		leftImgS = new Image[lC];
		rightImgS = new Image[rC];
		
		upCon = new Connection[uC];
		downCon = new Connection[dC];
		leftCon = new Connection[lC];
		rightCon = new Connection[rC];
		
		upRect = new Rectangle[uC];
		downRect = new Rectangle[dC];
		leftRect = new Rectangle[lC];
		rightRect = new Rectangle[rC];
		
		for(int i = 0; i < upRect.length; i++)
			upRect[i] = new Rectangle(-1,-1,1,1);
		for(int i = 0; i < downRect.length; i++)
			downRect[i] = new Rectangle(-1,-1,1,1);
		for(int i = 0; i < leftRect.length; i++)
			leftRect[i] = new Rectangle(-1,-1,1,1);
		for(int i = 0; i < rightRect.length; i++)
			rightRect[i] = new Rectangle(-1,-1,1,1);
		
		lW = new long[lC]; 
		lH = new long[lC];
		rW = new long[rC];
		rH = new long[rC];
		uW = new long[uC];
		uH = new long[uC];
		dW = new long[dC];
		dH = new long[dC];
		
		for(int i = 0; i < uC; i++)
			 upImg[i] = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < dC; i++)
			 downImg[i] = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < lC; i++)
			 leftImg[i] = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < rC; i++)
			 rightImg[i] = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
	
		dC = 0;
		uC = 0; 
		lC = 0;
		rC = 0;
		
		for(Connection c : map.mapConnections.aConnections)
		{
			if(c == null)
				continue;
			if(c.lType == 0x1)
			{
				down[dC] = new Map(ROMManager.currentROM, c.bBank & 0xFF, c.bMap & 0xFF);
				downImg[dC] = Map.renderMap(down[dC], false);
				downCon[dC] = c;
				dC++;
				if(dC > 1)
					System.out.println("Down");
			}
			else if (c.lType == 0x2)
			{
				up[uC] = new Map(ROMManager.currentROM, c.bBank & 0xFF, c.bMap & 0xFF);
				upImg[uC] = Map.renderMap(up[uC], false);
				upCon[uC] = c;
				uC++;
				if(uC > 1)
					System.out.println("Up");
			}
			else if(c.lType == 0x3)
			{
				left[lC] = new Map(ROMManager.currentROM, c.bBank & 0xFF, c.bMap & 0xFF);
				leftImg[lC] = Map.renderMap(left[lC], false);
				leftCon[lC] = c;
				lC++;
				if(lC > 1)
					System.out.println("Left");
			}
			else if(c.lType == 0x4)
			{
				right[rC] = new Map(ROMManager.currentROM, c.bBank & 0xFF, c.bMap & 0xFF);
				rightImg[rC] = Map.renderMap(right[rC], false);
				rightCon[rC] = c;
				rC++;
				if(rC > 1)
					System.out.println("Right");
			}

			//TODO Diving maps!
		}	
		upOfs = new double[upCon.length];
		downOfs = new double[downCon.length];
		leftOfs = new double[leftCon.length];
		rightOfs = new double[rightCon.length];
		
		centerImg = Map.renderMap(center, false); //Render last to switch the tileset back to normal
		
		RescaleImages();
	}
	
	private void ApplyEdits()
	{
		try
		{
			int i = 0;
			for(Connection con : upCon)
			{
				con.lOffset -= upOfs[i];
				for(Connection c : up[i].mapConnections.aConnections)
					if((int)((long)BankLoader.maps[c.bBank].get(c.bMap)) == center.dataOffset)
						c.lOffset = -con.lOffset;
				i++;
			}
		}
		catch(Exception e){}
		
		try
		{
			int i = 0;
			for(Connection con : downCon)
			{
				con.lOffset -= downOfs[i];
				for(Connection c : down[i].mapConnections.aConnections)
					if((int)((long)BankLoader.maps[c.bBank].get(c.bMap)) == center.dataOffset)
						c.lOffset = -con.lOffset;
				i++;
			}
		}
		catch(Exception e){}
		
		try
		{
			int i = 0;
			for(Connection con : leftCon)
			{
				con.lOffset -= leftOfs[i];
				for(Connection c : left[i].mapConnections.aConnections)
					if((int)((long)BankLoader.maps[c.bBank].get(c.bMap)) == center.dataOffset)
						c.lOffset = -con.lOffset;
				i++;
			}
		}
		catch(Exception e){}
		
		try
		{
			int i = 0;
			for(Connection con : rightCon)
			{
				con.lOffset -= rightOfs[i];
				for(Connection c : right[i].mapConnections.aConnections)
					if((int)((long)BankLoader.maps[c.bBank].get(c.bMap)) == center.dataOffset)
						c.lOffset = -con.lOffset;
				i++;
			}
		}
		catch(Exception e){}
		upOfs = new double[upCon.length];
		downOfs = new double[downCon.length];
		leftOfs = new double[leftCon.length];
		rightOfs = new double[rightCon.length];
	}
	
	double lastScale = -1;
	private void RecalculateScaling(boolean resize, boolean scroll)
	{
		try
		{
			long largestW = 0;
			long largestH = 0;
			for(int i = 0; i < left.length; i++)
			{
				lW[i] = left[i].getMapData().mapWidth * 16;
				lH[i] = left[i].getMapData().mapHeight * 16;
				if(lW[i] > largestW)
					largestW = lW[i];
				if(lH[i] > largestH)
					largestH = lH[i];
			}
			lWB = largestW;
			lHB = largestH;
		}
		catch(Exception e){}
		
		try
		{
			long largestW = 0;
			long largestH = 0;
			for(int i = 0; i < right.length; i++)
			{
				rW[i] = right[i].getMapData().mapWidth * 16;
				rH[i] = right[i].getMapData().mapHeight * 16;
				if(rW[i] > largestW)
					largestW = rW[i];
				if(rH[i] > largestH)
					largestH = rH[i];
			}
			rWB = largestW;
			rHB = largestH;
		}
		catch(Exception e){}
		long largestUOffset = 0;
		try
		{
			long largestW = 0;
			long largestH = 0;
			for(int i = 0; i < up.length; i++)
			{
				uW[i] = up[i].getMapData().mapWidth * 16;
				uH[i] = up[i].getMapData().mapHeight * 16;
				if(uW[i] > largestW)
				{
					largestW = uW[i];
					largestUOffset = upCon[i].lOffset;
				}
				if(uH[i] > largestH)
					largestH = uH[i];
			}
			uWB = largestW;
			uHB = largestH;
		}
		catch(Exception e){}
		long largestDOffset = 0;
		try
		{
			long largestW = 0;
			long largestH = 0;
			for(int i = 0; i < down.length; i++)
			{
				dW[i] = down[i].getMapData().mapWidth * 16;
				dH[i] = down[i].getMapData().mapHeight * 16;
				if(dW[i] > largestW)
				{
					largestW = dW[i];
					largestDOffset = downCon[i].lOffset;
				}
				if(dH[i] > largestH)
					largestH = dH[i];
			}
			dWB = largestW;
			dHB = largestH;
		}
		catch(Exception e){}
		
		if(resize)
		{
			long xAdj2 = 0;
			try
			{
				//TODO Find widest of each side
				xAdj = (int) (uWB / 16 > center.getMapData().mapWidth ? uWB / 16 - center.getMapData().mapWidth + largestUOffset : 0);
				xAdj2 = (int) (dWB / 16 > center.getMapData().mapWidth ? dWB / 16 - center.getMapData().mapWidth + largestDOffset : 0);
				int wAdj = (int) (uWB / 16 > center.getMapData().mapWidth ? uWB / 16 - center.getMapData().mapWidth + largestUOffset : 0);
				int wAdj2 = (int) (dWB / 16 > center.getMapData().mapWidth ? dWB / 16 - center.getMapData().mapWidth + largestDOffset : 0);
			}
			catch(Exception e){}
			xAdj = Math.max(xAdj,xAdj2) * 16;
			
			
			setPreferredSize(new Dimension((int)(((lWB + (center.getMapData().mapWidth * 16) + rWB + xAdj)) / scale), ((int)(((uHB + (center.getMapData().mapHeight * 16) + dHB)) / scale))));
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
			toCenterMap.setLocation((int)(lWB / scale), (int)(uHB / scale)); 	//TODO center town instead of bringing it to the corner. 
			jsp.scrollRectToVisible(toCenterMap);						// Corner might be useful for a more seamless transition from the
																		// map editor though, so we'll have to see.
		}
		
		try
		{
			for(int i = 0; i < upCon.length; i++)
				upRect[i] = new Rectangle((int)((lWB + (upCon[i].lOffset * 16) + xAdj) / scale),0,(int)(uW[i] / scale),(int)(uH[i] / scale));
		}
		catch(Exception e){}
		try
		{
			//TODO Find largest of connections
			centerRect = new Rectangle((int)((lWB/ scale) + xAdj / scale), (int)(uHB / scale),(int)((center.getMapData().mapWidth * 16) / scale),(int)((center.getMapData().mapHeight * 16) / scale));
		}
		catch(Exception e){}
		try
		{
			for(int i = 0; i < leftCon.length; i++)
				leftRect[i] = new Rectangle(0,((int)((uHB + (leftCon[i].lOffset * 16) + xAdj) / scale)),(int)(lW[i] / scale),(int)(lH[i] / scale));
		}
		catch(Exception e){}
		try
		{
			for(int i = 0; i < rightCon.length; i++)
				rightRect[i] = new Rectangle((int)((lWB + ((int)center.getMapData().mapWidth * 16) + xAdj) / scale), (int)((uHB + (int)(rightCon[i].lOffset * 16)) / scale),(int)(rW[i] / scale),(int)(rH[i] / scale));
		}
		catch(Exception e){e.printStackTrace();}
		try
		{
			for(int i = 0; i < downCon.length; i++)
				downRect[i] = new Rectangle((int)((lWB + (int)(downCon[i].lOffset * 16) + xAdj) / scale), (int)((uHB + ((int)center.getMapData().mapHeight * 16)) / scale),(int)(dW[i] / scale),(int)(dH[i] / scale));
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
			for(int i = 0; i < upImgS.length; i++)
				upImgS[i] = upImg[i].getScaledInstance((int)(upImg[i].getWidth(null) / scale), (int)(upImg[i].getHeight(null) / scale), filter);
		}
		catch(Exception e){}
		try
		{
			centerImgS = centerImg.getScaledInstance((int)(centerImg.getWidth(null) / scale), (int)(centerImg.getHeight(null) / scale), filter);
		}
		catch(Exception e){}
		try
		{
			for(int i = 0; i < leftImgS.length; i++)
				leftImgS[i] = leftImg[i].getScaledInstance((int)(leftImg[i].getWidth(null) / scale), (int)(leftImg[i].getHeight(null) / scale), filter);
		}
		catch(Exception e){e.printStackTrace();}
		try
		{
			for(int i = 0; i < rightImgS.length; i++)
			rightImgS[i] = rightImg[i].getScaledInstance((int)(rightImg[i].getWidth(null) / scale), (int)(rightImg[i].getHeight(null) / scale), filter);
		}
		catch(Exception e){}
		try
		{
			for(int i = 0; i < downImgS.length; i++)
			downImgS[i] = downImg[i].getScaledInstance((int)(downImg[i].getWidth(null) / scale), (int)(downImg[i].getHeight(null) / scale), filter);
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
		
		/*upRect = new Rectangle(-1,-1,1,1);
		downRect = new Rectangle(-1,-1,1,1);
		leftRect = new Rectangle(-1,-1,1,1);
		rightRect = new Rectangle(-1,-1,1,1);
		centerRect = new Rectangle(-1,-1,1,1);*/
		
		//lW =0; lH = 0; rW = 0; rH = 0; uW = 0; uH = 0; dW = 0; dH = 0;
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
			for(int i = 0; i < upImgS.length;i++)
			{
				g.drawImage(upImgS[i], upRect[i].x - (int)(upOfs[i] * 16 / scale), upRect[i].y,this);
				drawRect(g, upRect[i]);
			}
		}
		catch(Exception e)
		{e.printStackTrace();}
		try
		{
			g.drawImage(centerImgS,centerRect.x,centerRect.y,this);
		}
		catch(Exception e)
		{e.printStackTrace();}
		try
		{
			for(int i = 0; i < leftImgS.length;i++)
			{
				g.drawImage(leftImgS[i],leftRect[i].x,leftRect[i].y - (int)(leftOfs[i] * 16 / scale),this);
				drawRect(g, leftRect[i]);
			}
		}
		catch(Exception e)
		{e.printStackTrace();}
		try
		{
			for(int i = 0; i < rightImgS.length;i++)
			{
				g.drawImage(rightImgS[i],rightRect[i].x,rightRect[i].y - (int)(rightOfs[i] * 16 / scale),this);
				drawRect(g, rightRect[i]);
			}
		}
		catch(Exception e)
		{e.printStackTrace();}
		try
		{
			for(int i = 0; i < downImgS.length;i++)
			{
				g.drawImage(downImgS[i], downRect[i].x - (int)(downOfs[i] * 16 / scale), downRect[i].y,this);
				drawRect(g, downRect[i]);
			}
		}
		catch(Exception e)
		{e.printStackTrace();}
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
		up[0].save();
		}
		catch(Exception e){}
		try
		{
		down[0].save();
		}
		catch(Exception e){}
		try
		{
		left[0].save();
		}
		catch(Exception e){}
		try
		{
		right[0].save();
		}
		catch(Exception e){}
	}

	public int getLargestWidth()
	{
		return 0;
	}
}
