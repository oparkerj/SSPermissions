package com.ssplugins.ssp;

import com.ssplugins.ssp.perm.SSPermAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class SSPermissions extends JavaPlugin {

	private static SSPermissions instance;
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
	
	public static SSPermissions get() {
		return instance;
	}
	
	public static void log(String msg) {
		instance.getLogger().info(msg);
	}
	
}