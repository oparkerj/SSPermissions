package com.ssplugins.ssperm.perm;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

public interface PlayerManager {
	
	SSPlayer getPlayer(Player player);
	
	SSOfflinePlayer getOfflinePlayer(OfflinePlayer player);
	
	Optional<SSPlayer> getPlayerById(String id);
	
	Optional<SSPlayer> getPlayerByName(String name);
	
	Optional<SSOfflinePlayer> getOfflinePlayerByName(String name);
	
	Permissions getPlayerPermissions(Player player);

	Set<String> getAllPermissions(Player player);

}
