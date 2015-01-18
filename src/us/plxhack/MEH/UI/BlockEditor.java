package us.plxhack.MEH.UI;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JScrollPane;

import java.awt.Component;

import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;

import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JCheckBox;

import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBAImage;
import org.zzl.minegaming.GBAUtils.ImagePanel;
import org.zzl.minegaming.GBAUtils.Palette;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.zzl.minegaming.GBAUtils.Lz77;
import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.ROMManager;

import us.plxhack.MEH.IO.MapIO;
import us.plxhack.MEH.IO.Render.BlockRenderer.TripleType;

import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import java.awt.FlowLayout;

import javax.swing.JTextField;

public class BlockEditor extends JDialog
{
	TilesetPickerPanel tpp;
	JComboBox cmbBoxPalette;
	JScrollPane scrollPaneTEP;
	JScrollPane scrollPaneTiles;
	public static JLabel lblMeep;
	//static JLabel lblBehavior;
	ImagePanel panelSelectedBlock;
	ImagePanel panelThirdLayer;
	public static BlockEditorPanel blockEditorPanel;
	public static TripleEditorPanel tripleEditorPanel;
	boolean xFlip = false;
	boolean yFlip = false;
	TileEditorPanel tileEditorPanel;
	public static JTextField txtBehavior;
	
	private JRadioButton rdBgOver;
	private JRadioButton rdBgUnder;
	private JRadioButton rdBgTriple;
	private JRadioButton rdBattleNone;
	private JRadioButton rdBattleGrass;
	private JRadioButton rdBattleWater;
	private ButtonGroup battleGroup;
	private JComboBox cmbBehaviors;
	
	public BlockEditor(JFrame parent, String text, ModalityType modalityType) 
	{
		super(parent, text, modalityType);
		setResizable(false);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		// Block selector panel
		
		tileEditorPanel = new TileEditorPanel(false);
		tileEditorPanel.setPreferredSize(new Dimension((TileEditorPanel.editorWidth) * 16 + ((Integer)UIManager.get("ScrollBar.width")).intValue() + 2, ((DataStore.EngineVersion == 1 ? 0x200 + 0x56 : 0x200 + 0x300) / TileEditorPanel.editorWidth) * 16));
		tileEditorPanel.setSize(tileEditorPanel.getPreferredSize());
		tileEditorPanel.setLayout(null);
		
		tileEditorPanel.globalTiles = MainGUI.tileEditorPanel.globalTiles;
		tileEditorPanel.localTiles = MainGUI.tileEditorPanel.localTiles;
		tileEditorPanel.RerenderTiles(tileEditorPanel.imgBuffer, 0);
		
		scrollPaneTEP = new JScrollPane(tileEditorPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneTEP.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		scrollPaneTEP.getVerticalScrollBar().setUnitIncrement(16);
		
		JPanel rightOfBlockSelector = new JPanel();
//		rightOfBlockSelector.setPreferredSize(new Dimension(256, 10));
		rightOfBlockSelector.setLayout(new BorderLayout(5, 5));
		
/*		JSplitPane splitBlocksFromRight = new JSplitPane();
		splitBlocksFromRight.setEnabled(false);
		splitBlocksFromRight.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
//		splitBlocksFromRight.setPreferredSize(new Dimension(150, 10));
		splitBlocksFromRight.setBorder(null);
		
		splitBlocksFromRight.setLeftComponent(scrollPaneTEP);
		splitBlocksFromRight.setRightComponent(rightOfBlockSelector);
		getContentPane().add(splitBlocksFromRight, BorderLayout.WEST);*/
		
		getContentPane().add(scrollPaneTEP);
		getContentPane().add(rightOfBlockSelector);
		
		JPanel blockModifier = new JPanel();
		blockModifier.setPreferredSize(new Dimension(400,80));
//		blockModifier.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		rightOfBlockSelector.add(blockModifier, BorderLayout.NORTH);
		blockModifier.setLayout(null);
		
		lblMeep = new JLabel("Meep");
		lblMeep.setBounds(12, 12, 80, 15);
		blockModifier.add(lblMeep);
		
		cmbBoxPalette = new JComboBox();
		cmbBoxPalette.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				tpp.setPalette(cmbBoxPalette.getSelectedIndex());
			}
		});
		cmbBoxPalette.setModel(new DefaultComboBoxModel(new String[] {"Palette 0", "Palette 1", "Palette 2", "Palette 3", "Palette 4", "Palette 5", "Palette 6", "Palette 7", "Palette 8", "Palette 9", "Palette 10", "Palette 11", "Palette 12"}));
		cmbBoxPalette.setPreferredSize(new Dimension(36, 24));
		cmbBoxPalette.setBounds(10, 30, 107, 24);
		blockModifier.add(cmbBoxPalette);
		
		final JCheckBox chbxXFlip = new JCheckBox("X Flip");
		chbxXFlip.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				xFlip = chbxXFlip.isSelected();
				tpp.setFlip(xFlip, yFlip);
				tpp.fetchNewBlock();
			}
		});
		chbxXFlip.setPreferredSize(new Dimension(63, 16));
		chbxXFlip.setBounds(130, 12, 70, 23);
		blockModifier.add(chbxXFlip);
		
		final JCheckBox chbxYFlip = new JCheckBox("Y Flip");
		chbxYFlip.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				yFlip = chbxYFlip.isSelected();
				tpp.setFlip(xFlip, yFlip);
				tpp.fetchNewBlock();
			}
		});
		chbxYFlip.setPreferredSize(new Dimension(62, 16));
		chbxYFlip.setBounds(130, 31, 70, 23);
		blockModifier.add(chbxYFlip);
		
		panelSelectedBlock = new ImagePanel(null);
		panelSelectedBlock.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelSelectedBlock.setBounds(210, 16, 32, 32);
		blockModifier.add(panelSelectedBlock);
		
		JPanel tileSelectAndBehavior = new JPanel();
		tileSelectAndBehavior.setLayout(new BoxLayout(tileSelectAndBehavior, BoxLayout.X_AXIS));
		
		// Tile selector

		tpp = new TilesetPickerPanel(MapIO.blockRenderer.getGlobalTileset(),MapIO.blockRenderer.getLocalTileset(), this);
//		tpp.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tpp.setPreferredSize(new Dimension(256 + ((Integer)UIManager.get("ScrollBar.width")).intValue() + 5, 1024));
		
		scrollPaneTiles = new JScrollPane(tpp, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneTiles.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		scrollPaneTiles.getVerticalScrollBar().setUnitIncrement(16);
		
		blockEditorPanel = new BlockEditorPanel(this);
		blockEditorPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		blockEditorPanel.setPreferredSize(new Dimension(64, 32));
		blockEditorPanel.setBounds(300, 16, 64, 32);
		blockModifier.add(blockEditorPanel);
		
		tripleEditorPanel = new TripleEditorPanel(this);
		tripleEditorPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		tripleEditorPanel.setPreferredSize(new Dimension(20,20));
		tripleEditorPanel.setBounds(363, 28, 20, 20);
		blockModifier.add(tripleEditorPanel);
		
		JPanel behaviorModifier = new JPanel();
		behaviorModifier.setLayout(new BoxLayout(behaviorModifier, BoxLayout.Y_AXIS));
//		behaviorModifier.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
/*		JSplitPane splitTileAndBehavior = new JSplitPane();
		splitTileAndBehavior.setEnabled(false);
		splitTileAndBehavior.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
//		splitTileAndBehavior.setPreferredSize(new Dimension(150, 10));
		splitTileAndBehavior.setBorder(null);
		rightOfBlockSelector.add(splitTileAndBehavior, BorderLayout.WEST);
		
		splitTileAndBehavior.setLeftComponent(scrollPaneTiles);
		splitTileAndBehavior.setRightComponent(behaviorModifier);*/
		
		rightOfBlockSelector.add(tileSelectAndBehavior);
		tileSelectAndBehavior.add(scrollPaneTiles);
		tileSelectAndBehavior.add(Box.createRigidArea(new Dimension(3,0)));
		tileSelectAndBehavior.add(behaviorModifier);
		
		cmbBehaviors = new JComboBox();
		cmbBehaviors.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if(crappyWorkaround)
					return;
				
				if(txtBehavior.getText().equals(""))
					txtBehavior.setText(DataStore.EngineVersion == 1 ? "00000000" : "0000");
				
				long behavior = Long.parseLong(txtBehavior.getText(), 16);
				behavior &= (DataStore.EngineVersion == 1 ? 0xFFFFFE00 : 0xFF00);
				behavior |= cmbBehaviors.getSelectedIndex() & (DataStore.EngineVersion == 1 ? 0x1FF : 0xFF);
				txtBehavior.setText(String.format("%08X", behavior));
			}
		});
		
		String[] behaviorItems = new String[DataStore.EngineVersion == 0x1 ? 0x200 : 0x100];
		behaviorItems[0] = "meep";
		for(int i = 0; i < (DataStore.EngineVersion == 0x1 ? 0x200 : 0x100); i++)
		{
				behaviorItems[i] = DataStore.getBehaviorString(i);
				
				if(behaviorItems[i].equals(""))
					behaviorItems[i] = Integer.toHexString(i).toUpperCase();
		}
		
		JPanel pnlBlockBehavior = new JPanel();
		cmbBehaviors.setModel(new DefaultComboBoxModel(behaviorItems));
		cmbBehaviors.setPreferredSize(new Dimension(200, 20));
		pnlBlockBehavior.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "Block Behavior", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//		pnlBlockBehavior.setBounds(6, 4, 138, 60);
		pnlBlockBehavior.add(cmbBehaviors, BorderLayout.WEST);
		behaviorModifier.add(pnlBlockBehavior);
		
		JPanel pnlWildBattle = new JPanel(new GridLayout(0, 1));
		pnlWildBattle.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "Wild Battles", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//		pnlWildBattle.setBounds(6, 66, 138, 92);
		behaviorModifier.add(pnlWildBattle);
		
		battleGroup = new ButtonGroup();
		
		rdBattleNone = new JRadioButton("No Battles");
		rdBattleNone.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(txtBehavior.getText().equals(""))
					txtBehavior.setText((DataStore.EngineVersion == 1 ? "00000000" : "0000"));
				
				long behavior = Long.parseLong(txtBehavior.getText(), 16);
				behavior &= DataStore.EngineVersion == 1 ? 0xF8FFFFFF : 0xF8FF;
				behavior |= DataStore.EngineVersion == 1 ? 0x01000000 : 0x0100;
				txtBehavior.setText(String.format("%08X", behavior));
			}
		});
		rdBattleNone.setSelected(true);
//		rdBattleNone.setPreferredSize(new Dimension(121, 16));
		pnlWildBattle.add(rdBattleNone);
		battleGroup.add(rdBattleNone);
		
		rdBattleGrass = new JRadioButton("Grass Battles");
		rdBattleGrass.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(txtBehavior.getText().equals(""))
					txtBehavior.setText((DataStore.EngineVersion == 1 ? "00000000" : "0000"));
				
				long behavior = Long.parseLong(txtBehavior.getText(), 16);
				behavior &= DataStore.EngineVersion == 1 ? 0xF8FFFFFF : 0xF8FF;
				behavior |= DataStore.EngineVersion == 1 ? 0x01000000 : 0x0100;
				txtBehavior.setText(String.format("%08X", behavior));
			}
		});
//		rdBattleGrass.setPreferredSize(new Dimension(121, 16));
		pnlWildBattle.add(rdBattleGrass);
		battleGroup.add(rdBattleGrass);
		
		rdBattleWater = new JRadioButton("Water Battles");
		rdBattleWater.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(txtBehavior.getText().equals(""))
					txtBehavior.setText((DataStore.EngineVersion == 1 ? "00000000" : "0000"));
				
				long behavior = Long.parseLong(txtBehavior.getText(), 16);
				behavior &= DataStore.EngineVersion == 1 ? 0xF8FFFFFF : 0xF8FF;
				behavior |= DataStore.EngineVersion == 1 ? 0x02000000 : 0x0200;
				txtBehavior.setText(String.format("%08X", behavior));
			}
		});
//		rdBattleWater.setPreferredSize(new Dimension(121, 16));
		pnlWildBattle.add(rdBattleWater);
		battleGroup.add(rdBattleWater);
		
		
		JPanel pnlLayerPriority = new JPanel(new GridLayout(0, 1));
		pnlLayerPriority.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "Layer Priority", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//		pnlLayerPriority.setBounds(6, 160, 138, 92);
		behaviorModifier.add(pnlLayerPriority);
		
		ButtonGroup bgPerms = new ButtonGroup();
		
		rdBgOver = new JRadioButton("Covers Player");
		rdBgOver.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(txtBehavior.getText().equals(""))
					txtBehavior.setText((DataStore.EngineVersion == 1 ? "00000000" : "0000"));
				
				long behavior = Long.parseLong(txtBehavior.getText(), 16);
				behavior &= DataStore.EngineVersion == 1 ? 0x8FFFFFFF : 0x8FFF;
				behavior |= 0x00000000;
				txtBehavior.setText(String.format("%08X", behavior));
			}
		});
		rdBgOver.setSelected(true);
//		rdBgOver.setPreferredSize(new Dimension(107, 16));
		pnlLayerPriority.add(rdBgOver);
		bgPerms.add(rdBgOver);
		
		rdBgUnder = new JRadioButton("Player Over");
		rdBgUnder.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(txtBehavior.getText().equals(""))
					txtBehavior.setText((DataStore.EngineVersion == 1 ? "00000000" : "0000"));
				
				long behavior = Long.parseLong(txtBehavior.getText(), 16);
				behavior &= DataStore.EngineVersion == 1 ? 0x8FFFFFFF : 0x8FFF;
				behavior |= DataStore.EngineVersion == 1 ? 0x20000000 : 0x2000;
				txtBehavior.setText(String.format("%08X", behavior));
			}
		});
//		rdBgUnder.setPreferredSize(new Dimension(117, 16));
		pnlLayerPriority.add(rdBgUnder);
		bgPerms.add(rdBgUnder);
		
		rdBgTriple = new JRadioButton("Triple Layer");
		rdBgTriple.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(txtBehavior.getText().equals(""))
					txtBehavior.setText((DataStore.EngineVersion == 1 ? "00000000" : "0000"));
				
				long behavior = Long.parseLong(txtBehavior.getText(), 16);
				behavior &= DataStore.EngineVersion == 1 ? 0x8FFFFFFF : 0x8FFF;
				behavior |= DataStore.mehTripleEditByte << DataStore.EngineVersion == 1 ? 24 : 8;
				txtBehavior.setText(String.format("%08X", behavior));
			}
		});
//		rdBgTriple.setPreferredSize(new Dimension(102, 16));
		pnlLayerPriority.add(rdBgTriple);
		bgPerms.add(rdBgTriple);
		
		txtBehavior = new JTextField();
		txtBehavior.setDocument(new HexDocument());
		txtBehavior.getDocument().addDocumentListener(new DocumentListener()
		{

			public void removeUpdate(DocumentEvent e)
			{
				updateBehaviors();
			}

			public void insertUpdate(DocumentEvent e)
			{
				updateBehaviors();
			}

			public void changedUpdate(DocumentEvent e)
			{
				updateBehaviors();
			}
		});
//		txtBehavior.setBounds(6, 235, 138, 24);
		behaviorModifier.add(txtBehavior);
		txtBehavior.setColumns(1);
		
		
		this.setSize(scrollPaneTEP.getPreferredSize().width + scrollPaneTiles.getPreferredSize().width + behaviorModifier.getPreferredSize().width,400);	
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnExport = new JMenu("Export");
		mnFile.add(mnExport);
		
		JMenuItem mntmExportGlobalTileset = new JMenuItem("Global Tileset");
		mntmExportGlobalTileset.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				BufferedImage b = MapIO.blockRenderer.getGlobalTileset().getIndexedTileSet(tpp.viewingPalette, MapIO.blockRenderer.currentTime);
				FileDialog fd = new FileDialog(new Frame(), "Locate a Tileset", FileDialog.SAVE);
				fd.setFilenameFilter(new FilenameFilter() {
				    public boolean accept(File dir, String name) {
				      return (name.toLowerCase().endsWith(".png"));
				    }
				});
				fd.setFile("Global-Tileset.png");
				fd.setDirectory(System.getProperty("user.home"));
				fd.setVisible(true);
				String location = fd.getDirectory() + fd.getFile();
				
				File outputfile = new File(location);
			    try
				{
					ImageIO.write(b, "png", outputfile);
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		mnExport.add(mntmExportGlobalTileset);
		
		JMenuItem mntmExportLocalTileset = new JMenuItem("Local tileset");
		mntmExportLocalTileset.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				BufferedImage b = MapIO.blockRenderer.getLocalTileset().getIndexedTileSet(tpp.viewingPalette,MapIO.blockRenderer.currentTime);
				FileDialog fd = new FileDialog(new Frame(), "Locate a Tileset", FileDialog.SAVE);
				fd.setFilenameFilter(new FilenameFilter() {
				    public boolean accept(File dir, String name) {
				      return (name.toLowerCase().endsWith(".png"));
				    }
				});
				fd.setFile("Local-Tileset.png");
				fd.setDirectory(System.getProperty("user.home"));
				fd.setVisible(true);
		        String location = fd.getDirectory() + fd.getFile();
		        
				File outputfile = new File(location);
			    try
				{
					ImageIO.write(b, "png", outputfile);
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		mnExport.add(mntmExportLocalTileset);
		
		JMenu mnImport = new JMenu("Import");
		mnFile.add(mnImport);
		
		JMenuItem mntmImportGlobalTileset = new JMenuItem("Global Tileset");
		mntmImportGlobalTileset.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				FileDialog fd = new FileDialog(new Frame(), "Locate A Tileset", FileDialog.LOAD);
				fd.setFilenameFilter(new FilenameFilter() {
				    public boolean accept(File dir, String name) {
				      return (name.toLowerCase().endsWith(".png"));
				    }
				});
				fd.setDirectory(System.getProperty("user.home"));
				fd.setVisible(true);
		        String location = fd.getDirectory() + fd.getFile();
		        
				File inputfile = new File(location);
			    try
				{
			    	//TODO: Make sure we're getting valid images here, or things go ka-boom.
					BufferedImage tileset = ImageIO.read(inputfile);
					IndexColorModel icm = (IndexColorModel)tileset.getColorModel();
					
					byte[] reds = new byte[256];
					byte[] blues = new byte[256];
					byte[] greens = new byte[256];
					
					icm.getReds(reds);
					icm.getBlues(blues);
					icm.getGreens(greens);
					
					
					//Import and edit the palette
					Palette[] p = MapIO.blockRenderer.getGlobalTileset().getPalette(MapIO.blockRenderer.currentTime);
					
					//We don't need to change the first color here.
					reds[0] = p[tpp.viewingPalette].getReds()[0];
					greens[0] = p[tpp.viewingPalette].getGreens()[0];
					blues[0] = p[tpp.viewingPalette].getBlues()[0];
					
					p[tpp.viewingPalette].setColors(reds, greens, blues);
					MapIO.blockRenderer.getGlobalTileset().setPalette(p,MapIO.blockRenderer.currentTime);			
					p = MapIO.blockRenderer.getLocalTileset().getPalette(MapIO.blockRenderer.currentTime);
					p[tpp.viewingPalette].setColors(reds, greens, blues);
					MapIO.blockRenderer.getLocalTileset().setPalette(p,MapIO.blockRenderer.currentTime);
					
					GBAImage i = GBAImage.fromImage(tileset, p[tpp.viewingPalette]);
					byte[] compData = Lz77.compressLZ77(i.getRaw());
					int freespace = ROMManager.getActiveROM().findFreespace(compData.length);
					ROMManager.currentROM.writeBytes(freespace, compData);
					ROMManager.currentROM.floodBytes((int)MapIO.blockRenderer.getGlobalTileset().tilesetHeader.pGFX, 
							DataStore.FreespaceByte, Lz77.getUncompressedSize(ROMManager.currentROM, 
									(int)MapIO.blockRenderer.getGlobalTileset().tilesetHeader.pGFX));
										//TODO: Make removing old data optional
					
					MapIO.blockRenderer.getGlobalTileset().tilesetHeader.pGFX = freespace;
					MapIO.blockRenderer.getGlobalTileset().tilesetHeader.bCompressed = 1;
					
					rerenderTiles();
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		mnImport.add(mntmImportGlobalTileset);
		
		JMenuItem mntmImportLocalTileset = new JMenuItem("Local Tileset");
		mntmImportLocalTileset.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				FileDialog fd = new FileDialog(new Frame(), "Locate A Tileset", FileDialog.LOAD);
				fd.setFilenameFilter(new FilenameFilter() {
				    public boolean accept(File dir, String name) {
				      return (name.toLowerCase().endsWith(".png"));
				    }
				});
				fd.setDirectory(System.getProperty("user.home"));
				fd.setVisible(true);
		        String location = fd.getDirectory() + fd.getFile();
		        
				File inputfile = new File(location);
			    try
				{
			    	//TODO: Make sure we're getting valid images here, or things go ka-boom.
					BufferedImage tileset = ImageIO.read(inputfile);
					IndexColorModel icm = (IndexColorModel)tileset.getColorModel();
					
					byte[] reds = new byte[256];
					byte[] blues = new byte[256];
					byte[] greens = new byte[256];
					
					icm.getReds(reds);
					icm.getBlues(blues);
					icm.getGreens(greens);
					
					
					//Import and edit the palette
					Palette[] p = MapIO.blockRenderer.getLocalTileset().getPalette(MapIO.blockRenderer.currentTime);
					
					//We don't need to change the first color here.
					reds[0] = p[tpp.viewingPalette].getReds()[0];
					greens[0] = p[tpp.viewingPalette].getGreens()[0];
					blues[0] = p[tpp.viewingPalette].getBlues()[0];
					
					p[tpp.viewingPalette].setColors(reds, greens, blues);
					MapIO.blockRenderer.getGlobalTileset().setPalette(p,MapIO.blockRenderer.currentTime);			
					p = MapIO.blockRenderer.getLocalTileset().getPalette(MapIO.blockRenderer.currentTime);
					p[tpp.viewingPalette].setColors(reds, greens, blues);
					MapIO.blockRenderer.getLocalTileset().setPalette(p,MapIO.blockRenderer.currentTime);
					
					GBAImage i = GBAImage.fromImage(tileset, p[tpp.viewingPalette]);
					byte[] compData = Lz77.compressLZ77(i.getRaw());
					int freespace = ROMManager.getActiveROM().findFreespace(compData.length);
					ROMManager.currentROM.writeBytes(freespace, compData);
					ROMManager.currentROM.floodBytes((int)MapIO.blockRenderer.getLocalTileset().tilesetHeader.pGFX, 
							DataStore.FreespaceByte, Lz77.getUncompressedSize(ROMManager.currentROM, 
									(int)MapIO.blockRenderer.getLocalTileset().tilesetHeader.pGFX));
										//TODO: Make removing old data optional
					
					MapIO.blockRenderer.getLocalTileset().tilesetHeader.pGFX = freespace;
					MapIO.blockRenderer.getLocalTileset().tilesetHeader.bCompressed = 1;
					
					rerenderTiles();
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		mnImport.add(mntmImportLocalTileset);
	}
	
	public void rerenderTiles()
	{
		//Rerender tiles
		MapIO.blockRenderer.getGlobalTileset().renderGraphics();
		MapIO.blockRenderer.getGlobalTileset().rerenderTileSet(tpp.viewingPalette,MapIO.blockRenderer.currentTime);
		MapIO.blockRenderer.getLocalTileset().renderGraphics();
		MapIO.blockRenderer.getLocalTileset().rerenderTileSet(tpp.viewingPalette,MapIO.blockRenderer.currentTime);
		
		//Refresh block picker
		tileEditorPanel.RerenderTiles(tileEditorPanel.imgBuffer, 0);
		tileEditorPanel.repaint();
		
		//Refresh Map Editor
		MapEditorPanel.Redraw = true;
		MainGUI.mapEditorPanel.repaint();
		
		//Refresh block editor
		tpp.setPalette(cmbBoxPalette.getSelectedIndex());
		blockEditorPanel.repaint();
		tripleEditorPanel.repaint();
	}
	
	
	boolean crappyWorkaround = false;
	public void updateBehaviors()
	{
		if(txtBehavior.getText().equals(""))
			return;
		
		long behavior = Long.parseLong(txtBehavior.getText(), 16);
		int bgPrio = (int)((behavior & (DataStore.EngineVersion == 1 ? 0x07000000 : 0x0700)) >> 24);
		
		if(bgPrio == 0x20)
			rdBgUnder.setSelected(true);
		else if(bgPrio == DataStore.mehTripleEditByte)
			rdBgTriple.setSelected(true);
		else if(bgPrio == 0)
			rdBgOver.setSelected(true);
		
		if((behavior & (DataStore.EngineVersion == 1 ? 0x07000000 : 0x0700)) >> 24 == 0x1)
			rdBattleGrass.setSelected(true);
		else if((behavior & (DataStore.EngineVersion == 1 ? 0x07000000 : 0x0700)) >> 24 == 0x2)
			rdBattleWater.setSelected(true);
		else
			rdBattleNone.setSelected(true);
		
		crappyWorkaround = true;
		cmbBehaviors.setSelectedIndex((int)behavior & (DataStore.EngineVersion == 1 ? 0x1FF : 0xFF));
		crappyWorkaround = false;
			
		blockEditorPanel.getBlock().backgroundMetaData = behavior;
		blockEditorPanel.getBlock().save();
	}
	
}
