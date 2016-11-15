package com.ssplugins.ssperm;

import com.ssplugins.ssperm.perm.Permissions;
import com.ssplugins.ssperm.perm.PlayerManager;
import com.ssplugins.ssperm.perm.SSPlayer;
import com.ssplugins.ssperm.perm.Settings;
import com.ssplugins.ssperm.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

class PlayerMan implements PlayerManager {
	
	private List<PermPlayer> players = new ArrayList<>();
	
	PlayerMan() {}
	
	void remove(Player player) {
		players.removeIf(permPlayer -> permPlayer.id().equalsIgnoreCase(player.getUniqueId().toString()));
	}
	
	void unloadPlayers() {
		players.forEach(PermPlayer::unload);
		players.clear();
	}
	
	void reloadFormats() {
		players.forEach(PermPlayer::refreshChatFormat);
	}
	
	String getChatFormat(Player player) {
		String base = Manager.getOptions().getConfig().getString("chatFormat");
		base = Util.checkFormat(Util.color(base));
		SSPlayer p = getPlayer(player);
		Settings playerSettings = p.getSettings();
		Settings groupSettings = p.getGroup().getSettings();
		base = base.replace("<prefix>", Settings.pick("prefix", playerSettings.getPrefix(), groupSettings.getPrefix()));
		base = base.replace("<player>", Settings.pick("nameColor", playerSettings.getNameColor(), groupSettings.getNameColor()) + Settings.pick("nameFormat", playerSettings.getNameFormat(), groupSettings.getNameFormat()) + "%1$s");
		base = base.replace("<suffix>", Settings.pick("suffix", playerSettings.getSuffix(), groupSettings.getSuffix()));
		base = base.replace("<group>", p.getGroup().getName());
		base = base.replace("<msg>", Settings.pick("chatColor", playerSettings.getChatColor(), groupSettings.getChatColor()) + Settings.pick("chatFormat", playerSettings.getChatFormat(), groupSettings.getChatFormat()) + "%2$s");
		return base;
	}
	
	String getChatFormat(SSPlayer player) {
		String base = Manager.getOptions().getConfig().getString("chatFormat");
		base = Util.checkFormat(Util.color(base));
		Settings playerSettings = player.getSettings();
		Settings groupSettings = player.getGroup().getSettings();
		base = base.replace("<prefix>", Settings.pick("prefix", playerSettings.getPrefix(), groupSettings.getPrefix()));
		base = base.replace("<player>", Settings.pick("nameColor", playerSettings.getNameColor(), groupSettings.getNameColor()) + Settings.pick("nameFormat", playerSettings.getNameFormat(), groupSettings.getNameFormat()) + "%1$s");
		base = base.replace("<suffix>", Settings.pick("suffix", playerSettings.getSuffix(), groupSettings.getSuffix()));
		base = base.replace("<group>", player.getGroup().getName());
		base = base.replace("<msg>", Settings.pick("chatColor", playerSettings.getChatColor(), groupSettings.getChatColor()) + Settings.pick("chatFormat", playerSettings.getChatFormat(), groupSettings.getChatFormat()) + "%2$s");
		return base;
	}
	
	@Override
	public SSPlayer getPlayer(Player player) {
		if (players.stream().anyMatch(permPlayer -> permPlayer.id().equalsIgnoreCase(player.getUniqueId().toString()))) {
			Optional<PermPlayer> optional = players.stream().filter(permPlayer -> permPlayer.id().equalsIgnoreCase(player.getUniqueId().toString())).findFirst();
			if (optional.isPresent()) return optional.get();
		}
		PermPlayer permPlayer = new PermPlayer(player);
		permPlayer.setFormat(getChatFormat(permPlayer));
		players.add(permPlayer);
		return permPlayer;
	}
	
	@Override
	public Optional<SSPlayer> getPlayerById(String id) {
		Player player = Bukkit.getPlayer(UUID.fromString(id));
		if (player == null) return Optional.empty();
		return Optional.of(getPlayer(player));
	}
	
	@Override
	public Optional<SSPlayer> getPlayerByName(String name) {
		Player player = Bukkit.getPlayer(name);
		if (player == null) return Optional.empty();
		return Optional.of(getPlayer(player));
	}
	
	@Override
	public Permissions getPlayerPermissions(Player player) {
		return getPlayer(player).getPermissions();
	}
	
	@Override
	public Set<String> getAllPermissions(Player player) {
		return getPlayer(player).getAllPermissions();
	}
}
