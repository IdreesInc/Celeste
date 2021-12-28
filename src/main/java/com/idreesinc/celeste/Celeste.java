package com.idreesinc.celeste;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.idreesinc.celeste.commands.CommandCeleste;
import com.idreesinc.celeste.commands.CommandFallingStar;
import com.idreesinc.celeste.commands.CommandShootingStar;
import com.idreesinc.celeste.utilities.Metrics;
import com.idreesinc.celeste.utilities.UpdateChecker;
import com.idreesinc.celeste.utilities.WorldLootGenerator;

public class Celeste extends JavaPlugin {

    //public WeightedRandomBag<String> fallingStarDrops = new WeightedRandomBag<>();
	public WorldLootGenerator worldLoot = new WorldLootGenerator(this);

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Metrics metrics = new Metrics(this, 8292);

        this.getCommand("celeste").setExecutor(new CommandCeleste(this));
        this.getCommand("shootingstar").setExecutor(new CommandShootingStar(this));
        this.getCommand("fallingstar").setExecutor(new CommandFallingStar(this));
        worldLoot.runWorldLootGenerator();

        BukkitRunnable stargazingTask = new Astronomer(this);
        stargazingTask.runTaskTimer(this, 0, 10);

        checkForUpdates();
    }

    public void reload() {
        reloadConfig();
        worldLoot.runWorldLootGenerator();
        checkForUpdates();
    }

    public void checkForUpdates() {
        if (this.getConfig().getBoolean("check-for-updates")) {
            new UpdateChecker(this, 81862).getVersion(version -> {
                try {
                    double current = Double.parseDouble(this.getDescription().getVersion());
                    double api = Double.parseDouble(version);
                    if (current < api) {
                        this.getLogger().info("There is an update available for Celeste (" + current + " -> " + api + ")");
                    }
                } catch (NumberFormatException e) {
                }
            });
        }
    }
}
