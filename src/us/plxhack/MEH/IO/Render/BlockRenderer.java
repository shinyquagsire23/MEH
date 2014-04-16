package us.plxhack.MEH.IO.Render;

import org.zzl.minegaming.GBAUtils.DataStore;
import us.plxhack.MEH.IO.Tileset;

import java.awt.*;
import java.awt.image.BufferedImage;

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
    	return renderBlock(blockNum, true);
    }
    
	public Image renderBlock(int blockNum, boolean transparency)
	{
		boolean isSecondaryBlock = false;
		if(blockNum >= DataStore.MainTSBlocks)
		{
			isSecondaryBlock = true;
			blockNum -= DataStore.MainTSBlocks;
		}
		
		int blockPointer = (int) ((isSecondaryBlock ? local.getTilesetHeader().pBlocks : global.getTilesetHeader().pBlocks) + (blockNum * 16));
		BufferedImage block = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)block.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		int x = 0;
		int y = 0;
		int top = 0;
		for(int i = 0; i < 16; i++)
		{
			int orig = global.getROM().readWord(blockPointer + i);
			int tileNum = global.getROM().readWord(blockPointer + i) & 0x3FF;
			int palette = (global.getROM().readWord(blockPointer + i) & 0xF000) >> 12;
			boolean xFlip = (global.getROM().readWord(blockPointer + i) & 0x400) > 0;
			boolean yFlip = (global.getROM().readWord(blockPointer + i) & 0x800) > 0;
			if(transparency && top == 0)
			{
				try{
						g.setColor(global.getPalette()[palette].getIndex(0));
				}catch(Exception e){
					
				}
				g.fillRect(x*8, y*8, 8, 8);
			}

			if(tileNum < DataStore.MainTSSize)
			{
				g.drawImage(global.getTile(tileNum, palette,xFlip,yFlip),x*8,y*8,null);
			}
			else
			{
				g.drawImage(local.getTile(tileNum - DataStore.MainTSSize, palette, xFlip, yFlip),x*8,y*8,null);
			}
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
				top = 1;
			}
			i++;
		}
		return createImage(block.getSource());
	}
}
