package com.idreesinc.celeste.commands;

import com.idreesinc.celeste.Celeste;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandCeleste implements CommandExecutor {

    Celeste celeste;

    public CommandCeleste(Celeste celeste) {
        this.celeste = celeste;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("celeste.reload")) {
                celeste.reload();
                sender.sendMessage("Celeste has been reloaded");
            } else {
                sender.sendMessage("You do not have permission to use this command");
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
            if (sender.hasPermission("celeste.info")) {
                double shootingStarRate=0;
                double adaptiveFallingStars = celeste.getConfig().getDouble("adaptive-falling-stars");
                if(adaptiveFallingStars !=0){
                    if(celeste.getConfig().getBoolean("adaptive-use-global-player-count")){
                        shootingStarRate = (celeste.getServer().getOnlinePlayers().size() / celeste.getConfig().getDouble("adaptive-falling-stars")) * 0.1;
                    } else {
                        List<World> worlds = celeste.getServer().getWorlds();
                        double highestRate = 0;
                        for (World world : worlds) {
                            double worldRate = (world.getPlayers().size() / celeste.getConfig().getDouble("adaptive-falling-stars"))*0.1;
                            if(highestRate<worldRate)
                            {
                                highestRate = worldRate;
                            }
                        }
                        shootingStarRate = highestRate;
                    }
                }
                shootingStarRate = shootingStarRate + celeste.getConfig().getDouble("falling-stars-per-minute");

                sender.sendMessage("Celeste v" + celeste.getDescription().getVersion());
                sender.sendMessage("Shooting stars: " + (celeste.getConfig().getBoolean("shooting-stars-enabled") ? "Enabled" : "Disabled"));
                sender.sendMessage("Falling stars: " + (celeste.getConfig().getBoolean("falling-stars-enabled") ? "Enabled" : "Disabled"));
                sender.sendMessage("Falling Stars rate (highest): "+shootingStarRate);
                sender.sendMessage("Meteor showers: " + (celeste.getConfig().getBoolean("new-moon-meteor-shower") ? "Enabled" : "Disabled"));
            } else {
                sender.sendMessage("You do not have permission to use this command");
            }
            return true;
        }
        return false;
    }

}
