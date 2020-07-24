package com.idreesinc.celeste.commands;

import com.idreesinc.celeste.Celeste;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandCeleste implements CommandExecutor {

    Celeste celeste;

    public CommandCeleste(Celeste celeste) {
        this.celeste = celeste;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            celeste.reload();
            sender.sendMessage("Celeste has been reloaded");
            return true;
        }
        return false;
    }

}
