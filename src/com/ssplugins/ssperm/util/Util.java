package com.ssplugins.ssperm.util;

import com.ssplugins.ssperm.perm.Group;
import com.ssplugins.ssperm.perm.Settings;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	
	// Utility methods
	
	public static String getNone() {
		return "{none}";
	}
	
	public static String replace(String base, String key, String value) {
		return base.replaceAll(Pattern.quote(key), Matcher.quoteReplacement(value));
	}
	
	public static String color(String input) {
		return ChatColor.translateAlternateColorCodes('&', input);
	}
	
	public static String discolor(String input) {
		return ChatColor.stripColor(color(input));
	}
	
	public static String notNull(String input) {
		return (input == null ? "" : input);
	}
	
	public static String notNull(ChatColor color) {
		if (color == null) return null;
		return String.valueOf(color.getChar());
	}
	
	// End utility methods
	// Begin config methods
	
	public static void addToList(Config config, String path, String item) {
		List<String> list = getList(config, path);
		list.add(item);
		config.set(path, list);
	}
	
	public static void removeFromList(Config config, String path, String item) {
		List<String> list = getList(config, path);
		list.remove(item);
		config.set(path, list);
	}
	
	public static List<String> getList(Config config, String path) {
		if (config.contains(path)) {
			return config.getConfig().getStringList(path);
		}
		return new ArrayList<>();
	}
	
	public static boolean verify(String key, String value) {
		switch (key) {
			case "nameColor":
			case "chatColor":
				return ChatColor.getByChar(value).isColor();
			case "nameFormat":
			case "chatFormat":
				return ChatColor.getByChar(value).isFormat();
			default:
				return true;
		}
	}
	
	public static String getConfigNull(String key) {
		switch (key) {
			case "prefix":
				return getNone();
			case "suffix":
				return getNone();
			case "nameColor":
				return "f";
			case "nameFormat":
				return "r";
			case "chatColor":
				return "f";
			case "chatFormat":
				return "r";
			default:
				return null;
		}
	}
	
	public static List<String> noneList() {
		List<String> list = new ArrayList<>();
		list.add(getNone());
		return list;
	}
	
	public static List<String> getChatVars() {
		List<String> list = new ArrayList<>();
		list.add("<prefix> - Player group prefix");
		list.add("<player> - Player name");
		list.add("<suffix> - Player group suffix.");
		list.add("<group> - Player group name.");
		list.add("<msg> - Chat message.");
		return list;
	}
	
	public static void defaultOptions(Config config) {
		config.setDefault("chatFormat", "<prefix><player><suffix> &7> <msg>");
		config.setValue("chatVariables", getChatVars());
		config.save();
	}
	
	// End config methods
	// Start group methods
	
	public static List<Group> getGroups(Group parent) {
		List<Group> checked = new ArrayList<>();
		checked.add(parent);
		return getGroups(parent, new ArrayList<>(), checked);
	}
	
	private static List<Group> getGroups(Group parent, List<Group> groups, List<Group> checked) {
		for (Group group : parent.getInheritedGroups()) {
			if (groups.stream().anyMatch(group1 -> group1.getName().equalsIgnoreCase(group.getName())) || checked.stream().anyMatch(group1 -> group1.getName().equalsIgnoreCase(group.getName()))) continue;
			groups.add(group);
			checked.add(group);
			groups.addAll(getGroups(group, groups, checked));
		}
		return groups;
	}
	
	public static void loadOptions(Settings settings, Config config, String id) {
		Settings.getAllOptions().forEach(s -> {
			config.setDefault(id + ".options." + s, getConfigNull(s));
			String v = config.getConfig().getString(id + ".options." + s);
			if (!verify(s, v)) v = getConfigNull(s);
			settings.unsafeSet(s, color(v));
		});
		config.save();
	}
	
	// End group methods
	
}
