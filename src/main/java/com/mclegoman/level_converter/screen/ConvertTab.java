/*
    Level Converter
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/level_converter
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.screen;

import com.mclegoman.level_converter.Main;
import com.mclegoman.level_converter.convert.Convert;
import com.mclegoman.level_converter.util.Formats;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConvertTab extends Tab {
	public JComboBox<Formats> inputFormats;
	public JComboBox<Formats> outputFormats;
	public JTextField inputFile;
	public JTextField outputDir;
	public JButton advanced;
	public JCheckBox convertPlayerData;
	public JButton convert;
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
		inputFormats.addActionListener(e -> updateOutputFormats());
		JButton selectInput = new JButton("...");
		addRow(tab, grid, "Input File:", inputFile = new JTextField(), selectInput);
		selectInput.addActionListener(o -> {
			selectInputFile(() -> inputFile.getText(), (s) -> inputFile.setText(s));
		});
		JButton selectOutput = new JButton("...");
		addRow(tab, grid, "Output Location:", outputDir = new JTextField(Paths.get("").toAbsolutePath().toString()), selectOutput);
		selectOutput.addActionListener(o -> {
			selectOutputDirectory(() -> outputDir.getText(), (s) -> outputDir.setText(s));
		});
		advanced = new JButton("Advanced...");
		advanced.addActionListener(e -> {
			advanced.setEnabled(false);
		});
		addRow(tab, grid, null, advanced);
		addRow(tab, grid, null, new JLabel("Convert Player Data:"), convertPlayerData = new JCheckBox((Icon)null, true));
		addRow(tab, grid, null, getConvert());
	}
	public static void selectInputFile(Supplier<String> dir, Consumer<String> onAccept) {
		String iDir = !dir.get().isEmpty() ? dir.get() : Paths.get("").toAbsolutePath().toString();
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(iDir));
		chooser.setDialogTitle(Main.data.getName() + ": Select level file to convert");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(Main.window) == JFileChooser.APPROVE_OPTION) onAccept.accept(chooser.getSelectedFile().getAbsolutePath());
	}
	public static void selectOutputDirectory(Supplier<String> dir, Consumer<String> onAccept) {
		String oDir = !dir.get().isEmpty() ? dir.get() : Paths.get("").toAbsolutePath().toString();
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(oDir));
		chooser.setDialogTitle(Main.data.getName() + ": Select directory for converted level");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(Main.window) == JFileChooser.APPROVE_OPTION) onAccept.accept(chooser.getSelectedFile().getAbsolutePath());
	}
	private void updateInputFormats() {
		this.inputFormats.removeAllItems();
		this.inputFormats.addItem(Formats.classic);
		this.inputFormats.addItem(Formats.indev);
	}
	private void updateOutputFormats() {
		this.outputFormats.removeAllItems();
		Formats selectedInputFormat = (Formats) this.inputFormats.getSelectedItem();
		if (selectedInputFormat == Formats.classic) {
			this.outputFormats.addItem(Formats.indev);
			this.outputFormats.addItem(Formats.infdev);
		} else if (selectedInputFormat == Formats.indev) {
			this.outputFormats.addItem(Formats.infdev);
		}
	}
	private JButton getConvert() {
		convert = new JButton("Convert!");
		convert.addActionListener(e -> {
			if (!this.inputFile.getText().isEmpty() && !this.outputDir.getText().isEmpty()) {
				// TODO: Check if the input and output are valid.
				File input = new File(this.inputFile.getText());
				File output = new File(this.outputDir.getText());
				// We make sure that the user can't change anything after starting a conversion.
				this.inputFormats.setEnabled(false);
				this.outputFormats.setEnabled(false);
				this.inputFile.setEnabled(false);
				this.outputDir.setEnabled(false);
				this.advanced.setEnabled(false);
				this.convertPlayerData.setEnabled(false);
				this.convert.setEnabled(false);
				// This just prevents the user from switching tabs.
				Main.window.getContentPane().setEnabled(false);
				Convert.convert(new Convert.Data(
							(Formats) this.inputFormats.getSelectedItem(),
							(Formats) this.outputFormats.getSelectedItem(),
							input,
							output,
							this.convertPlayerData.isSelected()
						),
						(message, messageType) -> {
							this.inputFormats.setEnabled(true);
							this.outputFormats.setEnabled(true);
							this.inputFile.setEnabled(true);
							this.outputDir.setEnabled(true);
							this.advanced.setEnabled(true);
							this.convertPlayerData.setEnabled(true);
							this.convert.setEnabled(true);
							Main.window.getContentPane().setEnabled(true);
							JOptionPane.showMessageDialog(Main.window, message, Main.data.getName(), messageType);
				});
			} else {
				String message = inputFile.getText().isEmpty() && outputDir.getText().isEmpty() ? "Input File and Output Directory are both required!" : (inputFile.getText().isEmpty() ? "Input File is required!" : "Output Directory is required!");
				JOptionPane.showMessageDialog(Main.window, message, Main.data.getName(), JOptionPane.WARNING_MESSAGE);
			}
		});
		return convert;
	}
}
