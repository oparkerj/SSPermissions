package com.ssplugins.ssperm;

import com.ssplugins.ssperm.perm.SSPlayer;
import com.ssplugins.ssperm.util.Config;
import com.ssplugins.ssperm.util.SSEvent;
import com.ssplugins.ssperm.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

class Events implements Listener {
	
	private Manager manager;
	
	Events(Manager manager) {
		this.manager = manager;
	}
	
	static <E extends SSEvent> void callEvent(E event) {
		Bukkit.getPluginManager().callEvent(event);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		SSPlayer player = manager.getPlayerManager().getPlayer(event.getPlayer());
		String format = manager.getPlayerMan().getChatFormat(player);
		event.setFormat(format);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		manager.getPlayerMan().remove(event.getPlayer());
		manager.getAttMan().remove(event.getPlayer().getUniqueId().toString());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		markName(player);
		manager.getAttMan().setup(player);
		Config catalog = Manager.getCatalog();
		if (!catalog.contains(player.getUniqueId().toString()) || !catalog.getString(player.getUniqueId().toString()).equalsIgnoreCase(player.getName())) {
			catalog.set(player.getUniqueId().toString(), player.getName());
		}
	}
	
	private void markName(Player player) {
		Config config = Manager.getCatalog();
		if (config.contains(player.getUniqueId().toString())) {
			String v = config.getString(player.getUniqueId().toString());
			if (!v.equalsIgnoreCase(player.getName())) config.set(player.getUniqueId().toString(), player.getName());
		}
		else config.set(player.getUniqueId().toString(), player.getName());
	}
}
