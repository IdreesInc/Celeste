package com.idreesinc.celeste;

import com.idreesinc.celeste.commands.CommandIdrees;
import com.idreesinc.celeste.commands.CommandShootingStar;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Celeste extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Hello, world!");
        this.getCommand("idrees").setExecutor(new CommandIdrees());
        this.getCommand("star").setExecutor(new CommandShootingStar(this));
        BukkitRunnable shootingStarTask = new StargazingTask(this);
        shootingStarTask.runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}
