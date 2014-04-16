package us.plxhack.MEH.IO;

import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.ROMManager;
import us.plxhack.MEH.MapElements.WildData;
import us.plxhack.MEH.MapElements.WildDataCache;
import us.plxhack.MEH.Plugins.PluginManager;
import us.plxhack.MEH.UI.DNPokePatcher;
import us.plxhack.MEH.UI.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;

public class MapIO
{
	public static Map loadedMap;

	public static BorderMap borderMap;
	public static int selectedBank = 0;
	public static int selectedMap = 0;
	public static int currentBank = 0;
	public static int currentMap = 0;
	public static boolean doneLoading = false;
	public static WildData wildData;

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
        MainGUI.updateTree();
	}

	public static void loadMapFromPointer(long offs, boolean justPointer)
	{
		MainGUI.setStatus("Loading Map...");
		final long offset = offs;

		if (!justPointer) {
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
				MainGUI.reloadMimeLabels();
				MainGUI.mapEditorPanel.setGlobalTileset(TilesetCache.get(loadedMap.getMapData().globalTileSetPtr));
				MainGUI.mapEditorPanel.setLocalTileset(TilesetCache.get(loadedMap.getMapData().localTileSetPtr));
				MainGUI.eventEditorPanel.setGlobalTileset(TilesetCache.get(loadedMap.getMapData().globalTileSetPtr));
				MainGUI.eventEditorPanel.setLocalTileset(TilesetCache.get(loadedMap.getMapData().localTileSetPtr));

				MainGUI.tileEditorPanel.setGlobalTileset(TilesetCache.get(loadedMap.getMapData().globalTileSetPtr));
				MainGUI.tileEditorPanel.setLocalTileset(TilesetCache.get(loadedMap.getMapData().localTileSetPtr));
				MainGUI.tileEditorPanel.DrawTileset();
				MainGUI.tileEditorPanel.repaint();

				MainGUI.mapEditorPanel.setMap(loadedMap);
				MainGUI.mapEditorPanel.DrawMap();
				MainGUI.mapEditorPanel.DrawMovementPerms();
				MainGUI.mapEditorPanel.repaint();

				MainGUI.eventEditorPanel.setMap(loadedMap);
				MainGUI.eventEditorPanel.Redraw = true;
				MainGUI.eventEditorPanel.DrawMap();
				MainGUI.eventEditorPanel.repaint();
				MainGUI.borderTileEditor.setGlobalTileset(TilesetCache.get(loadedMap.getMapData().globalTileSetPtr));
				MainGUI.borderTileEditor.setLocalTileset(TilesetCache.get(loadedMap.getMapData().localTileSetPtr));
				MainGUI.borderTileEditor.setMap(borderMap);
				MainGUI.borderTileEditor.repaint();
				MainGUI.connectionsEditorPanel.loadConnections(loadedMap);
				MainGUI.connectionsEditorPanel.repaint();
				try {
					wildData = (WildData) WildDataCache.getWildData(currentBank, currentMap).clone();
				}
				catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}

				MainGUI.loadWildPokemon();

				MainGUI.mapEditorPanel.repaint();
				Date eD = new Date();
				long time = eD.getTime() - d.getTime();
				//MainGUI.setStatus("Done! Finished in " + (double) (time / 1000) + " seconds!");
				doneLoading = true;

				PluginManager.fireMapLoad(selectedBank, selectedMap);

			}
		}.start();
        MainGUI.setStatus(MainGUI.mapBanks.getLastSelectedPathComponent().toString() + " Loaded.");
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
		addStringArray(MainGUI.pkName1, pokemonNames);
		addStringArray(MainGUI.pkName2, pokemonNames);
		addStringArray(MainGUI.pkName3, pokemonNames);
		addStringArray(MainGUI.pkName4, pokemonNames);
		addStringArray(MainGUI.pkName5, pokemonNames);
		addStringArray(MainGUI.pkName6, pokemonNames);
		addStringArray(MainGUI.pkName7, pokemonNames);
		addStringArray(MainGUI.pkName8, pokemonNames);
		addStringArray(MainGUI.pkName9, pokemonNames);
		addStringArray(MainGUI.pkName10, pokemonNames);
		addStringArray(MainGUI.pkName11, pokemonNames);
		addStringArray(MainGUI.pkName12, pokemonNames);
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

		try {
			Runtime r = Runtime.getRuntime();
			String s = (DataStore.mehSettingCallScriptEditor.toLowerCase().endsWith(".jar") ? "java -jar " : "") + DataStore.mehSettingCallScriptEditor + " \"" + ROMManager.currentROM.input_filepath.replace("\"", "") + "\" 0x" + String.format("%x", scriptOffset);
			r.exec(s);
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "It seems that your script editor has gone missing. Look around for it and try it again. I'm sure it'll work eventually.");
			e.printStackTrace();
		}
	}

	public static void addStringArray(JComboBox b, String[] strs) {
		b.removeAllItems();
		for (String s : strs)
			b.addItem(s);
		b.repaint();
	}
	
	public static void repaintTileEditorPanel() {
		MainGUI.tileEditorPanel.repaint();
	}

	public static void patchDNPokemon() {
		DNPokePatcher n = new DNPokePatcher();
		n.setVisible(true);
	}

	public static void saveMap() {
		MapIO.loadedMap.save();
		MainGUI.connectionsEditorPanel.save(); // Save surrounding maps
		WildDataCache.setWildData(currentBank, currentMap, wildData);
		PluginManager.fireMapSave(MapIO.currentBank, MapIO.currentMap);
	}
	
	public static void saveROM() {
		PluginManager.fireROMSave();
		
		WildDataCache.save();
		ROMManager.getActiveROM().commitChangesToROMFile();
	}
	
	public static void saveAll() {
		saveMap();
		saveROM();
	}
}
