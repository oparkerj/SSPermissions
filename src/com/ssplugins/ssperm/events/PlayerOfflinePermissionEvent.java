package com.ssplugins.ssperm.events;

import com.ssplugins.ssperm.perm.SSOfflinePlayer;
import com.ssplugins.ssperm.util.SSEvent;
import org.bukkit.OfflinePlayer;

public class PlayerOfflinePermissionEvent extends SSEvent {
	
	private SSOfflinePlayer player;
	private String permission;
	private boolean add;
	
	public PlayerOfflinePermissionEvent(SSOfflinePlayer player, String permission, boolean add) {
		this.player = player;
		this.permission = permission;
		this.add = add;
	}
	
	public OfflinePlayer getPlayer() {
		return player.getPlayer();
	}
	
	public SSOfflinePlayer getSSPlayer() {
		return player;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public boolean isAdd() {
		return add;
	}
}
