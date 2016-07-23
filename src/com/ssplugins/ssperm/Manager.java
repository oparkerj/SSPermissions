package com.ssplugins.ssperm;

import com.ssplugins.ssperm.perm.*;
import com.ssplugins.ssperm.util.Config;
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
		groupMan.loadGroups();
		Bukkit.getOnlinePlayers().forEach(attMan::setup);
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
}
