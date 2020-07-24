package com.idreesinc.celeste;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class CelestialSphere {

    public static void createShootingStar(Player player) {
        createShootingStar(player, true);
    }

    public static void createShootingStar(Player player, boolean approximate) {
        createShootingStar(player.getLocation(), approximate);
    }

    public static void createShootingStar(Location location) {
        createShootingStar(location, true);
    }

    public static void createShootingStar(Location location, boolean approximate) {
        Location starLocation;
        double w = 100 * Math.sqrt(new Random().nextDouble());
        double t = 2d * Math.PI * new Random().nextDouble();
        double x = w * Math.cos(t);
        double y = Math.max(new Random().nextDouble() * 50 + 100, location.getY() + 50);
        double z = w * Math.sin(t);
        if (approximate) {
            starLocation = new Location(location.getWorld(), location.getX() + x, y, location.getZ() + z);
        } else {
            starLocation = new Location(location.getWorld(), location.getX(), y, location.getZ());
        }
        Vector direction = new Vector(new Random().nextDouble() * 2 - 1, new Random().nextDouble() * -0.75d, new Random().nextDouble() * 2 - 1);
        direction.normalize();
        double speed = new Random().nextDouble() * 2 + 0.75;
        location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, starLocation, 0, direction.getX(),
                direction.getY(), direction.getZ(), speed, null, true);
        if (new Random().nextDouble() >= 0.5) {
            location.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, starLocation, 0, direction.getX(),
                    direction.getY(), direction.getZ(), speed, null, true);
        }
    }

    public static void createFallingStar(Celeste celeste, Player player) {
        createFallingStar(celeste, player, true);
    }

    public static void createFallingStar(Celeste celeste, Player player, boolean approximate) {
        createFallingStar(celeste, player.getLocation(), approximate);
    }

    public static void createFallingStar(Celeste celeste, final Location location) {
        createFallingStar(celeste, location, true);
    }

    public static void createFallingStar(Celeste celeste, final Location location, boolean approximate) {
        Location target = location;
        if (approximate) {
            double fallingStarRadius = celeste.getConfig().getDouble("falling-stars-radius");
            double w = fallingStarRadius * Math.sqrt(new Random().nextDouble());
            double t = 2d * Math.PI * new Random().nextDouble();
            double x = w * Math.cos(t);
            double z = w * Math.sin(t);
            target = new Location(location.getWorld(), location.getX() + x, location.getY(), location.getZ() + z);
        }
        BukkitRunnable fallingStarTask = new FallingStar(celeste, target);
        fallingStarTask.runTaskTimer(celeste, 0, 1);
    }
}
