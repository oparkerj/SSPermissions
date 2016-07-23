package com.ssplugins.ssperm;

import com.ssplugins.ssperm.perm.Group;
import com.ssplugins.ssperm.perm.Permissions;
import com.ssplugins.ssperm.perm.Settings;
import com.ssplugins.ssperm.util.Util;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

class PermGroup extends PermissionHolder implements Group {
	
	private Manager manager;

	private String name;
	
	PermGroup(String name) {
		super(true, name);
		this.name = name;
		manager = Manager.get();
	}
	
	void removeSilent(Player player) {
		Util.removeFromList(Manager.getGroups(), name + ".players", player.getUniqueId().toString());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Permissions getPermissions() {
		return getPermList();
	}

	@Override
	public Settings getSettings() {
		return getOptions();
	}

	@Override
	public List<String> getPlayers() {
		return Manager.getGroups().getConfig().getStringList(name + ".players");
	}

	@Override
	public boolean addPlayer(Player player) {
		if (hasPlayer(player)) return false;
		manager.getGroupMan().resetPlayer(player);
		Util.addToList(Manager.getGroups(), name + ".players", player.getUniqueId().toString());
		manager.getAttMan().playerSet(player, this);
		return true;
	}

	@Override
	public boolean removePlayer(Player player) {
		if (!hasPlayer(player)) return false;
		Util.removeFromList(Manager.getGroups(), name + ".players", player.getUniqueId().toString());
		manager.getAttMan().playerSet(player, manager.getGroupManager().getDefaultGroup());
		return true;
	}

	@Override
	public boolean hasPlayer(Player player) {
		return getPlayers().contains(player.getUniqueId().toString());
	}
	
	@Override
	public Set<String> getAllPermissions() {
		Set<String> perms = getPermissions().getAll();
		Util.getGroups(this).stream().peek(group -> perms.addAll(group.getPermissions().getAll()));
		return perms;
	}
	
	@Override
	public List<Group> getInheritedGroups() {
		return Manager.getGroups().getConfig().getStringList(name + ".inherits").stream().map(s -> {
			if (s.equalsIgnoreCase(Util.getNone())) return null;
			Optional<Group> optional = manager.getGroupManager().getGroup(s);
			if (optional.isPresent()) return optional.get();
			else return null;
		}).filter(group -> group != null).collect(Collectors.toList());
	}
	
	@Override
	public boolean inherit(Group group) {
		if (Manager.getGroups().getConfig().getStringList(name + ".inherits").stream().anyMatch(s -> group.getName().equalsIgnoreCase(s))) return false;
		if (group.getName().equalsIgnoreCase(name)) return false;
		Util.addToList(Manager.getGroups(), name + ".inherits", group.getName());
		return true;
	}
	
	@Override
	public void unInherit(String name) {
		Util.removeFromList(Manager.getGroups(), this.name + ".inherits", name);
	}
}
