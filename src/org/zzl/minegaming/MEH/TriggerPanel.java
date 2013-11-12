package org.zzl.minegaming.MEH;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class TriggerPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Create the panel.
	 */
	public TriggerPanel() {
		setBorder(new TitledBorder(null, "Triggers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblScriptAddr = new JLabel("Script Addr:");
		add(lblScriptAddr);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		add(textField_3);
		
		JLabel lblFlagcheck = new JLabel("FlagCheck:");
		add(lblFlagcheck);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		add(textField_4);
		
		JLabel lblNewLabel = new JLabel("Flag Value:");
		add(lblNewLabel);
		
		textField_2 = new JTextField();
		add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		add(btnSave);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Flag Operations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel);
		panel.setLayout(null);
		
		JLabel lblFlag = new JLabel("Check");
		lblFlag.setBounds(6, 19, 29, 14);
		panel.add(lblFlag);
		lblFlag.setHorizontalAlignment(SwingConstants.LEFT);
		
		textField = new JTextField();
		textField.setBounds(35, 16, 86, 20);
		panel.add(textField);
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setColumns(10);
		
		JLabel lblValue = new JLabel("Value");
		lblValue.setBounds(121, 19, 26, 14);
		lblValue.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblValue);
		
		textField_1 = new JTextField();
		textField_1.setBounds(147, 16, 86, 20);
		textField_1.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_1.setColumns(10);
		panel.add(textField_1);

	}

}
