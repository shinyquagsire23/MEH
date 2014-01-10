package org.zzl.minegaming.MEH;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;

import org.zzl.minegaming.MEH.MapElements.SpritesExitManager;

public class ExitPanel extends JPanel {
	int myIndex;
	JSpinner spinner;
	JSpinner spinner_1;
	
	void Load(SpritesExitManager mgr, int index){
		spinner.setValue(mgr.mapExits[index].bBank);
		spinner_1.setValue(mgr.mapExits[index].bMap);
	}
    void Save(SpritesExitManager mgr){
    	mgr.mapExits[myIndex].bBank = (Byte)spinner.getValue();
    	mgr.mapExits[myIndex].bMap = (Byte)spinner_1.getValue();
    }
	/**
	 * Create the panel.
	 */
	public ExitPanel(SpritesExitManager mgr, int index) {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		myIndex=index;
		setBorder(new TitledBorder(null, "Exit", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblDestBank = new JLabel("Dest Bank:");
		add(lblDestBank);
		
		spinner = new JSpinner();
		spinner.setPreferredSize(new Dimension(100, 30));
		add(spinner);
		
		JLabel lblDestMap = new JLabel("Dest Map:");
		add(lblDestMap);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Save(MainGUI.loadedMap.mapExitManager);
			}
		});
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(0, 0));
		add(horizontalStrut);
		
		spinner_1 = new JSpinner();
		spinner_1.setPreferredSize(new Dimension(100, 30));
		add(spinner_1);
		add(btnSave);
        Load(mgr, index);
	}

}
