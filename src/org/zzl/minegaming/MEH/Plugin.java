package org.zzl.minegaming.MEH;

import javax.swing.ImageIcon;

import org.zzl.minegaming.GBAUtils.GBARom;

public class Plugin
{
	private ImageIcon buttonImage;
	private String toolTip;
	protected GBARom rom;
	public boolean bLoadROM;
	public boolean createButton = true;
	
	public void load()
	{
		//Stuff to do on load
	}
	
	public void execute()
	{
		//Stuff to do when it's appropriate button is pressed
	}
	
	public void loadROM(GBARom rom)
	{
		this.rom = rom;
		new DataStore("../MEH.ini", rom.getFriendlyROMHeader());//Init our data store and grab ini from outside plugin folder
		//Do stuff when ROM is loaded
	}
	
	public void saveROM()
	{
		//Do stuff when ROM is saved
	}
	
	public void loadMap(int map, int bank)
	{
		//Do stuff when a new map is loaded
	}
	
	public void saveMap(int map, int bank)
	{
		//Do stuff when a map is saved
	}
	
	public void unload()
	{
		//Stuff to do when unloading app
	}
	
	public ImageIcon getButtonImage()
	{
		return buttonImage;
	}
	
	public void setButtonImage(ImageIcon icon)
	{
		buttonImage = icon;
	}
	
	public String getToolTip()
	{
		return toolTip;
	}
	
	public void setToolTip(String s)
	{
		toolTip = s;
	}


}
