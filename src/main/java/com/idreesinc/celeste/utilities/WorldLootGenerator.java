package com.idreesinc.celeste.utilities;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;

import com.idreesinc.celeste.Celeste;

/*
 * This class is made to handle a few things:
 * 	What worlds are able to have shooting/falling stars in them.
 * 	the amount of shooting/falling stars in a specific world.
 * 	and set loot tables for the loot that can spawn from shooting stars.
 * 	have that loot also be world specific.
 * 
 * This class handles issues #1 and #3 of the Celeste github repo.
 * 
 * @author Trico Everfire.
 */


public class WorldLootGenerator {
	
	private Celeste celeste;
	public Map<String,WLConfiguration> worldLootConfigs = new HashMap<>(); //Saves the world config data in a hashmap.
	
	public class WLConfiguration { //config to handle each world's data, this way each world can get it's own data.
		public boolean newMoonMeteorShower;
		public int starStartSpawnTime = 13000;
		public int starStopSpawnTime = 23000;
		public boolean hasShootingStars = true;
		public double shootingStarsPerMinute = 6;
		public double shootingStarsPerMinuteMeteorShower = 15;
		public int shootingStarsMinHeight = 130;
		public int shootingStarsMaxHeight = 160;
		public boolean hasFallingingStars = true;
		public double fallingStarsPerMinute = 0.2f;
		public double fallingStarsPerMinuteMeteorShower = 0.3f;
		public int fallingStarRadius = 75;
		public boolean fallingStarSoundEnabled = true;
		public double fallingStarVolume = 6;
		public int fallingStarSparkTime = 200;
		public int fallingStarExperience = 100;
		public List<String> fallingStarLoot;
		
	}
	
	public WorldLootGenerator(Celeste celeste){
		this.celeste = celeste;
	}
	
	public void runWorldLootGenerator() {
		this.worldLootConfigs.clear(); //clear map for config updating.
		FileConfiguration config = this.celeste.getConfig();
		ConfigurationSection worlds = config.getConfigurationSection("shooting-star-worlds");
		for(String world : worlds.getKeys(false)) {
			ConfigurationSection worldSettings = worlds.getConfigurationSection(world);
			WLConfiguration worldConfig = new WLConfiguration();
			worldConfig.newMoonMeteorShower = worldSettings.getBoolean("new-moon-meteor-shower",true);
			worldConfig.starStartSpawnTime = worldSettings.getInt("star-start-spawn-time", 13000);
			worldConfig.starStopSpawnTime = worldSettings.getInt("star-stop-spawn-time", 23000);
			
			worldConfig.hasShootingStars = worldSettings.getBoolean("shooting-stars-enabled",true);
			worldConfig.shootingStarsPerMinute = worldSettings.getDouble("shooting-stars-per-minute", 6);
			worldConfig.shootingStarsPerMinuteMeteorShower = worldSettings.getDouble("shooting-stars-per-minute-during-meteor-showers",15);
			worldConfig.shootingStarsMinHeight = worldSettings.getInt("shooting-stars-min-height",130);
			worldConfig.shootingStarsMaxHeight = worldSettings.getInt("shooting-stars-max-height",160);
			
			worldConfig.hasFallingingStars = worldSettings.getBoolean("falling-stars-enabled",true);
			worldConfig.fallingStarsPerMinute = worldSettings.getDouble("falling-stars-per-minute", 0.2);
			worldConfig.fallingStarsPerMinute = worldSettings.getDouble("falling-stars-per-minute", 0.3);
			worldConfig.fallingStarRadius = worldSettings.getInt("falling-stars-radius", 75);
			worldConfig.fallingStarSoundEnabled = worldSettings.getBoolean("falling-stars-sound-enabled",true);
			worldConfig.fallingStarVolume = worldSettings.getDouble("falling-stars-volume", 6);
			worldConfig.fallingStarSparkTime = worldSettings.getInt("falling-stars-spark-time",200);
			worldConfig.fallingStarExperience = worldSettings.getInt("falling-stars-experience", 100);
			worldConfig.fallingStarLoot = worldSettings.getStringList("falling-stars-loot");
			worldLootConfigs.put(world, worldConfig);
		}
	}
	
	/*
  shooting-stars-enabled: true
  falling-stars-enabled: true
  shooting-stars-per-minute: 6
  shooting-stars-min-height: 130
  shooting-stars-max-height: 160
  falling-stars-per-minute: 0.2
  falling-stars-radius: 75
  falling-stars-sound-enabled: true
  falling-stars-volume: 6
  falling-stars-spark-time: 200
  falling-stars-experience: 100
  falling-stars-loot:
   "minecraft:chests/simple_dungeon"
  shooting-stars-per-minute-during-meteor-showers: 15
  falling-stars-per-minute-during-meteor-showers: 0.3 
	 */

}
