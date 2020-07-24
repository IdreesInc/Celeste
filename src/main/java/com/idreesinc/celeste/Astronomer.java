package com.idreesinc.celeste;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
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

        List<World> worlds = celeste.getServer().getWorlds();
        for (World world : worlds) {
            if (!world.getEnvironment().equals(World.Environment.NORMAL)) {
                continue;
            }
            if (world.getPlayers().size() == 0) {
                continue;
            }
            if (!(world.getTime() >= 13000 && world.getTime() <= 23000)) {
                continue;
            }
            if (world.hasStorm()) {
                continue;
            }

            double shootingStarChance;
            double fallingStarChance;
            if (celeste.getConfig().getBoolean("new-moon-meteor-shower") && (world.getFullTime() / 24000) % 8 == 4) {
                shootingStarChance =
                        celeste.getConfig().getDouble("shooting-stars-per-minute-during-meteor-showers") / 120d;
                fallingStarChance =
                        celeste.getConfig().getDouble("falling-stars-per-minute-during-meteor-showers") / 120d;
            } else {
                shootingStarChance = celeste.getConfig().getDouble("shooting-stars-per-minute") / 120d;
                fallingStarChance = celeste.getConfig().getDouble("falling-stars-per-minute") / 120d;
            }

            if (celeste.getConfig().getBoolean("shooting-stars-enabled") && new Random().nextDouble() <= shootingStarChance) {
                CelestialSphere.createShootingStar(celeste, world.getPlayers().get(new Random().nextInt(world.getPlayers().size())));
            }
            if (celeste.getConfig().getBoolean("falling-stars-enabled") && new Random().nextDouble() <=  fallingStarChance) {
                CelestialSphere.createFallingStar(celeste, world.getPlayers().get(new Random().nextInt(world.getPlayers().size())));
            }
        }
    }
}