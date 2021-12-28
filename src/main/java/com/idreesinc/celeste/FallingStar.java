package com.idreesinc.celeste;

import com.idreesinc.celeste.config.CelesteConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class FallingStar extends BukkitRunnable {

    private final Celeste celeste;
    private final Location location;
    private final Location dropLoc;
    private final CelesteConfig config;
    private double y = 256;
    private boolean soundPlayed = false;
    private boolean lootDropped = false;
    private int sparkTimer;

    public FallingStar(Celeste celeste, Location location) {
        this.celeste = celeste;
        this.location = location;
        config = celeste.configManager.getConfigForWorld(location.getWorld().getName());
        sparkTimer = config.fallingStarsSparkTime;
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
        if (config.fallingStarsSoundEnabled && !soundPlayed && y <= dropLoc.getY() + 75) {
            location.getWorld().playSound(dropLoc, Sound.BLOCK_BELL_RESONATE, (float) config.fallingStarsVolume, 0.5f);
            soundPlayed = true;
        }
        if (y <= dropLoc.getY()) {
            if (!lootDropped) {
                // Note that both simple loot and loot tables will drop if both are configured because why not
                if (config.fallingStarSimpleLoot != null && config.fallingStarSimpleLoot.entries.size() > 0) {
                    ItemStack drop = new ItemStack(Material.valueOf(config.fallingStarSimpleLoot.getRandom()), 1);
                    location.getWorld().dropItem(dropLoc, drop);
                    if (celeste.getConfig().getBoolean("debug")) {
                        celeste.getLogger().info("Spawned simple falling star loot");
                    }
                }
                if (config.fallingStarLootTable != null) {
                    // Armor stands are used as markers are not compatible with 1.14
                    Entity marker = dropLoc.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                    String command = String.format("execute at %s run loot spawn %s %s %s loot %s",
                            marker.getUniqueId(),
                            dropLoc.getX(),
                            dropLoc.getY(),
                            dropLoc.getZ(),
                            config.fallingStarLootTable);
                    celeste.getServer().dispatchCommand(celeste.getServer().getConsoleSender(), command);
                    marker.remove();
                    if (celeste.getConfig().getBoolean("debug")) {
                        celeste.getLogger().info("Spawned falling star loot from loot table '" + config.fallingStarLootTable + "'");
                    }
                }
                if (config.fallingStarsExperience > 0) {
                    ExperienceOrb orb = (ExperienceOrb) dropLoc.getWorld().spawnEntity(dropLoc, EntityType.EXPERIENCE_ORB);
                    orb.setExperience(config.fallingStarsExperience);
                    if (celeste.getConfig().getBoolean("debug")) {
                        celeste.getLogger().info("Dropping experience orbs with value " + config.fallingStarsExperience);
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