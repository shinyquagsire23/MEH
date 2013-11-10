package org.zzl.minegaming.MEH;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ROMManager;

public class BankLoader extends Thread implements Runnable
{
	GBARom rom;
	int tblOffs;
	JLabel lbl;
	JTree tree;
	public static final int[] numMaps = new int[]{53,4,4,5,6,6,7,6,6,12,7,16,9,23,12,12,13,1,1,1,2,0,0,0,85,43,11,1,0,12,0,0,2,0}; //new int[]{5,123,60,66,4,6,8,10,6,8,20,10,8,2,10,4,2,2,2,1,1,2,2,3,2,3,2,1,1,1,1,7,5,5,8,8,5,5,1,1,1,2,1}; // TODO: Load this from ini
	public static final int numBanks = 33;//43; // TODO: Load this from ini
	private static final int mapNamesPtr = (int)DataStore.MapLabels;
	public static ArrayList<Long>[] maps = (ArrayList<Long>[])new ArrayList[numBanks];
	public static ArrayList<Long> bankPointers = new ArrayList<Long>();
	public static boolean banksLoaded = false;
	
	public static void reset()
	{
		maps = (ArrayList<Long>[])new ArrayList[numBanks];
		bankPointers = new ArrayList<Long>();
		banksLoaded = false;
	}

	public BankLoader(int tableOffset, GBARom rom, JLabel label, JTree tree)
	{
		this.rom = rom;
		tblOffs = (int) ROMManager.currentROM.getPointer(tableOffset);
	
		lbl = label;
		this.tree = tree;
	}

	@Override
	public void run()
	{
		ArrayList<byte[]> bankPointersPre = rom.loadArrayOfStructuredData(tblOffs, numBanks, 4);
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

		int bankNum = 0;
		for (byte[] b : bankPointersPre)
		{
			setStatus("Loading banks into tree...\t" + bankNum);
			bankPointers.add(BitConverter.ToInt32(b));
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(String.valueOf(bankNum));
			model.insertNodeInto(node, root, root.getChildCount());
			bankNum++;
		}

		int mapNum = 0;
		for(long l : bankPointers)
		{
			ArrayList<byte[]> preMapList = rom.loadArrayOfStructuredData((int)(l - (0x8 << 24)), numMaps[mapNum], 4);
			ArrayList<Long> mapList = new ArrayList<Long>();
			int miniMapNum = 0;
			for(byte[] b : preMapList)
			{
				setStatus("Loading maps into tree...\tBank " + mapNum + ", map " + miniMapNum);
				try
				{
					long dataPtr = BitConverter.ToInt32(b);
					mapList.add(dataPtr);
					int mapName = BitConverter.GrabBytesAsInts(rom.getData(), (int)((dataPtr - (8 << 24)) + 0x14), 1)[0];
					//mapName -= 0x58; //TODO: Add Jambo51's map header hack
					int mapNamePokePtr = rom.getPointerAsInt((int)DataStore.MapLabels+ ((mapName - 0x58) * 4)); //TODO Use INIs
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(String.valueOf(rom.readPokeText(mapNamePokePtr) + " (" + mapNum + "." + miniMapNum + ")")); //TODO: Pull PokeText from header
					findNode(root,String.valueOf(mapNum)).add(node);
					miniMapNum++;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			maps[mapNum] = mapList;
			mapNum++;
		}

		setStatus("Refreshing tree...");
		model.reload(root);
		for (int i = 0; i < tree.getRowCount(); i++)
		{
			TreePath path = tree.getPathForRow(i);
			if (path != null)
			{
				javax.swing.tree.TreeNode node = (javax.swing.tree.TreeNode) path.getLastPathComponent();
				String str = node.toString();
				DefaultTreeModel models = (DefaultTreeModel) tree.getModel();
				models.valueForPathChanged(path, str);
			}
		}
		banksLoaded = true;
		setStatus("Banks Loaded!");
	}

	public void setStatus(String status)
	{
		lbl.setText(status);
	}

	private TreePath findPath(DefaultMutableTreeNode root, String s)
	{
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
		while (e.hasMoreElements())
		{
			DefaultMutableTreeNode node = e.nextElement();
			if (node.toString().equalsIgnoreCase(s))
			{
				return new TreePath(node.getPath());
			}
		}
		return null;
	}
	
	private DefaultMutableTreeNode findNode(DefaultMutableTreeNode root, String s)
	{
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
		while (e.hasMoreElements())
		{
			DefaultMutableTreeNode node = e.nextElement();
			if (node.toString().equalsIgnoreCase(s))
			{
				return node;
			}
		}
		return null;
	}
}
