package us.plxhack.MEH.UI;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.Image;

import org.zzl.minegaming.GBAUtils.*;

public class GradientTest extends JFrame
{
	public static boolean isOpen = false;
	
	/**
	 * Serial Stuffs
	 */
	private static final long serialVersionUID = -5311502864811966111L;

	private ImagePanel panel;
	
	public GradientTest(BufferedImage img)
	{
		panel = new ImagePanel(img);
		getContentPane().add(panel, BorderLayout.CENTER);
		this.setSize(img.getWidth(), img.getHeight());
	}

	public GradientTest(Image img)
	{
		panel = new ImagePanel(img);
		getContentPane().add(panel, BorderLayout.CENTER);
		this.setSize(img.getWidth(null), img.getHeight(null));
	}

	public void changeImage(Image img)
	{
		panel.setImage(img);
		panel.repaint();
	}
}
