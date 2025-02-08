package com.mclegoman.level_converter.screen;

import javax.swing.*;
import java.awt.*;

public class ConvertTab extends Tab {
	public String getName() {
		return "Convert";
	}
	public void init(JPanel tab) {
		GridBagConstraints grid = new GridBagConstraints();
		grid.insets = new Insets(1, 1, 1, 1);
		grid.gridx = grid.gridy = 0;

		addRow(tab, grid, null, new JLabel("This tab has not been created yet!"));
	}
}
