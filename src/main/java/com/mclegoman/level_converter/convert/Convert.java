/*
    Level Converter
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/level_converter
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.convert;

import com.mclegoman.level_converter.util.Formats;

import java.io.File;
import java.nio.file.Path;

public class Convert {
	public static void convert(Data data, FinishConvert onFinished) {
		// TODO: Start converting
		onFinished.run("Successfully converted level!");
	}
	public static class Data {
		private final Formats inType;
		private final Formats outType;
		private final File input;
		private final Path output;
		private final boolean convertPlayerData;
		public Data(Formats inType, Formats outType, File input, Path output, boolean convertPlayerData) {
			this.inType = inType;
			this.outType = outType;
			this.input = input;
			this.output = output;
			this.convertPlayerData = convertPlayerData;
		}
	}
	public interface FinishConvert {
		void run(String message);
	}
}
