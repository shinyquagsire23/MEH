package us.plxhack.MEH.UI;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.Lz77;
import org.zzl.minegaming.GBAUtils.ROMManager;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class WizardPatcher extends JFrame
{
	private JTextField txtFreespace;
	private JTextField txtStatusByte;
	final JLabel lblError;
	final JButton btnPatch;
	final JButton btnCancel;
	
	private int frRTCHookLoc = 0x4B0;
	private int frMapHookLoc = 0x0598CC;
	private int frNPCHookLoc = 0x083598;
	private int frBattleHookLoc = 0x00F290;	
	
	private int frWizardSize = 0x1200; //0x1000 plus some more in case of bugfixes
	
	private int emLevelLoc = 0x0B4C76;
	private int emPkmnLoc = 0x0B500A;
	
	private boolean successfulPatch = false;
	public WizardPatcher()
	{		
		setResizable(false);
		setTitle("Wizard D/N System Patcher");
		this.setSize(371, 237);
		getContentPane().setLayout(null);
		
		btnPatch = new JButton("Patch!");
		btnPatch.setBounds(271, 174, 81, 25);
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
		btnCancel.setBounds(178, 174, 81, 25);
		getContentPane().add(btnCancel);
		
		JLabel lblThisDialogWill = new JLabel("<html><center>This dialog will patch in support for Wizard. If you aren't sure what values to use just leave them as the defaults as they are compatible with Primal's DNS system.</center></html>");
		lblThisDialogWill.setBounds(12, 12, 340, 60);
		getContentPane().add(lblThisDialogWill);
		
		txtFreespace = new JTextField();
		txtFreespace.setBounds(159, 94, 114, 19);
		getContentPane().add(txtFreespace);
		txtFreespace.setColumns(10);
		
		JLabel lblFreespaceLocation = new JLabel("Freespace Location:");
		lblFreespaceLocation.setBounds(12, 96, 144, 15);
		getContentPane().add(lblFreespaceLocation);
		
		JLabel lblStatusByteLocation = new JLabel("Status Byte RAM:");
		lblStatusByteLocation.setBounds(12, 123, 144, 15);
		getContentPane().add(lblStatusByteLocation);
		
		txtStatusByte = new JTextField();
		txtStatusByte.setText("0203C000");
		txtStatusByte.setBounds(159, 125, 114, 19);
		getContentPane().add(txtStatusByte);
		txtStatusByte.setColumns(10);
		
		lblError = new JLabel("");
		lblError.setBounds(22, 142, 119, 69);
		getContentPane().add(lblError);
		
		JLabel lblBytes = new JLabel("(0x108 bytes)");
		lblBytes.setFont(new Font("Dialog", Font.BOLD, 10));
		lblBytes.setBounds(280, 97, 80, 15);
		getContentPane().add(lblBytes);
		
		btnPatch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println(successfulPatch);
				if(successfulPatch || btnPatch.getText().toLowerCase().equals("Close"))
				{
					setVisible(false);
					dispose();
					return;
				}
				
				int freespace;
				int status;
				try
				{
					freespace = Integer.parseInt(txtFreespace.getText(),16);
				}
				catch(Exception ex)
				{
					lblError.setForeground(Color.RED);
					lblError.setText("<html><center>Invalid Freespace Specified!</center></html>");
					return;
				}
				
				try
				{
					status = Integer.parseInt(txtStatusByte.getText(),16);
				}
				catch(Exception ex)
				{
					lblError.setForeground(Color.RED);
					lblError.setText("<html><center>Invalid Status RAM Specified!</center></html>");
					return;
				}
				
				if(patchROM(freespace, status))
				{
					lblError.setForeground(MainGUI.uiSettings.cursorColor);
					lblError.setText("<html><center>Success!</center></html>");
					btnCancel.setVisible(false);
					btnPatch.setText("Close");
					txtFreespace.setEditable(false);
					txtStatusByte.setEditable(false);
					successfulPatch = true;
				}
			}
		});
		
		if(ROMManager.getActiveROM() == null)
		{
			this.setVisible(false);
			this.dispose();
		}
		else
		{
			txtFreespace.setText("" + Integer.toHexString(ROMManager.getActiveROM().findFreespace(0x108, (int)DataStore.FreespaceStart, true)));
			if(ROMManager.getActiveROM().getGameCode().equalsIgnoreCase("BPRE"))
				lblBytes.setText("(0x" + Integer.toHexString(frWizardSize) + " bytes)");
			else
				lblBytes.setText("(0x" + Integer.toHexString(9001) + " bytes)");
		}
	}
	
	public boolean successful()
	{
		return successfulPatch;
	}
	
	private boolean patchROM(int freespace, int status)
	{
		if(!ROMManager.getActiveROM().getGameCode().equalsIgnoreCase("BPRE"))// & !ROMManager.getActiveROM().getGameCode().equalsIgnoreCase("BPEE"))
		{
			lblError.setForeground(Color.RED);
			lblError.setText("<html><center>Invalid ROM!<br/>Only Fire Red is currently supported!</center></html>");
			
			btnCancel.setVisible(false);
			btnPatch.setText("Close");
			txtFreespace.setEditable(false);
			txtStatusByte.setEditable(false);
			successfulPatch = true;
			return false;
		}
		if((freespace & 0xF) != 0x0 && (freespace & 0xF) != 0x4 && (freespace & 0xF) != 0x8 && (freespace & 0xF) != 0xC)
		{
			lblError.setForeground(Color.RED);
			lblError.setText("<html><center>Freespace offset must end in 0, 4, 8, or C!</center></html>");
			return false;
		}
		if (ROMManager.getActiveROM().getGameCode().equalsIgnoreCase("BPRE"))
		{
			byte frRTCHook[] = {
					(byte)0x00, (byte)0xB5, (byte)0x01, (byte)0x48, (byte)0x00, (byte)0x47, (byte)0x00, (byte)0x00, (byte)0x0, (byte)0x0, (byte)0x0, (byte)0x0, (byte)0x00, (byte)0x00, (byte)0x10, (byte)0xBC
			};
			byte[] frRTC;
			byte[] frMapTint;
			byte[] frMapHook;
			byte[] frNPCTint;
			byte[] frNPCHook;
			byte[] frBattle;
			byte[] frBattleHook;
			
			int[] rtcReps;
			int[] mapReps;
			int[] mapHookReps;
			int[] npcReps;
			int[] npcHookReps;
			int[] battleReps;
			int[] battleHookReps;
			
			try
			{
				frRTC = getFileBytes("binaries/rtc.bin");
				frMapTint = getFileBytes("binaries/maptint.bin");
				frMapHook = getFileBytes("binaries/maphook.bin");
				frNPCTint = getFileBytes("binaries/npctint.bin");
				frNPCHook = getFileBytes("binaries/npchook.bin");
				frBattle = getFileBytes("binaries/battlebg.bin");
				frBattleHook = getFileBytes("binaries/battlebghook.bin");
				
				rtcReps = getReps("binaries/rtc.rep");
				mapReps = getReps("binaries/maptint.rep");
				mapHookReps = getReps("binaries/maphook.rep");
				npcReps = getReps("binaries/npctint.rep");
				npcHookReps = getReps("binaries/npchook.rep");
				battleReps = getReps("binaries/battlebg.rep");
				battleHookReps = getReps("binaries/battlebghook.rep");
			}
			catch(Exception e)
			{
				File f = new File("binaries/");
				lblError.setForeground(Color.RED);
				lblError.setText("<html><center>Error loading binaries!<br/>One or more binaries from directory" + f.getAbsolutePath() + " failed to load!</center></html>");
				System.out.println("Error loading binaries!\nOne or more binaries from directory" + f.getAbsolutePath() + " failed to load!");
				return false;
			}
			frRTCHook = addToPointer(frRTCHook, freespace + 0x08000000+ 1, 0x8);
			for(int i : rtcReps)
				frRTC = addToPointer(frRTC, freespace + 0x08000000, i);
			
			for(int i : mapReps)
				frMapTint = addToPointer(frMapTint, freespace + 0x480, i);
			for(int i : mapHookReps)
				frMapHook = addToPointer(frMapHook, freespace + 0x08000000 + 0x480 + 1, i);
			
			for(int i : npcReps)
				frNPCTint = addToPointer(frNPCTint, freespace + 0x480 + 0x780, i);
			for(int i : npcHookReps)
				frNPCHook = addToPointer(frNPCHook, freespace + 0x08000000 + 0x480 + 0x780 + 1, i);
			
			for(int i : battleReps)
				frBattle = addToPointer(frBattle, freespace + 0x08000000 + 0x480 + 0x780 + 0x5C0, i);
			for(int i : battleHookReps)
				frBattleHook = addToPointer(frBattleHook, freespace + 0x08000000 + 0x480 + 0x780 + 0x5C0 + 1, i);

			ROMManager.getActiveROM().writeBytes(freespace, frRTC);
			ROMManager.getActiveROM().writeBytes(frRTCHookLoc, frRTCHook);
			ROMManager.getActiveROM().writeBytes(freespace+0x480, frMapTint);
			ROMManager.getActiveROM().writeBytes(frMapHookLoc, frMapHook);
			ROMManager.getActiveROM().writeBytes(freespace+0x480+0x780, frNPCTint);
			ROMManager.getActiveROM().writeBytes(frNPCHookLoc, frNPCHook);
			ROMManager.getActiveROM().writeBytes(freespace+0x480+0x780+0x5C0, frBattle);
			ROMManager.getActiveROM().writeBytes(frBattleHookLoc, frBattleHook);
			
			//Null out some stuffs
			ROMManager.getActiveROM().writeByte((byte) 0x0, 0x059A28);
			ROMManager.getActiveROM().writeByte((byte) 0x0, 0x059A29);
			ROMManager.getActiveROM().writeByte((byte) 0x0, 0x059A2A);
			ROMManager.getActiveROM().writeByte((byte) 0x0, 0x059A2B);
			ROMManager.getActiveROM().writeByte((byte) 0x0, 0x059A2C);
			ROMManager.getActiveROM().writeByte((byte) 0x0, 0x059A2D);
			ROMManager.getActiveROM().writeByte((byte) 0x0, 0x059A2E);
			ROMManager.getActiveROM().writeByte((byte) 0x0, 0x059A2F);
			
			ROMManager.getActiveROM().writeByte((byte) 0x0, 0x059A12);
			ROMManager.getActiveROM().writeByte((byte) 0x0, 0x059A13);
			
			//Battle BG decompression and array lineup
			int battleBGs = ROMManager.getActiveROM().getPointerAsInt(0xF2A0);
			battleBGs += 0x10;
			int numBGs = ROMManager.getActiveROM().readByteAsInt(0xF266);
			for(int i = 0; i < numBGs; i++)
			{
				int palette = ROMManager.getActiveROM().getPointerAsInt(battleBGs + (i*0x14));
				try
				{
					byte[] palArr = BitConverter.toBytes(Lz77.decompressLZ77(ROMManager.getActiveROM(), palette));
					int freespacePal = ROMManager.getActiveROM().findFreespace(0x80, (int) DataStore.FreespaceStart, true);
					ROMManager.getActiveROM().writePointer(freespacePal, battleBGs + (i*0x14));
					ROMManager.getActiveROM().Seek(freespacePal);
					ROMManager.getActiveROM().writeBytes(palArr);
					ROMManager.getActiveROM().writeBytes(palArr);
					ROMManager.getActiveROM().writeBytes(palArr);
					ROMManager.getActiveROM().writeBytes(palArr);
				}
				catch(Exception e){}
			}
			
			ROMManager.getActiveROM().updateFlags();
		}
		else if(ROMManager.getActiveROM().getGameCode().equalsIgnoreCase("BPEE"))
		{
			
		}
		
		return true;
	}
	
	//Adds an offset to a 32 bit pointer at a location
	private byte[] addToPointer(byte[] array, long pointer, int offset)
	{
		return BitConverter.PutBytes(array, BitConverter.ReverseBytes(BitConverter.GetBytes(pointer + BitConverter.ToInt32(BitConverter.GrabBytes(array, offset, 4)))), offset);
	}
	
	private byte[] getFileBytes(String filename) throws IOException
	{
		File file = new File(filename);

		InputStream is = new FileInputStream(file);
		long length = file.length();
		byte[] bytes = new byte[(int) length];
		int offset = 0, n = 0;
		while (offset < bytes.length
				&& (n = is.read(bytes, offset, bytes.length - offset)) >= 0)
		{
			offset += n;
		}
		is.close();
		return bytes;
	}
	
	private int[] getReps(String filename) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
		String line;
		ArrayList<Integer> offsets = new ArrayList<Integer>();
		while ((line = br.readLine()) != null) 
		{
		   offsets.add(Integer.parseInt(line, 16));
		}
		br.close();
		int[] array = new int[offsets.size()];
		for(int i = 0; i < offsets.size(); i++)
			array[i] = offsets.get(i);
		return array;
	}
}
