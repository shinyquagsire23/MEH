package org.zzl.minegaming.MEH;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

public class ExitPanel extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public ExitPanel() {
		setBorder(new TitledBorder(null, "Exit", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblDestMap = new JLabel("Dest Map:");
		add(lblDestMap);
		
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);

	}

}
