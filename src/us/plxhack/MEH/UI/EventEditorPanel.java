package us.plxhack.MEH.UI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.ROMManager;

import us.plxhack.MEH.IO.Map;
import us.plxhack.MEH.IO.MapIO;
import us.plxhack.MEH.IO.Tileset;
import us.plxhack.MEH.IO.Render.BlockRenderer;
import us.plxhack.MEH.IO.Render.OverworldSpritesManager;
import us.plxhack.MEH.MapElements.SpriteExit;
import us.plxhack.MEH.MapElements.SpriteNPC;
import us.plxhack.MEH.MapElements.SpriteSign;
import us.plxhack.MEH.MapElements.Trigger;
import us.plxhack.MEH.Structures.EventType;

public class EventEditorPanel extends JPanel
{
	private static final long serialVersionUID = -877213633894324075L;
	private Tileset globalTiles;
	private Tileset localTiles;
	public static BlockRenderer blockRenderer = new BlockRenderer();
	private Map map;
	public static boolean Redraw = true;
	public static boolean renderPalette = false;
	public static boolean renderTileset = false;
	public static NPCPane paneNPC;
	public Image imgTrigger;
	public Image imgWarp;
	public Image imgSign;
	public Image imgNPC;

	Point pointEvent;
	int moveSrcX;
	int moveSrcY;
	EventType selectedEvent;// -1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3
							// is Trigger
	int IndexNPC;
	int IndexSign;
	int IndexExit;
	int IndexTriggers;

	public void Grab(MouseEvent e)
	{
		int x = (e.getX() / 16);
		int y = (e.getY() / 16);
		IndexNPC = map.mapNPCManager.getSpriteIndexAt(x, y);
		if (IndexNPC != -1)
		{

			selectedEvent = EventType.NPC;// -1 is nothing, 0 is NPC, 1 is Sign,
											// 2 is Exit, 3 is Trigger
			// return;
		}
		IndexSign = map.mapSignManager.getSpriteIndexAt(x, y);
		if (IndexSign != -1)
		{

			selectedEvent = EventType.SIGN;// -1 is nothing, 0 is NPC, 1 is
											// Sign, 2 is Exit, 3 is Trigger
			return;
		}
		IndexExit = map.mapExitManager.getSpriteIndexAt(x, y);
		if (IndexExit != -1)
		{

			selectedEvent = EventType.WARP;// -1 is nothing, 0 is NPC, 1 is
											// Sign, 2 is Exit, 3 is Trigger
			return;
		}
		IndexTriggers = map.mapTriggerManager.getSpriteIndexAt(x, y);
		if (IndexTriggers != -1)
		{

			selectedEvent = EventType.TRIGGER;// -1 is nothing, 0 is NPC, 1 is
												// Sign, 2 is Exit, 3 is Trigger
			return;
		}
	}

	public EventEditorPanel() {
		selectedEvent = null;

		try {
			imgTrigger = ImageIO.read(MainGUI.class.getResource("/resources/trigger.png").openStream());
			imgWarp = ImageIO.read(MainGUI.class.getResource("/resources/warp.png").openStream());
			imgSign = ImageIO.read(MainGUI.class.getResource("/resources/sign.png").openStream());
			imgNPC = ImageIO.read(MainGUI.class.getResource("/resources/npc.png").openStream());
		}
		catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
				if(selectedEvent != null) {
					int x = (e.getX() / 16);
					int y = (e.getY() / 16);
					moveEvent(x,y);
				}
			}

			public void mouseMoved(MouseEvent e) {
			}

		});

		this.addMouseListener(new MouseListener() {

			@SuppressWarnings("unused")
			public void mouseClicked(MouseEvent e) {
				int x = (e.getX() / 16);
				int y = (e.getY() / 16);

				if (x > map.getMapData().mapWidth || y > map.getMapData().mapHeight) {
					return;
				}
				Grab(e);

				System.out.println(e.getButton());
				if (e.getButton() == MouseEvent.BUTTON1 && selectedEvent != null) {

					MainGUI.eventScrollPanel.removeAll();
					// If there's two events on tile, we'll handle that later
					// with some kind of picker
					switch (selectedEvent) {
						case NPC:
							MainGUI.eventScrollPanel.add(new NPCPane(map.mapNPCManager, IndexNPC));

							break;
						case SIGN:
							if (e.getClickCount() > 1 && IndexSign >= 0) {
								MapIO.openScript(BitConverter.shortenPointer(map.mapSignManager.mapSigns.get(IndexSign).pScript));
							}
							else if (IndexSign >= 0)
								MainGUI.eventScrollPanel.add(new SignPanel(map.mapSignManager, IndexSign));
							break;
						case WARP:
							if (e.getClickCount() > 1 && IndexExit >= 0) {
								// Load map number
								MapIO.loadMap(map.mapExitManager.mapExits.get(IndexExit).bBank & 0xFF, map.mapExitManager.mapExits.get(IndexExit).bMap & 0xFF);
							}
							else if (IndexExit >= 0)
								MainGUI.eventScrollPanel.add(new ExitPanel(map.mapExitManager, IndexExit));
							break;
						case TRIGGER:

							MainGUI.eventScrollPanel.add(new TriggerPanel(map.mapTriggerManager, IndexTriggers));
							break;

					}

					MainGUI.eventScrollPanel.revalidate();
					MainGUI.eventScrollPanel.repaint();
					Redraw = true;
					repaint();
				}
				else if (e.getButton() == 3) {
					switch (selectedEvent) {
						case NPC:
							MainGUI.showEventPopUp(x, y, selectedEvent, IndexNPC);
							break;
						case WARP:
							MainGUI.showEventPopUp(x, y, selectedEvent, IndexExit);
							break;
						case TRIGGER:
							MainGUI.showEventPopUp(x, y, selectedEvent, IndexTriggers);
							break;
						case SIGN:
							MainGUI.showEventPopUp(x, y, selectedEvent, IndexSign);
							break;
					}
				}
			}

			public void mousePressed(MouseEvent e) {
				int x = (e.getX() / 16);
				int y = (e.getY() / 16);

				if (x > map.getMapData().mapWidth || y > map.getMapData().mapHeight) {
					return;
				}

				Grab(e);

				if (e.getButton() == MouseEvent.BUTTON1) {
					moveSrcX = x;
					moveSrcY = y;

				}
			}

			public void mouseExited(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {
				int x = (e.getX() / 16);
				int y = (e.getY() / 16);
				moveEvent(x,y);
				selectedEvent = null;
			}
		});
	}
	
	public void moveEvent(int x, int y) {
		int srcX = moveSrcX;
		int srcY = moveSrcY;
		EventType s = selectedEvent;
		if (srcX != x || srcY != y) {
			try {
				switch (s) {
				    // -1 is nothing, 0 is NPC, 1 is Sign, 2 is Exit, 3 is
					// Trigger)
					case NPC:
						map.mapNPCManager.mapNPCs.get(IndexNPC).bX = (byte) x;
						map.mapNPCManager.mapNPCs.get(IndexNPC).bY = (byte) y;
						break;
					case SIGN:
						map.mapSignManager.mapSigns.get(IndexSign).bX = (byte) x;
						map.mapSignManager.mapSigns.get(IndexSign).bY = (byte) y;
						break;
					case WARP:
						map.mapExitManager.mapExits.get(IndexExit).bX = (byte) x;
						map.mapExitManager.mapExits.get(IndexExit).bY = (byte) y;
						break;
					case TRIGGER:
						map.mapTriggerManager.mapTriggers.get(IndexTriggers).bX = (byte) x;
						map.mapTriggerManager.mapTriggers.get(IndexTriggers).bY = (byte) y;
						break;
				}
				;
			}
			catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
			DrawMap();
			repaint();
		}
	}

	public void setGlobalTileset(Tileset global) {
		globalTiles = global;
		blockRenderer.setGlobalTileset(global);
	}

	public void setLocalTileset(Tileset local) {
		localTiles = local;
		blockRenderer.setLocalTileset(local);
	}

	public void setMap(Map m) {
		map = m;
		Dimension size = new Dimension();
		size.setSize((int) (m.getMapData().mapWidth + 1) * 16, (int) (m.getMapData().mapHeight + 1) * 16);
		setPreferredSize(size);
		this.setSize(size);
	}

	private Graphics gcBuff;
	private Image imgBuffer = null;

	public void DrawMap() {
		try {
			imgBuffer = createImage((int) map.getMapData().mapWidth * 16, (int) map.getMapData().mapHeight * 16);
			gcBuff = imgBuffer.getGraphics();

			gcBuff.drawImage(MapEditorPanel.imgBuffer, 0, 0, this);

			DrawSigns();
			DrawExits();
			DrawNPCs();
			DrawTriggers();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	void DrawText(String Text, int x, int y)
	{
		gcBuff.drawRect(x, y, 16, 16);
		gcBuff.drawString(Text, x, y + 16);
	}

	void DrawNPCs()
	{
		for (SpriteNPC n : map.mapNPCManager.mapNPCs)
		{
			if (DataStore.mehSettingShowSprites == 1)
			{

				Image imgNPCs = OverworldSpritesManager.GetImage(n.bSpriteSet & 0xFF);
				int adjX = (OverworldSpritesManager.GetSprite(n.bSpriteSet & 0xFF).mSpriteSize == 2 ? 8 : 0);
				int dstX = (n.bX * 16) - adjX;
				int dstY = (n.bY * 16) - (OverworldSpritesManager.GetSprite(n.bSpriteSet & 0xFF).mSpriteSize == 1 ? 16 : 0) - adjX * 2;
				gcBuff.drawImage(imgNPCs, dstX, dstY, this);
			}
			else
			{
				gcBuff.drawImage(imgNPC, n.bX * 16, n.bY * 16, this);
			}
		}
	}

	void DrawTriggers()
	{
		for (Trigger n : map.mapTriggerManager.mapTriggers)
			gcBuff.drawImage(imgTrigger, n.bX * 16, n.bY * 16, n.bX * 16 + 16, n.bY * 16 + 16, 0, 0, 64, 64, this);
	}

	void DrawSigns()
	{
		for (SpriteSign n : map.mapSignManager.mapSigns)
			gcBuff.drawImage(imgSign, n.bX * 16, n.bY * 16, n.bX * 16 + 16, n.bY * 16 + 16, 0, 0, 64, 64, this);
	}

	void DrawExits()
	{
		for (SpriteExit n : map.mapExitManager.mapExits)
			gcBuff.drawImage(imgWarp, n.bX * 16, n.bY * 16, n.bX * 16 + 16, n.bY * 16 + 16, 0, 0, 64, 64, this);
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (globalTiles != null)
		{
			if (EventEditorPanel.Redraw)
			{
				DrawMap();
				EventEditorPanel.Redraw = false;
			}
			g.drawImage(imgBuffer, 0, 0, this);

		}
		try
		{
			// g.drawImage(ImageIO.read(MainGUI.class.getResourceAsStream("/resources/smeargle.png")),
			// 100, 240, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void reset()
	{
		globalTiles = null;
		localTiles = null;
		map = null;
	}
}
