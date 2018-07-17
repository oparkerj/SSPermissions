package com.ssplugins.ssp;

import com.ssplugins.ssp.cmd.MainCommand;
import com.ssplugins.ssp.cmd.MainTabCompleter;
import com.ssplugins.ssp.events.SSPReloadEvent;
import com.ssplugins.ssp.perm.*;
import com.ssplugins.ssp.util.Config;
import com.ssplugins.ssp.util.ConfigReader;
import com.ssplugins.ssp.util.Util;
import org.bukkit.Bukkit;

import java.io.File;

class Manager implements SSPermAPI {
	
	private static Manager instance;
	
	private static Config options, groups, catalog;
	private GroupMan groupMan = new GroupMan();
	private PlayerMan playerMan = new PlayerMan();
	private AttMan attMan = new AttMan(this);
	
	Manager(SSPermissions plugin) {
		instance = this;
		options = new Config(plugin, "config.yml");
		groups = new Config(plugin, "groups.yml");
		catalog = new Config(plugin, "catalog.yml");
		options.generateFile();
		groups.generateFile();
		catalog.generateFile();
		Util.defaultOptions(options);
		groupMan.loadGroups();
		Bukkit.getOnlinePlayers().forEach(attMan::setup);
		plugin.getCommand("ssp").setExecutor(new MainCommand());
		plugin.getCommand("ssp").setTabCompleter(new MainTabCompleter());
		plugin.getServer().getPluginManager().registerEvents(new Events(this), plugin);
	}
	
	void disable() {
		attMan.clean();
	}
	
	static Manager get() {
		return instance;
	}
	
	static Config getOptions() {
		return options;
	}
	
	static Config getGroupsConfig() {
		return groups;
	}
	
	static Config getPlayer(String id) {
		//Config config = new Config(SSPerm.get(), new File(SSPerm.get().getDataFolder(), "players/" + id + ".yml"));
		Config config = new Config(SSPermissions.get(), new File(SSPermissions.get().getDataFolder().getAbsolutePath() + "/players", id + ".yml"));
		config.generateFile();
		return config;
	}
	
	static Config getCatalog() {
		return catalog;
	}
	
	AttMan getAttMan() {
		return attMan;
	}
	
	PlayerMan getPlayerMan() {
		return playerMan;
	}
	
	GroupMan getGroupMan() {
		return groupMan;
	}
	
	@Override
	public GroupManager getGroupManager() {
		return groupMan;
	}

	@Override
	public PlayerManager getPlayerManager() {
		return playerMan;
	}
	
	@Override
	public boolean setChatFormat(String format) {
		if (!Util.validChatFormat(format)) return false;
		getOptions().set("chatFormat", format);
		return true;
	}
	
	@Override
	public String getChatFormat(PlayerData data) {
		return getPlayerMan().getChatFormat(data);
	}
	
	@Override
	public ConfigReader getConfig() {
		return options;
	}
	
	@Override
	public void reload() {
		playerMan.unloadPlayers();
		groupMan.unloadGroups();
		attMan.clean();
		catalog.reloadConfig();
		options.reloadConfig();
		Util.defaultOptions(options);
		groups.reloadConfig();
		groupMan.loadGroups();
		Bukkit.getOnlinePlayers().forEach(attMan::setup);
		Events.callEvent(new SSPReloadEvent());
	}
}
