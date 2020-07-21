package com.idreesinc.celeste;

import com.idreesinc.celeste.commands.CommandIdrees;
import com.idreesinc.celeste.commands.CommandShootingStar;
import com.idreesinc.celeste.utilities.WeightedRandomBag;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Celeste extends JavaPlugin {

    public WeightedRandomBag<String> fallingStarDrops = new WeightedRandomBag<String>();

    @Override
    public void onEnable() {
        this.getCommand("idrees").setExecutor(new CommandIdrees());
        this.getCommand("star").setExecutor(new CommandShootingStar(this));
        ConfigurationSection loot;
        if (this.getConfig().isSet("falling-stars-loot")) {
            // User has added their own loot table, do not combine with defaults
            loot = this.getConfig().getConfigurationSection("falling-stars-loot");
        } else {
            // Use default loot table
            loot = this.getConfig().getDefaults().getConfigurationSection("falling-stars-loot");
        }
        System.out.println(this.getConfig().getDouble("falling-stars-spark-time"));
        if (loot != null) {
            for (String key : loot.getKeys(false)) {
                try {
                    Material.valueOf(key.toUpperCase());
                    fallingStarDrops.addEntry(key.toUpperCase(), loot.getDouble(key));
                } catch (IllegalArgumentException e) {
                    System.err.println("Error: Item with name " + key.toUpperCase() + " does not exist, skipping");
                }
            }
        } else {
            System.err.println("Error: Loot table for falling stars can't be found");
        }
        BukkitRunnable stargazingTask = new Astronomer(this);
        stargazingTask.runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}
