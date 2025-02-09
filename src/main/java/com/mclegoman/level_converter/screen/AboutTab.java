/*
    Level Converter
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/level_converter
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.screen;

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
		addTitle(tab, grid);
		addEmptyLabelRow(tab, grid, 1);
		addVersioning(tab, grid);
		addEmptyLabelRow(tab, grid, 2);
		JLabel attributions = new JLabel("Attributions");
		attributions.setFont(new Font(attributions.getFont().getFontName(), Font.BOLD, 20));
		addRow(tab, grid, null, attributions);
		addRow(tab, grid, null, linkLabel("ClassicExplorer", "https://github.com/bluecrab2/ClassicExplorer"), new JLabel("Included with permission from bluecrab2"));
		addRow(tab, grid, null, linkLabel("FlatLaf", "https://github.com/JFormDesigner/FlatLaf"), new JLabel("Licenced under Apache-2.0"));
		addRow(tab, grid, null, linkLabel("jackson-databind", "https://github.com/FasterXML/jackson-databind"), new JLabel("Licenced under Apache-2.0"));
		addRow(tab, grid, null, linkLabel("Quilt Config", "https://github.com/QuiltMC/quilt-config"), new JLabel("Licenced under Apache-2.0"));
	}
}
