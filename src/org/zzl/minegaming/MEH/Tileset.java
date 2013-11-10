package org.zzl.minegaming.MEH;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBAImage;
import org.zzl.minegaming.GBAUtils.GBAImageType;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.Lz77;
import org.zzl.minegaming.GBAUtils.Palette;
import org.zzl.minegaming.GBAUtils.ROMManager;

public class Tileset
{
	private GBARom rom;
	private int dataPtr;
	private GBAImage image;
	private BufferedImage[] bi;
	private Palette[] palettes; //Main gets 7, local gets 5
	private static Tileset lastPrimary;
	private boolean isPrimary = true;

	private int blockPtr, animPtr;
	public final int numBlocks;
	private HashMap<Integer,BufferedImage>[] renderedTiles;
	private HashMap<Integer,BufferedImage>[] customRenderedTiles;


	@SuppressWarnings("unchecked")
	public Tileset(GBARom rom, int offset)
	{
		this.rom = rom;
		dataPtr = offset;
		int imageDataPtr = rom.getPointerAsInt(offset+0x4);
		blockPtr = rom.getPointerAsInt(offset+0xC);
		animPtr = rom.getPointerAsInt(offset+0x10);

		isPrimary = (rom.readByte(offset+1) == 0);
		if(isPrimary)
			lastPrimary = this;
		int[] uncompressedData = null;
		byte b = rom.readByte(offset);

		if(b == 1)
			uncompressedData = Lz77.decompressLZ77(rom, imageDataPtr);
		if(uncompressedData == null)
			uncompressedData = BitConverter.ToInts(rom.readBytes(imageDataPtr, (isPrimary ? 128*320 : 128*192) / 2)); //TODO: Hardcoded to FR tileset sizes

		numBlocks = (isPrimary ? 0x280 : 0x56); //TODO: INI
		renderedTiles = (HashMap<Integer,BufferedImage>[])new HashMap[isPrimary ? 7 : 13];
		customRenderedTiles = (HashMap<Integer,BufferedImage>[])new HashMap[6];
		
		for(int i = 0; i < (isPrimary ? 7 : 13); i++)
			renderedTiles[i] = new HashMap<Integer,BufferedImage>();
		for(int i = 0; i < 6; i++)
			customRenderedTiles[i] = new HashMap<Integer,BufferedImage>();

		palettes = new Palette[13];
		bi = new BufferedImage[13];
		
		
		for(int i = 0; i < (isPrimary ? 7 : 13); i++)
		{
			palettes[i] = new Palette(GBAImageType.c16, rom.readBytes(rom.getPointerAsInt(offset+0x8)+(32*i),32));
		}
		
		image = new GBAImage(uncompressedData,palettes[0],new Point(128,320));
		
		for(int i = 0; i < (isPrimary ? 7 : 13); i++)
		{
			bi[i] = image.getBufferedImageFromPal(palettes[i]);
			if(i > 7)
			{
				lastPrimary.getPalette()[i] = palettes[i];
			}
		}
		
		if(!isPrimary)
			lastPrimary.rerenderCustomTiles();
		
		for(int i = 0; i < (isPrimary ? 7 : 13); i++)
			new TileLoader(renderedTiles,i).start();
	}
	
	public BufferedImage getTileWithCustomPal(int tileNum, Palette palette, boolean xFlip, boolean yFlip)
	{
		
		/*CustomTile cT = new CustomTile(tileNum,palette,xFlip,yFlip);
		if(customRenderedTiles.containsKey(cT)) //Check to see if we've cached that tile
		{
			return customRenderedTiles.get(cT);
		}*/
		int x = ((tileNum) % (bi[0].getWidth() / 8)) * 8;
		int y = ((tileNum) / (bi[0].getWidth() / 8)) * 8;
		BufferedImage toSend =  image.getBufferedImageFromPal(palette).getSubimage(x, y, 8, 8);
		
		//customRenderedTiles.put(cT, toSend);

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
		if(palette < 7)
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
		else
		{
			if(customRenderedTiles[palette-7].containsKey(tileNum)) //Check to see if we've cached that tile
			{
				if(xFlip && yFlip)
					return verticalFlip(horizontalFlip(customRenderedTiles[palette-7].get(tileNum)));
				else if(xFlip)
				{
					return horizontalFlip(customRenderedTiles[palette-7].get(tileNum));
				}
				else if(yFlip)
				{
					return verticalFlip(customRenderedTiles[palette-7].get(tileNum));
				}
				
				return customRenderedTiles[palette-7].get(tileNum);
			}
		}
		
		int x = ((tileNum) % (bi[0].getWidth() / 8)) * 8;
		int y = ((tileNum) / (bi[0].getWidth() / 8)) * 8;
		BufferedImage toSend =  bi[palette].getSubimage(x, y, 8, 8);
		if(palette < 7 || renderedTiles.length > 7)
			renderedTiles[palette].put(tileNum, toSend);
		else
			customRenderedTiles[palette-7].put(tileNum, toSend);

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
	
	public void rerenderTileSet(int palette)
	{
			bi[palette] = image.getBufferedImageFromPal(palettes[palette]);
	}
	
	public void rerenderCustomTiles()
	{
		for(int i = 0; i < 6; i++)
			rerenderTileSet(i+7);
	}
	public void resetCustomTiles()
	{
		customRenderedTiles = (HashMap<Integer,BufferedImage>[])new HashMap[6];
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

	public int getBlockPointer()
	{
		return blockPtr;
	}

	public int getAnimationPointer()
	{
		return animPtr;
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
			int k = numBlocks;
			for(int i = 0; i < numBlocks; i++)
			{
					try
					{
						buffer[pal].put(i, getTile(i,pal,false,false));
					}
					catch(Exception e)
					{
						System.out.println("An error occured while writing tile " + i + " with palette " + pal);
					}
			}
		}
		
	}
}
