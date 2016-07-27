package com.ssplugins.ssperm;

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
	
	private void updateFormat() {
		format = manager.getPlayerMan().getChatFormat(this);
	}
	
	private void updatePermission(String perm, boolean add) {
		manager.getAttMan().playerUpdate(player.getUniqueId().toString(), perm, add);
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public String id() {
		return player.getUniqueId().toString();
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
	public Group getGroup() {
		Optional<Group> optional = manager.getGroupManager().getGroups().stream().filter(group -> group.hasPlayer(player)).findFirst();
		if (optional.isPresent()) return optional.get();
		return manager.getGroupManager().getDefaultGroup();
	}
	
	@Override
	public Set<String> getAllPermissions() {
		Set<String> perms = getPermissions().getAll();
		Group group = getGroup();
		perms.addAll(group.getAllPermissions());
		return perms;
	}
	
	@Override
	public void resetGroups() {
		manager.getGroupManager().getGroups().stream().filter(group -> group.hasPlayer(player)).peek(group -> group.removePlayer(player));
		manager.getAttMan().playerSet(player, manager.getGroupManager().getDefaultGroup());
	}
	
	@Override
	public String getChatFormat() {
		return format;
	}
	
	@Override
	public void refreshChatFormat() {
		updateFormat();
	}
}
