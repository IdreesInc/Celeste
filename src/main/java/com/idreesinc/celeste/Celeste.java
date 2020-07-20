package com.idreesinc.celeste;

import com.idreesinc.celeste.commands.CommandIdrees;
import com.idreesinc.celeste.commands.CommandShootingStar;
import com.idreesinc.celeste.commands.ShootingStarTask;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Celeste extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Hello, world!");
        this.getCommand("idrees").setExecutor(new CommandIdrees());
        this.getCommand("star").setExecutor(new CommandShootingStar());
        BukkitRunnable shootingStarTask = new ShootingStarTask(this);
        shootingStarTask.runTaskTimer(this, 0, 5);
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}
