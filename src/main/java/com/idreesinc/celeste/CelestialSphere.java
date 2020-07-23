package com.idreesinc.celeste;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class CelestialSphere {
    public static void createShootingStar(Player player) {
        createShootingStar(player.getLocation());
    }

    public static void createShootingStar(Location location) {
//        double radius = 100 + new Random().nextDouble() * 30;
//        Vector locationMod = new Vector(new Random().nextDouble() * 2 - 1, new Random().nextDouble(), new Random().nextDouble() * 2 - 1);
//        locationMod.normalize();
//        locationMod.multiply(radius);
//        Location starLocation = new Location(location.getWorld(), locationMod.getX() + location.getX(), Math.min(256,
//                Math.max(150, locationMod.getY() + location.getY() + 10)), locationMod.getZ() + location.getZ());
//        Vector direction = new Vector(new Random().nextDouble() * 2 - 1, new Random().nextDouble() * -1 * Math.cos(locationMod.getY() / radius * (Math.PI / 2)), new Random().nextDouble() * 2 - 1);
//        direction.normalize();
        double w = 100 * Math.sqrt(new Random().nextDouble());
        double t = 2d * Math.PI * new Random().nextDouble();
        double x = w * Math.cos(t);
        double y = Math.max(new Random().nextDouble() * 50 + 100, location.getY() + 50);
        double z = w * Math.sin(t);
        Location starLocation = new Location(location.getWorld(), location.getX() + x, y, location.getZ() + z);
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
