/*
    Level Converter
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/level_converter
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.util;

public enum Formats {
	classic("Classic"),
	indev("Indev"),
	infdev("Infdev");
	private String name;
	Formats(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
}
