package com.ssplugins.ssperm;

import com.ssplugins.ssperm.perm.SSPlayer;
import com.ssplugins.ssperm.util.SSEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
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
	
	@EventHandler(ignoreCancelled = true)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		SSPlayer player = manager.getPlayerManager().getPlayer(event.getPlayer());
		String format = player.getChatFormat();
		event.setFormat(format);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		manager.getPlayerMan().remove(event.getPlayer());
		manager.getAttMan().remove(event.getPlayer().getUniqueId().toString());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		manager.getAttMan().setup(event.getPlayer());
	}
}
