package us.plxhack.MEH.IO;

import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBARom;


public class Block
{
	public Tile tilesForeground[][];
	public Tile tilesBackground[][];
	public int blockID;
	private GBARom rom;
	
	public Block(int blockID, GBARom rom)
	{
		this.blockID = blockID;
		this.rom = rom;
		tilesForeground = new Tile[2][2];
		tilesBackground = new Tile[2][2];
		for(int i = 0; i < 2; i++)
		{
			for(int j = 0; j < 2; j++)
			{
				tilesForeground[i][j] = new Tile(0,0,false,false);
				tilesBackground[i][j] = new Tile(0,0,false,false);
			}
		}
	}
	
	public void setTile(int x, int y, Tile t)
	{
		try
		{
			if (x < 2)
				tilesBackground[x][y] = t.getNewInstance();
			else
				tilesForeground[x-2][y] = t.getNewInstance();
		}
		catch(Exception e){}
	}
	
	public Tile getTile(int x, int y)
	{
		try
		{
			if (x < 2)
				return tilesBackground[x][y].getNewInstance();
			else
				return tilesForeground[x-2][y].getNewInstance();
		}
		catch(Exception e)
		{
			return new Tile(0,0,false,false);
		}
	}
	
	public void save()
	{
		int pBlocks = (int)MapIO.blockRenderer.getGlobalTileset().tilesetHeader.pBlocks;;
		int blockNum = blockID;
		if (blockNum >= DataStore.MainTSBlocks)
		{
			blockNum -= DataStore.MainTSBlocks;
			pBlocks = (int)MapIO.blockRenderer.getLocalTileset().tilesetHeader.pBlocks;
		}
		
		pBlocks += (blockNum * 16);
		rom.Seek(pBlocks);
		
		for (int i = 0; i < 2; i++)
		{
			for (int y1 = 0; y1 < 2; y1++)
			{
				for (int x1 = 0; x1 < 2; x1++)
				{
					if(i == 0)
					{
						int toWrite = tilesBackground[x1][y1].getTileNumber() & 0x3FF;
						toWrite |= (tilesBackground[x1][y1].getPaletteNum() & 0xF) << 12;
						toWrite |= (tilesBackground[x1][y1].xFlip ? 0x1 : 0x0) << 10;
						toWrite |= (tilesBackground[x1][y1].yFlip ? 0x1 : 0x0) << 11;
						rom.writeWord(toWrite);
					}
					else
					{
						int toWrite = tilesForeground[x1][y1].getTileNumber() & 0x3FF;
						toWrite |= (tilesForeground[x1][y1].getPaletteNum() & 0xF) << 12;
						toWrite |= (tilesForeground[x1][y1].xFlip ? 0x1 : 0x0) << 10;
						toWrite |= (tilesForeground[x1][y1].yFlip ? 0x1 : 0x0) << 11;
						rom.writeWord(toWrite);
					}
				}
			}
		}
	}
}
