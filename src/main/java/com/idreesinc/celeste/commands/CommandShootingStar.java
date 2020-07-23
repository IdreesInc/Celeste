package com.idreesinc.celeste.commands;

import com.idreesinc.celeste.Celeste;
import com.idreesinc.celeste.CelestialSphere;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandShootingStar implements CommandExecutor {

    Celeste celeste;

    public CommandShootingStar(Celeste celeste) {
        this.celeste = celeste;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("Make a wish!");
            CelestialSphere.createShootingStar(player);
            CelestialSphere.createFallingStar(celeste, player, false);
            return true;
        }
        return false;
    }

}
