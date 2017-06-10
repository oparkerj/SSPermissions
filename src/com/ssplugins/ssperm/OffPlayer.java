package com.ssplugins.ssperm;

import com.ssplugins.ssperm.events.PlayerOfflineOptionEvent;
import com.ssplugins.ssperm.events.PlayerOfflinePermissionEvent;
import com.ssplugins.ssperm.perm.*;
import com.ssplugins.ssperm.util.Option;
import com.ssplugins.ssperm.util.Util;
import org.bukkit.OfflinePlayer;

import java.util.Optional;
import java.util.Set;

class OffPlayer implements SSOfflinePlayer {
	
	private Manager manager;
	
	private OfflinePlayer player;
	
	OffPlayer(OfflinePlayer player) {
		this.player = player;
		manager = Manager.get();
		Util.loadOptions(null, Manager.getPlayer(id()), id());
	}
	
	private void optionUpdated(Option option, String oldValue, String newValue) {
		Events.callEvent(new PlayerOfflineOptionEvent(this, option, oldValue, newValue));
	}
	
	private void permissionUpdated(String perm, boolean add) {
		Events.callEvent(new PlayerOfflinePermissionEvent(this, perm, add));
	}
	
	@Override
	public OfflinePlayer getPlayer() {
		return player;
	}
	
	@Override
	public String id() {
		return player.getUniqueId().toString();
	}
	
	@Override
	public Optional<SSPlayer> getSSPlayer() {
		if (!player.isOnline()) return Optional.empty();
		return Optional.of(manager.getPlayerMan().getPlayer(player.getPlayer()));
	}
	
	@Override
	public Permissions getPermissions() {
		if (player.isOnline()) return manager.getPlayerMan().getPlayer(player.getPlayer()).getPermissions();
		else return Permissions.temp(id(), Manager.getPlayer(id()), this::permissionUpdated);
	}
	
	@Override
	public Settings getSettings() {
		if (player.isOnline()) return manager.getPlayerMan().getPlayer(player.getPlayer()).getSettings();
		else return Settings.temp(id(), Manager.getPlayer(id()), this::optionUpdated);
	}
	
	@Override
	public Group getGroup() {
		Optional<Group> optional = manager.getGroupManager().getGroups().stream().filter(group -> group.hasPlayer(player)).findFirst();
		return optional.orElseGet(() -> manager.getGroupManager().getDefaultGroup());
	}
	
	@Override
	public Set<String> getAllPermissions() {
		Set<String> perms = getPermissions().getAll();
		Group group = getGroup();
		perms.addAll(group.getAllPermissions());
		return perms;
	}
	
	@Override
	public void leaveGroup() {
		manager.getGroupManager().getGroups().stream().filter(group -> group.hasPlayer(player)).forEach(group -> group.removePlayer(player));
		manager.getAttMan().playerSet(player, manager.getGroupManager().getDefaultGroup());
	}
	
	@Override
	public String getChatFormat() {
		return getSettings().getMessageFormat();
	}
}
