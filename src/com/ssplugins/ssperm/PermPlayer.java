package com.ssplugins.ssperm;

import com.ssplugins.ssperm.events.PlayerModifyPermissionEvent;
import com.ssplugins.ssperm.events.PlayerOptionsUpdatedEvent;
import com.ssplugins.ssperm.perm.Group;
import com.ssplugins.ssperm.perm.Permissions;
import com.ssplugins.ssperm.perm.SSPlayer;
import com.ssplugins.ssperm.perm.Settings;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

class PermPlayer extends PermissionHolder implements SSPlayer {
	
	private Manager manager;
	private String format;
	
	private Player player;
	private boolean loaded = true;
	
	PermPlayer(Player player) {
		super(false, player.getUniqueId().toString());
		this.player = player;
		manager = Manager.get();
		super.setOptionCallback(this::updateFormat);
		super.setPermissionCallback(this::updatePermission);
	}
	
	void setFormat(String format) {
		this.format = format;
	}
	
	private void updateFormat(String option) {
		format = manager.getPlayerMan().getChatFormat(this);
		Events.callEvent(new PlayerOptionsUpdatedEvent(this, option));
	}
	
	private void updatePermission(String perm, boolean add) {
		manager.getAttMan().playerUpdate(player.getUniqueId().toString(), perm, add);
		Events.callEvent(new PlayerModifyPermissionEvent(this, perm, add));
	}
	
	void unload() {
		loaded = false;
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
		if (optional.isPresent()) return optional.get();
		return manager.getGroupManager().getDefaultGroup();
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
	public void resetGroups() {
		if (!loaded) return;
		manager.getGroupManager().getGroups().stream().filter(group -> group.hasPlayer(player)).forEach(group -> group.removePlayer(player));
		manager.getAttMan().playerSet(player, manager.getGroupManager().getDefaultGroup());
	}
	
	@Override
	public String getChatFormat() {
		if (!loaded) return null;
		return format;
	}
	
	@Override
	public void refreshChatFormat() {
		if (!loaded) return;
		updateFormat("refreshChatFormat");
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
