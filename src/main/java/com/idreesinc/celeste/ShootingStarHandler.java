package com.idreesinc.celeste;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class ShootingStarHandler {
    public static void createShootingStar(Player player) {
        createShootingStar(player.getLocation());
    }

    public static void createShootingStar(Location location) {
        double radius = 30 + new Random().nextDouble() * 30;
        Vector locationMod = new Vector(new Random().nextDouble() * 2 - 1, new Random().nextDouble(), new Random().nextDouble() * 2 - 1);
        locationMod.normalize();
        locationMod.multiply(radius);
        Location starLocation = new Location(location.getWorld(), locationMod.getX() + location.getX(), Math.min(256,
                Math.max(70, locationMod.getY() + location.getY() + 10)), locationMod.getZ() + location.getZ());
        Vector direction = new Vector(new Random().nextDouble() * 2 - 1, new Random().nextDouble() * -1 * Math.cos(locationMod.getY() / radius * (Math.PI / 2)), new Random().nextDouble() * 2 - 1);
        direction.normalize();
        location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, starLocation, 0, direction.getX(), direction.getY(), direction.getZ(), new Random().nextDouble() * 1.5 + 0.5, null, true);
    }
}
