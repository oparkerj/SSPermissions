package com.ssplugins.ssp.events;

import com.ssplugins.ssp.perm.SSPlayer;
import com.ssplugins.ssp.util.Option;
import com.ssplugins.ssp.util.SSEvent;
import org.bukkit.entity.Player;

public class PlayerOptionsUpdatedEvent extends SSEvent {
	
	private SSPlayer player;
	private Option option;
	private String oldValue;
	private String newValue;
	
	public PlayerOptionsUpdatedEvent(SSPlayer player, Option option, String oldValue, String newValue) {
		this.player = player;
		this.option = option;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	public Player getPlayer() {
		return player.getPlayer();
	}
	
	public SSPlayer getSSPlayer() {
		return player;
	}
	
	public Option getOption() {
		return option;
	}
	
	public String getOldValue() {
		return oldValue;
	}
	
	public String getNewValue() {
		return newValue;
	}
}
