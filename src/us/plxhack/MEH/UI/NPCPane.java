	package us.plxhack.MEH.UI;

    import org.zzl.minegaming.GBAUtils.BitConverter;
    import us.plxhack.MEH.IO.MapIO;
    import us.plxhack.MEH.MapElements.SpriteNPC;
    import us.plxhack.MEH.MapElements.SpritesNPCManager;

    import javax.swing.*;
    import javax.swing.border.TitledBorder;
    import javax.swing.event.ChangeEvent;
    import javax.swing.event.ChangeListener;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;

public class NPCPane extends JPanel{

	//NPC Controls
	JLabel lblNPCNum;
	JComboBox numNPCs;
	JLabel lblb1;
	JLabel lblSpriteSet;JSpinner txtSpriteSet;
	JLabel lblb3;
	JLabel lblb4;
	JLabel lblbX;JTextField txtX;
	JLabel lblb6;
	JLabel lblbY;JTextField txtY;
	JLabel lblb8;
	JLabel lblb9;
	
	JLabel lblBehavior1;JTextField txtBehavior1;
	JLabel lblb10;
	
	JLabel lblBehavior2;JTextField txtBehavior2;
	JLabel lblIsTrainer;JCheckBox chkIsTrainer;
	JLabel lblb14;
	
	JLabel lblTrainerLOS;JTextField txtTrainerLOS;
	JLabel lblb16;
	JLabel lblScript;JTextField txtScript; 
	JLabel lbliFlag;JTextField txtiFlag;   
	JLabel lblb23;
	JLabel lblb24;
	int myIndex;
	
	private JTextField textField;
	private JTextField textField_1;
	void Load(SpritesNPCManager mgr, int NPCIndex){
		SpriteNPC t = mgr.mapNPCs.get(NPCIndex);
		txtSpriteSet.setValue(t.bSpriteSet & 0xFF);
		txtBehavior1.setText(Byte.toString(t.bBehavior1));
		txtBehavior2.setText(Byte.toString(t.bBehavior2));
		chkIsTrainer.setSelected(t.bIsTrainer==1);
		txtTrainerLOS.setText(Byte.toString(t.bTrainerLOS));
		txtScript.setText(String.format("%X", ((int)t.pScript)));
		txtiFlag.setText(BitConverter.toHexString(t.iFlag));
	}
	void Save(SpritesNPCManager mgr){
		try
		{
		SpriteNPC t = mgr.mapNPCs.get(myIndex);
		mgr.mapNPCs.get(myIndex).bSpriteSet = (byte)((int)((Integer)txtSpriteSet.getValue()));
		mgr.mapNPCs.get(myIndex).bBehavior1 = Byte.parseByte(txtBehavior1.getText());
		mgr.mapNPCs.get(myIndex).bBehavior2 = Byte.parseByte(txtBehavior2.getText());
		mgr.mapNPCs.get(myIndex).bIsTrainer = (byte) (chkIsTrainer.isSelected() ? 1:0);
		
		mgr.mapNPCs.get(myIndex).bTrainerLOS= Byte.parseByte(txtTrainerLOS.getText());
		mgr.mapNPCs.get(myIndex).pScript= Integer.parseInt(txtScript.getText(), 16);
		mgr.mapNPCs.get(myIndex).iFlag = Integer.parseInt(txtiFlag.getText());
		}
		catch(Exception e){}
		MainGUI.eventEditorPanel.Redraw = true;
		MainGUI.eventEditorPanel.repaint();
	}
	
    void CreateNPCPane(){
    	
    }
	@SuppressWarnings("deprecation")
	NPCPane(SpritesNPCManager mgr, int NPCIndex){
		myIndex=NPCIndex;
		setBorder(new TitledBorder(null, "NPC", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		
		lblSpriteSet=new JLabel("Spriteset:");
		add(lblSpriteSet);
		lblSpriteSet.setBounds(10,38,lblSpriteSet.getText().length()*8,16);
		Rectangle r=lblSpriteSet.getBounds();
		txtSpriteSet =new JSpinner(new SpinnerNumberModel(0,0,255,1));
		txtSpriteSet.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				Save(MapIO.loadedMap.mapNPCManager);
			}
		});
		add(txtSpriteSet);
		txtSpriteSet.setBounds(90,38,87,16);
		//txtSpriteSet.disable();
		
		
		lblBehavior1=new JLabel("Behavior1:");
		add(lblBehavior1);
		lblBehavior1.setBounds(10,54,lblBehavior1.getText().length()*8,16);
		r=lblBehavior1.getBounds();
		txtBehavior1 =new JTextField();
		txtBehavior1.setColumns(2);
		add(txtBehavior1);
		txtBehavior1.setBounds(90,54,32,16);
		
		
		lblBehavior2=new JLabel("Behavior2:");
		add(lblBehavior2);
		lblBehavior2.setBounds(10,70,lblBehavior2.getText().length()*8,16);
		r=lblBehavior2.getBounds();
		txtBehavior2 =new JTextField();
		txtBehavior2.setColumns(2);
		add(txtBehavior2);
		txtBehavior2.setBounds(90,70,64,16);
		
		
		lblIsTrainer=new JLabel("Is a Trainer:");
		add(lblIsTrainer);
		lblIsTrainer.setBounds(10,86,lblIsTrainer.getText().length()*8,16);
		r=lblIsTrainer.getBounds();
        chkIsTrainer =new JCheckBox();
        add(chkIsTrainer);
        chkIsTrainer.setBounds(114,86,32,16);
		
		
        lblTrainerLOS=new JLabel("TrainerLOS:");
        add(lblTrainerLOS);
        lblTrainerLOS.setBounds(10,102,lblTrainerLOS.getText().length()*8,16);
        r=lblTrainerLOS.getBounds();
		txtTrainerLOS =new JTextField();
		txtTrainerLOS.setColumns(3);
		add(txtTrainerLOS);
		txtTrainerLOS.setBounds(90,102,32,16);
		
		 lbliFlag = new JLabel("NPC Flag:");
		 add(lbliFlag);
		 lbliFlag.setBounds(10, 128, 88, 16);
		
		txtiFlag = new JTextField();
		txtiFlag.setColumns(4);
		add(txtiFlag);
		txtiFlag .setText("0");
		txtiFlag .setBounds(90, 128, 32, 16);
		
		lblScript = new JLabel("NPC Script Pointer:");
		add(lblScript);
		lblScript.setBounds(10, 150, 88, 16);
		
		txtScript = new JTextField();
		txtScript.setColumns(9);
		add(txtScript);
		txtScript.setText("0");
		txtScript.setBounds(90, 150, 32, 16);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				Save(MapIO.loadedMap.mapNPCManager);
			}
		});
		add(btnSave);
		btnSave.setBounds(55, 163, 89, 23);
		Load(mgr, NPCIndex);
	}
}
