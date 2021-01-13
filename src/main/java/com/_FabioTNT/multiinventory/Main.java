package com._FabioTNT.multiinventory;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private DataManager dataManager;

    @Override
    public void onEnable() {
        getCommand("multiinventory").setExecutor(new Commands(this));
        saveDefaultConfig();
        dataManager = new DataManager(this);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
