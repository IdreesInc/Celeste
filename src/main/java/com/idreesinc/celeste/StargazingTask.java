package com.idreesinc.celeste;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class StargazingTask extends BukkitRunnable {

    private final double SHOOTING_STARS_PER_SECOND = 1d/30d;
    private final double FALLING_STARS_PER_SECOND = 1d/600d;
    private final JavaPlugin plugin;

    public StargazingTask(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void run() {
        if (new Random().nextDouble() <= SHOOTING_STARS_PER_SECOND) {
            ShootingStarHandler.createShootingStar(getRandomPlayer());
            System.out.println("Spawning shooting star");
        }
        if (new Random().nextDouble() <= FALLING_STARS_PER_SECOND) {
            ShootingStarHandler.createFallingStar(plugin, getRandomPlayer());
            System.out.println("Spawning falling star");
        }
    }

    private Player getRandomPlayer() {
        ArrayList<Player> players = new ArrayList<Player>(plugin.getServer().getOnlinePlayers());
        int randomPlayer = new Random().nextInt(players.size());
        return players.get(randomPlayer);
    }

}