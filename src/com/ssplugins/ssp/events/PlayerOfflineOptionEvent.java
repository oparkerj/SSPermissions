package com.ssplugins.ssp.events;

import com.ssplugins.ssp.perm.SSOfflinePlayer;
import com.ssplugins.ssp.util.Option;
import com.ssplugins.ssp.util.SSEvent;
import org.bukkit.OfflinePlayer;

public class PlayerOfflineOptionEvent extends SSEvent {
	
	private SSOfflinePlayer player;
	private Option option;
	private String oldValue;
	private String newValue;
	
	public PlayerOfflineOptionEvent(SSOfflinePlayer player, Option option, String oldValue, String newValue) {
		this.player = player;
		this.option = option;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	public OfflinePlayer getPlayer() {
		return player.getPlayer();
	}
	
	public SSOfflinePlayer getSSPlayer() {
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
