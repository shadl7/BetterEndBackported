package mod.beethoven92.betterendforge.config;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.config.jsons.JsonEntryConfig;
import mod.beethoven92.betterendforge.config.jsons.JsonIdConfig;
import mod.beethoven92.betterendforge.config.jsons.JsonPathConfig;

public class Configs {
	public static final JsonIdConfig BIOME_CONFIG = new JsonEntryConfig(BetterEnd.MOD_ID, "biomes");
	public static final JsonPathConfig GENERATOR_CONFIG = new JsonPathConfig(BetterEnd.MOD_ID, "generator", false);

	public static final JsonPathConfig CLIENT_CONFIG = new JsonPathConfig(BetterEnd.MOD_ID, "client", false);
	
	public static void saveConfigs() {
		BIOME_CONFIG.saveChanges();
		GENERATOR_CONFIG.saveChanges();
		if (BetterEnd.isClient()) CLIENT_CONFIG.saveChanges();
	}
}
