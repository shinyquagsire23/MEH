package us.plxhack.MEH.IO.Render;

import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBARom;

import us.plxhack.MEH.IO.Block;
import us.plxhack.MEH.IO.MapIO;
import us.plxhack.MEH.IO.Tile;
import us.plxhack.MEH.IO.Tileset;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlockRenderer extends Component
{
	public enum TripleType
	{
		NONE,
		LEGACY,
		REFERENCE;
	}
	
	private Tileset global;
	private Tileset local;
	public BlockRenderer(Tileset global, Tileset local)
	{
		this.global = global;
		this.local = local;
	}
	
	public BlockRenderer()
	{
		this(null,null);
	}
	
	public void setGlobalTileset(Tileset global)
    {
    	this.global = global;
    }
    
    public void setLocalTileset(Tileset local)
    {
    	this.local = local;
    }
	
    
    public Tileset getGlobalTileset()
    {
    	return global;
    }
    
    public Tileset getLocalTileset()
    {
    	return local;
    }
    
    public Image renderBlock(int blockNum)
    {
    	return renderBlock(blockNum, true);
    }
    
	public Image renderBlock(int blockNum, boolean transparency)
	{
		int origBlockNum = blockNum;
		boolean isSecondaryBlock = false;
		if (blockNum >= DataStore.MainTSBlocks)
		{
			isSecondaryBlock = true;
			blockNum -= DataStore.MainTSBlocks;
		}

		int blockPointer = (int) ((isSecondaryBlock ? local.getTilesetHeader().pBlocks : global.getTilesetHeader().pBlocks) + (blockNum * 16));
		BufferedImage block = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) block.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		int x = 0;
		int y = 0;
		int top = 0;
		
		TripleType type = TripleType.NONE;
		if((getBehaviorByte(origBlockNum) >> (DataStore.EngineVersion == 1 ? 24 : 8) & 0x30) == 0x30 && DataStore.EngineVersion == 1) //Temporarily disabling triple tile support in RSE
			type = TripleType.LEGACY;
			
		else if((getBehaviorByte(origBlockNum) >> (DataStore.EngineVersion == 1 ? 24 : 8) & 0x60) == 0x60 && DataStore.EngineVersion == 1)
			type = TripleType.REFERENCE;
		
		if(type != TripleType.NONE)
			System.out.println("Rendering triple tile! " + type.toString());
		
		for (int i = 0; i < (type != TripleType.NONE ? 24 : 16); i++)
		{
			if(type == TripleType.REFERENCE && i == 16)
			{
				boolean second = false;
				int tripNum = (int) ((getBehaviorByte(origBlockNum) >> 14) & 0x3FF);
				if (tripNum >= DataStore.MainTSBlocks)
				{
					second = true;
					tripNum -= DataStore.MainTSBlocks;
				}

				blockPointer = (int) ((second ? local.getTilesetHeader().pBlocks : global.getTilesetHeader().pBlocks) + (tripNum * 16)) + 8;
				blockPointer -= i;
			}
			int orig = global.getROM().readWord(blockPointer + i);
			int tileNum = global.getROM().readWord(blockPointer + i) & 0x3FF;
			int palette = (global.getROM().readWord(blockPointer + i) & 0xF000) >> 12;
			boolean xFlip = (global.getROM().readWord(blockPointer + i) & 0x400) > 0;
			boolean yFlip = (global.getROM().readWord(blockPointer + i) & 0x800) > 0;
			if (transparency && top == 0)
			{
				try
				{
					g.setColor(global.getPalette()[palette].getIndex(0));
				}
				catch (Exception e)
				{

				}
				g.fillRect(x * 8, y * 8, 8, 8);
			}

			if (tileNum < DataStore.MainTSSize)
			{
				g.drawImage(global.getTile(tileNum, palette, xFlip, yFlip), x * 8, y * 8, null);
			}
			else
			{
				g.drawImage(local.getTile(tileNum - DataStore.MainTSSize, palette, xFlip, yFlip), x * 8, y * 8, null);
			}
			x++;
			if (x > 1)
			{
				x = 0;
				y++;
			}
			if (y > 1)
			{
				x = 0;
				y = 0;
				top = 1;
			}
			i++;
		}
		return createImage(block.getSource());
	}

	public Block getBlock(int blockNum)
	{
		boolean isSecondaryBlock = false;
		int realBlockNum = blockNum;
		if (blockNum >= DataStore.MainTSBlocks)
		{
			isSecondaryBlock = true;
			blockNum -= DataStore.MainTSBlocks;
		}

		int blockPointer = (int) ((isSecondaryBlock ? local.getTilesetHeader().pBlocks : global.getTilesetHeader().pBlocks) + (blockNum * 16));
		int x = 0;
		int y = 0;
		int top = 0;
		Block b = new Block(realBlockNum, global.getROM());
		
		boolean tripleTile = false;
		if((b.backgroundMetaData >> 24 & 0x30) == 0x30)
		{
			tripleTile = true;
			System.out.println("Rendering triple tile block!");
		}
		
		for (int i = 0; i < (tripleTile ? 24 : 16); i++)
		{
			int orig = global.getROM().readWord(blockPointer + i);
			int tileNum = global.getROM().readWord(blockPointer + i) & 0x3FF;
			int palette = (global.getROM().readWord(blockPointer + i) & 0xF000) >> 12;
			boolean xFlip = (global.getROM().readWord(blockPointer + i) & 0x400) > 0;
			boolean yFlip = (global.getROM().readWord(blockPointer + i) & 0x800) > 0;

			if(i < 16)
				b.setTile(x+(top*2), y, new Tile(tileNum, palette, xFlip, yFlip));
			x++;
			if (x > 1)
			{
				x = 0;
				y++;
			}
			if (y > 1)
			{
				x = 0;
				y = 0;
				top = 1;
			}
			i++;
		}
		return b;
	}
	
	public long getBehaviorByte(int blockID)
	{
		int pBehavior = (int)MapIO.blockRenderer.getGlobalTileset().tilesetHeader.pBehavior;
		int blockNum = blockID;
		
		if (blockNum >= DataStore.MainTSBlocks)
		{
			blockNum -= DataStore.MainTSBlocks;
			pBehavior = (int)MapIO.blockRenderer.getLocalTileset().tilesetHeader.pBehavior;
		}
		global.getROM().Seek(pBehavior + (blockNum * (DataStore.EngineVersion == 1 ? 4 : 2)));
		long bytes = DataStore.EngineVersion == 1 ? global.getROM().getPointer(true) : global.getROM().getPointer(true) & 0xFFFF;
		return bytes;
	}
}
