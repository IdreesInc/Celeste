package com.idreesinc.celeste;

import com.idreesinc.celeste.commands.CommandIdrees;
import com.idreesinc.celeste.commands.CommandShootingStar;
import org.bukkit.plugin.java.JavaPlugin;

public class Celeste extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Hello, world!");
        this.getCommand("idrees").setExecutor(new CommandIdrees());
        this.getCommand("star").setExecutor(new CommandShootingStar());
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}
