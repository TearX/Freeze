package me.tear.freeze;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class EventListener implements Listener {
	
	Main plugin;
	HashMap<Player, Player> staffPlayer;
    EventListener(Main plugin)
    {
        this.plugin = plugin;
        this.staffPlayer = plugin.getStaffPlayerMap();
    }
		
    
    //cancel movement if frozen
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player p = (Player) event.getPlayer();
		if (plugin.getStaffPlayerMap().containsValue(p)) {
		event.setCancelled(true);
		}
	}
	//send message if frozen player chats
	@EventHandler
	public void onChat(AsyncPlayerChatEvent chat) {
		Player p = (Player) chat.getPlayer();
		if (plugin.getStaffPlayerMap().containsValue(p)) {
			for (Entry<Player, Player> entry: plugin.getStaffPlayerMap().entrySet()) {
				if (entry.getValue().equals(p)) {
					entry.getKey().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("player") + chat.getMessage()));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("player") + chat.getMessage()));
					chat.setCancelled(true);
				}
			}
				
		}
	}
				
	//send message if staff chats
	@EventHandler
	public void onChat2(AsyncPlayerChatEvent chat) {
		Player p = (Player) chat.getPlayer();
		if (plugin.getStaffPlayerMap().containsKey(p)) {
					plugin.getStaffPlayerMap().get(p).sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("staff") + chat.getMessage()));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("staff") + chat.getMessage()));
					chat.setCancelled(true);
				}
			}
	//if frozen player leaves
	@EventHandler
	public void onLeave(PlayerQuitEvent leave) {
		Player p = (Player) leave.getPlayer();
		if (plugin.getStaffPlayerMap().containsValue(p)) {
			for (Entry<Player, Player> entry: plugin.getStaffPlayerMap().entrySet()) {
				if (entry.getValue().equals(p)) {
					p.removePotionEffect(PotionEffectType.BLINDNESS);
					p.removePotionEffect(PotionEffectType.GLOWING);
					p.setInvulnerable(false);
					TextComponent message = new TextComponent(ChatColor.DARK_RED + p.getName() + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("quitban")));
					message.setColor(ChatColor.GOLD);
					message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ban " + p.getName()));
					entry.getKey().spigot().sendMessage(message);
					Bukkit.broadcastMessage(ChatColor.DARK_RED + p.getName() + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("quitbc")));
					for (Entry<Player, Player> entry2: plugin.getStaffPlayerMap().entrySet()) {
						if (entry2.getValue().equals(p)) {
					plugin.getStaffPlayerMap().remove(entry2.getKey());
					return;
						}
					}
				}
			}
		}
	}
	//if frozen player executes command
	@EventHandler
	public void onCommandExecuted(PlayerCommandPreprocessEvent command) {
		Player p = (Player) command.getPlayer();
		if (plugin.getStaffPlayerMap().containsValue(p)) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("noPerm")));
			command.setCancelled(true);
		}
	}
	//if message is not staff or frozen player
	@EventHandler
	public void onMessage(AsyncPlayerChatEvent message) {
		if (Collections.disjoint(message.getRecipients(), plugin.getStaffPlayerMap().entrySet())) {
			message.getRecipients().removeAll(plugin.getStaffPlayerMap().values());
			message.getRecipients().removeAll(plugin.getStaffPlayerMap().keySet());
			return;
		}	
	}
	//if player is damaging
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent damage) {
		if (damage.getDamager() instanceof Player) {
			Player p = (Player) damage.getDamager();
			if (plugin.getStaffPlayerMap().containsValue(p)) {
				damage.setCancelled(true);
				return;
			}
		}
	}
	//if player is damaged
	@EventHandler
	public void onDamaged(EntityDamageEvent damaged) {
		if (damaged.getEntity() instanceof Player) {
			Player p = (Player) damaged.getEntity();
			if (plugin.getStaffPlayerMap().containsValue(p)) {
				damaged.setCancelled(true);
				return;
	}
}	
	}
	}