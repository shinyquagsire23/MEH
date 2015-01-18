package us.plxhack.MEH;

import java.awt.Dimension;
import java.awt.Toolkit;

import us.plxhack.MEH.IO.BankLoader;
import us.plxhack.MEH.Plugins.PluginManager;
import us.plxhack.MEH.UI.MainGUI;

import javax.swing.*;

public class Main
{
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// Override for window managers such as bspwm and i3 where they may not
		// properly fetch the right L&F
		if (System.getProperty("os.name").toLowerCase().contains("nix") || System.getProperty("os.name").toLowerCase().contains("nux") || System.getProperty("os.name").toLowerCase().contains("aix"))
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");

		System.setProperty("sun.java2d.opengl", "True");
		System.setProperty("sun.java2d.accthreshold", "0");
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MEH");
		System.setProperty("awt.useSystemAAFontSettings","on");
		System.setProperty("swing.aatext", "true");
		JFrame window = new MainGUI();
		window.setSize(1024, 736);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
		window.setTitle("Map Editor of Happiness | No ROM Loaded");
		window.setMinimumSize(new Dimension(516,338));
		BankLoader.reset();
		window.setVisible(true);
		try
		{
			PluginManager.loadAllPlugins();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
