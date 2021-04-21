package me.slimediamond.betterstop;

import org.bukkit.plugin.java.JavaPlugin;

public final class Betterstop extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("BetterStop loading");
        this.getCommand("stop").setExecutor(new StopCommand(this));
        getLogger().info("BetterStop loaded");
        loadConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("BetterStop disabled");
    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
