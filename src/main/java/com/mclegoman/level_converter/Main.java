/*
    Level Converter
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/level_converter
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter;

import com.mclegoman.level_converter.config.ConvertConfig;
import com.mclegoman.level_converter.data.Version;
import com.mclegoman.level_converter.screen.Window;

import java.awt.*;

public class Main {
	public static Version data;
	public static Window window;
	public static void main(String[] args) {
		ConvertConfig.init();
		window = Window.create();
	}
	public static Image getIcon() {
		return Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("icon.png"));
	}
	static {
		data = Version.create("version.json");
	}
}