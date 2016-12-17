package com.ssplugins.ssperm.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config implements ConfigReader {
	
	private JavaPlugin plugin;
	private String name;
	
	private FileConfiguration config;
	private File configFile;
	
	public Config(JavaPlugin plugin, String filename) {
		this.plugin = plugin;
		this.name = filename;
		reloadConfig();
	}
	
	public Config(JavaPlugin plugin, File file) {
		this.plugin = plugin;
		if (file.isDirectory()) {
			configFile = new File(file, "config.yml");
			name = "config.yml";
		}
		else {
			configFile = file;
			name = file.getName();
		}
		reloadConfig();
	}
	
	public FileConfiguration getConfig() {
		if (config == null) {
			reloadConfig();
		}
		return config;
	}
	
	public void generateFile() {
		saveConfig(getConfig());
	}
	
	public void saveConfig(FileConfiguration cfg) {
		this.config = cfg;
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		saveConfig(config);
	}
	
	public void reloadConfig() {
		if (configFile == null) {
			configFile = new File(plugin.getDataFolder(), name);
		}
		config = YamlConfiguration.loadConfiguration(configFile);
	}
	
	public void clearConfig() {
		FileConfiguration data = getConfig();
		for (String s : data.getConfigurationSection("").getKeys(false)) {
			data.set(s, null);
		}
		saveConfig(data);
	}
	
	public void setDefault(String path, Object value) {
		FileConfiguration data = getConfig();
		if (!data.contains(path)) data.set(path, value);
		saveConfig(data);
	}
	
	public void defaultValue(String path, Object value) {
		FileConfiguration data = getConfig();
		if (!data.contains(path)) data.set(path, value);
	}
	
	public void removeSection(String path) {
		FileConfiguration data = getConfig();
		if (data.contains(path)) data.set(path, null);
		saveConfig(data);
	}
	
	public void set(String path, Object value) {
		FileConfiguration data = getConfig();
		data.set(path, value);
		saveConfig(data);
	}
	
	public void setValue(String path, Object value) {
		FileConfiguration data = getConfig();
		data.set(path, value);
	}
	
	public boolean contains(String path) {
		return getConfig().contains(path);
	}
	
	@Override
	public String getString(String path) {
		return getConfig().getString(path);
	}
	
	@Override
	public boolean getBoolean(String path) {
		return getConfig().getBoolean(path);
	}
	
	@Override
	public List<String> getStringList(String path) {
		return getConfig().getStringList(path);
	}
	
	@Override
	public Object get(String path) {
		return getConfig().get(path);
	}
}