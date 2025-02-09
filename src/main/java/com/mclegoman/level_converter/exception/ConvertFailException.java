/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.exception;

public class ConvertFailException extends Exception {
	public ConvertFailException(String error) {
		super("Failed to convert level: " + error);
	}
}
