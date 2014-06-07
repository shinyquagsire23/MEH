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

import javax.swing.JScrollPane;

import java.awt.Component;

import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;

import javax.swing.border.BevelBorder;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
	private JRadioButton rdBattleGrass;
	private JRadioButton rdBattleWater;
	private NoneSelectedButtonGroup battleGroup;
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
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
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
		tpp.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
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
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
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
		panel_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
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
					txtBehavior.setText("00000000");
				
				long behavior = Long.parseLong(txtBehavior.getText(), 16);
				behavior &= (DataStore.EngineVersion == 1 ? 0xFFFFFE00 : 0xFFFFFF00);
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
		
		cmbBehaviors.setModel(new DefaultComboBoxModel(behaviorItems));
		cmbBehaviors.setBorder(new TitledBorder(null, "Block Behavior", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		cmbBehaviors.setBounds(6, 12, 138, 49);
		panel_4.add(cmbBehaviors);
		
		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_5.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_5.setBorder(new TitledBorder(null, "Wild Battles", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(6, 66, 138, 68);
		panel_4.add(panel_5);
		
		battleGroup = new NoneSelectedButtonGroup();
		
		rdBattleGrass = new JRadioButton("Grass Battles");
		rdBattleGrass.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(txtBehavior.getText().equals(""))
					txtBehavior.setText("00000000");
				
				long behavior = Long.parseLong(txtBehavior.getText(), 16);
				behavior &= 0xF8FFFFFF;
				behavior |= 0x01000000;
				txtBehavior.setText(String.format("%08X", behavior));
			}
		});
		rdBattleGrass.setPreferredSize(new Dimension(121, 16));
		panel_5.add(rdBattleGrass);
		battleGroup.add(rdBattleGrass);
		
		rdBattleWater = new JRadioButton("Water Battles");
		rdBattleWater.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(txtBehavior.getText().equals(""))
					txtBehavior.setText("00000000");
				
				long behavior = Long.parseLong(txtBehavior.getText(), 16);
				behavior &= 0xF8FFFFFF;
				behavior |= 0x02000000;
				txtBehavior.setText(String.format("%08X", behavior));
			}
		});
		rdBattleWater.setPreferredSize(new Dimension(124, 16));
		panel_5.add(rdBattleWater);
		battleGroup.add(rdBattleWater);
		
		
		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_6.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_6.setBorder(new TitledBorder(null, "Layer Priority", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setBounds(6, 140, 138, 92);
		panel_4.add(panel_6);
		
		NoneSelectedButtonGroup bgPerms = new NoneSelectedButtonGroup();
		
		rdBgOver = new JRadioButton("Covers Player");
		rdBgOver.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(txtBehavior.getText().equals(""))
					txtBehavior.setText("00000000");
				
				long behavior = Long.parseLong(txtBehavior.getText(), 16);
				behavior &= 0x8FFFFFFF;
				behavior |= 0x00000000;
				txtBehavior.setText(String.format("%08X", behavior));
			}
		});
		rdBgOver.setSelected(true);
		rdBgOver.setPreferredSize(new Dimension(107, 16));
		panel_6.add(rdBgOver);
		bgPerms.add(rdBgOver);
		
		rdBgUnder = new JRadioButton("Player Over");
		rdBgUnder.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(txtBehavior.getText().equals(""))
					txtBehavior.setText("00000000");
				
				long behavior = Long.parseLong(txtBehavior.getText(), 16);
				behavior &= 0x8FFFFFFF;
				behavior |= 0x20000000;
				txtBehavior.setText(String.format("%08X", behavior));
			}
		});
		rdBgUnder.setPreferredSize(new Dimension(117, 16));
		panel_6.add(rdBgUnder);
		bgPerms.add(rdBgUnder);
		
		rdBgTriple = new JRadioButton("Triple Tiles");
		rdBgTriple.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(txtBehavior.getText().equals(""))
					txtBehavior.setText("00000000");
				
				long behavior = Long.parseLong(txtBehavior.getText(), 16);
				behavior &= 0x8FFFFFFF;
				behavior |= DataStore.mehTripleEditByte << 24;
				txtBehavior.setText(String.format("%08X", behavior));
			}
		});
		rdBgTriple.setPreferredSize(new Dimension(102, 16));
		panel_6.add(rdBgTriple);
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
				BufferedImage b = MapIO.blockRenderer.getGlobalTileset().getIndexedTileSet(tpp.viewingPalette);
				FileDialog fd = new FileDialog(new Frame(), "Locate A Tileset", FileDialog.SAVE);
				fd.setFilenameFilter(new FilenameFilter() {
				    public boolean accept(File dir, String name) {
				      return (name.toLowerCase().endsWith(".png"));
				    }
				});
				fd.setFile("Global-Tileset.png");
				fd.setDirectory(System.getProperty("user.home"));
				fd.show();
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
				BufferedImage b = MapIO.blockRenderer.getLocalTileset().getIndexedTileSet(tpp.viewingPalette);
				FileDialog fd = new FileDialog(new Frame(), "Locate A Tileset", FileDialog.SAVE);
				fd.setFilenameFilter(new FilenameFilter() {
				    public boolean accept(File dir, String name) {
				      return (name.toLowerCase().endsWith(".png"));
				    }
				});
				fd.setFile("Local-Tileset.png");
				fd.setDirectory(System.getProperty("user.home"));
				fd.show();
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
				fd.show();
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
					Palette[] p = MapIO.blockRenderer.getGlobalTileset().getPalette();
					
					//We don't need to change the first color here.
					reds[0] = p[tpp.viewingPalette].getReds()[0];
					greens[0] = p[tpp.viewingPalette].getGreens()[0];
					blues[0] = p[tpp.viewingPalette].getBlues()[0];
					
					p[tpp.viewingPalette].setColors(reds, greens, blues);
					MapIO.blockRenderer.getGlobalTileset().setPalette(p);			
					p = MapIO.blockRenderer.getLocalTileset().getPalette();
					p[tpp.viewingPalette].setColors(reds, greens, blues);
					MapIO.blockRenderer.getLocalTileset().setPalette(p);
					
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
				fd.show();
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
					Palette[] p = MapIO.blockRenderer.getLocalTileset().getPalette();
					
					//We don't need to change the first color here.
					reds[0] = p[tpp.viewingPalette].getReds()[0];
					greens[0] = p[tpp.viewingPalette].getGreens()[0];
					blues[0] = p[tpp.viewingPalette].getBlues()[0];
					
					p[tpp.viewingPalette].setColors(reds, greens, blues);
					MapIO.blockRenderer.getGlobalTileset().setPalette(p);			
					p = MapIO.blockRenderer.getLocalTileset().getPalette();
					p[tpp.viewingPalette].setColors(reds, greens, blues);
					MapIO.blockRenderer.getLocalTileset().setPalette(p);
					
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
		MapIO.blockRenderer.getGlobalTileset().rerenderTileSet(tpp.viewingPalette);
		MapIO.blockRenderer.getLocalTileset().renderGraphics();
		MapIO.blockRenderer.getLocalTileset().rerenderTileSet(tpp.viewingPalette);
		
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
		int bgPrio = (int)((behavior & 0x70000000) >> 24);
		
		if(bgPrio == 0x20)
			rdBgUnder.setSelected(true);
		else if(bgPrio == DataStore.mehTripleEditByte)
			rdBgTriple.setSelected(true);
		else if(bgPrio == 0)
			rdBgOver.setSelected(true);
		
		if((behavior & 0x07000000) >> 24 == 0x1)
			rdBattleGrass.setSelected(true);
		else if((behavior & 0x07000000) >> 24 == 0x2)
			rdBattleWater.setSelected(true);
		else
			battleGroup.deselectAll();
		
		crappyWorkaround = true;
		cmbBehaviors.setSelectedIndex((int)behavior & (DataStore.EngineVersion == 1 ? 0x1FF : 0xFF));
		crappyWorkaround = false;
			
		blockEditorPanel.getBlock().backgroundMetaData = behavior;
		blockEditorPanel.getBlock().save();
	}
	
	public class NoneSelectedButtonGroup extends ButtonGroup
	{

		private static final long serialVersionUID = 1L;

		@Override
		public void setSelected(ButtonModel model, boolean selected)
		{
			if (selected)
				super.setSelected(model, selected);

			else
				clearSelection();
		}
		
		public void deselectAll()
		{
			for(AbstractButton b : this.buttons)
				b.setSelected(false);
		}
	}
}
