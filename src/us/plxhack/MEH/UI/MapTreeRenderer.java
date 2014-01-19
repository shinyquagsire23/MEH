package us.plxhack.MEH.UI;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class MapTreeRenderer extends DefaultTreeCellRenderer
{
	private static final long serialVersionUID = 4125843897499413188L;
	ImageIcon mainIcon;
	ImageIcon mainIcon_expanded;
	ImageIcon mapIcon;

	public MapTreeRenderer()
	{
		mainIcon = new ImageIcon(
				MainGUI.class.getResource("/resources/folder.png"));
		mainIcon_expanded = new ImageIcon(
				MainGUI.class.getResource("/resources/folder_open.png"));
		mapIcon = new ImageIcon(MainGUI.class.getResource("/resources/map.png"));
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus)
	{

		super.getTreeCellRendererComponent(tree, value, selected, expanded,
				leaf, row, hasFocus);

		JLabel label = this;

		try
		{
			if (tree.getPathForRow(row).getParentPath().getParentPath() == null || tree.getPathForRow(row).getParentPath() == null )
			{
				if (expanded)
					label.setIcon(mainIcon_expanded);
				else
					label.setIcon(mainIcon);
			}
			else
				label.setIcon(mapIcon);
		}
		catch (Exception e)
		{
		}

		return this;
	}
}
