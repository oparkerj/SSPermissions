package com.ssplugins.ssperm;

import com.ssplugins.ssperm.perm.Permissions;
import com.ssplugins.ssperm.perm.PlayerManager;
import com.ssplugins.ssperm.perm.SSPlayer;
import com.ssplugins.ssperm.perm.Settings;
import com.ssplugins.ssperm.util.Util;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

class PlayerMan implements PlayerManager {
	
	private List<PermPlayer> players = new ArrayList<>();
	
	PlayerMan() {}
	
	void remove(Player player) {
		players.removeIf(permPlayer -> permPlayer.id().equalsIgnoreCase(player.getUniqueId().toString()));
	}
	
	String getChatFormat(Player player) {
		String base = Manager.getOptions().getConfig().getString("chatFormat");
		SSPlayer p = getPlayer(player);
		Settings playerSettings = p.getSettings();
		Settings groupSettings = p.getGroup().getSettings();
		base = Util.replace(base, "<prefix>", Settings.pick("prefix", playerSettings.getPrefix(), groupSettings.getPrefix()));
		base = Util.replace(base, "<player>", Settings.pick("nameColor", playerSettings.getNameColor(), groupSettings.getNameColor()) + Settings.pick("nameFormat", playerSettings.getNameFormat(), groupSettings.getNameFormat()) + "%1$s");
		base = Util.replace(base, "<suffix>", Settings.pick("suffix", playerSettings.getSuffix(), groupSettings.getSuffix()));
		base = Util.replace(base, "<group>", p.getGroup().getName());
		base = Util.replace(base, "<msg>", Settings.pick("chatColor", playerSettings.getChatColor(), groupSettings.getChatColor()) + Settings.pick("chatFormat", playerSettings.getChatFormat(), groupSettings.getChatFormat()) + "%2$s");
		return base;
	}
	
	@Override
	public SSPlayer getPlayer(Player player) {
		if (players.stream().anyMatch(permPlayer -> permPlayer.id().equalsIgnoreCase(player.getUniqueId().toString()))) {
			Optional<PermPlayer> optional = players.stream().filter(permPlayer -> permPlayer.id().equalsIgnoreCase(player.getUniqueId().toString())).findFirst();
			if (optional.isPresent()) return optional.get();
		}
		PermPlayer permPlayer = new PermPlayer(player);
		permPlayer.updateFormat();
		players.add(permPlayer);
		return permPlayer;
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
