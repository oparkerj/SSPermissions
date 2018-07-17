package com.ssplugins.ssp;

import com.ssplugins.ssp.events.PlayerModifyPermissionEvent;
import com.ssplugins.ssp.events.PlayerOptionsUpdatedEvent;
import com.ssplugins.ssp.perm.*;
import com.ssplugins.ssp.util.Option;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

class PermPlayer extends PermissionHolder implements SSPlayer {
	
	private Manager manager;
	
	private Player player;
	private boolean loaded = true;
	
	PermPlayer(Player player) {
		super(false, player.getUniqueId().toString());
		this.player = player;
		manager = Manager.get();
		super.setOptionCallback(this::optionUpdated);
		super.setPermissionCallback(this::permissionUpdated);
	}
	
	private void optionUpdated(Option option, String oldValue, String newValue) {
		Events.callEvent(new PlayerOptionsUpdatedEvent(this, option, oldValue, newValue));
	}
	
	private void permissionUpdated(String perm, boolean add) {
		Events.callEvent(new PlayerModifyPermissionEvent(this, perm, add));
	}
	
	void unload() {
		loaded = false;
		super.unloadCallbacks();
	}
	
	@Override
	public Player getPlayer() {
		if (!loaded) return null;
		return player;
	}
	
	@Override
	public String id() {
		if (!loaded) return null;
		return player.getUniqueId().toString();
	}
	
	@Override
	public SSOfflinePlayer getOfflinePlayer() {
		return manager.getPlayerMan().getOfflinePlayer(player);
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
	public Group getGroup() {
		if (!loaded) return null;
		Optional<Group> optional = manager.getGroupManager().getGroups().stream().filter(group -> group.hasPlayer(player)).findFirst();
		return optional.orElseGet(() -> manager.getGroupManager().getDefaultGroup());
	}
	
	@Override
	public Set<String> getAllPermissions() {
		if (!loaded) return null;
		Set<String> perms = getPermissions().getAll();
		Group group = getGroup();
		perms.addAll(group.getAllPermissions());
		return perms;
	}
	
	@Override
	public void leaveGroup() {
		if (!loaded) return;
		manager.getGroupManager().getGroups().stream().filter(group -> group.hasPlayer(player)).forEach(group -> group.removePlayer(player));
		manager.getAttMan().playerSet(player, manager.getGroupManager().getDefaultGroup());
	}
	
	@Override
	public String getChatFormat() {
		if (!loaded) return null;
		return getSettings().getMessageFormat();
	}
	
	@Override
	public boolean isLoaded() {
		return loaded;
	}
	
	@Override
	public Optional<SSPlayer> refresh() {
		if (!player.isOnline()) return Optional.empty();
		return Optional.of(Manager.get().getPlayerManager().getPlayer(player));
	}
}
