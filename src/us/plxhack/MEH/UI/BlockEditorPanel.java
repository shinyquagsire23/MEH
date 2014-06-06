package us.plxhack.MEH.UI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.ROMManager;

import us.plxhack.MEH.IO.Block;
import us.plxhack.MEH.IO.BorderMap;
import us.plxhack.MEH.IO.MapIO;
import us.plxhack.MEH.IO.Tile;
import us.plxhack.MEH.IO.Tileset;
import us.plxhack.MEH.IO.Render.BlockRenderer;
import us.plxhack.MEH.Structures.EditMode;
import us.plxhack.MEH.Structures.MapTile;

public class BlockEditorPanel extends JPanel
{

	
	private Tileset globalTiles;
	private Tileset localTiles;
	private BlockRenderer blockRenderer = new BlockRenderer();
	private Block block;
	private int mouseX = 0;
	private int mouseY = 0;
	private BlockEditor host;

	//Could really go for one of those friggin Ding Dongs right now... 
	public BlockEditorPanel(BlockEditor hostess) {
		this.host = hostess;
		this.block = new Block(0, ROMManager.getActiveROM());
		this.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				int x = e.getX() / 16;
				int y = e.getY() / 16;

				if (e.getModifiersEx() == 1024) {
	                mouseX = e.getX() / 16;
	                mouseY = e.getY() / 16;
					int bufWidth = (TilesetPickerPanel.bufferWidth > 4 ? 4 : TilesetPickerPanel.bufferWidth); //TODO multiple blocks somehow
					int bufHeight = (TilesetPickerPanel.bufferHeight > 2 ? 2 : TilesetPickerPanel.bufferHeight);
					for(int DrawX=0; DrawX < bufWidth; DrawX++)
						for(int DrawY=0; DrawY < bufHeight; DrawY++)
							block.setTile(DrawX + x, DrawY + y, TilesetPickerPanel.selectBuffer[DrawX][DrawY]);
					block.save();
					host.rerenderTiles();
				}
				else 
				{
					TilesetPickerPanel.calculateSelectBox(e);
				}
                repaint();
			}

			public void mouseMoved(MouseEvent e) {
				if(block == null) return;
				mouseX = (e.getX()  / 16);
				mouseY = (e.getY() / 16);
				
				if(mouseX > 4)
					mouseX = 4;
				if(mouseY > 2)
					mouseY = 2;
				
				if(mouseX < 0)
					mouseX = 0;
				if(mouseY < 0)
					mouseY = 0;
				
				repaint();
			}

		});

		this.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)
			{
				int x = (e.getX() / 16);
				int y = (e.getY() / 16);
				if(e.getButton() == MouseEvent.BUTTON1)
				{
					int bufWidth = (TilesetPickerPanel.bufferWidth > 4 ? 4 : TilesetPickerPanel.bufferWidth);
					int bufHeight = (TilesetPickerPanel.bufferHeight > 2 ? 2 : TilesetPickerPanel.bufferHeight);
					for(int DrawX=0; DrawX < bufWidth; DrawX++)
						for(int DrawY=0; DrawY < bufHeight; DrawY++)
							block.setTile(DrawX + x, DrawY + y, TilesetPickerPanel.selectBuffer[DrawX][DrawY]);
					block.save();
					host.rerenderTiles();
					repaint();
				}
				else if(e.getButton() == 3)
				{
					host.tpp.baseSelectedTile = block.getTile(x, y).getTileNumber();
					host.comboBox.setSelectedIndex(block.getTile(x, y).getPaletteNum());
					host.xFlip = block.getTile(x, y).xFlip;
					host.yFlip = block.getTile(x, y).yFlip;
					host.scrollPaneTiles.getVerticalScrollBar().setValue(Math.min((block.getTile(x, y).getTileNumber() / 16) * 16, host.scrollPaneTep.getVerticalScrollBar().getMaximum()));
					//MainGUI.lblTileVal.setText("Current Tile: 0x" + BitConverter.toHexString(MainGUI.tileEditorPanel.baseSelectedTile));
					MapIO.repaintTileEditorPanel();
				}
			}

			public void mouseReleased(MouseEvent e)
			{
				if(e.getButton() == 3)
				{
					TilesetPickerPanel.calculateSelectBox(e);

					//Fill the tile buffer
					TilesetPickerPanel.selectBuffer = new Tile[TilesetPickerPanel.selectBox.width / 16][TilesetPickerPanel.selectBox.height / 16];
					
					if(TilesetPickerPanel.selectBox.width > 4*16)
						TilesetPickerPanel.selectBox.width = 4*16;
					if(TilesetPickerPanel.selectBox.height > 2*16)
						TilesetPickerPanel.selectBox.height = 2*16;
					
					TilesetPickerPanel.bufferWidth = TilesetPickerPanel.selectBox.width / 16;
					TilesetPickerPanel.bufferHeight = TilesetPickerPanel.selectBox.height / 16;
					
					for(int x = 0; x < TilesetPickerPanel.bufferWidth; x++)
						for(int y = 0; y < TilesetPickerPanel.bufferHeight; y++)
							TilesetPickerPanel.selectBuffer[x][y] = (Tile)block.getTile(TilesetPickerPanel.selectBox.x / 16 + x, TilesetPickerPanel.selectBox.y / 16 + y);
				}
			}

			public void mousePressed(MouseEvent e)
			{
				if(e.getButton() == 3)
				{
					TilesetPickerPanel.selectBox = new Rectangle(e.getX(),e.getY(),0,0);
				}
			}

			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
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

	public void setBlock(Block b)
	{
		block = b;
		if((MapIO.blockRenderer.getBehaviorByte(b.blockID) >> (DataStore.EngineVersion == 1 ? 24 : 8) & 0x60) == 0x60 && DataStore.EngineVersion == 1)
		{
			int tripNum = (int) ((MapIO.blockRenderer.getBehaviorByte(block.blockID) >> 14) & 0x3FF);
			host.tripleEditorPanel.setBlock(MapIO.blockRenderer.getBlock(tripNum));
		}
	}
	
	public Block getBlock()
	{
		return block;
	}
	
	public void setTriple(Block b)
	{
		host.tripleEditorPanel.setBlock(b);
		
		int id = b.blockID;
		long behavior = block.backgroundMetaData;
		behavior &= 0x8F003FFF;
		if(id != 0)
			behavior |= (id << 14) + (0x60 << 24);
		else
			behavior |= 0x20 << 24; //Default to 0x20 because if they had 0x10 previously they should not have been using triple tiles.
		block.backgroundMetaData = behavior;
		block.save();
		host.rerenderTiles();
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		for (int y = 0; y < 2; y++)
		{
			for (int x = 0; x < 4; x++)
			{
				if (block.getTile(x, y).getTileNumber() < DataStore.MainTSSize)
				{
					g.setColor(host.tpp.global.getPalette()[block.getTile(x, y).getPaletteNum()].getIndex(0)); //Set default background color
					g.fillRect(x * 16, y * 16,16,16);
					g.drawImage(host.tpp.global.getTile(block.getTile(x, y).getTileNumber(), block.getTile(x, y).getPaletteNum(), block.getTile(x, y).xFlip, block.getTile(x, y).yFlip).getScaledInstance(16, 16, Image.SCALE_FAST), x * 16, y * 16, null);
				}
				else
				{
					g.setColor(host.tpp.local.getPalette()[block.getTile(x, y).getPaletteNum()].getIndex(0)); //Set default background color
					g.fillRect(x * 16, y * 16,16,16);
					g.drawImage(host.tpp.local.getTile(block.getTile(x, y).getTileNumber() - DataStore.MainTSSize, block.getTile(x, y).getPaletteNum(), block.getTile(x, y).xFlip, block.getTile(x, y).yFlip).getScaledInstance(16, 16, Image.SCALE_FAST), x * 16, y * 16, null);
				}
			}
		}

		g.setColor(MainGUI.uiSettings.cursorColor);
		try
		{
			g.drawRect((((mouseX) % 4) * 16), (mouseY * 16), TilesetPickerPanel.selectBox.width - 1, TilesetPickerPanel.selectBox.height - 1);
		}
		catch (Exception e)
		{

		}
		try
		{
			// g.drawImage(ImageIO.read(MainGUI.class.getResourceAsStream("/resources/smeargle.png")),
			// 100, 240, null);
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
		block = null;
	}
}
