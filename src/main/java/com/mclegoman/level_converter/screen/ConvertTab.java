/*
    Level Converter
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/level_converter
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.screen;

import com.mclegoman.level_converter.Main;
import com.mclegoman.level_converter.util.Formats;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConvertTab extends Tab {
	public JComboBox<Formats> inputFormats;
	public JComboBox<Formats> outputFormats;
	public JTextField inputFile;
	public JTextField outputDir;
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
		JButton selectInput = new JButton("...");
		addRow(tab, grid, "Input File:", inputFile = new JTextField(), selectInput);
		selectInput.addActionListener(o -> {
			selectInputFile(() -> "", (s) -> inputFile.setText(s));
		});
		JButton selectOutput = new JButton("...");
		addRow(tab, grid, "Output Location:", outputDir = new JTextField(Paths.get("").toAbsolutePath().toString()), selectOutput);
		selectOutput.addActionListener(o -> {
			selectOutputDirectory(() -> "", (s) -> outputDir.setText(s));
		});
		inputFormats.addActionListener(e -> updateOutputFormats());
		JButton advanced = new JButton("Advanced...");
		advanced.addActionListener(e -> {
			advanced.setEnabled(false);
		});
		addRow(tab, grid, null, advanced);
		addRow(tab, grid, null, new JLabel("Convert Player Data:"), convertPlayerData = new JCheckBox((Icon)null, true));
		JButton convert = new JButton("Convert!");
		convert.addActionListener(e -> {
			if (!inputFile.getText().isEmpty() && !outputDir.getText().isEmpty()) {
				inputFormats.setEnabled(false);
				outputFormats.setEnabled(false);
				inputFile.setEnabled(false);
				outputDir.setEnabled(false);
				advanced.setEnabled(false);
				convertPlayerData.setEnabled(false);
				convert.setEnabled(false);
				// TODO: Start converting
			}
		});
		addRow(tab, grid, null, convert);
	}
	public static void selectInputFile(Supplier<String> dir, Consumer<String> onAccept) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(dir.get()));
		chooser.setDialogTitle(Main.data.getName() + ": Select level file to convert");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) onAccept.accept(chooser.getSelectedFile().getAbsolutePath());
	}
	public static void selectOutputDirectory(Supplier<String> dir, Consumer<String> onAccept) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(dir.get()));
		chooser.setDialogTitle(Main.data.getName() + ": Select directory for converted level");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) onAccept.accept(chooser.getSelectedFile().getAbsolutePath());
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
