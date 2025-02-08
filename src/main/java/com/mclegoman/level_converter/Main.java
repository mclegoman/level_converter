package com.mclegoman.level_converter;

import com.mclegoman.level_converter.data.Version;
import com.mclegoman.level_converter.screen.Window;

import java.awt.*;

public class Main {
	public static final Version data;
	public static Window window;
	public static void main(String[] args) {
		window = Window.create();
	}
	public static Image getIcon() {
		return Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("icon.png"));
	}
	static {
		data = new Version("level_converter", "Level Converter", "a1.0", "dannytaylor", new Version.Licence("LGPL-3.0-or-later", "https://github.com/mclegoman/level_converter/blob/readme/licence"), "2025", "https://github.com/mclegoman/level_converter");
	}
}