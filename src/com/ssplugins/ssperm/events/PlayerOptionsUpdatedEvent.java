package com.ssplugins.ssperm.events;

import com.ssplugins.ssperm.perm.SSPlayer;
import com.ssplugins.ssperm.util.SSEvent;
import org.bukkit.entity.Player;

public class PlayerOptionsUpdatedEvent extends SSEvent {
	
	private SSPlayer player;
	private String option;
	
	public PlayerOptionsUpdatedEvent(SSPlayer player, String option) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player.getPlayer();
	}
	
	public SSPlayer getSSPlayer() {
		return player;
	}
	
	public String getOption() {
		return option;
	}
}
