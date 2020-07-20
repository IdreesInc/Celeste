package com.idreesinc.celeste.commands;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class CommandShootingStar implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("Look up!");
            double radius = 30 + new Random().nextDouble() * 30;
            Vector locationMod = new Vector(new Random().nextDouble() * 2 - 1, new Random().nextDouble(), new Random().nextDouble() * 2 - 1);
            locationMod.normalize();
            locationMod.multiply(radius);
            Location location = player.getLocation();
            Location starLocation = new Location(player.getWorld(), locationMod.getX() + location.getX(), Math.min(256, Math.max(70, locationMod.getY() + location.getY() + 10)), locationMod.getZ() + location.getZ());
            Vector direction = new Vector(new Random().nextDouble() * 2 - 1, new Random().nextDouble() * -1 * Math.cos(locationMod.getY() / radius * (Math.PI / 2)), new Random().nextDouble() * 2 - 1);
            direction.normalize();
            player.sendMessage(direction.toString());
            player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, starLocation, 0, direction.getX(), direction.getY(), direction.getZ(), new Random().nextDouble() * 1.5 + 0.5, null, true);
        }
        return true;
    }

}
