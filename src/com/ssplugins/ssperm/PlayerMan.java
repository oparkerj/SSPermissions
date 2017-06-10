package com.ssplugins.ssperm;

import com.ssplugins.ssperm.perm.*;
import com.ssplugins.ssperm.util.Option;
import com.ssplugins.ssperm.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

class PlayerMan implements PlayerManager {
	
	private Map<String, PermPlayer> players = new HashMap<>();
	
	PlayerMan() {}
	
	void remove(Player player) {
		PermPlayer p = players.remove(player.getUniqueId().toString());
		if (p == null) return;
		p.unload();
	}
	
	void unloadPlayers() {
		players.forEach((s, permPlayer) -> permPlayer.unload());
		players.clear();
	}
	
	String getChatFormat(Player player) {
		return getChatFormat(getPlayer(player));
	}
	
	String getChatFormat(PlayerData data) {
		String base = Manager.getOptions().getConfig().getString("chatFormat");
		String playerFormat = data.getChatFormat();
		String groupFormat = data.getGroup().getMessageFormat();
		String format = Settings.pickFormat(base, playerFormat, groupFormat);
		format = Util.checkFormat(Util.color(format));
		Settings playerSettings = data.getSettings();
		Settings groupSettings = data.getGroup().getSettings();
		format = format.replace("<prefix>", Settings.pick(Option.PREFIX, playerSettings.getPrefix(), groupSettings.getPrefix()));
		format = format.replace("<player>", Settings.pick(Option.NAME_COLOR, playerSettings.getNameColor(), groupSettings.getNameColor()) + Settings.pick(Option.NAME_FORMAT, playerSettings.getNameFormat(), groupSettings.getNameFormat()) + "%1$s");
		format = format.replace("<suffix>", Settings.pick(Option.SUFFIX, playerSettings.getSuffix(), groupSettings.getSuffix()));
		format = format.replace("<group>", data.getGroup().getName());
		format = format.replace("<msg>", Settings.pick(Option.CHAT_COLOR, playerSettings.getChatColor(), groupSettings.getChatColor()) + Settings.pick(Option.CHAT_FORMAT, playerSettings.getChatFormat(), groupSettings.getChatFormat()) + "%2$s");
		return format;
	}
	
	@Override
	public SSPlayer getPlayer(Player player) {
		return players.computeIfAbsent(player.getUniqueId().toString(), s -> new PermPlayer(player));
	}
	
	@Override
	public SSOfflinePlayer getOfflinePlayer(OfflinePlayer player) {
		return new OffPlayer(player);
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
	public Optional<SSOfflinePlayer> getOfflinePlayerByName(String name) {
		String id = Util.getID(Manager.getCatalog(), name);
		if (id == null) return Optional.empty();
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(id));
		return Optional.of(getOfflinePlayer(offlinePlayer));
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
