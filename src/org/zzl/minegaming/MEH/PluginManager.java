package org.zzl.minegaming.MEH;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import javax.swing.JButton;

import org.zzl.minegaming.GBAUtils.ROMManager;

public class PluginManager
{
	private static ArrayList<Plugin> plugins = new ArrayList<Plugin>();
	public static void loadAllPlugins() throws Exception
	{
		if(DataStore.mehUsePlugins==0) return;//If they're off, don't load! 
		File search = new File("plugins/");
		for(File f : search.listFiles())
		{
			URLClassLoader classLoader = URLClassLoader .newInstance(new URL[] { new URL("file:./plugins/" + f.getName()) });
			Class<?> clazz = classLoader.loadClass("org.zzl.minegaming.TestPlugin.Plugin"); //TODO: Read main class from somewhere. YAML?
			final Plugin plugin = (Plugin) clazz.newInstance();
			plugin.load();
			plugins.add(plugin);
			
			if(plugin.createButton)
			{
				JButton btnPlugin = new JButton("");
				btnPlugin.setToolTipText(plugin.getToolTip());
				btnPlugin.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						plugin.execute();
					}
				});
				btnPlugin.setIcon(plugin.getButtonImage());
				btnPlugin.setFocusPainted(false);
				btnPlugin.setBorderPainted(false);
				btnPlugin.setPreferredSize(new Dimension(54, 48));
				MainGUI.panelButtons.add(btnPlugin);
			}
		}
	}
	
	public static void unloadAllPlugins()
	{
		for(Plugin p : plugins)
			p.unload();
	}
	
	public static void fireROMLoad()
	{
		for(Plugin p : plugins)
			p.loadROM(ROMManager.currentROM);
	}
	
	public static void fireROMSave()
	{
		for(Plugin p : plugins)
			p.saveROM();
	}
	
	public static void fireMapLoad(int bank, int map)
	{
		for(Plugin p : plugins)
			p.loadMap(bank,map);
	}
	
	public static void fireMapSave(int bank, int map)
	{
		for(Plugin p : plugins)
			p.saveMap(bank,map);
	}
}
