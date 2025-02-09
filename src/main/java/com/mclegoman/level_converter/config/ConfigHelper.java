/*
    Luminance
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.config;

import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.serializers.TomlSerializer;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.config.implementor_api.ConfigEnvironment;
import org.quiltmc.config.implementor_api.ConfigFactory;

import java.nio.file.Paths;

public class ConfigHelper {
	private static ConfigEnvironment tomlConfigEnvironment;
	public static <C extends ReflectiveConfig> C register(String namespace, String id, Class<C> config) {
		return ConfigFactory.create(getConfigEnvironment(), namespace, id, Paths.get("").toAbsolutePath(), builder -> {}, config, builder -> {});
	}
	public static <C extends ReflectiveConfig> void reset(C config) {
		reset(config, true);
	}
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <C extends ReflectiveConfig> void reset(C config, boolean save) {
		for (TrackedValue value : config.values()) value.setValue(value.getDefaultValue(), false);
		if (save) config.save();
	}
	public static ConfigEnvironment getConfigEnvironment() {
		if (tomlConfigEnvironment == null) {
			tomlConfigEnvironment = new ConfigEnvironment(Paths.get("").toAbsolutePath(), "toml", TomlSerializer.INSTANCE);
			tomlConfigEnvironment.registerSerializer(TomlSerializer.INSTANCE);
		}
		return tomlConfigEnvironment;
	}
}
