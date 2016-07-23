package com.ssplugins.ssperm.perm;

import org.bukkit.entity.Player;

import java.util.Set;

public interface PlayerManager {
	
	SSPlayer getPlayer(Player player);
	
	Permissions getPlayerPermissions(Player player);

	Set<String> getAllPermissions(Player player); // Adding permissions to this should add it to the player, not the group.

}
