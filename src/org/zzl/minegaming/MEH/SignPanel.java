package org.zzl.minegaming.MEH;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.SpritesSignManager;

public class SignPanel extends JPanel {
	private JTextField textField;
	
    void Load(SpritesSignManager mgr, int index){
    	textField.setText(BitConverter.toHexString((int) mgr.mapSigns[index].pScript));
    }
	/**
	 * Create the panel.
	 */
	public SignPanel(SpritesSignManager mgr, int index) {
		setBorder(new TitledBorder(null, "Sign", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblScriptPointer = new JLabel("Script Pointer");
		add(lblScriptPointer);
		
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);
		Load(mgr, index);

	}

}
