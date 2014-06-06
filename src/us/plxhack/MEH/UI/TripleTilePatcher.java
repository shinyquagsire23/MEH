package us.plxhack.MEH.UI;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.ROMManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TripleTilePatcher extends JFrame
{
	final JLabel lblError;
	final JButton btnPatch;
	final JButton btnCancel;
	
	byte frPatch[] = {
			 (byte)0x10, (byte)0xB5, (byte)0x0C, (byte)0x1C, (byte)0x12, (byte)0x04, (byte)0x12, (byte)0x0C,
			 (byte)0x01, (byte)0x28, (byte)0x32, (byte)0xD0, (byte)0x01, (byte)0x28, (byte)0x02, (byte)0xDC,
			 (byte)0x00, (byte)0x28, (byte)0x38, (byte)0xD0, (byte)0x64, (byte)0xE0, (byte)0x3D, (byte)0x48,
			 (byte)0x00, (byte)0xF0, (byte)0x3F, (byte)0xF8, (byte)0x3A, (byte)0x48, (byte)0x00, (byte)0xF0,
			 (byte)0x49, (byte)0xF8, (byte)0x04, (byte)0xB4, (byte)0x30, (byte)0x1C, (byte)0x39, (byte)0x1C,
			 (byte)0x3A, (byte)0x4A, (byte)0x00, (byte)0xF0, (byte)0x69, (byte)0xF8, (byte)0x02, (byte)0x1C,
			 (byte)0x3A, (byte)0x49, (byte)0x8A, (byte)0x42, (byte)0x02, (byte)0xDD, (byte)0x01, (byte)0x31,
			 (byte)0x52, (byte)0x1A, (byte)0x04, (byte)0x21, (byte)0x10, (byte)0x31, (byte)0x36, (byte)0x48,
			 (byte)0x00, (byte)0x68, (byte)0x03, (byte)0x69, (byte)0x45, (byte)0x69, (byte)0x40, (byte)0x58,
			 (byte)0x40, (byte)0x69, (byte)0x91, (byte)0x00, (byte)0x40, (byte)0x58, (byte)0x01, (byte)0x02,
			 (byte)0x89, (byte)0x0D, (byte)0x32, (byte)0x48, (byte)0x81, (byte)0x42, (byte)0x00, (byte)0xDC,
			 (byte)0x02, (byte)0xE0, (byte)0x01, (byte)0x30, (byte)0x09, (byte)0x1A, (byte)0x2B, (byte)0x1C,
			 (byte)0xDC, (byte)0x68, (byte)0x09, (byte)0x01, (byte)0x0C, (byte)0x19, (byte)0x04, (byte)0xBC,
			 (byte)0x26, (byte)0x48, (byte)0x53, (byte)0x00, (byte)0x00, (byte)0xF0, (byte)0x22, (byte)0xF8,
			 (byte)0x36, (byte)0xE0, (byte)0x26, (byte)0x48, (byte)0x00, (byte)0xF0, (byte)0x11, (byte)0xF8,
			 (byte)0x23, (byte)0x48, (byte)0x00, (byte)0xF0, (byte)0x1B, (byte)0xF8, (byte)0x21, (byte)0x48,
			 (byte)0x00, (byte)0xF0, (byte)0x24, (byte)0xF8, (byte)0x2C, (byte)0xE0, (byte)0x20, (byte)0x48,
			 (byte)0x00, (byte)0xF0, (byte)0x07, (byte)0xF8, (byte)0x1D, (byte)0x48, (byte)0x00, (byte)0xF0,
			 (byte)0x11, (byte)0xF8, (byte)0x1E, (byte)0x48, (byte)0x00, (byte)0xF0, (byte)0x1A, (byte)0xF8,
			 (byte)0x22, (byte)0xE0, (byte)0x00, (byte)0x68, (byte)0x53, (byte)0x00, (byte)0x18, (byte)0x18,
			 (byte)0x21, (byte)0x88, (byte)0x01, (byte)0x80, (byte)0x61, (byte)0x88, (byte)0x41, (byte)0x80,
			 (byte)0x40, (byte)0x30, (byte)0xA1, (byte)0x88, (byte)0x01, (byte)0x80, (byte)0xE1, (byte)0x88,
			 (byte)0x41, (byte)0x80, (byte)0x70, (byte)0x47, (byte)0x00, (byte)0x68, (byte)0x18, (byte)0x18,
			 (byte)0x21, (byte)0x89, (byte)0x01, (byte)0x80, (byte)0x61, (byte)0x89, (byte)0x41, (byte)0x80,
			 (byte)0x40, (byte)0x30, (byte)0xA1, (byte)0x89, (byte)0x01, (byte)0x80, (byte)0xE1, (byte)0x89,
			 (byte)0x41, (byte)0x80, (byte)0x70, (byte)0x47, (byte)0x00, (byte)0x68, (byte)0x53, (byte)0x00,
			 (byte)0x18, (byte)0x18, (byte)0x00, (byte)0x22, (byte)0x02, (byte)0x80, (byte)0x42, (byte)0x80,
			 (byte)0x40, (byte)0x30, (byte)0x02, (byte)0x80, (byte)0x42, (byte)0x80, (byte)0x70, (byte)0x47,
			 (byte)0x01, (byte)0x20, (byte)0x0B, (byte)0x49, (byte)0x00, (byte)0xF0, (byte)0x0B, (byte)0xF8,
			 (byte)0x02, (byte)0x20, (byte)0x09, (byte)0x49, (byte)0x00, (byte)0xF0, (byte)0x07, (byte)0xF8,
			 (byte)0x03, (byte)0x20, (byte)0x07, (byte)0x49, (byte)0x00, (byte)0xF0, (byte)0x03, (byte)0xF8,
			 (byte)0x10, (byte)0xBC, (byte)0x01, (byte)0xBC, (byte)0x00, (byte)0x47, (byte)0x08, (byte)0x47,
			 (byte)0x10, (byte)0x47, (byte)0xC0, (byte)0x46, (byte)0x18, (byte)0x50, (byte)0x00, (byte)0x03,
			 (byte)0x14, (byte)0x50, (byte)0x00, (byte)0x03, (byte)0x1C, (byte)0x50, (byte)0x00, (byte)0x03,
			 (byte)0xA5, (byte)0x67, (byte)0x0F, (byte)0x08, (byte)0x49, (byte)0x8E, (byte)0x05, (byte)0x08,
			 (byte)0xFC, (byte)0x6D, (byte)0x03, (byte)0x02, (byte)0x7F, (byte)0x02, (byte)0x00, (byte)0x00,
			 (byte)0x42
		};
	private int frLoc = 0x05A9B4;

	private boolean successfulPatch = false;
	public TripleTilePatcher()
	{		
		setResizable(false);
		setTitle("Day/Night Pokemon Patcher");
		this.setSize(371, 150);
		getContentPane().setLayout(null);
		
		btnPatch = new JButton("Patch!");
		btnPatch.setBounds(271, 113, 81, 25);
		getContentPane().add(btnPatch);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				setVisible(false);
				dispose();
			}
		});
		btnCancel.setBounds(178, 113, 81, 25);
		getContentPane().add(btnCancel);
		
		JLabel lblThisDialogWill = new JLabel("<html><center>This dialog will patch in support for reference-based triple layer tiles in Fire Red. Since this is an optimised rewrite of the existing renderer, no free space is needed and the routine will be written over the existing renderer.</center></html>");
		lblThisDialogWill.setBounds(12, 30, 340, 60);
		getContentPane().add(lblThisDialogWill);
		
		lblError = new JLabel("");
		lblError.setBounds(22, 142, 119, 69);
		getContentPane().add(lblError);
		
		btnPatch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(successfulPatch)
				{
					setVisible(false);
					dispose();
				}
				
				if(patchROM())
				{
					lblError.setForeground(MainGUI.uiSettings.cursorColor);
					lblError.setText("<html><center>Success!</center></html>");
					btnCancel.setVisible(false);
					btnPatch.setText("Close");
					successfulPatch = true;
				}
			}
		});
		
		if(ROMManager.getActiveROM() == null)
		{
			this.setVisible(false);
			this.dispose();
		}
	}
	
	public boolean successful()
	{
		return successfulPatch;
	}
	
	private boolean patchROM()
	{
		if(!ROMManager.getActiveROM().getGameCode().equalsIgnoreCase("BPRE"))
		{
			lblError.setForeground(Color.RED);
			lblError.setText("<html><center>Invalid ROM!<br/>Only Fire Red is currently supported!</center></html>");
			
			btnCancel.setVisible(false);
			btnPatch.setText("Close");
			successfulPatch = true;
			return false;
		}
		if (ROMManager.getActiveROM().getGameCode().equalsIgnoreCase("BPRE"))
		{
			ROMManager.getActiveROM().writeBytes(frLoc, frPatch);
			ROMManager.getActiveROM().updateFlags();
		}
		
		return true;
	}
}
