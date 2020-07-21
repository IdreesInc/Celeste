package com.idreesinc.celeste;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

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
        sparkTimer = celeste.getConfig().getInt("falling-stars-spark-time");
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
        if (!soundPlayed && y <= dropLoc.getY() + 75) {
            location.getWorld().playSound(dropLoc, Sound.BLOCK_BELL_RESONATE, 15, 0.5f);
            soundPlayed = true;
        }
        if (y <= dropLoc.getY()) {
            if (!lootDropped) {
                if (celeste.fallingStarDrops.entries.size() > 0) {
                    ItemStack drop = new ItemStack(Material.valueOf(celeste.fallingStarDrops.getRandom()), 1);
                    location.getWorld().dropItem(dropLoc, drop);
                }
                ExperienceOrb orb = (ExperienceOrb) dropLoc.getWorld().spawnEntity(dropLoc, EntityType.EXPERIENCE_ORB);
                orb.setExperience(celeste.getConfig().getInt("falling-stars-experience"));
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