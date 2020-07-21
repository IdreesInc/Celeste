package com.idreesinc.celeste;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class Astronomer extends BukkitRunnable {

    private final Celeste celeste;

    public Astronomer(Celeste celeste) {
        this.celeste = celeste;
    }

    public void run() {
        if (celeste.getServer().getOnlinePlayers().size() == 0) {
            return;
        }
        if (new Random().nextDouble() <= celeste.getConfig().getDouble("shooting-stars-per-minute") / 60d) {
            CelestialSphere.createShootingStar(getRandomPlayer());
            System.out.println("Spawning shooting star");
        }
        if (new Random().nextDouble() <=  celeste.getConfig().getDouble("falling-stars-per-minute") / 60d) {
            CelestialSphere.createFallingStar(celeste, getRandomPlayer());
            System.out.println("Spawning falling star");
        }
    }

    private Player getRandomPlayer() {
        ArrayList<Player> players = new ArrayList<Player>(celeste.getServer().getOnlinePlayers());
        int randomPlayer = new Random().nextInt(players.size());
        return players.get(randomPlayer);
    }

}