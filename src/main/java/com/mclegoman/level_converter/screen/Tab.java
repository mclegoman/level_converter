/*
    Level Converter
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/level_converter
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.screen;

import com.mclegoman.level_converter.Main;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public abstract class Tab {
	public abstract String getName();
	public abstract void init(JPanel tab);
	public JPanel create() {
		JPanel tab = new JPanel(new GridBagLayout());
		tab.setBorder(getBorder());
		init(tab);
		return tab;
	}
	public Border getBorder() {
		return new EmptyBorder(48, 48, 48, 48);
	}
	protected static void addRow(Container parent, GridBagConstraints grid, String label, Component... components) {
		if (label != null) {
			grid.gridwidth = 1;
			grid.anchor = GridBagConstraints.LINE_END;
			grid.fill = GridBagConstraints.NONE;
			grid.weightx = 0;
			parent.add(new JLabel(label), grid);
			grid.gridx++;
			grid.anchor = GridBagConstraints.LINE_START;
			grid.fill = GridBagConstraints.HORIZONTAL;
		} else {
			grid.gridwidth = 2;
			grid.anchor = GridBagConstraints.CENTER;
			grid.fill = GridBagConstraints.NONE;
		}
		grid.weightx = 1;
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		for (Component comp : components) {
			panel.add(comp);
			panel.add(addSpacer());
		}
		parent.add(panel, grid);
		grid.gridy++;
		grid.gridx = 0;
	}
	protected static Component addSpacer(int w, int h) {
		return Box.createRigidArea(new Dimension(w, h));
	}
	protected static Component addSpacer() {
		return addSpacer(8, 0);
	}
	protected static void addEmptyRow(JPanel tab, GridBagConstraints grid, int amount) {
		for (int i = 0; i < amount; i++) addRow(tab, grid, null, new JLabel(" "));
	}
	protected static void addVersioning(Container parent, GridBagConstraints grid) {
		addRow(parent, grid, null, new JLabel(Main.data.getName() + " " + Main.data.getVersion() + " (c) " + Main.data.getYear() + " " + Main.data.getAuthor() + ". " + Main.data.getLicence().getId()));
		addRow(parent, grid, null, linkLabel("Source Code", Main.data.getSource()));
		addRow(parent, grid, null, linkLabel("Licence", Main.data.getLicence().getUrl()));
	}
	protected static void addTitle(Container parent, GridBagConstraints grid) {
		JLabel title = new JLabel(Main.data.getName());
		title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 48));
		addRow(parent, grid, null, new JLabel(new ImageIcon(Main.getIcon())), Box.createRigidArea(new Dimension(12, 0)), title);
		addRow(parent, grid, null, new JLabel(Main.data.getDescription()));
	}
	protected static JLabel linkLabel(String text, String url) {
		JLabel link = new JLabel(text);
		link.setForeground(Color.getHSBColor(0.53F, 0.62F, 0.78F));
		link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		link.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (Exception error) {
					System.out.println(error.getLocalizedMessage());
				}
			}
		});
		return link;
	}
}
