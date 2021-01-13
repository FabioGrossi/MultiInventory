package com._FabioTNT.multiinventory;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class DataManager {

    private final Main plugin;

    public DataManager(Main plugin){
        this.plugin = plugin;
    }

    public boolean saveInventory(Player player, String name){
        String playerName = player.getName();
        FileConfiguration config = plugin.getConfig();
        if (!config.isConfigurationSection(playerName)){
            config.createSection(playerName);
        }
        ConfigurationSection playerSection = config.getConfigurationSection(playerName);
        if (playerSection.isConfigurationSection(name)){
            deleteInventory(player, name);
        }else {
            playerSection.createSection(name);
        }
        ConfigurationSection nameSection = playerSection.getConfigurationSection(name);
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++){
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null){
                itemStack = new ItemStack(Material.AIR);
            }
            nameSection.set(String.valueOf(i), itemStack);
        }
        plugin.saveConfig();
        return true;
    }

    public PlayerInventory getInventory(Player player, String name){
        String playerName = player.getName();
        FileConfiguration config = plugin.getConfig();
        if (!config.isConfigurationSection(playerName)){
            config.createSection(playerName);
        }
        ConfigurationSection playerSection = config.getConfigurationSection(playerName);
        PlayerInventory inventory = player.getInventory();
        if (!playerSection.isConfigurationSection(name)){
            return null;
        }
        ConfigurationSection nameSection = playerSection.getConfigurationSection(name);
        for (String key : playerSection.getConfigurationSection(name).getKeys(false)){
            inventory.setItem(Integer.parseInt(key), nameSection.getItemStack(key));
        }
        plugin.saveConfig();
        return inventory;
    }

    public boolean setInventory(Player player, Inventory inventory){
        for (int i = 0; i < inventory.getSize(); i++){
            ItemStack itemStack = inventory.getItem(i);
            player.getInventory().setItem(i, itemStack);
        }
        plugin.saveConfig();
        return true;
    }

    public boolean deleteInventory(Player player, String name){
        String playerName = player.getName();
        FileConfiguration config = plugin.getConfig();
        if (!config.isConfigurationSection(playerName)) {
            return false;
        }
        ConfigurationSection playerSection = config.getConfigurationSection(playerName);
        if (!playerSection.isConfigurationSection(name)){
            return false;
        }
        playerSection.set("name", "");
        plugin.saveConfig();
        return true;
    }

}
