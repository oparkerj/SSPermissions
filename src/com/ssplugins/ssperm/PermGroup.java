package com.ssplugins.ssperm;

import com.ssplugins.ssperm.events.GroupModifyPermissionEvent;
import com.ssplugins.ssperm.events.GroupOptionsUpdatedEvent;
import com.ssplugins.ssperm.perm.Group;
import com.ssplugins.ssperm.perm.Permissions;
import com.ssplugins.ssperm.perm.SSPlayer;
import com.ssplugins.ssperm.perm.Settings;
import com.ssplugins.ssperm.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
		super.setOptionCallback(this::refreshFormats);
	}
	
	private void updatePermission(String perm, boolean add) {
		if (isDefault()) {
			Bukkit.getOnlinePlayers().forEach(o -> {
				if (manager.getPlayerManager().getPlayer(o).getGroup().isDefault()) {
					manager.getAttMan().playerUpdate(o.getUniqueId().toString(), perm, add);
				}
			});
			return;
		}
		getPlayers().forEach(s -> {
			manager.getAttMan().playerUpdate(s, perm, add);
		});
		Events.callEvent(new GroupModifyPermissionEvent(this, perm, add));
	}
	
	private void refreshFormats(String option) {
		getPlayers().forEach(s -> {
			Optional<SSPlayer> optional = Manager.get().getPlayerManager().getPlayerById(s);
			if (optional.isPresent()) {
				optional.get().refreshChatFormat();
			}
		});
		Events.callEvent(new GroupOptionsUpdatedEvent(this, option));
	}
	
	void removeSilent(Player player) {
		Util.removeFromList(Manager.getGroups(), name + ".players", player.getUniqueId().toString());
	}
	
	void removeSection() {
		Manager.getGroups().removeSection(name);
	}
	
	void unload() {
		loaded = false;
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
	public List<String> getPlayers() {
		if (!loaded) return null;
		if (isDefault()) return Bukkit.getOnlinePlayers().stream().filter(player -> Manager.get().getPlayerManager().getPlayer(player).getGroup().isDefault()).map(player -> player.getUniqueId().toString()).collect(Collectors.toList());
		return Manager.getGroups().getConfig().getStringList(name + ".players");
	}

	@Override
	public boolean addPlayer(Player player) {
		if (!loaded) return false;
		if (hasPlayer(player)) return false;
		manager.getGroupMan().resetPlayer(player);
		if (!isDefault()) Util.addToList(Manager.getGroups(), name + ".players", player.getUniqueId().toString());
		manager.getAttMan().playerSet(player, this);
		manager.getPlayerManager().getPlayer(player).refreshChatFormat();
		return true;
	}

	@Override
	public boolean removePlayer(Player player) {
		if (!loaded) return false;
		if (!hasPlayer(player)) return false;
		Util.removeFromList(Manager.getGroups(), name + ".players", player.getUniqueId().toString());
		manager.getAttMan().playerSet(player, manager.getGroupManager().getDefaultGroup());
		manager.getPlayerManager().getPlayer(player).refreshChatFormat();
		return true;
	}

	@Override
	public boolean hasPlayer(Player player) {
		if (!loaded) return false;
		return getPlayers().contains(player.getUniqueId().toString());
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
		return Manager.getGroups().getConfig().getStringList(name + ".inherits").stream().map(s -> {
			if (s.equalsIgnoreCase(Util.getNone())) return null;
			Optional<Group> optional = manager.getGroupManager().getGroup(s);
			if (optional.isPresent()) return optional.get();
			else return null;
		}).filter(group -> group != null).collect(Collectors.toList());
	}
	
	@Override
	public List<Group> findParentGroups() {
		if (!loaded) return null;
		return manager.getGroupManager().getGroups().stream().filter(group -> group.getInheritedGroups().contains(this)).collect(Collectors.toList());
	}
	
	@Override
	public boolean inherit(Group group) {
		if (!loaded) return false;
		if (Manager.getGroups().getConfig().getStringList(name + ".inherits").stream().anyMatch(s -> group.getName().equalsIgnoreCase(s))) return false;
		if (group.getName().equalsIgnoreCase(name)) return false;
		Util.addToList(Manager.getGroups(), name + ".inherits", group.getName());
		getPlayers().forEach(s -> manager.getAttMan().playerSet(s, this));
		return true;
	}
	
	@Override
	public boolean unInherit(String name) {
		if (!loaded) return false;
		boolean f = Util.removeFromList(Manager.getGroups(), this.name + ".inherits", name);
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
