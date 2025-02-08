/*
    Level Converter
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/level_converter
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.screen;

import com.mclegoman.level_converter.util.Formats;

import javax.swing.*;
import java.awt.*;

public class ConvertTab extends Tab {
	public JComboBox<Formats> inputFormats;
	public JComboBox<Formats> outputFormats;
	public JCheckBox convertPlayerData;
	public String getName() {
		return "Convert";
	}
	public void init(JPanel tab) {
		GridBagConstraints grid = new GridBagConstraints();
		grid.insets = new Insets(1, 1, 1, 1);
		grid.gridx = grid.gridy = 0;
		addTitle(tab, grid);
		addEmptyRow(tab, grid, 1);
		addRow(tab, grid, "Input Format:", inputFormats = new JComboBox<>());
		addRow(tab, grid, "Output Format:", outputFormats = new JComboBox<>());
		updateInputFormats();
		updateOutputFormats();
		addRow(tab, grid, "Input File:", new JTextField());
		addRow(tab, grid, "Output Location:", new JTextField());
		inputFormats.addActionListener(e -> updateOutputFormats());
		JButton advanced = new JButton("Advanced...");
		advanced.addActionListener(e -> {
			advanced.setEnabled(false);
		});
		addRow(tab, grid, null, advanced);
		JPanel convertPlayerDataPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		convertPlayerDataPanel.add(new JLabel("Convert Player Data:"));
		convertPlayerDataPanel.add(convertPlayerData = new JCheckBox());
		addRow(tab, grid, null, convertPlayerDataPanel);
		JButton convert = new JButton("Convert!");
		convert.addActionListener(e -> {
			convert.setEnabled(false);
		});
		addRow(tab, grid, null, convert);
	}
	private void updateInputFormats() {
		inputFormats.removeAllItems();
		inputFormats.addItem(Formats.classic);
		inputFormats.addItem(Formats.indev);
	}
	private void updateOutputFormats() {
		outputFormats.removeAllItems();
		Formats selectedInputFormat = (Formats) inputFormats.getSelectedItem();
		if (selectedInputFormat == Formats.classic) {
			outputFormats.addItem(Formats.indev);
			outputFormats.addItem(Formats.infdev);
		} else if (selectedInputFormat == Formats.indev) {
			outputFormats.addItem(Formats.infdev);
		}
	}
}
