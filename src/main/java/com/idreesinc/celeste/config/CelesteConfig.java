package com.idreesinc.celeste.config;

import com.idreesinc.celeste.utilities.WeightedRandomBag;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class CelesteConfig {
    public boolean newMoonMeteorShower;
    public int beginSpawningStarsTime;
    public int endSpawningStarsTime;

    public boolean shootingStarsEnabled;
    public double shootingStarsPerMinute;
    public double shootingStarsPerMinuteMeteorShower;
    public int shootingStarsMinHeight;
    public int shootingStarsMaxHeight;

    public boolean fallingStarsEnabled;
    public double fallingStarsMaxHeight;
    public double fallingStarsPerMinute;
    public double fallingStarsPerMinuteMeteorShower;
    public int fallingStarsRadius;
    public boolean fallingStarsSoundEnabled;
    public double fallingStarsVolume;
    public int fallingStarsSparkTime;
    public int fallingStarsExperience;
    public WeightedRandomBag<String> fallingStarSimpleLoot;
    public String fallingStarLootTable;

    public CelesteConfig(ConfigurationSection section) {
        // Used to build the global config
        buildFromConfigurationSection(section);
    }

    public CelesteConfig(ConfigurationSection section, CelesteConfig globalConfig) {
        // Used to build per-world configs
        buildFromConfigurationSectionWithGlobal(section, globalConfig);
    }

    private void buildFromConfigurationSection(ConfigurationSection section) {
        newMoonMeteorShower = section.getBoolean("new-moon-meteor-shower");
        beginSpawningStarsTime = section.getInt("begin-spawning-stars-time");
        endSpawningStarsTime = section.getInt("end-spawning-stars-time");

        shootingStarsEnabled = section.getBoolean("shooting-stars-enabled");
        shootingStarsPerMinute = section.getDouble("shooting-stars-per-minute");
        shootingStarsPerMinuteMeteorShower = section.getDouble("shooting-stars-per-minute-during-meteor-showers");
        shootingStarsMinHeight = section.getInt("shooting-stars-min-height");
        shootingStarsMaxHeight = section.getInt("shooting-stars-max-height");

        fallingStarsEnabled = section.getBoolean("falling-stars-enabled");
        fallingStarsMaxHeight = section.getDouble("falling-stars-max-height");
        fallingStarsPerMinute = section.getDouble("falling-stars-per-minute");
        fallingStarsPerMinuteMeteorShower = section.getDouble("falling-stars-per-minute-during-meteor-showers");
        fallingStarsRadius = section.getInt("falling-stars-radius");
        fallingStarsSoundEnabled = section.getBoolean("falling-stars-sound-enabled");
        fallingStarsVolume = section.getDouble("falling-stars-volume");
        fallingStarsSparkTime = section.getInt("falling-stars-spark-time");
        fallingStarsExperience = section.getInt("falling-stars-experience");

        fallingStarLootTable = section.getString("falling-stars-loot-table");
        if (section.isSet("falling-stars-loot")) {
            fallingStarSimpleLoot = calculateSimpleLoot(section.getConfigurationSection("falling-stars-loot"));
        }
    }

    private void buildFromConfigurationSectionWithGlobal(ConfigurationSection section, CelesteConfig globalConfig) {
        newMoonMeteorShower = section.getBoolean("new-moon-meteor-shower", globalConfig.newMoonMeteorShower);
        beginSpawningStarsTime = section.getInt("begin-spawning-stars-time", globalConfig.beginSpawningStarsTime);
        endSpawningStarsTime = section.getInt("end-spawning-stars-time", globalConfig.endSpawningStarsTime);

        shootingStarsEnabled = section.getBoolean("shooting-stars-enabled", globalConfig.shootingStarsEnabled);
        shootingStarsPerMinute = section.getDouble("shooting-stars-per-minute", globalConfig.shootingStarsPerMinute);
        shootingStarsPerMinuteMeteorShower = section.getDouble("shooting-stars-per-minute-during-meteor-showers", globalConfig.shootingStarsPerMinuteMeteorShower);
        shootingStarsMinHeight = section.getInt("shooting-stars-min-height", globalConfig.shootingStarsMinHeight);
        shootingStarsMaxHeight = section.getInt("shooting-stars-max-height", globalConfig.shootingStarsMaxHeight);

        fallingStarsEnabled = section.getBoolean("falling-stars-enabled", globalConfig.fallingStarsEnabled);
        fallingStarsMaxHeight = section.getDouble("falling-stars-max-height", globalConfig.fallingStarsMaxHeight);
        fallingStarsPerMinute = section.getDouble("falling-stars-per-minute", globalConfig.fallingStarsPerMinute);
        fallingStarsPerMinuteMeteorShower = section.getDouble("falling-stars-per-minute-during-meteor-showers", globalConfig.fallingStarsPerMinuteMeteorShower);
        fallingStarsRadius = section.getInt("falling-stars-radius", globalConfig.fallingStarsRadius);
        fallingStarsSoundEnabled = section.getBoolean("falling-stars-sound-enabled", globalConfig.fallingStarsSoundEnabled);
        fallingStarsVolume = section.getDouble("falling-stars-volume", globalConfig.fallingStarsVolume);
        fallingStarsSparkTime = section.getInt("falling-stars-spark-time", globalConfig.fallingStarsSparkTime);
        fallingStarsExperience = section.getInt("falling-stars-experience", globalConfig.fallingStarsExperience);

        if (section.isSet("falling-stars-loot") || section.isSet("falling-stars-loot-table")) {
            // Ensure that neither type of loot is inherited from the global config if any overrides are used
            // This means that even if the global config sets simple loot and the world config overrides just loot
            // table, the end result will be a config with the loot table set and the simple table empty
            fallingStarLootTable = section.getString("falling-stars-loot-table");
            if (section.isSet("falling-stars-loot")) {
                fallingStarSimpleLoot = calculateSimpleLoot(section.getConfigurationSection("falling-stars-loot"));
            }
        } else {
            fallingStarLootTable = globalConfig.fallingStarLootTable;
            fallingStarSimpleLoot = globalConfig.fallingStarSimpleLoot;
        }
    }

    public WeightedRandomBag<String> calculateSimpleLoot(ConfigurationSection loot) {
        WeightedRandomBag<String> fallingStarDrops = new WeightedRandomBag<>();
        for (String key : loot.getKeys(false)) {
            try {
                Material.valueOf(key.toUpperCase());
                fallingStarDrops.addEntry(key.toUpperCase(), loot.getDouble(key));
            } catch (IllegalArgumentException e) {
                System.err.println("Error: Item with name " + key.toUpperCase() + " does not exist, skipping");
            }
        }
        return fallingStarDrops;
    }
}
