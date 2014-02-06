package us.plxhack.MEH.UI;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ImagePanel;
import org.zzl.minegaming.GBAUtils.ROMManager;

import us.plxhack.MEH.IO.BankLoader;
import us.plxhack.MEH.IO.BorderMap;
import us.plxhack.MEH.IO.Map;
import us.plxhack.MEH.IO.TilesetCache;
import us.plxhack.MEH.MapElements.WildData;
import us.plxhack.MEH.MapElements.WildDataCache;
import us.plxhack.MEH.MapElements.WildDataType;
import us.plxhack.MEH.Plugins.Plugin;
import us.plxhack.MEH.Plugins.PluginManager;
import us.plxhack.MEH.Structures.ConnectionType;
import us.plxhack.MEH.Structures.EditMode;
import us.plxhack.MEH.Structures.EventType;

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
	public static int currentType = 0;
	private static int eventIndex;
	private static EventType eventType;
	private static int popupX;
	private static int popupY;
	int paneSize2;

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
	public static JLabel lblEncounterPercent;

	public static JPanel panel_5;
	private JMenuItem mnOpen;
	private JMenuItem mnSave;
	private JMenuItem mntmNewMenuItem_1;
	private PermissionTilePanel permissionTilePanel;
	private JScrollPane movementScrollPane;
	private JPanel connectionsTabPanel;
	private JPanel connectinonsInfoPanel;
	private JScrollPane connectionsEditorScroll;
	private JLabel lblNewLabel;
	private JPanel panelWildEditor;
	private static JSpinner spnHeight;
	private static JSpinner spnWidth;
	private JMenu mnAddCon;
	private JMenuItem mntmLeftCon;
	private JMenuBar menuBar_1;
	private JMenuItem mntmRightCon;
	private JMenuItem mntmUpCon;
	private JMenuItem mntmDownCon;

	// Wild Data Editor
	private static JSpinner pkMin1;
	private static JSpinner pkMax1;
	private static JComboBox pkName1;
	private static JSpinner pkNo1;
	private static JPanel panelpk1_5;
	private static JLabel lblpkmax;
	private static JLabel lblPkMn;
	private static JLabel lblpknum;
	private static JLabel lblpkchance;
	private static JLabel pkchance1;
	private static JSpinner pkMin2;
	private static JSpinner pkMax2;
	private static JComboBox pkName2;
	private static JSpinner pkNo2;
	private static JLabel pkchance2;
	private static JSpinner pkMin3;
	private static JSpinner pkMax3;
	private static JComboBox pkName3;
	private static JSpinner pkNo3;
	private static JLabel pkchance3;
	private static JSpinner pkMin4;
	private static JSpinner pkMax4;
	private static JComboBox pkName4;
	private static JSpinner pkNo4;
	private static JLabel pkchance4;
	private static JSpinner pkMin5;
	private static JSpinner pkMax5;
	private static JComboBox pkName5;
	private static JSpinner pkNo5;
	private static JLabel pkchance5;
	private static JPanel panelpk6_10;
	private static JSpinner pkMin6;
	private static JSpinner pkMax6;
	private static JComboBox pkName6;
	private static JSpinner pkNo6;
	private static JLabel pkchance6;
	private static JSpinner pkMin7;
	private static JSpinner pkMax7;
	private static JComboBox pkName7;
	private static JSpinner pkNo7;
	private static JLabel pkchance7;
	private static JSpinner pkMin8;
	private static JSpinner pkMax8;
	private static JComboBox pkName8;
	private static JSpinner pkNo8;
	private static JLabel pkchance8;
	private static JSpinner pkMin9;
	private static JSpinner pkMax9;
	private static JComboBox pkName9;
	private static JSpinner pkNo9;
	private static JLabel pkchance9;
	private static JSpinner pkMin10;
	private static JSpinner pkMax10;
	private static JComboBox pkName10;
	private static JSpinner pkNo10;
	private static JLabel pkchance10;
	private static JPanel panelpk11_12;
	private static JSpinner pkMin11;
	private static JSpinner pkMin12;
	private static JSpinner pkMax11;
	private static JSpinner pkMax12;
	private static JComboBox pkName11;
	private static JComboBox pkName12;
	private static JSpinner pkNo11;
	private static JSpinner pkNo12;
	private static JLabel pkchance11;
	private static JLabel pkchance12;
	private static JScrollPane scrollPaneWildEditor;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JComboBox pkTime;
	private JComboBox pkEnvironment;
	
	// Map Creation
	JPanel panelMapTilesContainer;
	JPanel splitterMapTiles;
	public static MapEditorPanel mapEditorPanel;
	public JScrollPane mapScrollPane;
	JScrollPane tilesetScrollPane;
	JPanel eventsPanel;

	private JPanel panel_1;
	JPanel panelTilesContainer;
	JPanel wildPokemonPanel;
	JPanel panelPermissions;
	public static BorderEditorPanel borderTileEditor;
	public static EventEditorPanel eventEditorPanel;
	public static TileEditorPanel tileEditorPanel;
	public static ConnectionsEditorPanel connectionsEditorPanel;
	public static JLabel lblTileVal;
	public static JPopupMenu popupMenu;
	public DataStore dataStore;
	private JPanel editorPanel;
	static boolean doneLoading = false;

	void CreateToolbar()
	{
		JToolBar toolBar = new JToolBar();
		lblTileVal = new JLabel("Current Tile: 0x0000");
		toolBar.add(lblTileVal);
		editorPanel.add(toolBar, BorderLayout.NORTH);
		toolBar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBar.setPreferredSize(new Dimension(128, 32));
	}

	void CreateBorderArea()
	{
		JPanel panelBorderTilesContainer = new JPanel();
		panelBorderTilesContainer.setPreferredSize(new Dimension(10, 100));
		panelMapTilesContainer.add(panelBorderTilesContainer, BorderLayout.NORTH);
		panelBorderTilesContainer.setLayout(new BorderLayout(0, 0));

		JPanel panelBorderTilesSplitter = new JPanel();
		panelBorderTilesSplitter.setBackground(SystemColor.controlShadow);
		panelBorderTilesSplitter.setPreferredSize(new Dimension(10, 1));
		panelBorderTilesContainer.add(panelBorderTilesSplitter, BorderLayout.SOUTH);
		// Set up tileset

		JPanel panelBorderTilesToAbsolute = new JPanel();
		panelBorderTilesContainer.add(panelBorderTilesToAbsolute, BorderLayout.CENTER);
		panelBorderTilesToAbsolute.setLayout(null);

		borderTileEditor = new BorderEditorPanel();
		borderTileEditor.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Border Tiles", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		borderTileEditor.setBounds(12, 12, 114, 75);
		panelBorderTilesToAbsolute.add(borderTileEditor);
	}

	void CreateEventsPanel()
	{

		eventsPanel = new JPanel();
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
		JScrollPane selectedEventScroll = new JScrollPane(panel_5, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		eventsPanel.add(selectedEventScroll, BorderLayout.EAST);
		panel_5.setLayout(new BorderLayout(0, 0));
		eventEditorPanel = new EventEditorPanel();

		popupMenu = new JPopupMenu();
		addPopup(eventEditorPanel, popupMenu);

		JMenuItem mntmEditScript = new JMenuItem("Edit Script");
		mntmEditScript.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int offset = 0;
				switch (eventType)
				{
					case NPC:
						offset = (int) loadedMap.mapNPCManager.mapNPCs.get(eventIndex).pScript;
						break;
					case SIGN:
						offset = (int) loadedMap.mapSignManager.mapSigns.get(eventIndex).pScript;
						break;

					case TRIGGER:
						offset = (int) loadedMap.mapTriggerManager.mapTriggers.get(eventIndex).pScript;
						break;

					default:
						break;
				}

				if (offset != 0)
				{
					try
					{
						String[] Params = new String[] { ROMManager.currentROM.input_filepath, Long.toHexString(offset) };
						Process p = Runtime.getRuntime().exec(DataStore.mehSettingCallScriptEditor + " " + ROMManager.currentROM.input_filepath + ":" + Long.toHexString(offset));// ",Params);
					}
					catch (Exception e1)
					{
						MainGUI.lblInfo.setText("Script failed to load, please check to see if " + DataStore.mehSettingCallScriptEditor + " exists");
					}
				}
			}
		});
		popupMenu.add(mntmEditScript);

		JMenuItem mntmRemoveEvent = new JMenuItem("Remove Event");
		mntmRemoveEvent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(loadedMap.mapExitManager.getSpriteIndexAt(popupX, popupY) != -1)
				{
					int result = JOptionPane.showConfirmDialog(new JFrame(),"Removing warps is currently slightly buggy and may cause existing warps in the map to break.\nWe currently advise to just temporarily relocate warps while this is sorted out.\nAre you sure you want to remove this warp?","ZOMG R U CRAZY???", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION)
						loadedMap.mapExitManager.remove(popupX, popupY);
				}
				else if(loadedMap.mapSignManager.getSpriteIndexAt(popupX, popupY) != -1)
				{
					loadedMap.mapSignManager.remove(popupX, popupY);
				}
				else if(loadedMap.mapNPCManager.getSpriteIndexAt(popupX, popupY) != -1)
				{
					loadedMap.mapNPCManager.remove(popupX, popupY);
				}
				else if(loadedMap.mapTriggerManager.getSpriteIndexAt(popupX, popupY) != -1)
				{
					loadedMap.mapTriggerManager.remove(popupX, popupY);
				}
				
				eventEditorPanel.Redraw = true;
				eventEditorPanel.repaint();
			}
		});
		popupMenu.add(mntmRemoveEvent);

		JMenu mnAdd = new JMenu("Add...");
		popupMenu.add(mnAdd);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Warp");
		mntmNewMenuItem_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				loadedMap.mapExitManager.add(popupX, popupY);
				eventEditorPanel.Redraw = true;
				eventEditorPanel.repaint();
			}
		});
		mnAdd.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("NPC");
		mntmNewMenuItem_3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				loadedMap.mapNPCManager.add(popupX, popupY);
				eventEditorPanel.Redraw = true;
				eventEditorPanel.repaint();
			}
		});
		mnAdd.add(mntmNewMenuItem_3);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Sign");
		mntmNewMenuItem_4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				loadedMap.mapSignManager.add(popupX, popupY);
				eventEditorPanel.Redraw = true;
				eventEditorPanel.repaint();
			}
		});
		mnAdd.add(mntmNewMenuItem_4);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Trigger Script");
		mntmNewMenuItem_5.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				loadedMap.mapTriggerManager.add(popupX, popupY);
				eventEditorPanel.Redraw = true;
				eventEditorPanel.repaint();
			}
		});
		mnAdd.add(mntmNewMenuItem_5);
		eventEditorPanel.setLayout(null);

		JScrollPane eventScrollPane = new JScrollPane(eventEditorPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		eventsPanel.add(eventScrollPane, BorderLayout.CENTER);
		eventScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		eventScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		eventScrollPane.getHorizontalScrollBar().setUnitIncrement(16);

	}

	void CreateMapPanel()
	{
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

		mapScrollPane = new JScrollPane(mapEditorPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		editorPanel.add(mapScrollPane, BorderLayout.CENTER);
		mapScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		mapScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		mapScrollPane.getHorizontalScrollBar().setUnitIncrement(16);

		panelTilesContainer = new JPanel();
		editorPanel.add(panelTilesContainer, BorderLayout.EAST);
		panelTilesContainer.setPreferredSize(new Dimension((TileEditorPanel.editorWidth + 1) * 16 + 13, 10));
		panelTilesContainer.setLayout(new BorderLayout(0, 0));

	}

	void CreateMenus()
	{
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
				connectionsEditorPanel.save(); // Save surrounding maps
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
				connectionsEditorPanel.save(); // Save surrounding maps
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
				// TODO
			}
		});
		mnSettings.add(mntmPreferences);

		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
	}

	public static JPanel panelButtons;

	void CreateButtons()
	{
		System.out.println("Resource path:" + MainGUI.class.getResource("."));
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
				connectionsEditorPanel.save(); // Save surrounding maps
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
				connectionsEditorPanel.save(); // Save surrounding maps
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

	private static JSlider pkEncounter;

	void CreateWildPokemonPanel()
	{

		wildPokemonPanel = new JPanel();
		editorTabs.addTab("Wild Pokemon", null, wildPokemonPanel, null);
		wildPokemonPanel.setLayout(new BorderLayout(0, 0));

		panelWildEditor = new JPanel();
		scrollPaneWildEditor = new JScrollPane(panelWildEditor);
		scrollPaneWildEditor.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneWildEditor.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneWildEditor.getVerticalScrollBar().setUnitIncrement(16);
		wildPokemonPanel.add(scrollPaneWildEditor, BorderLayout.CENTER);
		panelWildEditor.setLayout(null);
		panelWildEditor.setPreferredSize(new Dimension(panelWildEditor.getWidth(), 550));

		JPanel panelWildHeader = new JPanel();
		panelWildHeader.setBounds(12, 12, 544, 83);
		panelWildEditor.add(panelWildHeader);
		panelWildHeader.setLayout(null);

		lblNewLabel_1 = new JLabel("Environment:");
		lblNewLabel_1.setBounds(0, 5, 93, 15);
		panelWildHeader.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Time of Day:");
		lblNewLabel_2.setBounds(320, 5, 93, 15);
		panelWildHeader.add(lblNewLabel_2);

		pkTime = new JComboBox();
		pkTime.setEnabled(false);
		pkTime.setModel(new DefaultComboBoxModel(new String[] { "Morning", "Day", "Evening", "Night" }));
		pkTime.setSelectedIndex(1);
		pkTime.setBounds(409, 0, 112, 24);
		panelWildHeader.add(pkTime);

		pkEnvironment = new JComboBox();
		pkEnvironment.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				currentType = pkEnvironment.getSelectedIndex();
				loadWildPokemon();
			}
		});
		pkEnvironment.setModel(new DefaultComboBoxModel(new String[] { "Grass", "Water", "Rock Smash", "Fishing" }));
		pkEnvironment.setBounds(98, 0, 144, 24);
		panelWildHeader.add(pkEnvironment);

		JLabel lblNewLabel_3 = new JLabel("Total Encounter Rate:");
		lblNewLabel_3.setBounds(0, 55, 154, 15);
		panelWildHeader.add(lblNewLabel_3);

		pkEncounter = new JSlider();
		pkEncounter.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{
					int percent = Math.round((pkEncounter.getValue() / 255.0f) * 100);
					lblEncounterPercent.setText(percent + "%");

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					d.aWildPokemon[currentType].bRatio = (byte) pkEncounter.getValue();
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkEncounter.setPaintTicks(true);
		pkEncounter.setMajorTickSpacing(64);
		pkEncounter.setValue(128);
		pkEncounter.setMaximum(255);
		pkEncounter.setBounds(158, 32, 200, 52);
		panelWildHeader.add(pkEncounter);

		lblEncounterPercent = new JLabel("50%");
		lblEncounterPercent.setBounds(370, 55, 70, 15);
		panelWildHeader.add(lblEncounterPercent);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setDividerSize(0);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setEnabled(false);
		splitPane_1.setBounds(409, 30, 99, 52);
		panelWildHeader.add(splitPane_1);

		JButton btnAddPokeData = new JButton("Add");
		btnAddPokeData.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WildDataCache.createWildDataIfNotExists(currentBank, currentMap).addWildData(WildDataType.values()[currentType]);
				loadWildPokemon();
			}
		});
		splitPane_1.setLeftComponent(btnAddPokeData);

		JButton btnRemovePokeData = new JButton("Remove");
		btnRemovePokeData.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (WildDataCache.getWildData(currentBank, currentMap) == null)
					return;
				WildDataCache.getWildData(currentBank, currentMap).removeWildData(WildDataType.values()[currentType]);
				loadWildPokemon();
			}
		});
		splitPane_1.setRightComponent(btnRemovePokeData);
		splitPane_1.setDividerLocation(25);

		panelpk1_5 = new JPanel();
		panelpk1_5.setBounds(12, 100, 544, 202);
		panelWildEditor.add(panelpk1_5);
		panelpk1_5.setLayout(null);

		JLabel lblpkmin = new JLabel("Min");
		lblpkmin.setBounds(22, 12, 50, 15);
		panelpk1_5.add(lblpkmin);

		lblpkmax = new JLabel("Max");
		lblpkmax.setBounds(80, 12, 50, 15);
		panelpk1_5.add(lblpkmax);

		lblPkMn = new JLabel("Pokemon");
		lblPkMn.setHorizontalAlignment(SwingConstants.CENTER);
		lblPkMn.setBounds(147, 12, 159, 15);
		panelpk1_5.add(lblPkMn);

		lblpknum = new JLabel("No.");
		lblpknum.setHorizontalAlignment(SwingConstants.CENTER);
		lblpknum.setBounds(306, 12, 50, 15);
		panelpk1_5.add(lblpknum);

		lblpkchance = new JLabel("Chance");
		lblpkchance.setBounds(382, 12, 70, 15);
		panelpk1_5.add(lblpkchance);

		pkMin1 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMin1.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMin1.getValue();
					d.aWildPokemon[currentType].aWildPokemon[0].bMinLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkMin1.setBounds(12, 30, 60, 32);
		panelpk1_5.add(pkMin1);

		pkMax1 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMax1.setBounds(75, 30, 60, 32);
		panelpk1_5.add(pkMax1);

		pkName1 = new JComboBox();
		pkName1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pkNo1.setValue(pkName1.getSelectedIndex());
				try
				{
					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					d.aWildPokemon[currentType].aWildPokemon[0].wNum = pkName1.getSelectedIndex();
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkName1.setBounds(141, 30, 165, 32);
		panelpk1_5.add(pkName1);

		pkNo1 = new JSpinner(new SpinnerNumberModel(1, 1, DataStore.NumPokemon, 1));
		pkNo1.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				int i = (Integer) pkNo1.getValue();
				pkName1.setSelectedIndex(i);
			}
		});
		pkNo1.setBounds(306, 30, 72, 32);
		panelpk1_5.add(pkNo1);

		pkchance1 = new JLabel("20%");
		pkchance1.setHorizontalAlignment(SwingConstants.LEFT);
		pkchance1.setBounds(395, 38, 125, 15);
		panelpk1_5.add(pkchance1);

		pkMin2 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMin2.setBounds(12, 65, 60, 32);
		pkMin2.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMin2.getValue();
					d.aWildPokemon[currentType].aWildPokemon[1].bMinLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		panelpk1_5.add(pkMin2);

		pkMax2 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMax2.setBounds(75, 65, 60, 32);
		panelpk1_5.add(pkMax2);

		pkName2 = new JComboBox();
		pkName2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pkNo2.setValue(pkName2.getSelectedIndex());
				try
				{
					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					d.aWildPokemon[currentType].aWildPokemon[1].wNum = pkName2.getSelectedIndex();
				}
				catch (Exception ex)
				{
				}
			}
		});

		pkName2.setBounds(141, 65, 165, 32);
		panelpk1_5.add(pkName2);

		pkNo2 = new JSpinner(new SpinnerNumberModel(1, 1, DataStore.NumPokemon, 1));
		pkNo2.setBounds(306, 65, 72, 32);
		pkNo2.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				int i = (Integer) pkNo2.getValue();
				pkName2.setSelectedIndex(i);
			}
		});
		panelpk1_5.add(pkNo2);

		pkchance2 = new JLabel("20%");
		pkchance2.setHorizontalAlignment(SwingConstants.LEFT);
		pkchance2.setBounds(395, 73, 125, 15);
		panelpk1_5.add(pkchance2);

		pkMin3 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMin3.setBounds(12, 98, 60, 32);
		pkMin3.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMin3.getValue();
					d.aWildPokemon[currentType].aWildPokemon[2].bMinLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		panelpk1_5.add(pkMin3);

		pkMax3 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMax3.setBounds(75, 98, 60, 32);
		panelpk1_5.add(pkMax3);

		pkName3 = new JComboBox();
		pkName3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pkNo3.setValue(pkName3.getSelectedIndex());
				try
				{
					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					d.aWildPokemon[currentType].aWildPokemon[2].wNum = pkName3.getSelectedIndex();
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkName3.setBounds(141, 98, 165, 32);
		panelpk1_5.add(pkName3);

		pkNo3 = new JSpinner(new SpinnerNumberModel(1, 1, DataStore.NumPokemon, 1));
		pkNo3.setBounds(306, 98, 72, 32);
		pkNo3.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				int i = (Integer) pkNo3.getValue();
				pkName3.setSelectedIndex(i);
			}
		});
		panelpk1_5.add(pkNo3);

		pkchance3 = new JLabel("10%");
		pkchance3.setHorizontalAlignment(SwingConstants.LEFT);
		pkchance3.setBounds(395, 106, 125, 15);
		panelpk1_5.add(pkchance3);

		pkMin4 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMin4.setBounds(12, 133, 60, 32);
		pkMin4.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMin4.getValue();
					d.aWildPokemon[currentType].aWildPokemon[3].bMinLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		panelpk1_5.add(pkMin4);

		pkMax4 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMax4.setBounds(75, 133, 60, 32);
		panelpk1_5.add(pkMax4);

		pkName4 = new JComboBox();
		pkName4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pkNo4.setValue(pkName4.getSelectedIndex());
				try
				{
					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					d.aWildPokemon[currentType].aWildPokemon[3].wNum = pkName4.getSelectedIndex();
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkName4.setBounds(141, 133, 165, 32);
		panelpk1_5.add(pkName4);

		pkNo4 = new JSpinner(new SpinnerNumberModel(1, 1, DataStore.NumPokemon, 1));
		pkNo4.setBounds(306, 133, 72, 32);
		pkNo4.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				int i = (Integer) pkNo4.getValue();
				pkName4.setSelectedIndex(i);
			}
		});
		panelpk1_5.add(pkNo4);

		pkchance4 = new JLabel("10%");
		pkchance4.setHorizontalAlignment(SwingConstants.LEFT);
		pkchance4.setBounds(395, 141, 125, 15);
		panelpk1_5.add(pkchance4);

		pkMin5 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMin5.setBounds(12, 168, 60, 32);
		pkMin5.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMin5.getValue();
					d.aWildPokemon[currentType].aWildPokemon[4].bMinLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		panelpk1_5.add(pkMin5);

		pkMax5 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMax5.setBounds(75, 168, 60, 32);
		panelpk1_5.add(pkMax5);

		pkName5 = new JComboBox();
		pkName5.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pkNo5.setValue(pkName5.getSelectedIndex());
				try
				{
					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					d.aWildPokemon[currentType].aWildPokemon[4].wNum = pkName5.getSelectedIndex();
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkName5.setBounds(141, 168, 165, 32);
		panelpk1_5.add(pkName5);

		pkNo5 = new JSpinner(new SpinnerNumberModel(1, 1, DataStore.NumPokemon, 1));
		pkNo5.setBounds(306, 168, 72, 32);
		pkNo5.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				int i = (Integer) pkNo5.getValue();
				pkName5.setSelectedIndex(i);
			}
		});
		panelpk1_5.add(pkNo5);

		pkchance5 = new JLabel("10%");
		pkchance5.setHorizontalAlignment(SwingConstants.LEFT);
		pkchance5.setBounds(395, 176, 125, 15);
		panelpk1_5.add(pkchance5);

		panelpk6_10 = new JPanel();
		panelpk6_10.setLayout(null);
		panelpk6_10.setBounds(12, 275, 544, 202);
		panelWildEditor.add(panelpk6_10);

		pkMin6 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMin6.setBounds(12, 30, 60, 32);
		pkMin6.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMin6.getValue();
					d.aWildPokemon[currentType].aWildPokemon[5].bMinLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		panelpk6_10.add(pkMin6);

		pkMax6 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMax6.setBounds(75, 30, 60, 32);
		panelpk6_10.add(pkMax6);

		pkName6 = new JComboBox();
		pkName6.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pkNo6.setValue(pkName6.getSelectedIndex());
				try
				{
					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					d.aWildPokemon[currentType].aWildPokemon[5].wNum = pkName6.getSelectedIndex();
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkName6.setBounds(141, 30, 165, 32);
		panelpk6_10.add(pkName6);

		pkNo6 = new JSpinner(new SpinnerNumberModel(1, 1, DataStore.NumPokemon, 1));
		pkNo6.setBounds(306, 30, 72, 32);
		pkNo6.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				int i = (Integer) pkNo6.getValue();
				pkName6.setSelectedIndex(i);
			}
		});
		panelpk6_10.add(pkNo6);

		pkchance6 = new JLabel("10%");
		pkchance6.setHorizontalAlignment(SwingConstants.LEFT);
		pkchance6.setBounds(395, 38, 137, 15);
		panelpk6_10.add(pkchance6);

		pkMin7 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMin7.setBounds(12, 65, 60, 32);
		pkMin7.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMin7.getValue();
					d.aWildPokemon[currentType].aWildPokemon[6].bMinLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		panelpk6_10.add(pkMin7);

		pkMax7 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMax7.setBounds(75, 65, 60, 32);
		panelpk6_10.add(pkMax7);

		pkName7 = new JComboBox();
		pkName7.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pkNo7.setValue(pkName7.getSelectedIndex());
				try
				{
					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					d.aWildPokemon[currentType].aWildPokemon[6].wNum = pkName7.getSelectedIndex();
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkName7.setBounds(141, 65, 165, 32);
		panelpk6_10.add(pkName7);

		pkNo7 = new JSpinner(new SpinnerNumberModel(1, 1, DataStore.NumPokemon, 1));
		pkNo7.setBounds(306, 65, 72, 32);
		pkNo7.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				int i = (Integer) pkNo7.getValue();
				pkName7.setSelectedIndex(i);
			}
		});
		panelpk6_10.add(pkNo7);

		pkchance7 = new JLabel("5%");
		pkchance7.setHorizontalAlignment(SwingConstants.LEFT);
		pkchance7.setBounds(395, 73, 137, 15);
		panelpk6_10.add(pkchance7);

		pkMin8 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMin8.setBounds(12, 98, 60, 32);
		pkMin8.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMin8.getValue();
					d.aWildPokemon[currentType].aWildPokemon[7].bMinLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		panelpk6_10.add(pkMin8);

		pkMax8 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMax8.setBounds(75, 98, 60, 32);
		panelpk6_10.add(pkMax8);

		pkName8 = new JComboBox();
		pkName8.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pkNo8.setValue(pkName8.getSelectedIndex());
				try
				{
					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					d.aWildPokemon[currentType].aWildPokemon[7].wNum = pkName8.getSelectedIndex();
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkName8.setBounds(141, 98, 165, 32);
		panelpk6_10.add(pkName8);

		pkNo8 = new JSpinner(new SpinnerNumberModel(1, 1, DataStore.NumPokemon, 1));
		pkNo8.setBounds(306, 98, 72, 32);
		pkNo8.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				int i = (Integer) pkNo8.getValue();
				pkName8.setSelectedIndex(i);
			}
		});
		panelpk6_10.add(pkNo8);

		pkchance8 = new JLabel("5%");
		pkchance8.setHorizontalAlignment(SwingConstants.LEFT);
		pkchance8.setBounds(395, 106, 137, 15);
		panelpk6_10.add(pkchance8);

		pkMin9 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMin9.setBounds(12, 133, 60, 32);
		pkMin9.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMin9.getValue();
					d.aWildPokemon[currentType].aWildPokemon[8].bMinLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		panelpk6_10.add(pkMin9);

		pkMax9 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMax9.setBounds(75, 133, 60, 32);
		panelpk6_10.add(pkMax9);

		pkName9 = new JComboBox();
		pkName9.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pkNo9.setValue(pkName9.getSelectedIndex());
				try
				{
					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					d.aWildPokemon[currentType].aWildPokemon[8].wNum = pkName9.getSelectedIndex();
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkName9.setBounds(141, 133, 165, 32);
		panelpk6_10.add(pkName9);

		pkNo9 = new JSpinner(new SpinnerNumberModel(1, 1, DataStore.NumPokemon, 1));
		pkNo9.setBounds(306, 133, 72, 32);
		pkNo9.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				int i = (Integer) pkNo9.getValue();
				pkName9.setSelectedIndex(i);
			}
		});
		panelpk6_10.add(pkNo9);

		pkchance9 = new JLabel("4%");
		pkchance9.setHorizontalAlignment(SwingConstants.LEFT);
		pkchance9.setBounds(395, 141, 137, 15);
		panelpk6_10.add(pkchance9);

		pkMin10 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMin10.setBounds(12, 168, 60, 32);
		pkMin10.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMin10.getValue();
					d.aWildPokemon[currentType].aWildPokemon[9].bMinLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		panelpk6_10.add(pkMin10);

		pkMax10 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMax10.setBounds(75, 168, 60, 32);
		panelpk6_10.add(pkMax10);

		pkName10 = new JComboBox();
		pkName10.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pkNo10.setValue(pkName10.getSelectedIndex());
				try
				{
					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					d.aWildPokemon[currentType].aWildPokemon[9].wNum = pkName10.getSelectedIndex();
				}
				catch (Exception ex)
				{
				}
			}

		});
		pkName10.setBounds(141, 168, 165, 32);
		panelpk6_10.add(pkName10);

		pkNo10 = new JSpinner(new SpinnerNumberModel(1, 1, DataStore.NumPokemon, 1));
		pkNo10.setBounds(306, 168, 72, 32);
		pkNo10.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				int i = (Integer) pkNo10.getValue();
				pkName10.setSelectedIndex(i);
			}
		});
		panelpk6_10.add(pkNo10);

		pkchance10 = new JLabel("4%");
		pkchance10.setHorizontalAlignment(SwingConstants.LEFT);
		pkchance10.setBounds(395, 176, 137, 15);
		panelpk6_10.add(pkchance10);

		panelpk11_12 = new JPanel();
		panelpk11_12.setBounds(12, 477, 544, 73);
		panelWildEditor.add(panelpk11_12);
		panelpk11_12.setLayout(null);

		pkMin11 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMin11.setBounds(12, 0, 60, 32);
		pkMin11.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMin11.getValue();
					d.aWildPokemon[currentType].aWildPokemon[10].bMinLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		panelpk11_12.add(pkMin11);

		pkMin12 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMin12.setBounds(12, 35, 60, 32);
		pkMin12.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMin12.getValue();
					d.aWildPokemon[currentType].aWildPokemon[11].bMinLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		panelpk11_12.add(pkMin12);

		pkMax11 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMax11.setBounds(75, 0, 60, 32);
		panelpk11_12.add(pkMax11);

		pkMax12 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		pkMax12.setBounds(75, 35, 60, 32);
		panelpk11_12.add(pkMax12);

		pkName11 = new JComboBox();
		pkName11.setBounds(141, 0, 165, 32);
		pkName11.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pkNo11.setValue(pkName11.getSelectedIndex());
				try
				{
					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					d.aWildPokemon[currentType].aWildPokemon[10].wNum = pkName11.getSelectedIndex();
				}
				catch (Exception ex)
				{
				}
			}
		});
		panelpk11_12.add(pkName11);

		pkName12 = new JComboBox();
		pkName12.setBounds(141, 35, 165, 32);
		pkName12.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pkNo12.setValue(pkName12.getSelectedIndex());
				try
				{
					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					d.aWildPokemon[currentType].aWildPokemon[11].wNum = pkName12.getSelectedIndex();
				}
				catch (Exception ex)
				{
				}
			}
		});
		panelpk11_12.add(pkName12);

		pkNo11 = new JSpinner(new SpinnerNumberModel(1, 1, DataStore.NumPokemon, 1));
		pkNo11.setBounds(306, 0, 72, 32);
		pkNo11.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				int i = (Integer) pkNo11.getValue();
				pkName11.setSelectedIndex(i);
			}
		});
		panelpk11_12.add(pkNo11);

		pkNo12 = new JSpinner(new SpinnerNumberModel(1, 1, DataStore.NumPokemon, 1));
		pkNo12.setBounds(306, 35, 72, 32);
		pkNo12.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				int i = (Integer) pkNo12.getValue();
				pkName12.setSelectedIndex(i);
			}
		});
		panelpk11_12.add(pkNo12);

		pkMax1.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMax1.getValue();
					d.aWildPokemon[currentType].aWildPokemon[0].bMaxLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkMax2.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMax2.getValue();
					d.aWildPokemon[currentType].aWildPokemon[1].bMaxLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkMax3.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMax3.getValue();
					d.aWildPokemon[currentType].aWildPokemon[2].bMaxLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkMax4.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMax4.getValue();
					d.aWildPokemon[currentType].aWildPokemon[3].bMaxLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkMax5.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMax5.getValue();
					d.aWildPokemon[currentType].aWildPokemon[4].bMaxLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkMax6.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMax6.getValue();
					d.aWildPokemon[currentType].aWildPokemon[5].bMaxLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkMax7.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMax7.getValue();
					d.aWildPokemon[currentType].aWildPokemon[6].bMaxLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkMax8.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMax8.getValue();
					d.aWildPokemon[currentType].aWildPokemon[7].bMaxLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkMax9.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMax9.getValue();
					d.aWildPokemon[currentType].aWildPokemon[8].bMaxLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkMax10.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMax10.getValue();
					d.aWildPokemon[currentType].aWildPokemon[9].bMaxLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkMax11.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMax11.getValue();
					d.aWildPokemon[currentType].aWildPokemon[10].bMaxLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});
		pkMax12.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				try
				{

					WildData d = WildDataCache.getWildData(currentBank, currentMap);
					int i = (Integer) pkMax12.getValue();
					d.aWildPokemon[currentType].aWildPokemon[11].bMaxLV = (byte) i;
				}
				catch (Exception ex)
				{
				}
			}
		});

		pkchance11 = new JLabel("1%");
		pkchance11.setHorizontalAlignment(SwingConstants.LEFT);
		pkchance11.setBounds(395, 8, 137, 15);
		panelpk11_12.add(pkchance11);

		pkchance12 = new JLabel("1%");
		pkchance12.setHorizontalAlignment(SwingConstants.LEFT);
		pkchance12.setBounds(395, 43, 70, 15);
		panelpk11_12.add(pkchance12);

		lblNewLabel = new JLabel("Hey there,\nFirst off I'd like to thank you for taking the time to make your way to this tab. It seems that you are very interested in editing Wild Pokemon, because that is obviously the name of this tab. Unfortunately neither Shiny Quagsire nor interdpth have actually implemented this feature so we put this giant block of text here to tell you that this feature isn't implemented.");
		lblNewLabel.setPreferredSize(new Dimension(51215, 15));
		lblNewLabel.setMaximumSize(new Dimension(512, 15));
	}

	JPanel mimePanel;// Mr. Mime 2 dirty 4 mii
	private JPanel panel_4;

	void CreateMimeTab()
	{
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

	void CreateTabbedPanels()
	{
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
				if (tabbedPane.getSelectedIndex() == 0)
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

		tileEditorPanel.setPreferredSize(new Dimension((TileEditorPanel.editorWidth) * 16 + 16, ((DataStore.EngineVersion == 1 ? 0x200 + 0x56 : 0x200 + 0x300) / TileEditorPanel.editorWidth) * 16));
		tileEditorPanel.setSize(tileEditorPanel.getPreferredSize());
		tileEditorPanel.setLayout(null);

		tilesetScrollPane = new JScrollPane(tileEditorPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tilesetScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		tilesetScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		tilesetScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		tabbedPane.addTab("Tiles", null, tilesetScrollPane, null);

		permissionTilePanel = new PermissionTilePanel();
		permissionTilePanel.setLayout(null);
		movementScrollPane = new JScrollPane(permissionTilePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		movementScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		movementScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		movementScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		tabbedPane.addTab("Movement", null, movementScrollPane, null);

		CreateToolbar();
		CreateEventsPanel();
		CreateWildPokemonPanel();
		CreateMimeTab();

	}

	void CreateSplitPane()
	{
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.2);
		splitPane.setDividerSize(1);
		splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener()
		{

			public void propertyChange(PropertyChangeEvent e)
			{
				if (((JSplitPane) e.getSource()).getDividerLocation() > 300)
					((JSplitPane) e.getSource()).setDividerLocation(350);
				else if (((JSplitPane) e.getSource()).getDividerLocation() < 25)
					((JSplitPane) e.getSource()).setDividerLocation(25);

				if (paneSize == 0)
				{
					paneSize = 250;
					((JSplitPane) e.getSource()).setDividerLocation(paneSize);
				}
				else
					paneSize = ((JSplitPane) e.getSource()).getDividerLocation();
			}
		});
		splitPane.addComponentListener(new ComponentAdapter()
		{

			public void componentResized(ComponentEvent e)
			{
				if (((JSplitPane) e.getSource()).getDividerLocation() > 300)
					((JSplitPane) e.getSource()).setDividerLocation(350);
				else if (((JSplitPane) e.getSource()).getDividerLocation() < 25)
					((JSplitPane) e.getSource()).setDividerLocation(25);
				else
					((JSplitPane) e.getSource()).setDividerLocation(paneSize);

				if (paneSize == 0)
				{
					paneSize = 250;
					((JSplitPane) e.getSource()).setDividerLocation(paneSize);
				}
				else
					paneSize = ((JSplitPane) e.getSource()).getDividerLocation();
			}
		});
		splitPane.setDividerLocation(0.2);
	}

	void CreateStatusBar()
	{
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_2.setPreferredSize(new Dimension(10, 24));
		getContentPane().add(panel_2, BorderLayout.SOUTH);

		lblInfo = new JLabel("No ROM Loaded!");
		panel_2.add(lblInfo);
	}

	public void CreatePermissions()
	{

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
		setPreferredSize(new Dimension(800, 1800));
		addWindowListener(new WindowAdapter()
		{

			public void windowClosing(WindowEvent e)
			{
				// TODO: Are you *sure* you want to exit / Check for saved
				// changes
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

		spnWidth = new JSpinner(new SpinnerNumberModel(1, 1, 9001, 1));
		spnWidth.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if (!doneLoading)
					return;
				try
				{
					int x = (Integer) spnWidth.getValue();
					int y = (Integer) spnHeight.getValue();
					loadedMap.getMapTileData().resize(x, y);
					mapEditorPanel.Redraw = true;
					eventEditorPanel.Redraw = true;
					connectionsEditorPanel.loadConnections(loadedMap);
					mapEditorPanel.repaint();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		spnWidth.setPreferredSize(new Dimension(40, 24));
		spnWidth.setBounds(130, 105, 50, 24);
		panel_1.add(spnWidth);

		spnHeight = new JSpinner(new SpinnerNumberModel(1, 1, 9001, 1));
		spnHeight.setPreferredSize(new Dimension(40, 24));
		spnHeight.setBounds(130, 132, 50, 24);
		spnHeight.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if (!doneLoading)
					return;
				try
				{
					int x = (Integer) spnWidth.getValue();
					int y = (Integer) spnHeight.getValue();
					loadedMap.getMapTileData().resize(x, y);
					mapEditorPanel.Redraw = true;
					eventEditorPanel.Redraw = true;
					connectionsEditorPanel.loadConnections(loadedMap);
					mapEditorPanel.repaint();
				}
				catch (Exception ex)
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
		connectinonsInfoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));

		menuBar_1 = new JMenuBar();
		menuBar_1.setBorderPainted(false);
		connectinonsInfoPanel.add(menuBar_1);

		mnAddCon = new JMenu("Add...");
		menuBar_1.add(mnAddCon);

		mntmLeftCon = new JMenuItem("Left Connection");
		mntmLeftCon.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ConnectionAddGUI g = new ConnectionAddGUI(ConnectionType.LEFT);
				g.setVisible(true);
			}
		});
		mnAddCon.add(mntmLeftCon);

		mntmRightCon = new JMenuItem("Right Connection");
		mntmRightCon.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ConnectionAddGUI g = new ConnectionAddGUI(ConnectionType.RIGHT);
				g.setVisible(true);
			}
		});
		mnAddCon.add(mntmRightCon);

		mntmUpCon = new JMenuItem("Up Connection");
		mntmUpCon.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ConnectionAddGUI g = new ConnectionAddGUI(ConnectionType.UP);
				g.setVisible(true);
			}
		});
		mnAddCon.add(mntmUpCon);

		mntmDownCon = new JMenuItem("Down Connection");
		mntmDownCon.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ConnectionAddGUI g = new ConnectionAddGUI(ConnectionType.DOWN);
				g.setVisible(true);
			}
		});
		mnAddCon.add(mntmDownCon);

		connectionsEditorPanel = new ConnectionsEditorPanel();
		connectionsEditorScroll = new JScrollPane(connectionsEditorPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		connectionsEditorScroll.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		connectionsEditorScroll.getVerticalScrollBar().setUnitIncrement(16);
		connectionsEditorScroll.getHorizontalScrollBar().setUnitIncrement(16);
		connectionsEditorScroll.addMouseWheelListener(new MouseWheelListener()
		{
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				if (e.isControlDown() || e.isAltDown())
				{
					connectionsEditorPanel.scale += (double) (e.getWheelRotation() / 5d);
					if (connectionsEditorPanel.scale < 0.3)
						connectionsEditorPanel.scale = 0.3;
					else if (connectionsEditorPanel.scale > 10)
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
		panel_3.setPreferredSize(new Dimension(242, 10));
		splitPane.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		mapBanks = new JTree();
		mapBanks.addKeyListener(new KeyAdapter()
		{

			public void keyTyped(KeyEvent e)
			{
				loadMap();
			}
		});
		mapBanks.addTreeSelectionListener(new TreeSelectionListener()
		{

			public void valueChanged(TreeSelectionEvent e)
			{
				try
				{
					String s = ((DefaultMutableTreeNode) e.getPath().getPath()[2]).toString();
					s = s.split("\\(")[s.split("\\(").length - 1].replace(")", "");
					selectedBank = Integer.parseInt(s.split("\\.")[0]);
					selectedMap = Integer.parseInt(s.split("\\.")[1]);
				}
				catch (Exception ex)
				{
				}
			}
		});
		mapBanks.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		mapBanks.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					if (e.getClickCount() == 2)
					{
						loadMap();
					}
				}
			}
		});
		mapBanks.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("By Bank...")
		{

		}));
		mapBanks.setCellRenderer(new MapTreeRenderer());

		JScrollPane mapPane = new JScrollPane(mapBanks, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mapPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		panel_3.add(mapPane);
	}

	private static void addPopup(Component component, final JPopupMenu popup)
	{
		component.addMouseListener(new MouseAdapter()
		{

			public void mousePressed(MouseEvent e)
			{
				if (e.isPopupTrigger())
				{
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e)
			{
				if (e.isPopupTrigger())
				{
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e)
			{
				popupX = e.getX() / 16;
				popupY = e.getY() / 16;
				popup.show(e.getComponent(), (popupX * 16) + 8, (popupY * 16) + 8);
			}
		});
	}

	public static void reloadMimeLabels()
	{
		spnWidth.setValue((int) loadedMap.getMapData().mapWidth);
		spnHeight.setValue((int) loadedMap.getMapData().mapHeight);
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

	public static void loadMap()
	{
		long offset = BankLoader.maps[selectedBank].get(selectedMap);
		loadMapFromPointer(offset, false);
	}

	public static void loadMapFromPointer(long offs, boolean justPointer)
	{
		lblInfo.setText("Loading map...");
		final long offset = offs;

		if (!justPointer)
		{
			currentBank = -1;
			currentMap = -1;
		}

		new Thread()
		{

			public void run()
			{
				Date d = new Date();
				doneLoading = false;
				if (loadedMap != null)
					TilesetCache.get(loadedMap.getMapData().globalTileSetPtr).resetCustomTiles(); 

				loadedMap = new Map(ROMManager.getActiveROM(), (int) (offset));
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

				loadWildPokemon();

				mapEditorPanel.repaint();
				Date eD = new Date();
				long time = eD.getTime() - d.getTime();
				MainGUI.lblInfo.setText("Done! Finished in " + (double) (time / 1000) + " seconds!");
				doneLoading = true;

				PluginManager.fireMapLoad(selectedBank, selectedMap);

			}
		}.start();
	}

	public static String[] pokemonNames;

	public static void loadPokemonNames()
	{
		pokemonNames = new String[DataStore.NumPokemon];
		ROMManager.currentROM.Seek(ROMManager.currentROM.getPointerAsInt(DataStore.SpeciesNames));
		for (int i = 0; i < DataStore.NumPokemon; i++)
		{
			pokemonNames[i] = ROMManager.currentROM.readPokeText();
		}
		addStringArray(pkName1, pokemonNames);
		addStringArray(pkName2, pokemonNames);
		addStringArray(pkName3, pokemonNames);
		addStringArray(pkName4, pokemonNames);
		addStringArray(pkName5, pokemonNames);
		addStringArray(pkName6, pokemonNames);
		addStringArray(pkName7, pokemonNames);
		addStringArray(pkName8, pokemonNames);
		addStringArray(pkName9, pokemonNames);
		addStringArray(pkName10, pokemonNames);
		addStringArray(pkName11, pokemonNames);
		addStringArray(pkName12, pokemonNames);
	}

	public static void addStringArray(JComboBox b, String[] strs)
	{
		b.removeAllItems();
		for (String s : strs)
			b.addItem(s);
		b.repaint();
	}

	public static void loadWildPokemon()
	{
		WildData d = WildDataCache.getWildData(currentBank, currentMap);
		if (d == null)
		{
			panelpk1_5.setVisible(false);
			panelpk6_10.setVisible(false);
			panelpk11_12.setVisible(false);
			return;
		}

		if (currentBank != -1 && currentMap != -1 && d.aWildPokemon[currentType] != null)
		{
			pkEncounter.setValue(d.aWildPokemon[currentType].bRatio);
			panelpk1_5.setVisible(true);

			pkMin1.setValue((int) d.aWildPokemon[currentType].aWildPokemon[0].bMinLV);
			pkMax1.setValue((int) d.aWildPokemon[currentType].aWildPokemon[0].bMaxLV);
			pkName1.setSelectedIndex(d.aWildPokemon[currentType].aWildPokemon[0].wNum);
			pkName1.repaint();
			pkNo1.setValue((int) d.aWildPokemon[currentType].aWildPokemon[0].wNum);

			pkMin2.setValue((int) d.aWildPokemon[currentType].aWildPokemon[1].bMinLV);
			pkMax2.setValue((int) d.aWildPokemon[currentType].aWildPokemon[1].bMaxLV);
			pkName2.setSelectedIndex(d.aWildPokemon[currentType].aWildPokemon[1].wNum);
			pkName2.repaint();
			pkNo2.setValue((int) d.aWildPokemon[currentType].aWildPokemon[1].wNum);

			pkMin3.setValue((int) d.aWildPokemon[currentType].aWildPokemon[2].bMinLV);
			pkMax3.setValue((int) d.aWildPokemon[currentType].aWildPokemon[2].bMaxLV);
			pkName3.setSelectedIndex(d.aWildPokemon[currentType].aWildPokemon[2].wNum);
			pkName3.repaint();
			pkNo3.setValue((int) d.aWildPokemon[currentType].aWildPokemon[2].wNum);

			pkMin4.setValue((int) d.aWildPokemon[currentType].aWildPokemon[3].bMinLV);
			pkMax4.setValue((int) d.aWildPokemon[currentType].aWildPokemon[3].bMaxLV);
			pkName4.setSelectedIndex(d.aWildPokemon[currentType].aWildPokemon[3].wNum);
			pkName4.repaint();
			pkNo4.setValue((int) d.aWildPokemon[currentType].aWildPokemon[3].wNum);

			pkMin5.setValue((int) d.aWildPokemon[currentType].aWildPokemon[4].bMinLV);
			pkMax5.setValue((int) d.aWildPokemon[currentType].aWildPokemon[4].bMaxLV);
			pkName5.setSelectedIndex(d.aWildPokemon[currentType].aWildPokemon[4].wNum);
			pkName5.repaint();
			pkNo5.setValue((int) d.aWildPokemon[currentType].aWildPokemon[4].wNum);

			if (currentType == 0 || currentType == 3)
			{
				panelpk6_10.setVisible(true);

				pkMin6.setValue((int) d.aWildPokemon[currentType].aWildPokemon[5].bMinLV);
				pkMax6.setValue((int) d.aWildPokemon[currentType].aWildPokemon[5].bMaxLV);
				pkName6.setSelectedIndex(d.aWildPokemon[currentType].aWildPokemon[5].wNum);
				pkName6.repaint();
				pkNo6.setValue((int) d.aWildPokemon[currentType].aWildPokemon[5].wNum);

				pkMin7.setValue((int) d.aWildPokemon[currentType].aWildPokemon[6].bMinLV);
				pkMax7.setValue((int) d.aWildPokemon[currentType].aWildPokemon[6].bMaxLV);
				pkName7.setSelectedIndex(d.aWildPokemon[currentType].aWildPokemon[6].wNum);
				pkName7.repaint();
				pkNo7.setValue((int) d.aWildPokemon[currentType].aWildPokemon[6].wNum);

				pkMin8.setValue((int) d.aWildPokemon[currentType].aWildPokemon[7].bMinLV);
				pkMax8.setValue((int) d.aWildPokemon[currentType].aWildPokemon[7].bMaxLV);
				pkName8.setSelectedIndex(d.aWildPokemon[currentType].aWildPokemon[7].wNum);
				pkName8.repaint();
				pkNo8.setValue((int) d.aWildPokemon[currentType].aWildPokemon[7].wNum);

				pkMin9.setValue((int) d.aWildPokemon[currentType].aWildPokemon[8].bMinLV);
				pkMax9.setValue((int) d.aWildPokemon[currentType].aWildPokemon[8].bMaxLV);
				pkName9.setSelectedIndex(d.aWildPokemon[currentType].aWildPokemon[8].wNum);
				pkName9.repaint();
				pkNo9.setValue((int) d.aWildPokemon[currentType].aWildPokemon[8].wNum);

				pkMin10.setValue((int) d.aWildPokemon[currentType].aWildPokemon[9].bMinLV);
				pkMax10.setValue((int) d.aWildPokemon[currentType].aWildPokemon[9].bMaxLV);
				pkName10.setSelectedIndex(d.aWildPokemon[currentType].aWildPokemon[9].wNum);
				pkName10.repaint();
				pkNo10.setValue((int) d.aWildPokemon[currentType].aWildPokemon[9].wNum);

				if (currentType == 0)
				{
					panelpk11_12.setVisible(true);

					pkMin11.setValue((int) d.aWildPokemon[currentType].aWildPokemon[10].bMinLV);
					pkMax11.setValue((int) d.aWildPokemon[currentType].aWildPokemon[10].bMaxLV);
					pkName11.setSelectedIndex(d.aWildPokemon[currentType].aWildPokemon[10].wNum);
					pkName11.repaint();
					pkNo11.setValue((int) d.aWildPokemon[currentType].aWildPokemon[10].wNum);

					pkMin12.setValue((int) d.aWildPokemon[currentType].aWildPokemon[11].bMinLV);
					pkMax12.setValue((int) d.aWildPokemon[currentType].aWildPokemon[11].bMaxLV);
					pkName12.setSelectedIndex(d.aWildPokemon[currentType].aWildPokemon[11].wNum);
					pkName12.repaint();
					pkNo12.setValue((int) d.aWildPokemon[currentType].aWildPokemon[11].wNum);

					pkchance1.setText("20%");
					pkchance2.setText("20%");
					pkchance3.setText("10%");
					pkchance4.setText("10%");
					pkchance5.setText("10%");
					pkchance6.setText("10%");
					pkchance7.setText("5%");
					pkchance8.setText("5%");
					pkchance9.setText("4%");
					pkchance10.setText("4%");
					pkchance11.setText("1%");
					pkchance12.setText("1%");
				}
				else
				{
					pkchance1.setText("70%  (old rod)");
					pkchance2.setText("30%  (old rod)");
					pkchance3.setText("60%  (good rod)");
					pkchance4.setText("20%  (good rod)");
					pkchance5.setText("20%  (good rod)");
					pkchance6.setText("40%  (super rod)");
					pkchance7.setText("40%  (super rod)");
					pkchance8.setText("15%  (super rod)");
					pkchance9.setText("4%    (super rod)");
					pkchance10.setText("1%    (super rod)");
					panelpk11_12.setVisible(false);
				}
			}
			else
			{
				pkchance1.setText("60%");
				pkchance2.setText("30%");
				pkchance3.setText("5%");
				pkchance4.setText("4%");
				pkchance5.setText("1%");
				panelpk6_10.setVisible(false);
				panelpk11_12.setVisible(false);
			}
		}
		else
		{
			panelpk1_5.setVisible(false);
			panelpk6_10.setVisible(false);
			panelpk11_12.setVisible(false);
		}
	}

	public static void repaintTileEditorPanel()
	{
		tileEditorPanel.repaint();
	}

	public void openROM()
	{
		int i = GBARom.loadRom();
		String s = Plugin.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		// while(s.contains("/") || s.contains("\\"))
		System.out.println(s);
		dataStore = new DataStore("MEH.ini", ROMManager.currentROM.getGameCode());
		loadPokemonNames();

		if (1 != -1)
		{
			mapBanks.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("By Bank...")
			{

			}));
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

		// chckbxmntmDrawSprites.setSelected(DataStore.mehSettingShowSprites==1);
		// TODO Redo this in settings
		new BankLoader((int) DataStore.MapHeaders, ROMManager.getActiveROM(), lblInfo, mapBanks).start();
		new WildDataCache(ROMManager.getActiveROM()).start();
		mnSave.enable(true);
		PluginManager.fireROMLoad();
	}

	public void saveROM()
	{
		PluginManager.fireROMSave();
		WildDataCache.save();
		ROMManager.getActiveROM().commitChangesToROMFile();
	}

	public static void openScript(int scriptOffset)
	{
		if (DataStore.mehSettingCallScriptEditor == null || DataStore.mehSettingCallScriptEditor.isEmpty())
		{
			int reply = JOptionPane.showConfirmDialog(null, "It appears that you have no script editor registered with MEH. Would you like to search for one?", "You need teh Script Editorz!!!", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION)
			{
				FileDialog fd = new FileDialog(new Frame(), "Choose your script editor...", FileDialog.LOAD);
				fd.setFilenameFilter(new FilenameFilter()
				{
					public boolean accept(File dir, String name)
					{
						return ((System.getProperty("os.name").toLowerCase().contains("win") ? name.toLowerCase().endsWith(".exe") : name.toLowerCase().endsWith(".*")) || name.toLowerCase().endsWith(".jar"));
					}
				});

				fd.show();
				String location = fd.getDirectory() + fd.getFile();
				if (location.isEmpty())
					return;

				if (!location.isEmpty())
					DataStore.mehSettingCallScriptEditor = location;
			}
		}

		try
		{
			Runtime r = Runtime.getRuntime();
			String s = (DataStore.mehSettingCallScriptEditor.toLowerCase().endsWith(".jar") ? "java -jar " : "") + DataStore.mehSettingCallScriptEditor + " \"" + ROMManager.currentROM.input_filepath.replace("\"", "") + "\" 0x" + String.format("%x", scriptOffset);
			r.exec(s);
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "It seems that your script editor has gone missing. Look around for it and try it again. I'm sure it'll work eventually.");
			e.printStackTrace();
		}
	}

	public static void showEventPopUp(int x, int y, EventType event, int index)
	{
		popupX = x / 16;
		popupY = y / 16;
		popupMenu.show(eventEditorPanel, x, y);
		eventType = event;
		eventIndex = index;
	}
}
