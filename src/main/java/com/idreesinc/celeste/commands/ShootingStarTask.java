package com.idreesinc.celeste.commands;

import com.idreesinc.celeste.ShootingStarHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ShootingStarTask extends BukkitRunnable {

    private final JavaPlugin plugin;

    public ShootingStarTask(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void run() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            ShootingStarHandler.createShootingStar(player);
        }
    }

}