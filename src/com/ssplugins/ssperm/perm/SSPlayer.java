package com.ssplugins.ssperm.perm;

import com.ssplugins.ssperm.util.Unloadable;
import org.bukkit.entity.Player;

public interface SSPlayer extends PlayerData, Unloadable<SSPlayer> {
	
	Player getPlayer();
	
	SSOfflinePlayer getOfflinePlayer();
	
}
