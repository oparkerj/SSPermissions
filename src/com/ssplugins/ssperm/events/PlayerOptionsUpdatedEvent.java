package com.ssplugins.ssperm.events;

import com.ssplugins.ssperm.perm.SSPlayer;
import com.ssplugins.ssperm.util.SSEvent;
import org.bukkit.entity.Player;

public class PlayerOptionsUpdatedEvent extends SSEvent {
	
	private SSPlayer player;
	
	public PlayerOptionsUpdatedEvent(SSPlayer player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player.getPlayer();
	}
	
	public SSPlayer getSSPlayer() {
		return player;
	}
}
