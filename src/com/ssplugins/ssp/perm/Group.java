package com.ssplugins.ssp.perm;

import com.ssplugins.ssp.util.Unloadable;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public interface Group extends Unloadable<Group> {

	String getName();

	Permissions getPermissions();
	
	Set<String> getAllPermissions();

	Settings getSettings();
	
	String getMessageFormat();
	
	List<Group> getInheritedGroups();
	
	List<Group> findParentGroups();
	
	boolean inherit(Group group);
	
	boolean unInherit(String name);

	List<String> getPlayers();

	boolean addPlayer(Player player);
	
	boolean addPlayer(OfflinePlayer player);
	
	boolean addPlayer(PlayerData data);

	boolean removePlayer(Player player);
	
	boolean removePlayer(OfflinePlayer player);

	boolean hasPlayer(Player player);
	
	boolean hasPlayer(OfflinePlayer player);
	
	boolean isDefault();

}
