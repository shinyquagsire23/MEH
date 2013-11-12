package org.zzl.minegaming.MEH;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar.Separator;
import javax.swing.JViewport;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.Dimension;

import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import javax.swing.JToolBar.Separator;
import javax.swing.JToolBar;

import java.awt.Component;

import javax.swing.Box;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.border.EtchedBorder;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ImagePanel;
import org.zzl.minegaming.GBAUtils.ROMManager;

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

public class MainGUI extends JFrame
{
	public int paneSize = 0;
	public int initEditorPanePos = -1;
	
	public static JLabel lblInfo;
	public JTree mapBanks;
	public Map loadedMap;
	public BorderMap borderMap;
	private int selectedBank = 0;
	private int selectedMap = 0;
	public JLabel lblWidth;
	public JLabel lblBorderTilesPointer;
	public JLabel lblBorderWidth;
	public JLabel lblLocalTilesetPointer;
	public JLabel lblMapTilesPointer;
	public JLabel lblHeight;
	public JLabel lblBorderHeight;
	public JLabel lblGlobalTilesetPointer;
	public MapEditorPanel mapEditorPanel;
	public BorderEditorPanel borderTileEditor;
	public static TileEditorPanel tileEditorPanel;
	public static JLabel lblTileVal;
	public DataStore dataStore;
	public MainGUI()
	{
		setPreferredSize(new Dimension(800, 800));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) 
			{
				//TODO: Are you *sure* you want to exit / Check for saved changes
				System.exit(0);
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_2.setPreferredSize(new Dimension(10, 24));
		getContentPane().add(panel_2, BorderLayout.SOUTH);
	
		lblInfo = new JLabel("No ROM Loaded!");
		panel_2.add(lblInfo);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setPreferredSize(new Dimension(10, 50));
		panelButtons.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelButtons.setMinimumSize(new Dimension(10, 50));
		getContentPane().add(panelButtons, BorderLayout.NORTH);
		
		JButton btnOpenROM = new JButton("");
		btnOpenROM.setIcon(new ImageIcon(MainGUI.class.getResource("/resources/ROMopen.png")));
		btnOpenROM.setFocusPainted(false);
		btnOpenROM.setBorder(null);
		btnOpenROM.setBorderPainted(false);
		btnOpenROM.setPreferredSize(new Dimension(54, 48));
		
		btnOpenROM.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				int i = GBARom.loadRom();
				
				dataStore = new DataStore("PokeRoms.ini", ROMManager.currentROM.getGameCode() );
				
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
				
				new BankLoader((int)DataStore.MapHeaders,ROMManager.getActiveROM(),lblInfo,mapBanks).start();
			}
		});
		panelButtons.setLayout(new FlowLayout(FlowLayout.LEFT, -1, -2));
		panelButtons.add(btnOpenROM);
		
		JButton btnSaveROM = new JButton("");
		btnSaveROM.setIcon(new ImageIcon(MainGUI.class.getResource("/resources/ROMsave.png")));
		btnSaveROM.setFocusPainted(false);
		btnSaveROM.setBorderPainted(false);
		btnSaveROM.setPreferredSize(new Dimension(54, 48));
		panelButtons.add(btnSaveROM);
		
		Component horizontalStrut = Box.createHorizontalStrut(4);
		horizontalStrut.setForeground(Color.BLACK);
		panelButtons.add(horizontalStrut);
		
		JPanel separator1 = new JPanel();
		separator1.setBackground(SystemColor.scrollbar);
		separator1.setPreferredSize(new Dimension(1, 46));
		FlowLayout fl_separator1 = (FlowLayout) separator1.getLayout();
		fl_separator1.setVgap(0);
		fl_separator1.setHgap(0);
	
		panelButtons.add(separator1);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(4);
		horizontalStrut_1.setForeground(Color.BLACK);
		panelButtons.add(horizontalStrut_1);
		
		JButton btnNewMap = new JButton("");
		btnNewMap.setFocusPainted(false);
		btnNewMap.setBorderPainted(false);
		btnNewMap.setPreferredSize(new Dimension(48, 48));
		panelButtons.add(btnNewMap);
		
		JButton btnImportMap = new JButton("");
		btnImportMap.setFocusPainted(false);
		btnImportMap.setBorderPainted(false);
		btnImportMap.setPreferredSize(new Dimension(48, 48));
		panelButtons.add(btnImportMap);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.2);
		splitPane.setDividerSize(1);
		splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, 
			    new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e)
					{
						if(((JSplitPane)e.getSource()).getDividerLocation() > 300)
							((JSplitPane)e.getSource()).setDividerLocation(350);
						else if(((JSplitPane)e.getSource()).getDividerLocation() < 200)
							((JSplitPane)e.getSource()).setDividerLocation(200);
						
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
			@Override
			public void componentResized(ComponentEvent e) 
			{
				if(((JSplitPane)e.getSource()).getDividerLocation() > 300)
					((JSplitPane)e.getSource()).setDividerLocation(350);
				else if(((JSplitPane)e.getSource()).getDividerLocation() < 200)
					((JSplitPane)e.getSource()).setDividerLocation(200);
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
		
		
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JTabbedPane editorTabs = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(editorTabs);
		
		JPanel editorPanel = new JPanel();
		editorTabs.addTab("Map", null, editorPanel, null);
		editorPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTilesContainer = new JPanel();
		editorPanel.add(panelTilesContainer, BorderLayout.EAST);
		panelTilesContainer.setBorder(UIManager.getBorder("SplitPaneDivider.border"));
		panelTilesContainer.setPreferredSize(new Dimension((TileEditorPanel.editorWidth+1)*16 + 16, 10));
		panelTilesContainer.setLayout(new BorderLayout(0, 0));
		
	
		
		JPanel splitterMapTiles = new JPanel();
		splitterMapTiles.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		splitterMapTiles.setPreferredSize(new Dimension(4, 10));
		splitterMapTiles.setMaximumSize(new Dimension(4, 32767));
		panelTilesContainer.add(splitterMapTiles, BorderLayout.WEST);
		
		JPanel panelMapTilesContainer = new JPanel();
		panelTilesContainer.add(panelMapTilesContainer, BorderLayout.CENTER);
		panelMapTilesContainer.setLayout(new BorderLayout(0, 0));
		
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
		
		
		tileEditorPanel = new TileEditorPanel();
		tileEditorPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
			}
		});
		tileEditorPanel.setPreferredSize(new Dimension((tileEditorPanel.editorWidth)*16+16, ((DataStore.EngineVersion == 1 ? 0x200 + 0x56 : 0x200 + 0x300)/tileEditorPanel.editorWidth)*16));
		//panelMapTilesContainer.add(tileEditorPanel, BorderLayout.WEST);
		tileEditorPanel.setLayout(null);
		tileEditorPanel.setBorder(UIManager.getBorder("SplitPane.border"));
		
		JScrollPane tilesetScrollPane = new JScrollPane(tileEditorPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelMapTilesContainer.add(tilesetScrollPane, BorderLayout.WEST);
		tilesetScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		tilesetScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		tilesetScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		//Setup Map
		
		mapEditorPanel = new MapEditorPanel();
		mapEditorPanel.setLayout(null);
		mapEditorPanel.setBorder(UIManager.getBorder("SplitPane.border"));
		
		JScrollPane mapScrollPane = new JScrollPane(mapEditorPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			      JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		editorPanel.add(mapScrollPane, BorderLayout.CENTER);
		mapScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		mapScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		mapScrollPane.getHorizontalScrollBar().setUnitIncrement(16);

		
		JToolBar toolBar = new JToolBar();
		lblTileVal=new JLabel("Current Tile: 0x0000");
		toolBar.add(lblTileVal);
		editorPanel.add(toolBar, BorderLayout.NORTH);
		toolBar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBar.setPreferredSize(new Dimension(128, 32));
		
		JPanel eventsPanel = new JPanel();
		editorTabs.addTab("Events", null, eventsPanel, null);
		
		JPanel wildPokemonPanel = new JPanel();
		editorTabs.addTab("Wild Pokemon", null, wildPokemonPanel, null);
		
		JPanel mimePanel = new JPanel();
		editorTabs.addTab("Mime", null, mimePanel, null);
		mimePanel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 796, 454);
		mimePanel.add(panel_1);
		panel_1.setBorder(UIManager.getBorder("SplitPane.border"));
		panel_1.setLayout(null);
		
		JLabel lblWelcome = new JLabel("<html><center>Welcome to the map mime!\n<br>\nHere we will mime out your map so you can like see it, but without actually physically seeing it and stuff.</center></html>");
		lblWelcome.setBounds(90, 12, 559, 63);
		panel_1.add(lblWelcome);
		
		lblWidth = new JLabel("Width: ");
		lblWidth.setBounds(64, 107, 157, 15);
		panel_1.add(lblWidth);
		
		lblHeight = new JLabel("Height: ");
		lblHeight.setBounds(64, 123, 157, 15);
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
		
		JPanel panel_3 = new JPanel();
		splitPane.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		mapBanks = new JTree();
		mapBanks.addKeyListener(new KeyAdapter() {
			@Override
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
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getButton() == e.BUTTON1)
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
		
		JScrollPane mapPane = new JScrollPane(mapBanks,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			      JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
	
	public void reloadMimeLabels()
	{
		lblWidth.setText("Width: " + loadedMap.getMapData().mapWidth);
		lblHeight.setText("Height: " + loadedMap.getMapData().mapHeight);
		lblBorderWidth.setText("Border Width: " + loadedMap.getMapData().borderWidth);
		lblBorderHeight.setText("Border Height: " + loadedMap.getMapData().borderHeight);
		lblMapTilesPointer.setText("Map Tiles Pointer: " + BitConverter.toHexString(loadedMap.getMapData().mapTilesPtr));
		lblBorderTilesPointer.setText("Border Tiles Pointer: " + BitConverter.toHexString(loadedMap.getMapData().borderTilePtr));
		lblGlobalTilesetPointer.setText("Global Tileset Pointer: " + BitConverter.toHexString(loadedMap.getMapData().globalTileSetPtr));
		lblLocalTilesetPointer.setText("Local  Tileset  Pointer: " + BitConverter.toHexString(loadedMap.getMapData().localTileSetPtr));
	}
	
	public void loadMap()
	{
		lblInfo.setText("Loading map...");
		if(loadedMap != null)
			TilesetCache.get(loadedMap.getMapData().globalTileSetPtr).resetCustomTiles(); //Clean up any custom rendered tiles

		long offset=BankLoader.maps[selectedBank].get(selectedMap);
		loadedMap = new Map(ROMManager.getActiveROM(), (int)(offset));
		borderMap = new BorderMap(ROMManager.getActiveROM(), loadedMap);
		reloadMimeLabels();
		mapEditorPanel.setGlobalTileset(TilesetCache.get(loadedMap.getMapData().globalTileSetPtr));
		mapEditorPanel.setLocalTileset(TilesetCache.get(loadedMap.getMapData().localTileSetPtr));
		TilesetCache.get(loadedMap.getMapData().globalTileSetPtr).resetPalettes();
		TilesetCache.get(loadedMap.getMapData().localTileSetPtr).resetPalettes();
		for(int i = DataStore.MainTSPalCount-1; i < 13; i++)
			TilesetCache.get(loadedMap.getMapData().globalTileSetPtr).getPalette()[i] = TilesetCache.get(loadedMap.getMapData().localTileSetPtr).getROMPalette()[i];
		TilesetCache.get(loadedMap.getMapData().localTileSetPtr).setPalette(TilesetCache.get(loadedMap.getMapData().globalTileSetPtr).getPalette());
		TilesetCache.get(loadedMap.getMapData().localTileSetPtr).renderPalettedTiles();
		TilesetCache.get(loadedMap.getMapData().globalTileSetPtr).renderPalettedTiles();
		TilesetCache.get(loadedMap.getMapData().localTileSetPtr).startTileThreads();
		TilesetCache.get(loadedMap.getMapData().globalTileSetPtr).startTileThreads();
		mapEditorPanel.setMap(loadedMap);
		mapEditorPanel.DrawMap();
		mapEditorPanel.repaint();
		
		borderTileEditor.setGlobalTileset(TilesetCache.get(loadedMap.getMapData().globalTileSetPtr));
		borderTileEditor.setLocalTileset(TilesetCache.get(loadedMap.getMapData().localTileSetPtr));
		borderTileEditor.setMap(borderMap);
		borderTileEditor.repaint();
		
		tileEditorPanel.setGlobalTileset(TilesetCache.get(loadedMap.getMapData().globalTileSetPtr));
		tileEditorPanel.setLocalTileset(TilesetCache.get(loadedMap.getMapData().localTileSetPtr));
		tileEditorPanel.DrawTileset();
		tileEditorPanel.repaint();
	}
	
	public static void repaintTileEditorPanel()
	{
		tileEditorPanel.repaint();
	}
}
