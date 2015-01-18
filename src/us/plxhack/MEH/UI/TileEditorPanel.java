package us.plxhack.MEH.UI;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.DataStore;

import us.plxhack.MEH.IO.MapIO;
import us.plxhack.MEH.IO.Tileset;
import us.plxhack.MEH.Structures.EditMode;
import us.plxhack.MEH.Structures.MapTile;
import us.plxhack.MEH.UI.MapEditorPanel.SelectRect;

import javax.swing.*;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

//TODO Make this a base class for *any* tileset, ie movement perms
public class TileEditorPanel extends JPanel {
	private static final long serialVersionUID = -877213633894324075L;
	public int baseSelectedTile;	// Called it base in case of multiple tile
	// selection in the future.
	public static int editorWidth = 8; //Editor width in 16x16 tiles
	public static boolean tripleSelectMode = false; //We really need events...
	public Tileset globalTiles;
	public Tileset localTiles;
    private boolean Redraw = false;
    private boolean tiedToEditor = false;
	static Rectangle mouseTracker;
	public static SelectRect selectBox;
	public Color selectRectColor = MainGUI.uiSettings.cursorColor;

	public void SetRect(int width, int height) {

		if (height > 16)
			height = 16;
		if (width > 16)
			width = 16;
		mouseTracker.height = height;
		mouseTracker.width = width;
	}

	public void SetRect() {
		mouseTracker.height = 16;
		mouseTracker.width = 16;
	}

	int srcX;
	int srcY;

	public TileEditorPanel(boolean tied) {
		tiedToEditor = tied;
		mouseTracker = new Rectangle(0,0,16,16);
		selectBox = new SelectRect(0,0,16,16);

		this.addMouseMotionListener(new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {
/*				int b1 = InputEvent.BUTTON1_DOWN_MASK;
				int b2 = InputEvent.BUTTON2_DOWN_MASK;
                mouseTracker.x = e.getX();
                mouseTracker.y = e.getY();
                baseSelectedTile = (e.getX() / 16) + ((e.getY() / 16) * editorWidth);
                applySelectedTile();
				if ((e.getModifiersEx() & (b1 | b2)) != b1 && tiedToEditor) {
					MapEditorPanel.calculateSelectBox(e);
				}
                repaint();*/
            	
            	if (e.getModifiersEx() == 1024)  {
					mouseTracker.x = e.getX();
					mouseTracker.y = e.getY();
				}
				int x = (mouseTracker.x / 16);
				int y = (mouseTracker.y / 16);
				
				if(MapIO.DEBUG)
					System.out.println(x + " " + y);

				if(mouseTracker.x > (16 * editorWidth) - 1 || mouseTracker.y > ((DataStore.MainTSSize / editorWidth) * (DataStore.LocalTSSize / editorWidth) * 16) - 1)
					MapEditorPanel.calculateSelectBox(e,selectBox);
				
				MainGUI.setMouseCoordinates(mouseTracker.x / 16, mouseTracker.y / 16);
                repaint();
			}

			public void mouseMoved(MouseEvent e) {
				mouseTracker.x = e.getX();
				mouseTracker.y = e.getY();
				repaint();
			}
		});

		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
/*				int x = 0;
				int y = 0;

				x = (e.getX() / 16);
				y = (e.getY() / 16);

				//Reset tile rectangle
				if (e.getClickCount() == 2 && e.getButton() == 3) {
					SetRect();
				}

				else {
					srcX = x;
					srcY = y;
					baseSelectedTile = x + (y * editorWidth);
                    //applySelectedTile();
				}
                repaint();*/
			}

			public void mousePressed(MouseEvent e) {
				if(mouseTracker.x > (16 * editorWidth) - 1 || mouseTracker.y > ((DataStore.MainTSSize / editorWidth) * (DataStore.LocalTSSize / editorWidth) * 16) - 1)
					return;
				
				if(MapIO.DEBUG)
					System.out.println(e.getButton());
				
				int x = e.getX() / 16;
				int y = e.getY() / 16;
				
				if(e.getButton() == 1) {
					baseSelectedTile = x + (y * editorWidth);
					selectRectColor = MainGUI.uiSettings.markerColor;
				} else if(e.getButton() == 3) {
					selectBox = new SelectRect(x * 16,y * 16,16,16);
					selectRectColor = MainGUI.uiSettings.cursorSelectColor;
					MainGUI.lblTileVal.setText("Current Tile: 0x" + BitConverter.toHexString(MainGUI.tileEditorPanel.baseSelectedTile));
				}
				
                applySelectedTile();
				repaint();
			}

			public void mouseExited(MouseEvent e) {
                repaint();
			}
			
			public void mouseEntered(MouseEvent e) {
                repaint();
			}

			public void mouseReleased(MouseEvent e) {
				selectRectColor = MainGUI.uiSettings.cursorColor;
				
				if (e.getButton() == 3) {
					MapEditorPanel.calculateSelectBox(e,selectBox);

					//Fill the tile buffer
					MapEditorPanel.selectBuffer = new MapTile[MapEditorPanel.selectBox.width / 16][MapEditorPanel.selectBox.height / 16];
					MapEditorPanel.bufferWidth = MapEditorPanel.selectBox.width / 16;
					MapEditorPanel.bufferHeight = MapEditorPanel.selectBox.height / 16;
					for(int x = 0; x < MapEditorPanel.bufferWidth; x++)
						for(int y = 0; y < MapEditorPanel.bufferHeight; y++)
							MapEditorPanel.selectBuffer[x][y] = new MapTile(baseSelectedTile = x + (y * editorWidth), 0xC); //TODO implement movement perms
				}
                repaint();
			}
		});
	}
	
	public void tieToEditor()
	{
		tiedToEditor = true;
	}

	public void setGlobalTileset(Tileset global) {
		globalTiles = global;
		//blockRenderer.setGlobalTileset(global);
	}

	public void setLocalTileset(Tileset local) {
		localTiles = local;
		//blockRenderer.setLocalTileset(local);
	}
	public static Graphics gcBuff;
	public static Image imgBuffer = null;
	@SuppressWarnings("deprecation")
	public void DrawTileset() {
		imgBuffer = RerenderTiles(imgBuffer, 0, DataStore.MainTSBlocks+0x200,true);//(DataStore.EngineVersion == 1 ? 0x11D : 0x200), true);
		//new org.zzl.minegaming.GBAUtils.PictureFrame(imgBuffer).show();
		//Dimension d = new Dimension(16*editorWidth,(DataStore.MainTSSize / editorWidth)*(DataStore.LocalTSSize / editorWidth) *16);
		//imgBuffer = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_ARGB);
	}
	
	public Image RerenderSecondary(Image i) {
		return RerenderTiles(i, DataStore.MainTSBlocks);
	}
	
	public Image RerenderTiles(Image i, int startBlock) {
		return RerenderTiles(i, startBlock, DataStore.MainTSBlocks+(DataStore.EngineVersion == 1 ? 0x11D : 1024), false);
	}
	
	public Image RerenderTiles(Image b, int startBlock, int endBlock, boolean completeRender) {
		//startBlock = DataStore.MainTSBlocks;
		Dimension d = new Dimension(16*editorWidth,(DataStore.MainTSSize / editorWidth)*(DataStore.LocalTSSize / editorWidth)*16);
		if(completeRender) {
			if(DataStore.EngineVersion == 0)
				d.height = 3048;
			b = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_ARGB);
		}
		setSize(d);
		gcBuff=b.getGraphics();
		for(int i = startBlock; i < endBlock; i++) {
			int x = (i % editorWidth) * 16;
			int y = (i / editorWidth) * 16;

			try {
				gcBuff.drawImage(MapIO.blockRenderer.renderBlock(i,true), x, y, this);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (globalTiles != null) {
			if(Redraw==true) {
				DrawTileset();
				Redraw=false;
			}
			
			try
			{
				g.drawImage(((BufferedImage)imgBuffer).getSubimage(0, 0, 128, 2048), 0, 0, this); //Weird fix for a weird bug. :/
			}
			catch(Exception e)
			{
				if(MapIO.DEBUG)
					e.printStackTrace();
				
				if(imgBuffer != null)
					System.out.println("Error rendering blockset! Enable debug mode for more specific errors.");
			}
			
			g.setColor(MainGUI.uiSettings.markerColor);
			g.drawRect((baseSelectedTile % editorWidth) * 16, (baseSelectedTile / editorWidth) * 16, 15, 15);
			
			g.setColor(selectRectColor);
			if(mouseTracker.width <0)
				mouseTracker.x -= Math.abs(mouseTracker.width);
			if(mouseTracker.height <0)
				mouseTracker.y -= Math.abs(mouseTracker.height);
			
			if(mouseTracker.x > editorWidth * 16)
				mouseTracker.x = editorWidth * 16;
			
			g.drawRect(((mouseTracker.x / 16) % editorWidth) * 16,(mouseTracker.y / 16) * 16,selectBox.width-1,selectBox.height-1);
		}
		try {
			//best error image.
			//I'll always remember you Smeargle <3
			//g.drawImage(ImageIO.read(MainGUI.class.getResourceAsStream("/resources/smeargle.png")), 100, 240,null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		globalTiles = null;
		localTiles = null;
	}

    public void applySelectedTile() {
    	//TODO: Events
    	if(tiedToEditor)
    	{
    		MapEditorPanel.selectBuffer = new MapTile[1][1];
    		MapEditorPanel.selectBuffer[0][0] = new MapTile(baseSelectedTile,-1); //TODO Default movement perms
    		MapEditorPanel.bufferWidth = 1;
    		MapEditorPanel.bufferHeight = 1;
    		MapEditorPanel.selectBox.width = 16;
    		MapEditorPanel.selectBox.height = 16;
    		String k = "Current Tile: ";
    		k += String.format("0x%8s", Integer.toHexString(baseSelectedTile)).replace(' ', '0');
    		MainGUI.lblTileVal.setText("Current Tile: 0x" + BitConverter.toHexString(baseSelectedTile));
    	}
    	else
    	{
    		if(!tripleSelectMode)
    		{
    			BlockEditor.blockEditorPanel.setBlock(MapIO.blockRenderer.getBlock(baseSelectedTile));
    			long behavior = MapIO.blockRenderer.getBehaviorByte(baseSelectedTile);
    			BlockEditor.txtBehavior.setText(String.format("%08X", behavior));
    		}
    		else
    		{
    			BlockEditor.blockEditorPanel.setTriple(MapIO.blockRenderer.getBlock(baseSelectedTile));
    			baseSelectedTile = BlockEditor.blockEditorPanel.getBlock().blockID;
    			tripleSelectMode = false;
    			this.repaint();
    		}
    		BlockEditor.blockEditorPanel.repaint();
    		//BlockEditor.lblMeep.setText(String.format("0x%3s", Integer.toHexString(baseSelectedTile)).replace(' ', '0'));
    	}
    }
}
