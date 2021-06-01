package me.tear.freeze;

import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin{
	
	HashMap<Player, Player> staffPlayer = new HashMap<Player, Player>();
	
	public HashMap<Player, Player> getStaffPlayerMap() {
		return staffPlayer;
	}
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		this.getCommand("Freeze").setExecutor(new Freeze(this));
		this.getCommand("Unfreeze").setExecutor(new Unfreeze(this));
		this.getCommand("Freeze-reload").setExecutor(new Reload(this));
		getServer().getPluginManager().registerEvents(new EventListener(this), this);

	}
	
	@Override
	public void onDisable() {
		
	}
	
	
}

