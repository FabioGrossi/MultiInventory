package com._FabioTNT.multiinventory;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class Commands implements CommandExecutor {

    private final Main plugin;

    public Commands(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("multiinventory")){
            if (cmdSender instanceof ConsoleCommandSender){
                cmdSender.sendMessage("Il comando può essere eseguito solo da player");
                return true;
            }
            Player sender = (Player) cmdSender;
            if (!sender.isOp()){
                sender.sendMessage(ChatColor.RED + "Non hai i permessi!");
                return true;
            }
            if (args.length == 0){
                sendCommandInfo(sender);
                return true;
            }
            if (args.length == 1){
                if (args[0].equalsIgnoreCase("help")){
                    sendCommandInfo(sender);
                    return true;
                }
            }
            if (args.length == 2){
                String name = args[1];
                if (args[0].equalsIgnoreCase("save")){
                    plugin.getDataManager().saveInventory(sender, name);
                    sender.sendMessage(ChatColor.GREEN + "Inventario " + name + " salvato con successo!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("load")){
                    PlayerInventory inventory = plugin.getDataManager().getInventory(sender, name);
                    if (inventory == null){
                        sender.sendMessage(ChatColor.RED + "Inventario " + name + " non trovato!");
                        return true;
                    }
                    plugin.getDataManager().setInventory(sender, inventory);
                    sender.sendMessage(ChatColor.GREEN + "Inventario " + name + " caricato con successo!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("delete")){
                    if (plugin.getDataManager().deleteInventory(sender, name)){
                        sender.sendMessage(ChatColor.GREEN + "Inventario " + name + " eliminato con successo!");
                    }
                    sender.sendMessage(ChatColor.RED + "Inventario " + name + "  non trovato!");
                }
            }
        }
        return false;
    }

    private void sendCommandInfo(Player player){
        player.sendMessage("Info di MultiInventory");
        player.sendMessage("/mi save name > Per salvare un unventario");
        player.sendMessage("/mi load name > Per Impostare un inventario");
        player.sendMessage("/mi delete name > Per eliminare un inventario");
    }

}
