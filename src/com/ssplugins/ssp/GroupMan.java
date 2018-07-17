package com.ssplugins.ssp;

import com.ssplugins.ssp.events.GroupCreateEvent;
import com.ssplugins.ssp.events.GroupRemoveEvent;
import com.ssplugins.ssp.perm.Group;
import com.ssplugins.ssp.perm.GroupManager;
import com.ssplugins.ssp.perm.SSPlayer;
import com.ssplugins.ssp.util.Util;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

class GroupMan implements GroupManager{
	
	private PermGroup defGroup;
	private List<PermGroup> groups = new ArrayList<>();
	
	GroupMan() {}
	
	void loadGroups() {
		defGroup = new PermGroup("default");
		Manager.getGroupsConfig().getConfig().getConfigurationSection("").getKeys(false).forEach(this::loadGroup);
	}
	
	private void loadGroup(String name) {
		if (name.equalsIgnoreCase("default")) return;
		groups.add(new PermGroup(name));
	}
	
	void resetPlayer(Player player) {
		Manager.get().getPlayerManager().getPlayer(player).getGroup().removePlayer(player);
	}
	
	void resetPlayer(OfflinePlayer player) {
		Manager.get().getPlayerManager().getOfflinePlayer(player).getGroup().removePlayer(player);
	}
	
	void unloadGroups() {
		defGroup.unload();
		groups.forEach(PermGroup::unload);
		groups.clear();
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
		//Manager.getGroups().removeSection(name);
		final boolean[] y = {false};
		Iterator<PermGroup> it = groups.stream().filter(permGroup -> permGroup.getName().equalsIgnoreCase(name)).iterator();
		it.forEachRemaining(permGroup -> {
			permGroup.getPlayers().forEach(s -> {
				Optional<SSPlayer> optional = Manager.get().getPlayerMan().getPlayerById(s);
				optional.ifPresent(ssPlayer -> defGroup.addPlayer(ssPlayer.getPlayer()));
			});
			if (groups.removeIf(permGroup1 -> permGroup1.getName().equalsIgnoreCase(name))) {
				Manager.getGroupsConfig().removeSection(permGroup.getName());
				y[0] = true;
			}
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
		if (optional.isPresent()) return optional.map(permGroup -> permGroup);
		else return Optional.empty();
	}
	
	@Override
	public List<Group> getGroups() {
		return new ArrayList<>(groups);
	}
	
	@Override
	public Group getDefaultGroup() {
		return defGroup;
	}
}
