package org.zzl.minegaming.MEH;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.MEH.MapElements.SpritesExitManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ExitPanel extends JPanel {
	private JTextField textField;
	int myIndex;
	void Load(SpritesExitManager mgr, int index){
		textField.setText(BitConverter.toHexString(mgr.mapExits[index].hLevel));
	}
    void Save(SpritesExitManager mgr){
    	mgr.mapExits[myIndex].hLevel = Integer.parseInt(textField.getText(), 16);
    }
	/**
	 * Create the panel.
	 */
	public ExitPanel(SpritesExitManager mgr, int index) {
		myIndex=index;
		setBorder(new TitledBorder(null, "Exit", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblDestMap = new JLabel("Dest Map:");
		add(lblDestMap);
		
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save(Map.mapExitManager);
			}
		});
		add(btnSave);
        Load(mgr, index);
	}

}
