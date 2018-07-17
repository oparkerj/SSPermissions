package com.ssplugins.ssp.perm;

import com.ssplugins.ssp.util.Unloadable;
import org.bukkit.entity.Player;

public interface SSPlayer extends PlayerData, Unloadable<SSPlayer> {
	
	Player getPlayer();
	
	SSOfflinePlayer getOfflinePlayer();
	
}
