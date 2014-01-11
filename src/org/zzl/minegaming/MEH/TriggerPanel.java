package org.zzl.minegaming.MEH;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.TriggerManager;
import org.zzl.minegaming.MEH.MapElements.Triggers;

public class TriggerPanel extends JPanel {

	private JTextField txtFlagValue;
	private JTextField txtScriptAddr;
	private JTextField txtFlagCheck;
	int myIndex;
    void Load(TriggerManager mgr, int index){
    	Triggers t=mgr.mapTriggers[index];
    	txtScriptAddr.setText(BitConverter.toHexString((int)t.pScript));
    	txtFlagValue.setText(BitConverter.toHexString(t.hFlagValue));
    	txtFlagCheck.setText(BitConverter.toHexString(t.hFlagCheck));
    }
    
    public void Save(TriggerManager mgr){
    	mgr.mapTriggers[myIndex].pScript= Integer.parseInt(txtScriptAddr.getText(), 16);
    	mgr.mapTriggers[myIndex].hFlagValue = Integer.parseInt(txtFlagValue.getText(), 16);
    	mgr.mapTriggers[myIndex].hFlagCheck = Integer.parseInt(txtFlagCheck.getText(), 16);
    }
	/**
	 * Create the panel.
	 */
	public TriggerPanel(TriggerManager mgr, int index) {
		myIndex=index;
		setBorder(new TitledBorder(null, "Triggers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblScriptAddr = new JLabel("Script Addr:");
		add(lblScriptAddr);
		
		txtScriptAddr = new JTextField();
		txtScriptAddr.setColumns(10);
		add(txtScriptAddr);
		
		JLabel lblFlagcheck = new JLabel("FlagCheck:");
		add(lblFlagcheck);
		
		txtFlagCheck = new JTextField();
		txtFlagCheck.setColumns(10);
		add(txtFlagCheck);
		
		JLabel lblNewLabel = new JLabel("Flag Value:");
		add(lblNewLabel);
		
		txtFlagValue = new JTextField();
		add(txtFlagValue);
		txtFlagValue.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			Save(MainGUI.loadedMap.mapTriggerManager);
			}
		});
		add(btnSave);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Flag Operations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel);
		panel.setLayout(null);
		
		JButton button = new JButton("New button");
		add(button);
		
		
        Load(mgr, index);
	}

}
