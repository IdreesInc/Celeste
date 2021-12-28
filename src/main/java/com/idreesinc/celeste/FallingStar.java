package com.idreesinc.celeste;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.scheduler.BukkitRunnable;

import com.idreesinc.celeste.utilities.WorldLootGenerator;

public class FallingStar extends BukkitRunnable {

    private final Celeste celeste;
    private final Location location;
    private final Location dropLoc;
    private double y = 256;
    private boolean soundPlayed = false;
    private boolean lootDropped = false;
    private int sparkTimer;

    public FallingStar(Celeste celeste, Location location) {
        this.celeste = celeste;
        sparkTimer = celeste.worldLoot.worldLootConfigs.get(location.getWorld().getName()).fallingStarSparkTime;//getConfig().getInt("falling-stars-spark-time");
        this.location = location;
        dropLoc = new Location(location.getWorld(), location.getX(),
                location.getWorld().getHighestBlockAt(location).getY() + 1, location.getZ());
    }

    public void run() {
        double step = 1;
        location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location.getX(), y, location.getZ(),
                0,  0,  new Random().nextDouble(), 0,
                0.2, null, true);
        location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location.getX(),
                y + new Random().nextDouble() * step,
                location.getZ(),
                0,  0, -1, 0,
                1, null, true);
        if (y % (step * 2) == 0) {
            location.getWorld().spawnParticle(Particle.LAVA, location.getX(), y + new Random().nextDouble(),
                    location.getZ(),
                    0,  0, new Random().nextDouble(), 0,
                    0.2, null, true);
        }
        if (celeste.worldLoot.worldLootConfigs.get(location.getWorld().getName()).fallingStarSoundEnabled && !soundPlayed && y <= dropLoc.getY() + 75) {
            location.getWorld().playSound(dropLoc, Sound.BLOCK_BELL_RESONATE, (float) celeste.worldLoot.worldLootConfigs.get(location.getWorld().getName()).fallingStarVolume, 0.5f);
            soundPlayed = true;
        }
        if (y <= dropLoc.getY()) {
            if (!lootDropped) {
            	WorldLootGenerator.WLConfiguration config = celeste.worldLoot.worldLootConfigs.get(dropLoc.getWorld().getName().toLowerCase());
	            if(config != null) {
	            	for(String lootTableName : config.fallingStarLoot) {
	            		Entity marker = (Entity) dropLoc.getWorld().spawnEntity(location, EntityType.MARKER);
	            		celeste.getServer().dispatchCommand(celeste.getServer().getConsoleSender(),"execute at "+ marker.getUniqueId().toString() +" run loot spawn "+dropLoc.getX() +" "+dropLoc.getY()+" "+dropLoc.getZ()+" loot "+lootTableName);
	            		marker.remove();
	            	}
	            	if(config.fallingStarExperience > 0) {
	                    ExperienceOrb orb = (ExperienceOrb) dropLoc.getWorld().spawnEntity(dropLoc, EntityType.EXPERIENCE_ORB);
	                    orb.setExperience(config.fallingStarExperience);	
	            	}
	            }
                lootDropped = true;
            }
            if (y % (step * 5) == 0) {
                location.getWorld().spawnParticle(Particle.LAVA, dropLoc,
                        0, 0, new Random().nextDouble(), 0,
                        1, null, true);
            }
            sparkTimer--;
            if (sparkTimer <= 0) {
                this.cancel();
            }
        }
        y -= step;
    }

}