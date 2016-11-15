package com.ssplugins.ssperm;

import com.ssplugins.ssperm.cmd.MainCommand;
import com.ssplugins.ssperm.cmd.MainTabCompleter;
import com.ssplugins.ssperm.events.SSPReloadEvent;
import com.ssplugins.ssperm.perm.GroupManager;
import com.ssplugins.ssperm.perm.PlayerManager;
import com.ssplugins.ssperm.perm.SSPermAPI;
import com.ssplugins.ssperm.util.Config;
import com.ssplugins.ssperm.util.Util;
import org.bukkit.Bukkit;

import java.io.File;

class Manager implements SSPermAPI {
	
	private static Manager instance;
	
	private static Config options, groups;
	private GroupMan groupMan = new GroupMan();
	private PlayerMan playerMan = new PlayerMan();
	private AttMan attMan = new AttMan(this);
	
	Manager(SSPerm plugin) {
		instance = this;
		options = new Config(plugin, "config.yml");
		groups = new Config(plugin, "groups.yml");
		options.generateFile();
		groups.generateFile();
		Util.defaultOptions(options);
		groupMan.loadGroups();
		Bukkit.getOnlinePlayers().forEach(attMan::setup);
		plugin.getCommand("ssperm").setExecutor(new MainCommand());
		plugin.getCommand("ssperm").setTabCompleter(new MainTabCompleter());
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
	
	static Config getGroups() {
		return groups;
	}
	
	static Config getPlayer(String id) {
		//Config config = new Config(SSPerm.get(), new File(SSPerm.get().getDataFolder(), "players/" + id + ".yml"));
		Config config = new Config(SSPerm.get(), new File(SSPerm.get().getDataFolder().getAbsolutePath() + "/players", id + ".yml"));
		config.generateFile();
		return config;
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
		if (format == null || (!format.contains("<player>") || !format.contains("<msg>"))) return false;
		getOptions().set("chatFormat", format);
		playerMan.reloadFormats();
		return true;
	}
	
	@Override
	public void reload() {
		playerMan.unloadPlayers();
		groupMan.unloadGroups();
		attMan.clean();
		options.reloadConfig();
		groups.reloadConfig();
		groupMan.loadGroups();
		Bukkit.getOnlinePlayers().forEach(attMan::setup);
		Events.callEvent(new SSPReloadEvent());
	}
}
