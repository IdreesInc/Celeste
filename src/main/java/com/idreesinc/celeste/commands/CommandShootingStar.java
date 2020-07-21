package com.idreesinc.celeste.commands;

import com.idreesinc.celeste.ShootingStarHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandShootingStar implements CommandExecutor {

    Plugin plugin;

    public CommandShootingStar(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("Make a wish!");
            ShootingStarHandler.createShootingStar(player);
            ShootingStarHandler.createFallingStar(plugin, player);
        }
        return true;
    }

}
