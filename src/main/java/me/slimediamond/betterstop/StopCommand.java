package me.slimediamond.betterstop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class StopCommand implements CommandExecutor {
    Betterstop plugin;

    public StopCommand(Betterstop plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("betterstop.stop")) {
            sender.sendMessage(ChatColor.RED+"You think you can stop the server? How about no. (Permission denied)");
            return true;
        }
        String kickMessage;
        if (args.length == 0) {
            kickMessage = Objects.requireNonNull(plugin.getConfig().getString("messages.defaultKickMessage"));
        } else {
            kickMessage = Arrays.stream(args).collect(Collectors.joining(" ")).replace("[", "").replace("]", "");
        }
            for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                if (players.isOp() || players.hasPermission("betterstop.see")) {
                    //players.kickPlayer(ChatColor.GREEN+"Stop requested by: "+sender.getName()+ChatColor.RED+"\nMessage:\n"+ "Server Closed with no message.");
                    players.kickPlayer(Objects.requireNonNull(plugin.getConfig().getString("messages.admin")).replace("%message%", kickMessage).replace("%player%", sender.getName()).replace("&", "§").replace("\\n", "\n"));
                } else {
                    players.kickPlayer(Objects.requireNonNull(plugin.getConfig().getString("messages.normal")).replace("%message%", kickMessage).replace("%player%", sender.getName()).replace("&", "§").replace("\\n", "\n"));
                }
            }
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "save-all");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "minecraft:stop");
        return true;
    }
}
