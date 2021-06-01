package me.tear.freeze;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Reload implements CommandExecutor {
	
	Main plugin;
	HashMap<Player, Player> staffPlayer;
	Reload(Main plugin) {
	this.plugin = plugin;
	this.staffPlayer = plugin.getStaffPlayerMap();
	plugin.getCommand("Freeze-reload").setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("Freeze-reload")) {
			if (!sender.hasPermission("freeze.reload")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("noPerm")));
				return true;
			}
				plugin.getPluginLoader().disablePlugin(plugin);
				plugin.getPluginLoader().enablePlugin(plugin);
				plugin.reloadConfig();
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("reload")));
				return true;
		}
		return false;
	}
}
