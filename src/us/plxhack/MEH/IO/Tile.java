package us.plxhack.MEH.IO;

public class Tile
{
	private int tileNum;
	private int pal;
	public boolean xFlip;
	public boolean yFlip;
	public Tile(int tileNum, int palette, boolean xFlip, boolean yFlip)
	{
		if(tileNum > 0x3FF)
			tileNum = 0x3FF;
		
		if(palette > 12)
			palette = 12;
		
		this.tileNum = tileNum;
		this.pal = palette;
		this.xFlip = xFlip;
		this.yFlip = yFlip;
	}
	
	public int getTileNumber()
	{
		return tileNum;
	}
	
	public int getPaletteNum()
	{
		return pal;
	}
	
	public void setTileNumber(int number)
	{
		if(number > 0x3FF)
			number = 0x3FF;
		
		tileNum = number;
	}
	
	public void setPaletteNum(int palette)
	{
		if(palette > 12)
			palette = 12;
		
		pal = palette;
	}

	public Tile getNewInstance()
	{
		return new Tile(tileNum, pal, xFlip, yFlip);
	}
}
