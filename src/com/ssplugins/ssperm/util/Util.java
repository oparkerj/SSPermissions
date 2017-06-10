package com.ssplugins.ssperm.util;

import com.ssplugins.ssperm.perm.Group;
import com.ssplugins.ssperm.perm.Settings;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.*;
import java.util.stream.Collectors;

public class Util {
	
	// Utility methods
	
	public static String getNone() {
		return "{none}";
	}
	
	public static String color(String input) {
		if (input == null) return null;
		return ChatColor.translateAlternateColorCodes('&', input);
	}
	
	public static String discolor(String input) {
		if (input == null) return null;
		return ChatColor.stripColor(color(input));
	}
	
	public static String notNull(String input) {
		return (input == null ? "" : input);
	}
	
	public static String notNull(ChatColor color) {
		if (color == null) return null;
		return String.valueOf(color.getChar());
	}
	
	public static String join(List<String> list, String del) {
		StringBuilder builder = new StringBuilder();
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			builder.append(it.next());
			if (it.hasNext()) builder.append(del);
		}
		return builder.toString();
	}
	
	public static List<String> groupsToStrings(List<Group> groups) {
		return groups.stream().map(Group::getName).collect(Collectors.toList());
	}
	
	public static boolean hasAny(CommandSender sender, Permission... permissions) {
		for (Permission p : permissions) {
			if (sender.hasPermission(p)) return true;
		}
		return false;
	}
	
	public static String getChar(ChatColor color) {
		return color == null ? null : String.valueOf(color.getChar());
	}
	
	public static boolean validChatFormat(String format) {
		return format != null && format.contains("<player>") && format.contains("<msg>");
	}
	
	public static String combine(String[] parts, int start) {
		StringBuilder builder = new StringBuilder();
		for (int i = start; i < parts.length; i++) {
			builder.append(parts[i]).append(" ");
		}
		return builder.toString().trim();
	}
	
	// End utility methods
	// Begin config methods
	
	public static boolean addToList(Config config, String path, String item) {
		List<String> list = getList(config, path);
		if (list.contains(item)) return false;
		boolean s = list.add(item);
		config.set(path, new ArrayList<>(list));
		return s;
	}
	
	public static boolean removeFromList(Config config, String path, String item) {
		List<String> list = getList(config, path);
		boolean s = list.removeIf(s1 -> s1.equalsIgnoreCase(item));
		if (list.size() == 0) config.set(path, null);
		else config.set(path, list);
		return s;
	}
	
	public static List<String> getList(Config config, String path) {
		if (config.contains(path)) {
			return config.getConfig().getStringList(path);
		}
		return new ArrayList<>();
	}
	
	public static boolean verify(Option option, String value) {
		switch (option) {
			case NAME_COLOR:
			case CHAT_COLOR:
				return ChatColor.getByChar(value).isColor();
			case NAME_FORMAT:
			case CHAT_FORMAT:
				return ChatColor.getByChar(value).isFormat() || ChatColor.getByChar(value) == ChatColor.RESET;
			case MESSAGE_FORMAT:
				return validChatFormat(value) || value.equalsIgnoreCase(getConfigNull(option));
			default:
				return true;
		}
	}
	
	public static String getConfigNull(Option option) {
		switch (option) {
			case PREFIX:
				return getNone();
			case SUFFIX:
				return getNone();
			case MESSAGE_FORMAT:
				return getNone();
			case NAME_COLOR:
				return "f";
			case NAME_FORMAT:
				return "r";
			case CHAT_COLOR:
				return "f";
			case CHAT_FORMAT:
				return "r";
			default:
				return getNone();
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
		config.setDefault("msgPlayerWhenMoved", true);
		config.setValue("chatVariables", getChatVars());
		config.save();
	}
	
	public static String checkFormat(String format) {
		if (format == null || (!format.contains("<player>") || !format.contains("<msg>"))) return "<prefix><player><suffix> &7> <msg>";
		return format;
	}
	
	public static String getID(Config catalog, String name) {
		for (String s : catalog.getConfig().getConfigurationSection("").getKeys(false)) {
			if (catalog.getString(s).equalsIgnoreCase(name)) {
				return s;
			}
		}
		return null;
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
			groups.addAll(getGroups(group, new ArrayList<>(), checked));
		}
		return groups;
	}
	
	public static void loadOptions(Settings settings, Config config, String id) {
		for (Option option : Settings.getAllOptions()) {
			config.setDefault(Option.getOptionPath(id, option), getConfigNull(option));
			String v = config.getConfig().getString(Option.getOptionPath(id, option));
			if (!verify(option, v)) v = getConfigNull(option);
			if (settings != null) settings.unsafeSet(option, color(v));
		}
		config.save();
	}
	
	// End group methods
	// Debug methods
	
	public static void dumpGroups(String name, List<Group> list) {
		log("- " + name);
		list.forEach(group -> System.out.println(group.getName()));
		log("-");
	}
	
	public static <T> void dumpList(String name, List<T> list) {
		log("- " + name);
		list.forEach(t -> System.out.println(t.toString()));
		log("-");
	}
	
	public static void log(Object msg) {
		System.out.println(msg.toString());
	}
	
	// End debug methods
	
}
