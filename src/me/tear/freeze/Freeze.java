package me.tear.freeze;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Freeze implements CommandExecutor {
	
	Main plugin;
	HashMap<Player, Player> staffPlayer;
    Freeze(Main plugin) {
    this.plugin = plugin;
    this.staffPlayer = plugin.getStaffPlayerMap();
    plugin.getCommand("Freeze").setExecutor(this);
    }

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("Freeze")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("console")));
				return true;
			}
			Player player = (Player) sender;
			
			if (!player.hasPermission("freeze.use")) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("noPerm")));
				return true;
			}
			
			if (args.length == 0) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("noSubject")));
				return true;
			}
					
			Player p = Bukkit.getPlayer(args[0]);
			
			if (p == sender) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("selfCmd")));
				return true;
			}
			
			if (plugin.getStaffPlayerMap().containsValue(p)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("alreadyFrozen")));
				return true;
			}
			
			else if (args.length >= 2) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("moreSubjects")));
				return true;
			}
			else {
				if (!(p instanceof Player)) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("unknownSubject")));
					return true;
				}
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("freezeSuccessStaff")));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("freezeSuccessPlayer")));
				plugin.getStaffPlayerMap().put(player, p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 10));
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 10));
				return true;
			}
		}
		return false;
	}

}

