package org.zzl.minegaming.MEH;

import java.awt.Point;
import java.awt.image.BufferedImage;

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
	private Palette[] palettes; //Main gets 7, local gets 5
	
	public Tileset(GBARom rom, int offset)
	{
		this.rom = rom;
		dataPtr = offset;
		int imageDataPtr = rom.getPointerAsInt(offset+0x4);
		System.out.println(BitConverter.toHexString(imageDataPtr));
		int[] uncompressedData = null;
		byte b = rom.readByte(offset);
		if(b == 1)
			uncompressedData = Lz77.decompressLZ77(rom, imageDataPtr);
		if(uncompressedData == null)
			uncompressedData = BitConverter.ToInts(rom.readBytes(imageDataPtr, (128*320) / 2));
		palettes = new Palette[rom.readByte(offset+1) == 0 ? 7 : 5];
		for(int i = 0; i < (rom.readByte(offset+1) == 0 ? 7 : 5); i++)
		{
			palettes[i] = new Palette(GBAImageType.c16, rom.readBytes(rom.getPointerAsInt(offset+0x8)+(32*i),32));
		}
		image = new GBAImage(uncompressedData,palettes[0],new Point(128,320));
	}
	
	public BufferedImage getTile(int tileNum, int palette)
	{
		BufferedImage orig = image.getBufferedImageFromPal(palettes[palette]);
		int x = ((tileNum - 1) % (orig.getWidth() / 8)) * 8;
		int y = ((tileNum - 1) / (orig.getWidth() / 8)) * 8;
		return orig.getSubimage(x, y, 8, 8);
		
	}
	
	public BufferedImage getTileSet(int palette)
	{
		return image.getBufferedImageFromPal(palettes[palette]);
	}
}
