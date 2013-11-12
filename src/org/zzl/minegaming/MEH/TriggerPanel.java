package org.zzl.minegaming.MEH;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TriggerPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Create the panel.
	 */
	public TriggerPanel() {
		setBorder(new TitledBorder(null, "Triggers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblNewLabel = new JLabel("Script Address:");
		add(lblNewLabel);
		
		textField_2 = new JTextField();
		add(textField_2);
		textField_2.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Flag Operations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel);
		
		JLabel lblFlag = new JLabel("Check");
		panel.add(lblFlag);
		lblFlag.setHorizontalAlignment(SwingConstants.LEFT);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setColumns(10);
		
		JLabel lblValue = new JLabel("Value");
		lblValue.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblValue);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_1.setColumns(10);
		panel.add(textField_1);

	}

}
