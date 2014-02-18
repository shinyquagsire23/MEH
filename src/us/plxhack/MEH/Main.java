package us.plxhack.MEH;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javafx.application.Application;
import us.plxhack.MEH.IO.BankLoader;
import us.plxhack.MEH.Plugins.PluginManager;
import us.plxhack.MEH.UI.MainGUI;

public class Main {
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		System.setProperty("sun.java2d.opengl","True");
		System.setProperty("sun.java2d.accthreshold","0");
        	System.setProperty("apple.laf.useScreenMenuBar","true");
        	System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MEH");
		JFrame window = new MainGUI();
		window.setSize(1000, 600);
		window.setTitle("Map Editor of Happiness - No ROM Loaded");
		BankLoader.reset();
		window.setVisible(true);
		try {
			PluginManager.loadAllPlugins();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
