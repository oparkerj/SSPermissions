package com.ssplugins.ssperm.perm;

import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

public interface PlayerManager {
	
	SSPlayer getPlayer(Player player);
	
	Optional<SSPlayer> getPlayerById(String id);
	
	Optional<SSPlayer> getPlayerByName(String name);
	
	Permissions getPlayerPermissions(Player player);

	Set<String> getAllPermissions(Player player);

}
