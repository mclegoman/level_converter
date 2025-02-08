/*
    Level Converter
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/level_converter
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.screen;

import com.mclegoman.level_converter.Main;

import javax.swing.*;
import java.awt.*;

public class AboutTab extends Tab {
	public String getName() {
		return "About";
	}
	public void init(JPanel tab) {
		GridBagConstraints grid = new GridBagConstraints();
		grid.insets = new Insets(1, 1, 1, 1);
		grid.gridx = grid.gridy = 0;

		JLabel title = new JLabel(Main.data.getName());
		title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 48));
		addRow(tab, grid, null, new JLabel(new ImageIcon(Main.getIcon())), Box.createRigidArea(new Dimension(12, 0)), title);
		addRow(tab, grid, null, new JLabel("Easily convert classic and indev levels"));
		addEmptyRow(tab, grid, 1);
		addVersioning(tab, grid);
		addEmptyRow(tab, grid, 2);
		JLabel attributions = new JLabel("Attributions");
		attributions.setFont(new Font(attributions.getFont().getFontName(), Font.BOLD, 20));
		addRow(tab, grid, null, attributions);
		addRow(tab, grid, null, linkLabel("ClassicExplorer", "https://github.com/bluecrab2/ClassicExplorer"));
		addRow(tab, grid, null, new JLabel("Included with permission from bluecrab2"));
	}
}
