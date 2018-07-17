package com.ssplugins.ssp.perm;

import org.bukkit.OfflinePlayer;

import java.util.Optional;

public interface SSOfflinePlayer extends PlayerData {
	
	OfflinePlayer getPlayer();
	
	Optional<SSPlayer> getSSPlayer();
	
}
