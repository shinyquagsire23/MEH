package org.zzl.minegaming.MEH;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SignPanel extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public SignPanel() {
		setBorder(new TitledBorder(null, "Sign", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblScriptPointer = new JLabel("Script Pointer");
		add(lblScriptPointer);
		
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);

	}

}
