/*
    Level Converter
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/level_converter
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.screen;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdvancedTab extends Tab {
	public JCheckBox replaceBedrock;
	public JSpinner replaceBedrockBlockId;
	public String getName() {
		return "Advanced Settings";
	}
	public void init(JPanel tab) {
		GridBagConstraints grid = new GridBagConstraints();
		grid.insets = new Insets(0, 0, 0, 0);
		grid.gridx = grid.gridy = 0;
		addRow(tab, grid, "Replace Bedrock:", replaceBedrock = new JCheckBox());
		addRow(tab, grid, "Replace Bedrock with Block ID:", replaceBedrockBlockId = new JSpinner(new SpinnerNumberModel(
				new Integer(0),
				new Integer(0),
				new Integer(1024), // idk how many block id's there are off the top of my head, so this should be enough lol.
				new Integer(1)
		)));
	}
	public Border getBorder() {
		return new EmptyBorder(12, 12, 12, 12);
	}
}
