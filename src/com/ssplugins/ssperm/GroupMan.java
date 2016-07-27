package com.ssplugins.ssperm;

import com.ssplugins.ssperm.events.GroupCreateEvent;
import com.ssplugins.ssperm.events.GroupRemoveEvent;
import com.ssplugins.ssperm.perm.Group;
import com.ssplugins.ssperm.perm.GroupManager;
import com.ssplugins.ssperm.perm.SSPlayer;
import com.ssplugins.ssperm.util.Util;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class GroupMan implements GroupManager {
	
	private PermGroup defGroup;
	private List<PermGroup> groups = new ArrayList<>();
	
	GroupMan() {}
	
	void loadGroups() {
		defGroup = new PermGroup("default");
		Manager.getGroups().getConfig().getConfigurationSection("").getKeys(false).forEach(this::loadGroup);
	}
	
	private void loadGroup(String name) {
		if (name.equalsIgnoreCase("default")) return;
		groups.add(new PermGroup(name));
	}
	
	void resetPlayer(Player player) {
		Manager.get().getPlayerManager().getPlayer(player).getGroup().removePlayer(player);
	}
	
	@Override
	public Group createGroup(String name) {
		if (name.equalsIgnoreCase(Util.getNone()) || name.equalsIgnoreCase("default")) return null;
		if (groupExists(name)) {
			Optional<Group> optional = getGroup(name);
			if (optional.isPresent()) return optional.get();
		}
		PermGroup group = new PermGroup(name);
		groups.add(group);
		Events.callEvent(new GroupCreateEvent(group));
		return group;
	}

	@Override
	public boolean removeGroup(String name) {
		Events.callEvent(new GroupRemoveEvent(name));
		Manager.getGroups().removeSection(name);
		final boolean[] y = {false};
		groups.stream().filter(permGroup -> permGroup.getName().equalsIgnoreCase(name)).peek(permGroup -> {
			permGroup.getPlayers().forEach(s -> {
				Optional<SSPlayer> optional = Manager.get().getPlayerMan().getPlayerById(s);
				if (optional.isPresent()) defGroup.addPlayer(optional.get().getPlayer());
			});
			permGroup.prepareToRemove();
			if (groups.remove(permGroup)) y[0] = true;
		});
		return y[0];
	}

	@Override
	public boolean groupExists(String name) {
		return groups.stream().anyMatch(permGroup -> permGroup.getName().equalsIgnoreCase(name));
	}

	@Override
	public Optional<Group> getGroup(String name) {
		if (name.equalsIgnoreCase("default")) return Optional.of(defGroup);
		Optional<PermGroup> optional = groups.stream().filter(permGroup -> permGroup.getName().equalsIgnoreCase(name)).findFirst();
		if (optional.isPresent()) return Optional.of(optional.get());
		else return Optional.empty();
	}
	
	@Override
	public List<Group> getGroups() {
		return groups.stream().collect(Collectors.toList());
	}
	
	@Override
	public Group getDefaultGroup() {
		return defGroup;
	}
}
