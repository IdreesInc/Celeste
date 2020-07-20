package com.idreesinc.celeste.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class CommandShootingStar implements CommandExecutor {

    private final double HEIGHT_MOD = 20;
    private final double MIN_HEIGHT = 30;
    private final double SPEED_MOD = 1.5;
    private final double MIN_SPEED = 0.75;
    private final double LOCATION_MOD = 30;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("Look up!");
            Location location = player.getLocation();
            double[] xz = normalize(new Random().nextDouble() * 2 - 1, new Random().nextDouble() * 2 - 1);
            double y = new Random().nextDouble() * -0.5;
            double height = new Random().nextDouble()  * HEIGHT_MOD + MIN_HEIGHT;
            double speed = new Random().nextDouble() * SPEED_MOD + MIN_SPEED;
            if (args.length > 0) {
                try {
                    height = Double.parseDouble(args[0]);
                } catch(NumberFormatException e) {
                    player.sendMessage("Height must be numeric");
                }
            }
            if (args.length > 1) {
                try {
                    speed = Double.parseDouble(args[1]);
                } catch(NumberFormatException e) {
                    player.sendMessage("Speed must be numeric");
                }
            }
            location.setY(Math.min(location.getY() + height, player.getWorld().getMaxHeight()));
            double[] locationOffset = normalize(new Random().nextDouble() * 2 - 1, new Random().nextDouble() * 2 - 1);
            location.setX(location.getX() + locationOffset[0] * LOCATION_MOD);
            location.setZ(location.getZ() + locationOffset[1] * LOCATION_MOD);
            player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location, 0, xz[0], y, xz[1], speed, null, true);
        }
        return true;
    }

    private double[] normalize(double a, double b) {
        double length = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        return  new double[] {a / length, b / length};
    }

}
