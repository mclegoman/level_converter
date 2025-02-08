package com.mclegoman.level_converter.screen;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.mclegoman.level_converter.Main;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
	private JTabbedPane contentPane;
	public Window() {
		initComponents();
		setContentPane(contentPane);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImage(Main.getIcon());
		setTitle(Main.data.getName());
	}
	public static Window create() {
		setTheme();
		Window window = new Window();
		window.updateSize();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		return window;
	}
	public void updateSize() {
		setPreferredSize(new Dimension(800, 600));
		setMinimumSize(getPreferredSize());
		setSize(getPreferredSize());
		pack();
	}
	private void initComponents() {
		contentPane = new JTabbedPane(JTabbedPane.TOP);
		for (Tab tab : new Tab[]{new ConvertTab(), new AboutTab()}) contentPane.addTab(tab.getName(), tab.create());
	}
	private static void setTheme() {
		try {
			UIManager.setLookAndFeel(new FlatDarculaLaf());
		} catch (Exception error) {
			System.out.println(error.getLocalizedMessage());
		}
	}
}