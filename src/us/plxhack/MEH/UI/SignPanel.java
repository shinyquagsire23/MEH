package us.plxhack.MEH.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.zzl.minegaming.GBAUtils.BitConverter;

import us.plxhack.MEH.MapElements.SpritesSignManager;

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
		setLayout(null);
		
		JLabel lblScriptPointer = new JLabel("<html>Script Pointer:   <B style=\"color: green\">$</B><html>");
		lblScriptPointer.setBounds(12, 27, 127, 15);
		add(lblScriptPointer);
		
		textField = new JTextField();
		textField.setBounds(128, 23, 83, 26);
		add(textField);
		textField.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(12, 119, 68, 25);
		btnSave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Save(MainGUI.loadedMap.mapSignManager);
			}
		});
		add(btnSave);
		
		JButton btnOpenScript = new JButton("Open Script");
		btnOpenScript.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				MainGUI.openScript(Integer.parseInt(textField.getText(), 16));
			}
		});
		btnOpenScript.setBounds(56, 54, 142, 25);
		add(btnOpenScript);
		Load(mgr, index);

	}
}
