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

            double shootingStarChance = celeste.getConfig().getDouble("shooting-stars-per-minute") / 60d;
            double fallingStarChance = celeste.getConfig().getDouble("falling-stars-per-minute") / 60d;

            if (celeste.getConfig().getBoolean("new-moon-meteor-shower") && (world.getFullTime() / 24000) % 8 == 4) {
                shootingStarChance *= celeste.getConfig().getDouble("meteor-shower-shooting-stars-modifier");
                fallingStarChance *= celeste.getConfig().getDouble("meteor-shower-falling-stars-modifier");
            }

            if (new Random().nextDouble() <= shootingStarChance) {
                CelestialSphere.createShootingStar(world.getPlayers().get(new Random().nextInt(world.getPlayers().size())));
//                System.out.println("Spawning shooting star");
            }
            if (new Random().nextDouble() <=  fallingStarChance) {
                CelestialSphere.createFallingStar(celeste, world.getPlayers().get(new Random().nextInt(world.getPlayers().size())));
//                System.out.println("Spawning falling star");
            }
        }
    }
}