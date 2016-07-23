package com.ssplugins.ssperm.perm;

import com.ssplugins.ssperm.util.Util;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public interface Settings {

	String getPrefix();

	boolean setPrefix(String prefix);

	String getSuffix();

	boolean setSuffix(String suffix);

	ChatColor getNameColor();

	boolean setNameColor(ChatColor color);
	
	ChatColor getNameFormat();
	
	boolean setNameFormat(ChatColor format);

	ChatColor getChatColor();

	boolean setChatColor(ChatColor color);
	
	ChatColor getChatFormat();
	
	boolean setChatFormat(ChatColor format);
	
	void unsafeSet(String name, String value);
	
	static List<String> getAllOptions() {
		List<String> list = new ArrayList<>();
		list.add("prefix");
		list.add("suffix");
		list.add("nameColor");
		list.add("nameFormat");
		list.add("chatColor");
		list.add("chatFormat");
		return list;
	}
	
	static String pick(String name, String player, String group) {
		String v = Util.getConfigNull(name);
		if (player == null || player.equalsIgnoreCase(v)) return Util.notNull(group);
		else return Util.notNull(player);
	}
	
	static String pick(String name, ChatColor player, ChatColor group) {
		if (player == null) player = ChatColor.getByChar(Util.getConfigNull(name));
		if (group == null) group = ChatColor.getByChar(Util.getConfigNull(name));
		return ChatColor.COLOR_CHAR + pick(name, Util.notNull(player), Util.notNull(group));
	}

}
