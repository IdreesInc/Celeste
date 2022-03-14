package com.idreesinc.celeste;

import com.idreesinc.celeste.config.CelesteConfig;
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
            CelesteConfig config = celeste.configManager.getConfigForWorld(world.getName());
            if (!celeste.configManager.doesWorldHaveOverrides(world.getName())
                    && !world.getEnvironment().equals(World.Environment.NORMAL)) {
                // Ensure that Celeste only runs on normal worlds unless override is specified in config
                continue;
            }
            if (world.getPlayers().size() == 0) {
                continue;
            }
            if (!(world.getTime() >= config.beginSpawningStarsTime && world.getTime() <= config.endSpawningStarsTime)) {
                continue;
            }
            if (world.hasStorm()) {
                continue;
            }

            double shootingStarChance;
            double fallingStarChance;
            if (config.newMoonMeteorShower && (world.getFullTime() / 24000) % 8 == 4) {
                shootingStarChance = config.shootingStarsPerMinuteMeteorShower / 120d;
                fallingStarChance = config.fallingStarsPerMinuteMeteorShower / 120d;
            } else {
                shootingStarChance = config.shootingStarsPerMinute / 120d;
                fallingStarChance = config.fallingStarsPerMinute / 120d;
            }

            if (config.shootingStarsEnabled && new Random().nextDouble() <= shootingStarChance) {
                CelestialSphere.createShootingStar(celeste,
                        world.getPlayers().get(new Random().nextInt(world.getPlayers().size())));
            }
            if (config.fallingStarsEnabled && new Random().nextDouble() <=  fallingStarChance) {
                CelestialSphere.createFallingStar(celeste,
                        world.getPlayers().get(new Random().nextInt(world.getPlayers().size())));
            }
        }
    }
}