package org.zzl.minegaming.MEH;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JSpinner;
import java.awt.Dimension;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConnectionAddGUI extends JFrame
{
	PanelMapPreview panelMap;
	final JSpinner spinnerBank;
	public boolean hasResult;
	public int bank = 0;
	public int map = 0;
	ConnectionType cType;
	
	public ConnectionAddGUI(ConnectionType c) 
	{
		cType = c;
		setSize(new Dimension(450, 300));
		setTitle("Choose a map...");
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bank: ");
		lblNewLabel.setBounds(12, 14, 61, 15);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Map: ");
		lblNewLabel_1.setBounds(122, 14, 61, 15);
		getContentPane().add(lblNewLabel_1);
		
		final JSpinner spinnerMap = new JSpinner();
		spinnerMap.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				map = (Integer)spinnerMap.getValue();
				changePreview(bank,map);
			}
		});
		spinnerMap.setModel(new SpinnerNumberModel(0, 0, 0, 1));
		spinnerMap.setPreferredSize(new Dimension(28, 24));
		spinnerMap.setBounds(157, 12, 50, 20);
		getContentPane().add(spinnerMap);
		
		spinnerBank = new JSpinner();
		spinnerBank.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				spinnerMap.setModel(new SpinnerNumberModel(0, 0, DataStore.MapBankSize[(Integer)spinnerBank.getValue()], 1));
				int i = (Integer)spinnerMap.getValue();
				if(i > DataStore.MapBankSize[(Integer)spinnerBank.getValue()])
					spinnerMap.setValue(DataStore.MapBankSize[(Integer)spinnerBank.getValue()]-1);
				
				//map = (Integer)spinnerMap.getValue();
				bank = (Integer)spinnerBank.getValue();
				changePreview(bank,map);
			}
		});
		spinnerBank.setModel(new SpinnerNumberModel(0, 0, DataStore.MapBankSize.length, 1));
		spinnerBank.setBounds(50, 12, 50, 20);
		getContentPane().add(spinnerBank);
		
		panelMap = new PanelMapPreview();
		panelMap.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Map Preview", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelMap.setBounds(12, 41, 406, 176);
		getContentPane().add(panelMap);
		
		JButton btnNewButton = new JButton("Add Connection");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				map = (Integer)spinnerMap.getValue();
				bank = (Integer)spinnerBank.getValue();
				MainGUI.loadedMap.mapConnections.addConnection(cType, (byte)bank, (byte)map);
			}
		});
		btnNewButton.setBounds(284, 224, 134, 25);
		getContentPane().add(btnNewButton);
	}
	
	public void changePreview(int bank, int map)
	{
		panelMap.setMap(bank, map);
		panelMap.repaint();
	}
}
