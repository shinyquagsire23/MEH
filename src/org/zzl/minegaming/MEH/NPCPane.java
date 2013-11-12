	package org.zzl.minegaming.MEH;

	import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

	import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.SpritesExit;
import org.zzl.minegaming.MEH.MapElements.SpritesNPC;
import org.zzl.minegaming.MEH.MapElements.SpritesNPCManager;
import org.zzl.minegaming.MEH.MapElements.SpritesSigns;
import org.zzl.minegaming.MEH.MapElements.Triggers;
import javax.swing.JButton;

public class NPCPane extends JPanel{
    
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
    void CreateNPCPane(){
    	
    }
	@SuppressWarnings("deprecation")
	NPCPane(){
		//4 "Layers"
		LayoutManager mgr;
	
		paneNPCs=new JPanel();
		paneNPCs.setBounds(16, 16, 207, 179);
		paneNPCs.setLayout(null);
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "NPCs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 11, 187, 157);
		paneNPCs.add(panel_1);
		
		panel_1.setLayout(null);
		
		lblSpriteSet=new JLabel("Spriteset:");
		txtSpriteSet =new JTextField();
		lblSpriteSet.setBounds(10,38,lblSpriteSet.getText().length()*8,16);
		Rectangle r=lblSpriteSet.getBounds();
		txtSpriteSet.setBounds(90,38,87,16);
		txtSpriteSet.disable();
		
		
		lblBehavior1=new JLabel("Behavior1:");
		txtBehavior1 =new JTextField();
		lblBehavior1.setBounds(10,54,lblBehavior1.getText().length()*8,16);
		r=lblBehavior1.getBounds();
		txtBehavior1.setBounds(90,54,32,16);
		
		
		lblBehavior2=new JLabel("Behavior2:");
		txtBehavior2 =new JTextField();
		lblBehavior2.setBounds(10,70,lblBehavior2.getText().length()*8,16);
		r=lblBehavior2.getBounds();
		txtBehavior2.setBounds(90,70,64,16);
		
		
		lblIsTrainer=new JLabel("Is a Trainer:");
		chkIsTrainer =new JCheckBox();
		lblIsTrainer.setBounds(10,86,lblIsTrainer.getText().length()*8,16);
		r=lblIsTrainer.getBounds();
		chkIsTrainer.setBounds(114,86,32,16);
		
		
        lblTrainerLOS=new JLabel("TrainerLOS:");
		txtTrainerLOS =new JTextField();
		lblTrainerLOS.setBounds(10,102,lblTrainerLOS.getText().length()*8,16);
		r=lblTrainerLOS.getBounds();
		txtTrainerLOS.setBounds(98,102,32,16);

		
		
		
		
		
		panel_1.add(lblIsTrainer,BorderLayout.WEST);
		panel_1.add(chkIsTrainer,BorderLayout.EAST);
		panel_1.add(lblSpriteSet,BorderLayout.WEST);
		panel_1.add(txtSpriteSet,BorderLayout.EAST);
		panel_1.add(lblBehavior1,BorderLayout.WEST);
		panel_1.add(txtBehavior1,BorderLayout.EAST);
		panel_1.add(lblBehavior2,BorderLayout.WEST);
		panel_1.add(txtBehavior2,BorderLayout.EAST);
		panel_1.add(lblIsTrainer,BorderLayout.WEST);
		panel_1.add(chkIsTrainer,BorderLayout.EAST);
		panel_1.add(lblTrainerLOS,BorderLayout.WEST);
		panel_1.add(txtTrainerLOS,BorderLayout.EAST);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(54, 123, 89, 23);
		panel_1.add(btnSave);
	}
}
