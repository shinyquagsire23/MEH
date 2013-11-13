package org.zzl.minegaming.MEH;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.SpritesSignManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignPanel extends JPanel {
	private JTextField textField;
	int myIndex;
    void Load(SpritesSignManager mgr, int index){
    	textField.setText(BitConverter.toHexString((int) mgr.mapSigns[index].pScript));
    }
    void Save(SpritesSignManager mgr){
    	mgr.mapSigns[myIndex].pScript = Integer.parseInt(textField.getText(), 16);
    }
	/**
	 * Create the panel.
	 */
	public SignPanel(SpritesSignManager mgr, int index) {
		myIndex=index;
		setBorder(new TitledBorder(null, "Sign", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblScriptPointer = new JLabel("Script Pointer");
		add(lblScriptPointer);
		
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save(Map.mapSignManager);
			}
		});
		add(btnSave);
		Load(mgr, index);

	}

}
