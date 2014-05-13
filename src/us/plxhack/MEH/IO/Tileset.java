package us.plxhack.MEH.IO;

import org.zzl.minegaming.GBAUtils.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Tileset
{
	private GBARom rom;
	private GBAImage image;
	private BufferedImage[] bi;
	private Palette[] palettes; 
	private Palette[] palettesFromROM;
	private static Tileset lastPrimary;
	public TilesetHeader tilesetHeader;

	public final int numBlocks;
	private HashMap<Integer,BufferedImage>[] renderedTiles;
	private HashMap<Integer,BufferedImage>[] customRenderedTiles;
	private final byte[] localTSLZHeader = new byte[] { 10, 80, 9, 00, 32, 00, 00 };
	private final byte[] globalTSLZHeader = new byte[] { 10, 80, 9, 00, 32, 00, 00 };
	
	public boolean modified = false;


	@SuppressWarnings("unchecked")
	public Tileset(GBARom rom, int offset)
	{
		this.rom = rom;
		loadData(offset);
		numBlocks = 1024; //(tilesetHeader.isPrimary ? DataStore.MainTSBlocks : DataStore.LocalTSBlocks); //INI RSE=0x207 : 0x88, FR=0x280 : 0x56
		renderTiles(offset);	
	}
	
	public void loadData(int offset)
	{
		tilesetHeader = new TilesetHeader(rom,offset);
	}
	
	public void renderGraphics()
	{
		int imageDataPtr = (int)tilesetHeader.pGFX;

		if(tilesetHeader.isPrimary)
			lastPrimary = this;
		int[] uncompressedData = null;

		if(tilesetHeader.bCompressed == 1)
			uncompressedData = Lz77.decompressLZ77(rom, imageDataPtr);
		if(uncompressedData == null)
		{
			GBARom backup = (GBARom) rom.clone(); //Backup in case repairs fail
			rom.writeBytes((int)tilesetHeader.pGFX, (tilesetHeader.isPrimary ? globalTSLZHeader : localTSLZHeader)); //Attempt to repair the LZ77 data
			uncompressedData = Lz77.decompressLZ77(rom, imageDataPtr);
			rom = (GBARom) backup.clone(); //TODO add dialog to allow repairs to be permanant
			if(uncompressedData == null) //If repairs didn't go well, revert ROM and pull uncompressed data
			{
				uncompressedData = BitConverter.ToInts(rom.readBytes(imageDataPtr, (tilesetHeader.isPrimary ? 128*DataStore.MainTSHeight : 128*DataStore.LocalTSHeight) / 2)); //TODO: Hardcoded to FR tileset sizes
			}
		}
		
		renderedTiles = new HashMap[tilesetHeader.isPrimary ? DataStore.MainTSPalCount : 13];
		customRenderedTiles = new HashMap[13-DataStore.MainTSPalCount];
		
		for(int i = 0; i < (tilesetHeader.isPrimary ? DataStore.MainTSPalCount : 13); i++)
			renderedTiles[i] = new HashMap<Integer,BufferedImage>();
		for(int i = 0; i < 13-DataStore.MainTSPalCount; i++)
			customRenderedTiles[i] = new HashMap<Integer,BufferedImage>();
		
		image = new GBAImage(uncompressedData,palettes[0],new Point(128,(tilesetHeader.isPrimary ? DataStore.MainTSHeight : DataStore.LocalTSHeight)));
	}
	
	public void renderPalettes()
	{
		palettes = new Palette[13];
		bi = new BufferedImage[13];
		
		for(int i = 0; i < (tilesetHeader.isPrimary ? DataStore.MainTSPalCount : 13); i++)
		{
			palettes[i] = new Palette(GBAImageType.c16, rom.readBytes(((int)tilesetHeader.pPalettes)+(32*i),32));
		}
		palettesFromROM = palettes.clone();
	}
	
	public void renderTiles(int offset)
	{
		renderPalettes();
		renderGraphics();
	}
	
	public void startTileThreads()
	{
		for(int i = 0; i < (tilesetHeader.isPrimary ? DataStore.MainTSPalCount : 13); i++)
			new TileLoader(renderedTiles,i).start();
	}
	
	public BufferedImage getTileWithCustomPal(int tileNum, Palette palette, boolean xFlip, boolean yFlip)
	{
		int x = ((tileNum) % (bi[0].getWidth() / 8)) * 8;
		int y = ((tileNum) / (bi[0].getWidth() / 8)) * 8;
		BufferedImage toSend =  image.getBufferedImageFromPal(palette).getSubimage(x, y, 8, 8);

		if(!xFlip && !yFlip)
			return toSend;
		if(xFlip)
			toSend = horizontalFlip(toSend);
		if(yFlip)
			toSend = verticalFlip(toSend);
		
		return toSend;
	}

	public BufferedImage getTile(int tileNum, int palette, boolean xFlip, boolean yFlip)
	{
		if(palette < DataStore.MainTSPalCount)
		{
		if(renderedTiles[palette].containsKey(tileNum)) //Check to see if we've cached that tile
		{
			if(xFlip && yFlip)
				return verticalFlip(horizontalFlip(renderedTiles[palette].get(tileNum)));
			else if(xFlip)
			{
				return horizontalFlip(renderedTiles[palette].get(tileNum));
			}
			else if(yFlip)
			{
				return verticalFlip(renderedTiles[palette].get(tileNum));
			}
			
			return renderedTiles[palette].get(tileNum);
		}
		}
		else if(palette < 13)
		{
			if(customRenderedTiles[palette-DataStore.MainTSPalCount].containsKey(tileNum)) //Check to see if we've cached that tile
			{
				if(xFlip && yFlip)
					return verticalFlip(horizontalFlip(customRenderedTiles[palette-DataStore.MainTSPalCount].get(tileNum)));
				else if(xFlip)
				{
					return horizontalFlip(customRenderedTiles[palette-DataStore.MainTSPalCount].get(tileNum));
				}
				else if(yFlip)
				{
					return verticalFlip(customRenderedTiles[palette-DataStore.MainTSPalCount].get(tileNum));
				}
				
				return customRenderedTiles[palette-DataStore.MainTSPalCount].get(tileNum);
			}
		}
		else
		{
		//	System.out.println("Attempted to read tile " + tileNum + " of palette " + palette + " in " + (tilesetHeader.isPrimary ? "global" : "local") + " tileset!");
			return new BufferedImage(8,8,BufferedImage.TYPE_INT_ARGB);
		}
		
		int x = ((tileNum) % (128 / 8)) * 8;
		int y = ((tileNum) / (128 / 8)) * 8;
		BufferedImage toSend = new BufferedImage(8,8,BufferedImage.TYPE_INT_ARGB);
		try
		{
			toSend =  bi[palette].getSubimage(x, y, 8, 8);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		//	System.out.println("Attempted to read 8x8 at " + x + ", " + y);
		}
		if(palette < DataStore.MainTSPalCount || renderedTiles.length > DataStore.MainTSPalCount)
			renderedTiles[palette].put(tileNum, toSend);
		else
			customRenderedTiles[palette-DataStore.MainTSPalCount].put(tileNum, toSend);

		if(!xFlip && !yFlip)
			return toSend;
		if(xFlip)
			toSend = horizontalFlip(toSend);
		if(yFlip)
			toSend = verticalFlip(toSend);
		
		return toSend;
	}
	
	public Palette[] getPalette()
	{
		return palettes;
	}
	
	public Palette[] getROMPalette()
	{
		return palettesFromROM.clone(); //No touchy the real palette!
	}
	
	public void resetPalettes()
	{
		palettes = getROMPalette();
	}
	
	public void setPalette(Palette[] pal)
	{
		palettes = pal;
	}
	
	public void rerenderTileSet(int palette)
	{
			bi[palette] = image.getBufferedImageFromPal(palettes[palette]);
	}
	
	public void renderPalettedTiles()
	{		
		for(int i = 0; i < 13; i++)
		{
			bi[i] = image.getBufferedImageFromPal(palettes[i]);

		}
		for(int i = 0; i < 13; i++)
			rerenderTileSet(i);
	}
	public void resetCustomTiles()
	{
		customRenderedTiles = new HashMap[DataStore.MainTSPalCount];
		for(int i = 0; i < DataStore.MainTSPalCount; i++)
			customRenderedTiles[i] = new HashMap<Integer,BufferedImage>();
	}
	
    private BufferedImage horizontalFlip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        return dimg;
    }
 
    private BufferedImage verticalFlip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getColorModel()
                .getTransparency());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return dimg;
    }

	public BufferedImage getTileSet(int palette)
	{
		return bi[palette];
	}
	
	public BufferedImage getIndexedTileSet(int palette)
	{
		return image.getIndexedImage(palettes[palette], true);
	}
	
	public TilesetHeader getTilesetHeader()
	{
		return tilesetHeader;
	}

	public GBARom getROM()
	{
		return rom;
	}
	
	private class TileLoader extends Thread implements Runnable
	{
		HashMap<Integer,BufferedImage>[] buffer;
		int pal;
		public TileLoader(HashMap<Integer,BufferedImage>[] hash, int palette)
		{
			buffer = hash;
			pal = palette;
		}
		
		@Override
		public void run()
		{
			int k = (tilesetHeader.isPrimary ? DataStore.MainTSSize : DataStore.LocalTSSize);
			for(int i = 0; i < 1023; i++)
			{
					try
					{
						buffer[pal].put(i, getTile(i,pal,false,false));
					}
					catch(Exception e)
					{
						//e.printStackTrace();
						System.out.println("An error occured while writing tile " + i + " with palette " + pal);
					}
			}
		}
		
	}

	public void save()
	{
		for(int i = 0; i < (tilesetHeader.isPrimary ? DataStore.MainTSPalCount : 13); i++)
		{
			rom.Seek(((int)tilesetHeader.pPalettes)+(32*i));
			palettes[i].save(rom);
		}
		tilesetHeader.save();
	}
}