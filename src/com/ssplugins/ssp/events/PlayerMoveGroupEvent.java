package com.ssplugins.ssp.events;

import com.ssplugins.ssp.perm.Group;
import com.ssplugins.ssp.perm.SSOfflinePlayer;
import com.ssplugins.ssp.perm.SSPlayer;
import com.ssplugins.ssp.util.SSEvent;

public class PlayerMoveGroupEvent extends SSEvent {
	
	private SSPlayer player = null;
	private SSOfflinePlayer offlinePlayer = null;
	private Group oldGroup;
	private Group newGroup;
	
	public PlayerMoveGroupEvent(SSPlayer player, Group oldGroup, Group newGroup) {
		this.player = player;
		this.oldGroup = oldGroup;
		this.newGroup = newGroup;
	}
	
	public PlayerMoveGroupEvent(SSOfflinePlayer offlinePlayer, Group oldGroup, Group newGroup) {
		this.offlinePlayer = offlinePlayer;
		this.oldGroup = oldGroup;
		this.newGroup = newGroup;
	}
	
	public boolean isOfflinePlayer() {
		return offlinePlayer != null;
	}
	
	public SSPlayer getPlayer() {
		return player;
	}
	
	public SSOfflinePlayer getOfflinePlayer() {
		return offlinePlayer;
	}
	
	public Group getOldGroup() {
		return oldGroup;
	}
	
	public Group getNewGroup() {
		return newGroup;
	}
}
