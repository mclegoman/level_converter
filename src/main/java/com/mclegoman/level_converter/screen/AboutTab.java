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
		addRow(tab, grid, null, new JLabel(" "));
		addVersioning(tab, grid);
	}
}
