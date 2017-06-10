package com.ssplugins.ssperm;

import com.ssplugins.ssperm.perm.SSPermAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class SSPerm extends JavaPlugin {

	private static SSPerm instance;
	private static Manager manager;

	@Override
	public void onEnable() {
		instance = this;
		manager = new Manager(this);
	}
	
	@Override
	public void onDisable() {
		manager.disable();
	}
	
	public static SSPermAPI getAPI() {
		return manager;
	}
	
	public static SSPerm get() {
		return instance;
	}
	
	public static void log(String msg) {
		instance.getLogger().info(msg);
	}
	
}