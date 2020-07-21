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
            ArrayList<Player> players = getViablePlayers();
            if (players.size() == 0) {
                return;
            }
            CelestialSphere.createShootingStar(players.get(new Random().nextInt(players.size())));
            System.out.println("Spawning shooting star");
        }
        if (new Random().nextDouble() <=  celeste.getConfig().getDouble("falling-stars-per-minute") / 60d) {
            ArrayList<Player> players = getViablePlayers();
            if (players.size() == 0) {
                return;
            }
            CelestialSphere.createFallingStar(celeste, players.get(new Random().nextInt(players.size())));
            System.out.println("Spawning falling star");
        }
    }

    private ArrayList<Player> getViablePlayers() {
        ArrayList<Player> players = new ArrayList<Player>();
        for (Player player : celeste.getServer().getOnlinePlayers()) {
            if (player.getWorld().getTime() >= 13000 && player.getWorld().getTime() <= 23000) { // Nighttime
                players.add(player);
            }
        }
        return players;
    }

}