package org.zzl.minegaming.MEH;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.Dimension;

import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import javax.swing.JToolBar;

import java.awt.Component;

import javax.swing.Box;

import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.SystemColor;

import javax.swing.ImageIcon;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ImagePanel;
import org.zzl.minegaming.GBAUtils.ROMManager;
import org.zzl.minegaming.MEH.MapElements.TilesetCache;
import org.zzl.minegaming.MEH.MapElements.WildDataCache;

import javax.swing.JPopupMenu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JTextField;
import javax.swing.JSpinner;

public class MainGUI extends JFrame
{
	public int paneSize = 0;
	public int initEditorPanePos = -1;

	public static JLabel lblInfo;
	public JTree mapBanks;
	public static Map loadedMap;

	public static BorderMap borderMap;
	private static int selectedBank = 0;
	private static int selectedMap = 0;
	private static int currentBank = 0;
	private static int currentMap = 0;
	private JTabbedPane editorTabs;
	private JSplitPane splitPane;
	public static JLabel lblWidth;
	public static JLabel lblBorderTilesPointer;
	public static JLabel lblBorderWidth;
	public static JLabel lblLocalTilesetPointer;
	public static JLabel lblMapTilesPointer;
	public static JLabel lblHeight;
	public static JLabel lblBorderHeight;
	public static JLabel lblGlobalTilesetPointer;
	
	private JPanel panel_1;
	JPanel panelTilesContainer;
	JPanel wildPokemonPanel;
	JPanel panelPermissions;
	public static BorderEditorPanel borderTileEditor;
	public static EventEditorPanel eventEditorPanel;
	public static TileEditorPanel tileEditorPanel;
	public static ConnectionsEditorPanel connectionsEditorPanel;
	public static JLabel lblTileVal;
	public DataStore dataStore;
	private JPanel editorPanel;
	void CreateToolbar(){
		JToolBar toolBar = new JToolBar();
		lblTileVal=new JLabel("Current Tile: 0x0000");
		toolBar.add(lblTileVal);
		editorPanel.add(toolBar, BorderLayout.NORTH);
		toolBar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBar.setPreferredSize(new Dimension(128, 32));
	}
	
	//Map Creation
	JPanel panelMapTilesContainer;
	JPanel splitterMapTiles;
	public static MapEditorPanel mapEditorPanel;
	
	public JScrollPane mapScrollPane;
	void CreateBorderArea(){
		JPanel panelBorderTilesContainer = new JPanel();
		panelBorderTilesContainer.setPreferredSize(new Dimension(10, 100));
		panelMapTilesContainer.add(panelBorderTilesContainer, BorderLayout.NORTH);
		panelBorderTilesContainer.setLayout(new BorderLayout(0, 0));

		JPanel panelBorderTilesSplitter = new JPanel();
		panelBorderTilesSplitter.setBackground(SystemColor.controlShadow);
		panelBorderTilesSplitter.setPreferredSize(new Dimension(10, 1));
		panelBorderTilesContainer.add(panelBorderTilesSplitter, BorderLayout.SOUTH);
		//Set up tileset

		JPanel panelBorderTilesToAbsolute = new JPanel();
		panelBorderTilesContainer.add(panelBorderTilesToAbsolute, BorderLayout.CENTER);
		panelBorderTilesToAbsolute.setLayout(null);

		borderTileEditor = new BorderEditorPanel();
		borderTileEditor.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Border Tiles", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		borderTileEditor.setBounds(12, 12, 114, 75);
		panelBorderTilesToAbsolute.add(borderTileEditor);
	}
	JScrollPane tilesetScrollPane;
	void CreateTilesetArea(){
	}
	
	JPanel eventsPanel;
	void CreateEventsPanel(){
		
		eventsPanel=new JPanel();
		editorTabs.addTab("Events", null, eventsPanel, null);
		eventsPanel.setLayout(new BorderLayout(0, 0));
		JPanel splitm = new JPanel();
		splitm.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		splitm.setPreferredSize(new Dimension(4, 10));
		splitm.setMaximumSize(new Dimension(4, 32000));
        JPanel pmtc = new JPanel();
		
        pmtc.setLayout(new BorderLayout(0, 0));
		
		panel_5 = new JPanel();
		panel_5.setPreferredSize(new Dimension(220, 10));
		JScrollPane selectedEventScroll = new JScrollPane(panel_5, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		eventsPanel.add(selectedEventScroll, BorderLayout.EAST);
		panel_5.setLayout(new BorderLayout(0, 0));
		eventEditorPanel = new EventEditorPanel();
		eventEditorPanel.setLayout(null);
		
		JScrollPane eventScrollPane = new JScrollPane(eventEditorPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		eventsPanel.add(eventScrollPane, BorderLayout.CENTER);
		eventScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		eventScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		eventScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		
		
	}
	void CreateMapPanel(){
		editorPanel = new JPanel();
		editorTabs.addTab("Map", null, editorPanel, null);
		editorPanel.setLayout(new BorderLayout(0, 0));
		splitterMapTiles = new JPanel();
		splitterMapTiles.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		splitterMapTiles.setPreferredSize(new Dimension(4, 10));
		splitterMapTiles.setMaximumSize(new Dimension(4, 32767));
		

		panelMapTilesContainer = new JPanel();
		
		panelMapTilesContainer.setLayout(new BorderLayout(0, 0));
		
		mapEditorPanel = new MapEditorPanel();
		mapEditorPanel.setLayout(null);

		mapScrollPane = new JScrollPane(mapEditorPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		editorPanel.add(mapScrollPane, BorderLayout.CENTER);
		mapScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		mapScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		mapScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		
		panelTilesContainer = new JPanel();
		editorPanel.add(panelTilesContainer, BorderLayout.EAST);
		panelTilesContainer.setPreferredSize(new Dimension((TileEditorPanel.editorWidth+1)*16 + 13, 10));
		panelTilesContainer.setLayout(new BorderLayout(0, 0));

	}
	void CreateMenus(){
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mnOpen = new JMenuItem("Open...");
		mnOpen.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				openROM();
				
			}
		});
		mnFile.add(mnOpen);
		
		mnSave = new JMenuItem("Save");
		mnSave.enable(false);
		mnSave.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				loadedMap.save();
				connectionsEditorPanel.save(); //Save surrounding maps
				PluginManager.fireMapSave(currentBank, currentMap);
				saveROM();
			}
		});
		mnFile.add(mnSave);
		mnFile.addSeparator();
		
		JMenuItem mnSaveMap = new JMenuItem("Save Map...");
		mnFile.add(mnSaveMap);
		mnSaveMap.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				loadedMap.save();
				connectionsEditorPanel.save(); //Save surrounding maps
				PluginManager.fireMapSave(currentBank, currentMap);
			}
		});
		mnFile.addSeparator();
		
		mntmNewMenuItem_1 = new JMenuItem("Save Map to PNG...");
		mnFile.add(mntmNewMenuItem_1);

		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JMenuItem mntmPreferences = new JMenuItem("Preferences...");
		mntmPreferences.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//TODO
			}
		});
		mnSettings.add(mntmPreferences);

		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
	}
	public static JPanel panelButtons;
	void CreateButtons(){
		panelButtons = new JPanel();
		panelButtons.setPreferredSize(new Dimension(10, 50));
		panelButtons.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelButtons.setMinimumSize(new Dimension(10, 50));
		getContentPane().add(panelButtons, BorderLayout.NORTH);

		JButton btnOpenROM = new JButton("");
		btnOpenROM.setToolTipText("Open ROM for editing");
		btnOpenROM.setIcon(new ImageIcon(MainGUI.class.getResource("/resources/ROMopen.png")));
		btnOpenROM.setFocusPainted(false);
		btnOpenROM.setBorder(null);
		btnOpenROM.setBorderPainted(false);
		btnOpenROM.setPreferredSize(new Dimension(54, 48));

		btnOpenROM.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				openROM();
			}
		});
		panelButtons.setLayout(new FlowLayout(FlowLayout.LEFT, -1, -2));
		panelButtons.add(btnOpenROM);

		JButton btnSaveROM = new JButton("");
		btnSaveROM.setToolTipText("Write changes to ROM file");
		btnSaveROM.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				loadedMap.save();
				connectionsEditorPanel.save(); //Save surrounding maps
				PluginManager.fireMapSave(currentBank, currentMap);
				saveROM();
			}
		});
		btnSaveROM.setIcon(new ImageIcon(MainGUI.class.getResource("/resources/ROMsave.png")));
		btnSaveROM.setFocusPainted(false);
		btnSaveROM.setBorderPainted(false);
		btnSaveROM.setPreferredSize(new Dimension(54, 48));
		panelButtons.add(btnSaveROM);

		Component horizontalStrut = Box.createHorizontalStrut(7);
		horizontalStrut.setForeground(Color.BLACK);
		panelButtons.add(horizontalStrut);

		JPanel separator1 = new JPanel();
		separator1.setBackground(SystemColor.scrollbar);
		separator1.setPreferredSize(new Dimension(1, 46));
		FlowLayout fl_separator1 = (FlowLayout) separator1.getLayout();
		fl_separator1.setVgap(0);
		fl_separator1.setHgap(0);

		panelButtons.add(separator1);

		Component horizontalStrut_1 = Box.createHorizontalStrut(7);
		horizontalStrut_1.setForeground(Color.BLACK);
		panelButtons.add(horizontalStrut_1);

		JButton btnNewMap = new JButton("");
		btnNewMap.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				MapEditorPanel.renderPalette = !MapEditorPanel.renderPalette;
				mapEditorPanel.repaint();
			}
		});
		
		JButton btnSaveMap = new JButton("");
		btnSaveMap.setToolTipText("Save Map to VROM");
		btnSaveMap.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				loadedMap.save();
				connectionsEditorPanel.save(); //Save surrounding maps
				PluginManager.fireMapSave(currentBank, currentMap);
			}
		});
		btnSaveMap.setIcon(new ImageIcon(MainGUI.class.getResource("/resources/mapsave.png")));
		btnSaveMap.setPreferredSize(new Dimension(48, 48));
		btnSaveMap.setFocusPainted(false);
		btnSaveMap.setBorderPainted(false);
		panelButtons.add(btnSaveMap);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(7);
		horizontalStrut_2.setForeground(Color.BLACK);
		panelButtons.add(horizontalStrut_2);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1, 46));
		panel.setBackground(SystemColor.windowBorder);
		panelButtons.add(panel);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(7);
		horizontalStrut_3.setForeground(Color.BLACK);
		panelButtons.add(horizontalStrut_3);
		btnNewMap.setFocusPainted(false);
		btnNewMap.setBorderPainted(false);
		btnNewMap.setPreferredSize(new Dimension(48, 48));
		panelButtons.add(btnNewMap);

		JButton btnImportMap = new JButton("");
		btnImportMap.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				MapEditorPanel.renderTileset = !MapEditorPanel.renderTileset;
				mapEditorPanel.repaint();
			}
		});
		btnImportMap.setFocusPainted(false);
		btnImportMap.setBorderPainted(false);
		btnImportMap.setPreferredSize(new Dimension(48, 48));
		panelButtons.add(btnImportMap);
		
	}
	
	void CreateWildPokemonPanel(){
		

		 
		wildPokemonPanel = new JPanel();
		editorTabs.addTab("Wild Pokemon", null, wildPokemonPanel, null);
		wildPokemonPanel.setLayout(new BorderLayout(0, 0));
		
		panel_8 = new JPanel();
		panel_8.setPreferredSize(new Dimension(100, 10));
		wildPokemonPanel.add(panel_8, BorderLayout.WEST);
		
		panel_6 = new JPanel();
		wildPokemonPanel.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel_1 = new JLabel("<html>\n<center>\nHey there,\n<br/>\n<br/>\nFirst off I'd like to thank you for taking the time to make your way to this tab. It seems that you are very interested in editing Wild Pokemon, because that is obviously the name of this tab. Unfortunately neither Shiny Quagsire nor interdpth have actually implemented this feature so we put this giant block of text here to tell you that this feature isn't implemented.\n</center>\n</html>");
		panel_6.add(lblNewLabel_1, BorderLayout.CENTER);
		
		lblNewLabel = new JLabel("Hey there,\nFirst off I'd like to thank you for taking the time to make your way to this tab. It seems that you are very interested in editing Wild Pokemon, because that is obviously the name of this tab. Unfortunately neither Shiny Quagsire nor interdpth have actually implemented this feature so we put this giant block of text here to tell you that this feature isn't implemented.");
		lblNewLabel.setPreferredSize(new Dimension(51215, 15));
		lblNewLabel.setMaximumSize(new Dimension(512, 15));
		//panel_6.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(100, 10));
		wildPokemonPanel.add(panel, BorderLayout.EAST);
	}
	JPanel mimePanel;//Mr. Mime 2 dirty 4 mii
	private JPanel panel_4;
	void CreateMimeTab(){
		mimePanel = new JPanel();
		editorTabs.addTab("Mime", null, mimePanel, null);
		mimePanel.setLayout(new BorderLayout(0, 0));
		panel_1 = new JPanel();
		mimePanel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblWelcome = new JLabel("<html><center>Welcome to the map mime!\n<br>\nHere we will mime out your map so you can like see it, but without actually physically seeing it and stuff.</center></html>");
		lblWelcome.setBounds(90, 12, 559, 63);
		panel_1.add(lblWelcome);

		lblWidth = new JLabel("Width: ");
		lblWidth.setBounds(64, 107, 65, 15);
		panel_1.add(lblWidth);

		lblHeight = new JLabel("Height: ");
		lblHeight.setBounds(64, 134, 65, 15);
		panel_1.add(lblHeight);

		lblBorderHeight = new JLabel("Border Height: ");
		lblBorderHeight.setBounds(64, 178, 164, 15);
		panel_1.add(lblBorderHeight);

		lblMapTilesPointer = new JLabel("Map Tiles Pointer: ");
		lblMapTilesPointer.setBounds(245, 107, 339, 15);
		panel_1.add(lblMapTilesPointer);

		lblBorderTilesPointer = new JLabel("Border Tiles Pointer:");
		lblBorderTilesPointer.setBounds(245, 123, 339, 15);
		panel_1.add(lblBorderTilesPointer);

		lblGlobalTilesetPointer = new JLabel("Global Tileset Pointer:");
		lblGlobalTilesetPointer.setBounds(245, 162, 339, 15);
		panel_1.add(lblGlobalTilesetPointer);

		lblLocalTilesetPointer = new JLabel("Local  Tileset  Pointer:");
		lblLocalTilesetPointer.setBounds(245, 178, 339, 15);
		panel_1.add(lblLocalTilesetPointer);

		lblBorderWidth = new JLabel("Border Width: ");
		lblBorderWidth.setBounds(64, 162, 157, 15);
		panel_1.add(lblBorderWidth);
	}
	void CreateTabbedPanels(){
		getContentPane().add(splitPane, BorderLayout.CENTER);

	    editorTabs = new JTabbedPane(SwingConstants.TOP);
		splitPane.setRightComponent(editorTabs);
        CreateMapPanel();
        CreateBorderArea();


		panelTilesContainer.add(panelMapTilesContainer, BorderLayout.NORTH);
		
		panelTilesContainer.add(splitterMapTiles, BorderLayout.WEST);
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				System.out.println("" + tabbedPane.getSelectedIndex());
				if(tabbedPane.getSelectedIndex() == 0)
					MapEditorPanel.setMode(EditMode.TILES);
				else
					MapEditorPanel.setMode(EditMode.MOVEMENT);
				
				MapEditorPanel.Redraw = true;
				mapEditorPanel.repaint();
				borderTileEditor.repaint();
			}
		});
		tabbedPane.setBorder(null);
		panelTilesContainer.add(tabbedPane, BorderLayout.CENTER);
		tileEditorPanel = new TileEditorPanel();
		
		tileEditorPanel.setPreferredSize(new Dimension((TileEditorPanel.editorWidth)*16+16, ((DataStore.EngineVersion == 1 ? 0x200 + 0x56 : 0x200 + 0x300)/TileEditorPanel.editorWidth)*16));
		tileEditorPanel.setSize(tileEditorPanel.getPreferredSize());
		tileEditorPanel.setLayout(null);
		
		tilesetScrollPane = new JScrollPane(tileEditorPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tilesetScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		tilesetScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		tilesetScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		tabbedPane.addTab("Tiles", null, tilesetScrollPane, null);
		
		permissionTilePanel = new PermissionTilePanel();
		permissionTilePanel.setLayout(null);	
		movementScrollPane = new JScrollPane(permissionTilePanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		movementScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		movementScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		movementScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		tabbedPane.addTab("Movement", null, movementScrollPane, null);
		
		CreateToolbar();
		CreateTilesetArea();
		
		CreateEventsPanel();
		CreateWildPokemonPanel();
		CreateMimeTab();

		

	
	}
	
	int paneSize2;
	public static JPanel panel_5;
	private JMenuItem mnOpen;
	private JMenuItem mnSave;
	private JMenuItem mntmNewMenuItem_1;
	private JCheckBoxMenuItem chckbxmntmUsePlugins;
	private PermissionTilePanel permissionTilePanel;
	private JScrollPane movementScrollPane;
	private JPanel panel_7;
	private JPanel connectionsTabPanel;
	private JPanel connectinonsInfoPanel;
	private JScrollPane connectionsEditorScroll;
	private JLabel lblNewLabel;
	private JPanel panel_6;
	private JPanel panel_8;
	private JLabel lblNewLabel_1;
	private static JSpinner spnHeight;
	private static JSpinner spnWidth;
	
	void CreateSplitPane(){
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.2);
		splitPane.setDividerSize(1);
		splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, 
				new PropertyChangeListener() {
			
			public void propertyChange(PropertyChangeEvent e)
			{
				if(((JSplitPane)e.getSource()).getDividerLocation() > 300)
					((JSplitPane)e.getSource()).setDividerLocation(350);
				else if(((JSplitPane)e.getSource()).getDividerLocation() < 25)
					((JSplitPane)e.getSource()).setDividerLocation(25);

				if(paneSize == 0)
				{
					paneSize = 250;
					((JSplitPane)e.getSource()).setDividerLocation(paneSize);
				}
				else
					paneSize = ((JSplitPane)e.getSource()).getDividerLocation();
			}
		});
		splitPane.addComponentListener(new ComponentAdapter() {
			
			public void componentResized(ComponentEvent e) 
			{
				if(((JSplitPane)e.getSource()).getDividerLocation() > 300)
					((JSplitPane)e.getSource()).setDividerLocation(350);
				else if(((JSplitPane)e.getSource()).getDividerLocation() < 25)
					((JSplitPane)e.getSource()).setDividerLocation(25);
				else
					((JSplitPane)e.getSource()).setDividerLocation(paneSize);

				if(paneSize == 0)
				{
					paneSize = 250;
					((JSplitPane)e.getSource()).setDividerLocation(paneSize);
				}
				else
					paneSize = ((JSplitPane)e.getSource()).getDividerLocation();
			}
		});
		splitPane.setDividerLocation(0.2);
	}
	void CreateStatusBar(){
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_2.setPreferredSize(new Dimension(10, 24));
		getContentPane().add(panel_2, BorderLayout.SOUTH);

		lblInfo = new JLabel("No ROM Loaded!");
		panel_2.add(lblInfo);
	}
	public void CreatePermissions(){

		JPanel splitm = new JPanel();
		splitm.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		splitm.setPreferredSize(new Dimension(4, 10));
		splitm.setMaximumSize(new Dimension(4, 320));
        JPanel pmtc = new JPanel();
		
        pmtc.setLayout(new BorderLayout(0, 0));
    	panelPermissions = new JPanel();
		panelPermissions.setLayout(null);
        
        

        
        
        
	}
	
	public MainGUI()
	{
		setPreferredSize(new Dimension(800, 800));
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) 
			{
				//TODO: Are you *sure* you want to exit / Check for saved changes
				PluginManager.unloadAllPlugins();
				System.exit(0);
			}
		});
		CreateMenus();
		CreateStatusBar();

		CreateButtons();

        CreateSplitPane();


	    CreateTabbedPanels();

		BufferedImage mime = null;
		try
		{
			mime = ImageIO.read(MainGUI.class.getResourceAsStream("/resources/mime.jpg"));
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		ImagePanel mimePic = new ImagePanel(mime);
		mimePic.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		mimePic.setBounds(545, 71, 164, 164);
		panel_1.add(mimePic);
		
		spnWidth = new JSpinner(new SpinnerNumberModel(1,1,9001,1));
		spnWidth.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				if(!lblInfo.getText().startsWith("Done!"))
					return;
				try
				{
					int x = (Integer)spnWidth.getValue();
					int y = (Integer)spnHeight.getValue();
					loadedMap.getMapTileData().resize(x,y);
					mapEditorPanel.Redraw = true;
					eventEditorPanel.Redraw = true;
					connectionsEditorPanel.loadConnections(loadedMap);
					mapEditorPanel.repaint();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		spnWidth.setPreferredSize(new Dimension(40, 24));
		spnWidth.setBounds(130, 105, 50, 24);
		panel_1.add(spnWidth);
		
		spnHeight = new JSpinner(new SpinnerNumberModel(1,1,9001,1));
		spnHeight.setPreferredSize(new Dimension(40, 24));
		spnHeight.setBounds(130, 132, 50, 24);
		spnHeight.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				if(!lblInfo.getText().startsWith("Done!"))
					return;
				try
				{
					int x = (Integer)spnWidth.getValue();
					int y = (Integer)spnHeight.getValue();
					loadedMap.getMapTileData().resize(x,y);
					mapEditorPanel.Redraw = true;
					eventEditorPanel.Redraw = true;
					connectionsEditorPanel.loadConnections(loadedMap);
					mapEditorPanel.repaint();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		panel_1.add(spnHeight);
		
		connectionsTabPanel = new JPanel();
		editorTabs.addTab("Connections", null, connectionsTabPanel, null);
		connectionsTabPanel.setLayout(new BorderLayout(0, 0));
		
		connectinonsInfoPanel = new JPanel();
		connectinonsInfoPanel.setPreferredSize(new Dimension(10, 24));
		connectionsTabPanel.add(connectinonsInfoPanel, BorderLayout.NORTH);
		
		
		connectionsEditorPanel = new ConnectionsEditorPanel();
		connectionsEditorScroll = new JScrollPane(connectionsEditorPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		connectionsEditorScroll.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		connectionsEditorScroll.getVerticalScrollBar().setUnitIncrement(16);
		connectionsEditorScroll.getHorizontalScrollBar().setUnitIncrement(16);
		connectionsEditorScroll.addMouseWheelListener(new MouseWheelListener() 
		{
			public void mouseWheelMoved(MouseWheelEvent e) 
			{
				if(e.isControlDown() || e.isAltDown())
				{
					connectionsEditorPanel.scale += (double)(e.getWheelRotation() / 5d);
					if(connectionsEditorPanel.scale < 0.3)
						connectionsEditorPanel.scale = 0.3;
					else if(connectionsEditorPanel.scale > 10)
						connectionsEditorPanel.scale = 10;
					connectionsEditorPanel.RescaleImages(false);
					connectionsEditorPanel.repaint();
				}
				else
				{
					// Vertical scrolling
	                Adjustable adj = connectionsEditorScroll.getVerticalScrollBar();
	                int scroll = e.getUnitsToScroll() * adj.getBlockIncrement();
	                adj.setValue(adj.getValue() + scroll);
				}
			}
		});
		connectionsTabPanel.add(connectionsEditorScroll, BorderLayout.CENTER);
		
		CreatePermissions();
		JPanel panel_3 = new JPanel();
		splitPane.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		mapBanks = new JTree();
		mapBanks.addKeyListener(new KeyAdapter() {
			
			public void keyTyped(KeyEvent e) 
			{
				loadMap();
			}
		});
		mapBanks.addTreeSelectionListener(new TreeSelectionListener() {
			
			public void valueChanged(TreeSelectionEvent e) 
			{
				try
				{
					String s = ((DefaultMutableTreeNode)e.getPath().getPath()[2]).toString();
					s = s.split("\\(")[s.split("\\(").length - 1].replace(")", "");
					selectedBank = Integer.parseInt(s.split("\\.")[0]);
					selectedMap = Integer.parseInt(s.split("\\.")[1]);
				}
				catch(Exception ex){}
			}
		});
		mapBanks.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		mapBanks.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getButton() == MouseEvent.BUTTON1)
				{
					if(e.getClickCount() == 2)
					{
						loadMap();
					}
				}
			}
		});
		mapBanks.setModel(new DefaultTreeModel(
				new DefaultMutableTreeNode("By Bank...") {

				}
				));
		mapBanks.setCellRenderer(new MapTreeRenderer());

		JScrollPane mapPane = new JScrollPane(mapBanks,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mapPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		panel_3.add(mapPane);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public static void reloadMimeLabels()
	{
		spnWidth.setValue((int)loadedMap.getMapData().mapWidth);
		spnHeight.setValue((int)loadedMap.getMapData().mapHeight);
		lblBorderWidth.setText("Border Width: " + loadedMap.getMapData().borderWidth);
		lblBorderHeight.setText("Border Height: " + loadedMap.getMapData().borderHeight);
		lblMapTilesPointer.setText("Map Tiles Pointer: " + BitConverter.toHexString(loadedMap.getMapData().mapTilesPtr));
		lblBorderTilesPointer.setText("Border Tiles Pointer: " + BitConverter.toHexString(loadedMap.getMapData().borderTilePtr));
		lblGlobalTilesetPointer.setText("Global Tileset Pointer: " + BitConverter.toHexString(loadedMap.getMapData().globalTileSetPtr));
		lblLocalTilesetPointer.setText("Local  Tileset  Pointer: " + BitConverter.toHexString(loadedMap.getMapData().localTileSetPtr));
	}
    
	public static void loadMap(int bank, int map)
	{
		selectedMap = map;
		selectedBank = bank;
		loadMap();
	}
	
	private static void loadMap()
	{
		lblInfo.setText("Loading map...");
		new Thread()
		{
			
			public void run()
			{
				Date d = new Date();
				boolean firstLoad = false;
				if(loadedMap != null)
					TilesetCache.get(loadedMap.getMapData().globalTileSetPtr).resetCustomTiles(); //Clean up any custom rendered tiles
				
				long offset=BankLoader.maps[selectedBank].get(selectedMap);
				loadedMap = new Map(ROMManager.getActiveROM(), (int)(offset));
				currentBank = selectedBank;
				currentMap = selectedMap;
				TilesetCache.switchTileset(loadedMap);
				
				borderMap = new BorderMap(ROMManager.getActiveROM(), loadedMap);
				reloadMimeLabels();
				mapEditorPanel.setGlobalTileset(TilesetCache.get(loadedMap.getMapData().globalTileSetPtr));
				mapEditorPanel.setLocalTileset(TilesetCache.get(loadedMap.getMapData().localTileSetPtr));
				eventEditorPanel.setGlobalTileset(TilesetCache.get(loadedMap.getMapData().globalTileSetPtr));
				eventEditorPanel.setLocalTileset(TilesetCache.get(loadedMap.getMapData().localTileSetPtr));

				tileEditorPanel.setGlobalTileset(TilesetCache.get(loadedMap.getMapData().globalTileSetPtr));
				tileEditorPanel.setLocalTileset(TilesetCache.get(loadedMap.getMapData().localTileSetPtr));
				tileEditorPanel.DrawTileset();
				tileEditorPanel.repaint();
				
				
				mapEditorPanel.setMap(loadedMap);
				mapEditorPanel.DrawMap();
				mapEditorPanel.DrawMovementPerms();
				mapEditorPanel.repaint();
				
				eventEditorPanel.setMap(loadedMap);
				eventEditorPanel.Redraw = true;
				eventEditorPanel.DrawMap();
				eventEditorPanel.repaint();
				borderTileEditor.setGlobalTileset(TilesetCache.get(loadedMap.getMapData().globalTileSetPtr));
				borderTileEditor.setLocalTileset(TilesetCache.get(loadedMap.getMapData().localTileSetPtr));
				borderTileEditor.setMap(borderMap);
				borderTileEditor.repaint();
				connectionsEditorPanel.loadConnections(loadedMap);
				connectionsEditorPanel.repaint();

				mapEditorPanel.repaint();
				Date eD = new Date();
				long time = eD.getTime() - d.getTime();
				MainGUI.lblInfo.setText("Done! Finished in " + (double)(time / 1000) + " seconds!");
				
				PluginManager.fireMapLoad(selectedBank, selectedMap);
				
			}
		}.start();
	}

	public static void repaintTileEditorPanel()
	{
		tileEditorPanel.repaint();
	}
	
	public void openROM()
	{
		int i = GBARom.loadRom();

		dataStore = new DataStore("MEH.ini", ROMManager.currentROM.getGameCode() );

		if(1 != -1)
		{
			mapBanks.setModel(new DefaultTreeModel(
					new DefaultMutableTreeNode("By Bank...") {

					}
					));
			DefaultTreeModel model = (DefaultTreeModel) mapBanks.getModel();
			model.reload();
			BankLoader.reset();
			TilesetCache.clearCache();
			mapEditorPanel.reset();
			borderTileEditor.reset();
			tileEditorPanel.reset();
			mapEditorPanel.repaint();
			borderTileEditor.repaint();
			tileEditorPanel.repaint();
		}
		lblInfo.setText("Loading...");
		
		//chckbxmntmDrawSprites.setSelected(DataStore.mehSettingShowSprites==1); TODO Redo this in settings
		new BankLoader((int)DataStore.MapHeaders,ROMManager.getActiveROM(),lblInfo,mapBanks).start();
		new WildDataCache(ROMManager.getActiveROM()).start();
		mnSave.enable(true);
		PluginManager.fireROMLoad();
	}
	
	public void saveROM()
	{
		PluginManager.fireROMSave();
		ROMManager.getActiveROM().commitChangesToROMFile();
	}
	
	public static void openScript(int scriptOffset)
	{
		if(DataStore.mehSettingCallScriptEditor == null || DataStore.mehSettingCallScriptEditor.isEmpty())
		{
			int reply = JOptionPane.showConfirmDialog(null, "It appears that you have no script editor registered with MEH. Would you like to search for one?", "You need teh Script Editorz!!!", JOptionPane.YES_NO_OPTION);
			if(reply == JOptionPane.YES_OPTION)
			{
				FileDialog fd = new FileDialog(new Frame(), "Choose your script editor...", FileDialog.LOAD);
				fd.setFilenameFilter(new FilenameFilter()
				{
				    public boolean accept(File dir, String name)
				    {
				      return ((System.getProperty("os.name").toLowerCase().contains("win") ? name.toLowerCase().endsWith(".exe") : name.toLowerCase().endsWith(".*")) || name.toLowerCase().endsWith(".jar"));
				    }
				 });
				//fd.setDirectory(GlobalVars.LastDir);
				fd.show();
				String location = fd.getDirectory() + fd.getFile();
				if(location.isEmpty())
					return;
				
				DataStore.mehSettingCallScriptEditor = location;
			}
		}
		
		try
		{
			Runtime r = Runtime.getRuntime();
			r.exec((DataStore.mehSettingCallScriptEditor.toLowerCase().endsWith(".jar") ? "java -jar" : "") + DataStore.mehSettingCallScriptEditor + " \"" + ROMManager.currentROM.input_filepath.replace("\"", "") + "\" 0x" + String.format("%x", scriptOffset));
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "It seems that your script editor has gone missing. Look around for it and try it again. I'm sure it'll work eventually.");
			e.printStackTrace();
		}
	}
}
