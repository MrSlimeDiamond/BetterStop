package me.slimediamond.betterstop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("betterstop.stop")) {
            sender.sendMessage(ChatColor.RED+"You think you can stop the server? How about no. (Permission denied)");
            return true;
        }

        if (args.length == 0) {
            for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                if (players.isOp() || players.hasPermission("betterstop.see")) {
                    players.kickPlayer(ChatColor.GREEN+"Stop requested by: "+sender.getName()+ChatColor.RED+"\nMessage:\n"+ "Server Closed with no message.");
                } else {
                    players.kickPlayer(ChatColor.RED+"Server Closed");
                }
            }
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "save-all");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "minecraft:stop");
        }

        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
            if (players.isOp() || players.hasPermission("betterstop.see")) {
                players.kickPlayer(ChatColor.GREEN+"Stop requested by: "+sender.getName()+ChatColor.RED+"\nMessage:\n"+ Arrays.stream(args).collect(Collectors.joining(" ")).replace("[", "").replace("]", ""));
            } else players.kickPlayer(ChatColor.RED+Arrays.stream(args).collect(Collectors.joining(" ")).replace("[", "").replace("]", ""));
        }
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "save-all");
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "minecraft:stop");
        return true;
    }
}
