package us.plxhack.MEH.UI;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import java.awt.*;

public class MapTreeRenderer extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = 4125843897499413188L;
	ImageIcon mainIcon;
	ImageIcon mainIcon_expanded;
	ImageIcon mainIcon_expanded_maps;
	ImageIcon mapIcon;

	public MapTreeRenderer() {
		mainIcon = new ImageIcon(
				MainGUI.class.getResource("/resources/folder.png"));
		mainIcon_expanded = new ImageIcon(
				MainGUI.class.getResource("/resources/folder_open.png"));
		mainIcon_expanded_maps = new ImageIcon(
				MainGUI.class.getResource("/resources/folder_open_maps.png"));
		mapIcon = new ImageIcon(
				MainGUI.class.getResource("/resources/map.png"));
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded,
				leaf, row, hasFocus);
		
		JLabel label = this;
		
		try {
			if (((DefaultMutableTreeNode) value).getLevel() == 1) {
				if (expanded)
					label.setIcon(mainIcon_expanded);
				else
					label.setIcon(mainIcon);
			}
			else if (((DefaultMutableTreeNode) value).getLevel() == 2) {
				if (expanded)
					label.setIcon(mainIcon_expanded_maps);
				else
					label.setIcon(mainIcon);
			}
			else
				label.setIcon(mapIcon);
		}
		catch (Exception e) {
		}

		return this;
	}
}
