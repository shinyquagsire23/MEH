package us.plxhack.MEH.UI;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JViewport;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
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

public class BlockEditor extends JFrame
{
	TilesetPickerPanel tpp;
	JComboBox comboBox;
	JScrollPane scrollPaneTep;
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
	
	public BlockEditor() 
	{
		setResizable(false);
		
		scrollPaneTep = new JScrollPane((Component) null, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTep.setPreferredSize(new Dimension(16*9, 3));
		scrollPaneTep.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		scrollPaneTep.getVerticalScrollBar().setUnitIncrement(16);
		scrollPaneTep.getHorizontalScrollBar().setUnitIncrement(16);
		
		tileEditorPanel = new TileEditorPanel(false);
		tileEditorPanel.setLayout(null);
		tileEditorPanel.setSize(new Dimension(16*8, 2560));
		tileEditorPanel.setPreferredSize(new Dimension(16*8, 2560));
		
		tileEditorPanel.globalTiles = MainGUI.tileEditorPanel.globalTiles;
		tileEditorPanel.localTiles = MainGUI.tileEditorPanel.localTiles;
		tileEditorPanel.RerenderTiles(tileEditorPanel.imgBuffer, 0);
		
		scrollPaneTep.setViewportView(tileEditorPanel);
		getContentPane().add(scrollPaneTep, BorderLayout.WEST);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(256, 10));
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
//		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setPreferredSize(new Dimension(256, 60));
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(null);
		
		lblMeep = new JLabel("Meep");
		lblMeep.setBounds(12, 12, 80, 15);
		panel_2.add(lblMeep);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				tpp.setPalette(comboBox.getSelectedIndex());
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Palette 0", "Palette 1", "Palette 2", "Palette 3", "Palette 4", "Palette 5", "Palette 6", "Palette 7", "Palette 8", "Palette 9", "Palette 10", "Palette 11", "Palette 12"}));
		comboBox.setPreferredSize(new Dimension(36, 24));
		comboBox.setBounds(10, 30, 107, 24);
		panel_2.add(comboBox);
		
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
		panel_2.add(chbxXFlip);
		
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
		panel_2.add(chbxYFlip);
		
		panelSelectedBlock = new ImagePanel(null);
		panelSelectedBlock.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelSelectedBlock.setBounds(210, 16, 32, 32);
		panel_2.add(panelSelectedBlock);
		
		scrollPaneTiles = new JScrollPane((Component) null, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneTiles.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		scrollPaneTiles.getVerticalScrollBar().setUnitIncrement(16);
		scrollPaneTiles.getHorizontalScrollBar().setUnitIncrement(16);
		
		tpp = new TilesetPickerPanel(MapIO.blockRenderer.getGlobalTileset(),MapIO.blockRenderer.getLocalTileset(), this);
//		tpp.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tpp.setPreferredSize(new Dimension(256,1024));
		
		scrollPaneTiles.setViewportView(tpp);
		panel_1.add(scrollPaneTiles, BorderLayout.CENTER);
		
		JSplitPane panel = new JSplitPane();
		panel.setEnabled(false);
		panel.setResizeWeight(0.19);
		panel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel.setPreferredSize(new Dimension(150, 10));
		panel.setBorder(null);
		getContentPane().add(panel, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
//		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setLeftComponent(panel_3);
		panel_3.setLayout(null);
		
		blockEditorPanel = new BlockEditorPanel(this);
		blockEditorPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		blockEditorPanel.setPreferredSize(new Dimension(64, 32));
		blockEditorPanel.setBounds(40, 12, 64, 32);
		panel_3.add(blockEditorPanel);
		
		tripleEditorPanel = new TripleEditorPanel(this);
		tripleEditorPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		tripleEditorPanel.setPreferredSize(new Dimension(20,20));
		tripleEditorPanel.setBounds(103, 24, 20, 20);
		panel_3.add(tripleEditorPanel);
		
		JPanel panel_4 = new JPanel();
//		panel_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setRightComponent(panel_4);
		panel_4.setLayout(null);
		
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
		
		JPanel panel_5 = new JPanel();
		cmbBehaviors.setModel(new DefaultComboBoxModel(behaviorItems));
		cmbBehaviors.setPreferredSize(new Dimension(80, 20));
		panel_5.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "Block Behavior", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(6, 4, 138, 60);
		panel_5.add(cmbBehaviors);
		panel_4.add(panel_5);
		
		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_6.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_6.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "Wild Battles", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setBounds(6, 66, 138, 92);
		panel_4.add(panel_6);
		
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
		rdBattleNone.setPreferredSize(new Dimension(121, 16));
		panel_6.add(rdBattleNone);
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
		rdBattleGrass.setPreferredSize(new Dimension(121, 16));
		panel_6.add(rdBattleGrass);
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
		rdBattleWater.setPreferredSize(new Dimension(121, 16));
		panel_6.add(rdBattleWater);
		battleGroup.add(rdBattleWater);
		
		
		JPanel panel_7 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_7.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_7.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_7.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "Layer Priority", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_7.setBounds(6, 160, 138, 92);
		panel_4.add(panel_7);
		
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
		rdBgOver.setPreferredSize(new Dimension(107, 16));
		panel_7.add(rdBgOver);
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
		rdBgUnder.setPreferredSize(new Dimension(117, 16));
		panel_7.add(rdBgUnder);
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
		rdBgTriple.setPreferredSize(new Dimension(102, 16));
		panel_7.add(rdBgTriple);
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
		txtBehavior.setBounds(6, 235, 138, 24);
		panel_4.add(txtBehavior);
		txtBehavior.setColumns(1);
		
		
		this.setSize(565,385);	
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
		tpp.setPalette(comboBox.getSelectedIndex());
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
