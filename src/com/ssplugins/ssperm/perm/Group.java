package com.ssplugins.ssperm.perm;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public interface Group {

	String getName();

	Permissions getPermissions();
	
	Set<String> getAllPermissions();

	Settings getSettings();
	
	List<Group> getInheritedGroups();
	
	boolean inherit(Group group);
	
	boolean unInherit(String name);

	List<String> getPlayers();

	boolean addPlayer(Player player);

	boolean removePlayer(Player player);

	boolean hasPlayer(Player player);

}
