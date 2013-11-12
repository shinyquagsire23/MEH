	package org.zzl.minegaming.MEH;

	import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

	import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.SpritesExit;
import org.zzl.minegaming.MEH.MapElements.SpritesNPC;
import org.zzl.minegaming.MEH.MapElements.SpritesNPCManager;
import org.zzl.minegaming.MEH.MapElements.SpritesSigns;
import org.zzl.minegaming.MEH.MapElements.Triggers;

public class EventEditor extends JPanel{
    
	JPanel paneNPCs;
	JPanel paneTriggers;
	JPanel paneScripts;
	JPanel paneExits;
	//NPC Controls
	JLabel lblNPCNum;
	JComboBox numNPCs;
	JLabel lblb1;
	JLabel lblSpriteSet;JTextField txtSpriteSet;
	JLabel lblb3;
	JLabel lblb4;
	JLabel lblbX;JTextField txtX;
	JLabel lblb6;
	JLabel lblbY;JTextField txtY;
	JLabel lblb8;
	JLabel lblb9;
	
	JLabel lblBehavior1;JTextField txtBehavior1;
	JLabel lblb10;
	
	JLabel lblBehavior2;JTextField txtBehavior2;
	JLabel lblIsTrainer;JCheckBox chkIsTrainer;
	JLabel lblb14;
	
	JLabel lblTrainerLOS;JTextField txtTrainerLOS;
	JLabel lblb16;
	JLabel lblScript;JTextField txtScript; 
	JLabel lbliFlag;JTextField txtiFlag;   
	JLabel lblb23;
	JLabel lblb24;
	void Load(SpritesNPCManager mgr){
		
	}
	EventEditor(){
		//4 "Layers"
		paneNPCs = new JPanel();
		
		paneNPCs.setLayout(null);
		lblNPCNum=new JLabel("Number of NPCs");
		lblNPCNum.setBounds(0,0,32,16);
		paneNPCs.add(lblNPCNum);
		
		
		this.add(paneNPCs);
	}
  
}
