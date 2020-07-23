package com.idreesinc.celeste;

import com.idreesinc.celeste.commands.CommandFallingStar;
import com.idreesinc.celeste.commands.CommandShootingStar;
import com.idreesinc.celeste.utilities.Metrics;
import com.idreesinc.celeste.utilities.UpdateChecker;
import com.idreesinc.celeste.utilities.WeightedRandomBag;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Celeste extends JavaPlugin {

    public WeightedRandomBag<String> fallingStarDrops = new WeightedRandomBag<String>();

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 81862);

        this.getCommand("shootingstar").setExecutor(new CommandShootingStar(this));
        this.getCommand("fallingstar").setExecutor(new CommandFallingStar(this));
        ConfigurationSection loot;
        if (this.getConfig().isSet("falling-stars-loot")) {
            // User has added their own loot table, do not combine with defaults
            loot = this.getConfig().getConfigurationSection("falling-stars-loot");
        } else {
            // Use default loot table
            loot = this.getConfig().getDefaults().getConfigurationSection("falling-stars-loot");
        }
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
        stargazingTask.runTaskTimer(this, 0, 10);

        new UpdateChecker(this, 81862).getVersion(version -> {
//            System.out.println(version);
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
//                this.getLogger().info("Celeste is up to date!");
            } else {
                this.getLogger().info("There is a new update available for Celeste!");
            }
        });
    }
}
