package com.idreesinc.celeste;

import com.idreesinc.celeste.config.CelesteConfig;
import org.bukkit.World;
import org.bukkit.entity.Player;
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
            double shootingStarsPerMin;
            double fallingStarPerMin;
            if (config.newMoonMeteorShower && (world.getFullTime() / 24000) % 8 == 4) {
                shootingStarsPerMin = ((world.getPlayers().size() / config.adaptiveShootingStars)*0.1) + config.shootingStarsPerMinuteMeteorShower;
                shootingStarChance = shootingStarsPerMin / 120d;
                fallingStarPerMin = ((world.getPlayers().size() / config.adaptiveFallingStars)*0.1) + config.fallingStarsPerMinuteMeteorShower;
                fallingStarChance = fallingStarPerMin / 120d;
            } else {
                shootingStarsPerMin = ((world.getPlayers().size() / config.adaptiveShootingStars)*0.1) + config.shootingStarsPerMinute;
                shootingStarChance = shootingStarsPerMin / 120d;
                fallingStarPerMin = ((world.getPlayers().size() / config.adaptiveFallingStars)*0.1) + config.fallingStarsPerMinute;
                fallingStarChance = fallingStarPerMin / 120d;
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