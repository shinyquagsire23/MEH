package org.zzl.minegaming.MEH;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.SpritesExitManager;

public class ExitPanel extends JPanel {
	private JTextField textField;
	void Load(SpritesExitManager mgr, int index){
		textField.setText(BitConverter.toHexString(mgr.mapExits[index].hLevel));
	}
	/**
	 * Create the panel.
	 */
	public ExitPanel(SpritesExitManager mgr, int index) {
		setBorder(new TitledBorder(null, "Exit", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblDestMap = new JLabel("Dest Map:");
		add(lblDestMap);
		
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);
        Load(mgr, index);
	}

}
