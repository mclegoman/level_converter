/*
    Level Converter
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/level_converter
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.config;


import com.mclegoman.level_converter.Main;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.values.TrackedValue;

public class ConvertConfig extends ReflectiveConfig {
	public static final ConvertConfig config = ConfigHelper.register("", Main.data.getId(), ConvertConfig.class);
	public static void init() {}
	@Comment("Please exit Level Converter before changing these options!")
	public final ConvertSettings conversionSettings = new ConvertSettings();
	public static class ConvertSettings extends Section {
		@Comment("This sets the default spawnX of the level if it can't be found.")
		public final TrackedValue<Integer> spawnX = this.value(128);
		@Comment("This sets the default spawnY of the level if it can't be found.")
		public final TrackedValue<Integer> spawnY = this.value(36);
		@Comment("This sets the default spawnZ of the level if it can't be found.")
		public final TrackedValue<Integer> spawnZ = this.value(128);
		@Comment("This sets the default time of the level if it can't be found.")
		public final TrackedValue<Integer> time = this.value(0);
		@Comment("This sets the default height of the level if it can't be found.")
		public final TrackedValue<Integer> height = this.value(64);
		@Comment("This sets the default length of the level if it can't be found.")
		public final TrackedValue<Integer> length = this.value(256);
		@Comment("This sets the default width of the level if it can't be found.")
		public final TrackedValue<Integer> width = this.value(256);
	}
}
