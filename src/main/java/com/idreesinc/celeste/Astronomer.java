package com.idreesinc.celeste;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import com.idreesinc.celeste.utilities.WorldLootGenerator;

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
        
        for(String worldKey : celeste.worldLoot.worldLootConfigs.keySet()) {
        	World world = celeste.getServer().getWorld(worldKey);
        	if(world == null) {
        		celeste.getLogger().warning("Invalid world: "+worldKey+" please double check config");
        		continue;
        	}
        	WorldLootGenerator.WLConfiguration config = celeste.worldLoot.worldLootConfigs.get(worldKey);
        	
            if (world.getPlayers().size() == 0) {
                continue;
            }
            if (!(world.getTime() >= config.starStartSpawnTime && world.getTime() <= config.starStopSpawnTime)) {
                continue;
            }
            if (world.hasStorm()) {
                continue;
            }
            
            double shootingStarChance;
            double fallingStarChance;
            if (config.newMoonMeteorShower && (world.getFullTime() / 24000) % 8 == 4) {
                shootingStarChance =
                		config.shootingStarsPerMinuteMeteorShower / 120d;
                fallingStarChance =
                		config.fallingStarsPerMinuteMeteorShower / 120d;
            } else {
                shootingStarChance = config.shootingStarsPerMinute / 120d;
                fallingStarChance = config.fallingStarsPerMinute / 120d;
            }

            if (config.hasShootingStars && new Random().nextDouble() <= shootingStarChance) {
                CelestialSphere.createShootingStar(celeste, world.getPlayers().get(new Random().nextInt(world.getPlayers().size())));
            }
            if (config.hasFallingingStars && new Random().nextDouble() <=  fallingStarChance) {
                CelestialSphere.createFallingStar(celeste, world.getPlayers().get(new Random().nextInt(world.getPlayers().size())));
            }
        	
        }


    }
}