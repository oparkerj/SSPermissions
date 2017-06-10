package com.ssplugins.ssperm;

import com.ssplugins.ssperm.events.GroupModifyPermissionEvent;
import com.ssplugins.ssperm.events.GroupOptionsUpdatedEvent;
import com.ssplugins.ssperm.events.PlayerMoveGroupEvent;
import com.ssplugins.ssperm.perm.*;
import com.ssplugins.ssperm.util.Option;
import com.ssplugins.ssperm.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

class PermGroup extends PermissionHolder implements Group {
	
	private Manager manager;

	private String name;
	private boolean loaded = true;
	
	PermGroup(String name) {
		super(true, name);
		this.name = name;
		manager = Manager.get();
		super.setPermissionCallback(this::updatePermission);
		super.setOptionCallback(this::optionUpdated);
	}
	
	private void updatePermission(String perm, boolean add) {
		if (isDefault()) {
			Bukkit.getOnlinePlayers().forEach(o -> {
				if (manager.getPlayerManager().getPlayer(o).getGroup().isDefault()) {
					manager.getAttMan().playerUpdate(o.getUniqueId().toString(), perm, add);
				}
			});
		}
		else {
			getPlayers().forEach(s -> manager.getAttMan().playerUpdate(s, perm, add));
		}
		Events.callEvent(new GroupModifyPermissionEvent(this, perm, add));
	}
	
	private void optionUpdated(Option option, String oldValue, String newValue) {
		Events.callEvent(new GroupOptionsUpdatedEvent(this, option, oldValue, newValue));
	}
	
	void removeSilent(Player player) {
		Util.removeFromList(Manager.getGroups(), Option.getPlayersPath(name), player.getUniqueId().toString());
	}
	
	void removeSection() {
		Manager.getGroups().removeSection(name);
	}
	
	void unload() {
		loaded = false;
		super.unloadCallbacks();
	}

	@Override
	public String getName() {
		if (!loaded) return null;
		return Util.color(name);
	}

	@Override
	public Permissions getPermissions() {
		if (!loaded) return null;
		return getPermList();
	}

	@Override
	public Settings getSettings() {
		if (!loaded) return null;
		return getOptions();
	}
	
	@Override
	public String getMessageFormat() {
		return getSettings().getMessageFormat();
	}
	
	@Override
	public List<String> getPlayers() {
		if (!loaded) return null;
		if (isDefault()) return Bukkit.getOnlinePlayers().stream().filter(player -> Manager.get().getPlayerManager().getPlayer(player).getGroup().isDefault()).map(player -> player.getUniqueId().toString()).collect(Collectors.toList());
		return Manager.getGroups().getConfig().getStringList(Option.getPlayersPath(name));
	}

	@Override
	public boolean addPlayer(Player player) {
		if (!loaded) return false;
		if (hasPlayer(player)) return false;
		SSPlayer ssp = manager.getPlayerMan().getPlayer(player);
		Events.callEvent(new PlayerMoveGroupEvent(ssp, ssp.getGroup(), this));
		manager.getGroupMan().resetPlayer(player);
		if (!isDefault()) Util.addToList(Manager.getGroups(), Option.getPlayersPath(name), player.getUniqueId().toString());
		manager.getAttMan().playerSet(player, this);
		return true;
	}
	
	@Override
	public boolean addPlayer(OfflinePlayer player) {
		if (!loaded) return false;
		if (hasPlayer(player)) return false;
		SSOfflinePlayer ssp = manager.getPlayerMan().getOfflinePlayer(player);
		Events.callEvent(new PlayerMoveGroupEvent(ssp, ssp.getGroup(), this));
		manager.getGroupMan().resetPlayer(player);
		if (!isDefault()) Util.addToList(Manager.getGroups(), Option.getPlayersPath(name), player.getUniqueId().toString());
		return true;
	}
	
	@Override
	public boolean addPlayer(PlayerData data) {
		if (!loaded) return false;
		if (hasPlayer(data.id())) return false;
		OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(data.id()));
		if (!player.hasPlayedBefore()) return false;
		SSOfflinePlayer ssp = manager.getPlayerMan().getOfflinePlayer(player);
		Events.callEvent(new PlayerMoveGroupEvent(ssp, ssp.getGroup(), this));
		data.leaveGroup();
		if (!isDefault()) Util.addToList(Manager.getGroups(), Option.getPlayersPath(name), data.id());
		return true;
	}
	
	@Override
	public boolean removePlayer(Player player) {
		return removeFromGroup(player);
	}
	
	@Override
	public boolean removePlayer(OfflinePlayer player) {
		return removeFromGroup(player);
	}
	
	private boolean removeFromGroup(OfflinePlayer player) {
		if (!loaded) return false;
		if (!hasPlayer(player)) return false;
		Util.removeFromList(Manager.getGroups(), Option.getPlayersPath(name), player.getUniqueId().toString());
		manager.getAttMan().playerSet(player, manager.getGroupManager().getDefaultGroup());
		return true;
	}
	
	@Override
	public boolean hasPlayer(Player player) {
		return hasPlayer(player.getUniqueId().toString());
	}
	
	@Override
	public boolean hasPlayer(OfflinePlayer player) {
		return hasPlayer(player.getUniqueId().toString());
	}
	
	private boolean hasPlayer(String id) {
		if (!loaded) return false;
		return getPlayers().contains(id);
	}
	
	@Override
	public Set<String> getAllPermissions() {
		if (!loaded) return null;
		Set<String> perms = getPermissions().getAll();
		Util.getGroups(this).forEach(group -> perms.addAll(group.getPermissions().getAll()));
		return perms;
	}
	
	@Override
	public List<Group> getInheritedGroups() {
		if (!loaded) return null;
		return Manager.getGroups().getConfig().getStringList(Option.getInheritsPath(name)).stream().map(s -> {
			if (s.equalsIgnoreCase(Util.getNone())) return null;
			Optional<Group> optional = manager.getGroupManager().getGroup(s);
			return optional.orElse(null);
		}).filter(Objects::nonNull).collect(Collectors.toList());
	}
	
	@Override
	public List<Group> findParentGroups() {
		if (!loaded) return null;
		return manager.getGroupManager().getGroups().stream().filter(group -> group.getInheritedGroups().contains(this)).collect(Collectors.toList());
	}
	
	@Override
	public boolean inherit(Group group) {
		if (!loaded) return false;
		if (Manager.getGroups().getConfig().getStringList(Option.getInheritsPath(name)).stream().anyMatch(s -> group.getName().equalsIgnoreCase(s))) return false;
		if (group.getName().equalsIgnoreCase(name)) return false;
		Util.addToList(Manager.getGroups(), Option.getInheritsPath(name), group.getName());
		getPlayers().forEach(s -> manager.getAttMan().playerSet(s, this));
		return true;
	}
	
	@Override
	public boolean unInherit(String name) {
		if (!loaded) return false;
		boolean f = Util.removeFromList(Manager.getGroups(), Option.getInheritsPath(this.name), name);
		getPlayers().forEach(s -> manager.getAttMan().playerSet(s, this));
		return f;
	}
	
	@Override
	public boolean isDefault() {
		if (!loaded) return false;
		return name.equalsIgnoreCase("default");
	}
	
	@Override
	public boolean isLoaded() {
		return loaded;
	}
	
	@Override
	public Optional<Group> refresh() {
		return Manager.get().getGroupManager().getGroup(name);
	}
}
