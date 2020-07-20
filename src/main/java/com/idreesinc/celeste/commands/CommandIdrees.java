package com.idreesinc.celeste.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandIdrees implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack apple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
            player.getInventory().addItem(apple);
        }
        return true;
    }
}
