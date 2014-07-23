package us.plxhack.MEH.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.DataStore;

import us.plxhack.MEH.IO.MapIO;
import us.plxhack.MEH.IO.Tile;
import us.plxhack.MEH.IO.Tileset;
import us.plxhack.MEH.Structures.MapTile;
import us.plxhack.MEH.Structures.PermissionTileMode;

public class TilesetPickerPanel extends JPanel
{
	private static final long serialVersionUID = -877213633894324075L;
	public int baseSelectedTile;	// Called it base in case of multiple tile
	public int baseSelectedPal;
	public boolean baseXFlip;
	public boolean baseYFlip;
	// selection in the future.
    private boolean Redraw = true;
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
	public Tileset global;
	public Tileset local;
	public int viewingPalette;
	private boolean isMouseDown = false;
	private BlockEditor host;
	
	public static Tile[][] selectBuffer;
	public static int bufferWidth = 1;
	public static int bufferHeight = 1;
	public static Rectangle selectBox;

	public TilesetPickerPanel(Tileset global, Tileset local, BlockEditor hostess)
	{
		mouseTracker=new Rectangle(0,0,16,16);
		selectBox = new Rectangle(0,0,16,16);
		this.global = global;
		this.local = local;
		this.host = hostess;

		this.addMouseMotionListener(new MouseMotionListener()
		{

			
			public void mouseDragged(MouseEvent e)
			{
				int b1 = InputEvent.BUTTON1_DOWN_MASK;
				int b2 = InputEvent.BUTTON2_DOWN_MASK;
				if ((e.getModifiersEx() & (b1 | b2)) != b1) 
				{
					calculateSelectBox(e);
					repaint();
				}
			}

			
			public void mouseMoved(MouseEvent e)
			{
				mouseTracker.x=e.getX();
				mouseTracker.y=e.getY();
				if(mouseTracker.x > 256)
					mouseTracker.x = 256;
				if(mouseTracker.y > 1024)
					mouseTracker.y = 1024;
				repaint();

			}

		});

		this.addMouseListener(new MouseListener()
		{

			
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
					baseSelectedTile = x + (y * 128/8);
					baseSelectedPal = viewingPalette;
					selectBuffer = new Tile[1][1];
					selectBuffer[0][0] = new Tile(baseSelectedTile, baseSelectedPal, host.xFlip, host.yFlip); //TODO implement movement perms
					bufferWidth = 1;
					bufferHeight = 1;
					selectBox.width = 16;
					selectBox.height = 16;
					selectBox.x = ((e.getX() / 16) * 16);
					selectBox.y = ((e.getY() / 16) * 16);
					
					BlockEditor.lblMeep.setText("0x" + BitConverter.toHexString(baseSelectedTile));
					fetchNewBlock();
					repaint();
				}

			}

			
			public void mousePressed(MouseEvent e)
			{
				if(e.getButton() == 3)
				{
					selectBox = new Rectangle(e.getX(),e.getY(),0,0);
				}
			}

			
			public void mouseExited(MouseEvent e)
			{

			}

			
			public void mouseEntered(MouseEvent e)
			{
				isMouseDown = true;
			}

			
			public void mouseReleased(MouseEvent e)
			{
				if(e.getButton() == 3)
				{
					calculateSelectBox(e);

					//Fill the tile buffer
					selectBuffer = new Tile[selectBox.width / 16][selectBox.height / 16];
					bufferWidth = selectBox.width / 16;
					bufferHeight = selectBox.height / 16;
					for(int x = 0; x < bufferWidth; x++)
						for(int y = 0; y < bufferHeight; y++)
							selectBuffer[x][y] = new Tile(baseSelectedTile = (x + selectBox.x / 16) + ((y + selectBox.y / 16) * 128/8), baseSelectedPal, false, false);
					fetchNewBlock();
				}
			}

		});


	}
	
	public static void calculateSelectBox(MouseEvent e) {
		//Get width/height
		selectBox.width = (e.getX() - selectBox.x);
		selectBox.height = (e.getY() - selectBox.y);

		if(selectBox.width > 20)
			selectBox.width += 16;
		if(selectBox.height > 20)
			selectBox.height += 16;
		
		//If our selection is negative, adjust it to be positive 
		//starting from the position the mouse was released
		if (selectBox.width < 0) {
			selectBox.x = e.getX();
			selectBox.width = Math.abs(selectBox.width);
		}
		if (selectBox.height < 0) {
			selectBox.y = e.getY();
			selectBox.height = Math.abs(selectBox.height);
		}

		//Round the values to multiples of 16
		selectBox.x = ((selectBox.x / 16) * 16);
		selectBox.y = ((selectBox.y / 16) * 16);
		selectBox.width = (selectBox.width / 16) * 16;
		selectBox.height = (selectBox.height / 16) * 16;
		
		//Minimum sizes
		if(selectBox.height == 0) {
			selectBox.height = 16;
		}
		if(selectBox.width == 0) {
			selectBox.width = 16;
		}
	}
	
	public void fetchNewBlock()
	{
		baseXFlip = host.xFlip;
		baseYFlip = host.yFlip;
		BufferedImage buffer = new BufferedImage(selectBox.width / 2, selectBox.height / 2,BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		DrawTileset();
		g.drawImage(((BufferedImage)imgBuffer).getSubimage(selectBox.x / 2, selectBox.y / 2, bufferWidth * 8, bufferHeight * 8), 0, 0, null);
		host.panelSelectedBlock.setImage(buffer.getScaledInstance(32, 32, Image.SCALE_FAST));
		host.panelSelectedBlock.repaint();
	}

	public void setPalette(int p)
	{
		viewingPalette = p;
		Redraw = true;
		this.repaint();
	}
	

	public static Graphics gcBuff;
	static Image imgBuffer = null;
	public void DrawTileset()
	{
		Dimension d = new Dimension(128,512);//4 tiles per level, 16 levels total 

		imgBuffer = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_ARGB);
		//setSize(d);
		gcBuff=imgBuffer.getGraphics();
		gcBuff.drawImage(((BufferedImage)(global.getTileSet(viewingPalette, MapIO.blockRenderer.currentTime))), 0, 0, this);
		gcBuff.drawImage(((BufferedImage)(local.getTileSet(viewingPalette, MapIO.blockRenderer.currentTime))), 0, global.getTileSet(viewingPalette, MapIO.blockRenderer.currentTime).getHeight(), this);
	}
	
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(MainGUI.uiSettings == null)
			return;

		if (Redraw == true)
		{
			DrawTileset();
			Redraw = false;
		}
		g.setColor(global.getPalette(MapIO.blockRenderer.currentTime)[baseSelectedPal].getIndex(0));
		g.fillRect(0, 0, imgBuffer.getWidth(null)*2, imgBuffer.getHeight(null)*2);
		
		g.drawImage(imgBuffer.getScaledInstance(256, 1024, imgBuffer.SCALE_FAST), 0, 0, this);

		g.setColor(MainGUI.uiSettings.markerColor);
		g.drawRect((baseSelectedTile % (128/8)) * 16, (baseSelectedTile / (128/8)) * 16, 15, 15);

		g.setColor(MainGUI.uiSettings.cursorColor);
		if (mouseTracker.width < 0)
			mouseTracker.x -= Math.abs(mouseTracker.width);
		if (mouseTracker.height < 0)
			mouseTracker.y -= Math.abs(mouseTracker.height);
		g.drawRect(((mouseTracker.x / 16) % (128/8)) * 16, (mouseTracker.y / 16) * 16, selectBox.width - 1, selectBox.height - 1);
		try
		{
			// best error image.
			// I'll always remember you Smeargle <3
			// g.drawImage(ImageIO.read(MainGUI.class.getResourceAsStream("/resources/smeargle.png")),
			// 100, 240,null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setFlip(boolean xFlip, boolean yFlip)
	{
		for(Tile[] ta : selectBuffer)
			for(Tile t : ta)
			{
				if(xFlip)
					t.xFlip = !t.xFlip;
				if(yFlip)
					t.yFlip = !t.yFlip;
			}
	}

}
