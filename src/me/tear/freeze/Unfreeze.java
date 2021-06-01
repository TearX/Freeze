package me.tear.freeze;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Unfreeze implements CommandExecutor {
	
	Main plugin;
	HashMap<Player, Player> staffPlayer;
	Unfreeze(Main plugin) {
	this.plugin = plugin;
	this.staffPlayer = plugin.getStaffPlayerMap();
	plugin.getCommand("Unfreeze").setExecutor(this);
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("Unfreeze")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("console")));
				return true;
			}
			Player player = (Player) sender;
			
			if (!player.hasPermission("unfreeze.use")) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("noperm")));
				return true;
			}
			
			if (args.length == 0) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("noSubject")));
				return true;
			}
			
			Player p = Bukkit.getPlayer(args[0]);
			
			if (!(plugin.getStaffPlayerMap().containsValue(p))) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("notFrozen")));
				return true;
			}
			
			if (p == sender) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("selfCmd")));
				return true;
			}
			
			for (Entry<Player, Player> entry: plugin.getStaffPlayerMap().entrySet()) {
				if (entry.getValue().equals(p)) {
					if ((entry.getKey()) != player) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("frozenByOther")));
						return true;
					}
				}
			}
			
			if (args.length >= 2) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("moreSubjects")));
				return true;
			}
			else {
				if (!(p instanceof Player)) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("unknownSubject")));
					return true;
				}
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("unfreezeSuccessStaff")));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("unfreezeSuccessPlayer")));
				plugin.getStaffPlayerMap().remove(player);
				p.removePotionEffect(PotionEffectType.BLINDNESS);
				p.removePotionEffect(PotionEffectType.GLOWING);
				return true;
			}
		}
		return false;
	}

}

