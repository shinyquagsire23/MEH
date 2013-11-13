package org.zzl.minegaming.MEH;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.TriggerManager;
import org.zzl.minegaming.MEH.MapElements.Triggers;

public class TriggerPanel extends JPanel {

	private JTextField txtFlagValue;
	private JTextField txtScriptAddr;
	private JTextField txtFlagCheck;
    void Load(TriggerManager mgr, int index){
    	Triggers t=mgr.mapTriggers[index];
    	txtScriptAddr.setText(BitConverter.toHexString((int)t.pScript));
    	txtFlagValue.setText(BitConverter.toHexString((int)t.hFlagValue));
    	txtFlagCheck.setText(BitConverter.toHexString((int)t.hFlagCheck));
    }
	/**
	 * Create the panel.
	 */
	public TriggerPanel(TriggerManager mgr, int index) {
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
		add(btnSave);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Flag Operations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel);
		panel.setLayout(null);
		
		
        Load(mgr, index);
	}

}
