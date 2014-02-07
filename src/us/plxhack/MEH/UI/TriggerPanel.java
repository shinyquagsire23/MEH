package us.plxhack.MEH.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.zzl.minegaming.GBAUtils.BitConverter;

import us.plxhack.MEH.IO.MapIO;
import us.plxhack.MEH.MapElements.TriggerManager;
import us.plxhack.MEH.MapElements.Trigger;

public class TriggerPanel extends JPanel
{

	private JTextField txtFlagValue;
	private JTextField txtScriptAddr;
	private JTextField txtFlagCheck;
	int myIndex;

	void Load(TriggerManager mgr, int index)
	{
		Trigger t = mgr.mapTriggers.get(index);
		txtScriptAddr.setText(BitConverter.toHexString((int) t.pScript));
		txtFlagValue.setText(BitConverter.toHexString(t.hFlagValue));
		txtFlagCheck.setText(BitConverter.toHexString(t.hFlagCheck));
	}

	public void Save(TriggerManager mgr)
	{
		mgr.mapTriggers.get(myIndex).pScript = Integer.parseInt(txtScriptAddr.getText(), 16);
		mgr.mapTriggers.get(myIndex).hFlagValue = Integer.parseInt(txtFlagValue.getText(), 16);
		mgr.mapTriggers.get(myIndex).hFlagCheck = Integer.parseInt(txtFlagCheck.getText(), 16);
	}

	/**
	 * Create the panel.
	 */
	public TriggerPanel(TriggerManager mgr, int index)
	{
		myIndex = index;
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
		btnSave.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				Save(MapIO.loadedMap.mapTriggerManager);
			}
		});
		add(btnSave);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Flag Operations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel);
		panel.setLayout(null);

		JButton button = new JButton("New button");
		add(button);

		Load(mgr, index);
	}

}
