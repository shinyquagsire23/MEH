package org.zzl.minegaming.MEH;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class BlockRenderer extends Component
{
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
	
	public Image renderBlock(int blockNum)
	{
		boolean isSecondaryBlock = false;
		if(blockNum > 0x280)
		{
			isSecondaryBlock = true;
			blockNum -= 0x280;
		}
		
		int blockPointer = (isSecondaryBlock ? local.getBlockPointer() : global.getBlockPointer()) + (blockNum * 16);
		BufferedImage block = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)block.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		int x = 0;
		int y = 0;
		for(int i = 0; i < 16; i++)
		{
			int orig = global.getROM().readWord(blockPointer + i);
			int tileNum = global.getROM().readWord(blockPointer + i) & 0x3FF;
			int palette = (global.getROM().readWord(blockPointer + i) & 0xF000) >> 12;
			boolean xFlip = (global.getROM().readWord(blockPointer + i) & 0x400) > 0;
			boolean yFlip = (global.getROM().readWord(blockPointer + i) & 0x800) > 0;
			if(tileNum < global.numBlocks)
				g.drawImage(global.getTile(tileNum, palette,xFlip,yFlip),x*8,y*8,null);
			else
				g.drawImage(global.getTile(tileNum - global.numBlocks, palette, xFlip, yFlip),x*8,y*8,null);
			x++;
			if(x > 1)
			{
				x = 0;
				y++;
			}
			if(y > 1)
			{
				x = 0;
				y = 0;
			}
			i++;
		}
		return createImage(block.getSource());
	}
}
