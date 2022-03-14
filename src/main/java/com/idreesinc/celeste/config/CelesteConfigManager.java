package com.idreesinc.celeste.config;

import com.idreesinc.celeste.Celeste;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class CelesteConfigManager {
	
	private final Celeste celeste;
	private final Map<String, CelesteConfig> worldConfigs = new HashMap<>();
	private CelesteConfig globalConfig;

	public CelesteConfigManager(Celeste celeste) {
		this.celeste = celeste;
	}
	
	public void processConfigs() {
		this.worldConfigs.clear();
		FileConfiguration config = this.celeste.getConfig();
		globalConfig = new CelesteConfig(config);
		ConfigurationSection worlds = config.getConfigurationSection("world-overrides");
		if (worlds != null) {
			for (String world : worlds.getKeys(false)) {
				ConfigurationSection worldSettings = worlds.getConfigurationSection(world);
				if (worldSettings == null) {
					this.celeste.getLogger().severe("Your world override config for world '" + world + "' is malformed, please review example configs at https://github.com/IdreesInc/Celeste");
					continue;
				}
				CelesteConfig worldConfig = new CelesteConfig(worldSettings, globalConfig);
				worldConfigs.put(world, worldConfig);
			}
		}
	}

	/**
	 * Get the config for the given world name (case-sensitive) or the global config if no overrides have been added
	 * @param worldName The name of the world
	 * @return The config that applies to the given world
	 */
	public CelesteConfig getConfigForWorld(String worldName) {
		CelesteConfig config = worldConfigs.get(worldName);
		if (config == null) {
			config = globalConfig;
		}
		return config;
	}

	/**
	 * Check whether the world has been configured with specific overrides in the config file
	 * @return Whether the specified world's configuration has been modified
	 */
	public boolean doesWorldHaveOverrides(String worldName) {
		return worldConfigs.containsKey(worldName);
	}
}
