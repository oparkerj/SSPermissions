package com.ssplugins.ssperm.perm;

import com.ssplugins.ssperm.callback.OptionCallback;
import com.ssplugins.ssperm.util.Config;
import com.ssplugins.ssperm.util.Option;
import com.ssplugins.ssperm.util.Util;
import org.bukkit.ChatColor;

public interface Settings {

	String getPrefix();

	boolean setPrefix(String prefix);

	String getSuffix();

	boolean setSuffix(String suffix);
	
	String getMessageFormat();
	
	boolean setMessageFormat(String messageFormat);

	ChatColor getNameColor();

	boolean setNameColor(ChatColor color);
	
	ChatColor getNameFormat();
	
	boolean setNameFormat(ChatColor format);

	ChatColor getChatColor();

	boolean setChatColor(ChatColor color);
	
	ChatColor getChatFormat();
	
	boolean setChatFormat(ChatColor format);
	
	void unsafeSet(Option option, String value);
	
	static Option[] getAllOptions() {
		return Option.values();
	}
	
	static String pick(Option option, String player, String group) {
		String v = Util.getConfigNull(option);
		if (player == null || player.equalsIgnoreCase(v)) return Util.notNull(group);
		else return Util.notNull(player);
	}
	
	static String pickFormat(String def, String player, String group) {
		String v = Util.getConfigNull(Option.MESSAGE_FORMAT);
		if (player == null || player.equalsIgnoreCase(v)) {
			if (group == null || group.equalsIgnoreCase(v)) return Util.notNull(def);
			else return Util.notNull(group);
		}
		else return Util.notNull(player);
	}
	
	static String pick(Option option, ChatColor player, ChatColor group) {
		if (option.isFormatOption() && (player == null && group == null)) return "";
		if (player == null) player = ChatColor.getByChar(Util.getConfigNull(option));
		if (group == null) group = ChatColor.getByChar(Util.getConfigNull(option));
		return ChatColor.COLOR_CHAR + pick(option, Util.notNull(player), Util.notNull(group));
	}
	
	static Settings temp(String id, Config config, OptionCallback callback) {
		return new Settings() {
			@Override
			public String getPrefix() {
				String value = config.getString(Option.getOptionPath(id, Option.PREFIX));
				if (value.equals(Util.getConfigNull(Option.PREFIX))) value = null;
				return value;
			}
			
			@Override
			public boolean setPrefix(String prefix) {
				if (prefix == null) prefix = Util.getConfigNull(Option.PREFIX);
				if (!Util.verify(Option.PREFIX, prefix)) return false;
				String old = getPrefix();
				config.set(Option.getOptionPath(id, Option.PREFIX), prefix);
				callback.onCall(Option.PREFIX, old, prefix);
				return true;
			}
			
			@Override
			public String getSuffix() {
				String value = config.getString(Option.getOptionPath(id, Option.SUFFIX));
				if (value.equals(Util.getConfigNull(Option.SUFFIX))) value = null;
				return value;
			}
			
			@Override
			public boolean setSuffix(String suffix) {
				if (suffix == null) suffix = Util.getConfigNull(Option.SUFFIX);
				if (!Util.verify(Option.SUFFIX, suffix)) return false;
				String old = getSuffix();
				config.set(Option.getOptionPath(id, Option.SUFFIX), suffix);
				callback.onCall(Option.SUFFIX, old, suffix);
				return true;
			}
			
			@Override
			public String getMessageFormat() {
				String value = config.getString(Option.getOptionPath(id, Option.MESSAGE_FORMAT));
				if (value.equals(Util.getConfigNull(Option.MESSAGE_FORMAT))) value = null;
				return value;
			}
			
			@Override
			public boolean setMessageFormat(String messageFormat) {
				if (messageFormat == null) messageFormat = Util.getConfigNull(Option.MESSAGE_FORMAT);
				if (!Util.verify(Option.MESSAGE_FORMAT, messageFormat)) return false;
				String old = getMessageFormat();
				config.set(Option.getOptionPath(id, Option.MESSAGE_FORMAT), messageFormat);
				callback.onCall(Option.MESSAGE_FORMAT, old, messageFormat);
				return true;
			}
			
			@Override
			public ChatColor getNameColor() {
				String v = config.getString(Option.getOptionPath(id, Option.NAME_COLOR));
				if (v.equals(Util.getConfigNull(Option.NAME_COLOR))) return null;
				return ChatColor.getByChar(v);
			}
			
			@Override
			public boolean setNameColor(ChatColor color) {
				String newValue = Util.getChar(color);
				if (newValue == null) newValue = Util.getConfigNull(Option.NAME_COLOR);
				if (!Util.verify(Option.NAME_COLOR, newValue)) return false;
				String old = Util.getChar(getNameColor());
				config.set(Option.getOptionPath(id, Option.NAME_COLOR), newValue);
				callback.onCall(Option.NAME_COLOR, old, newValue);
				return true;
			}
			
			@Override
			public ChatColor getNameFormat() {
				String v = config.getString(Option.getOptionPath(id, Option.NAME_FORMAT));
				if (v.equals(Util.getConfigNull(Option.NAME_FORMAT))) return null;
				return ChatColor.getByChar(v);
			}
			
			@Override
			public boolean setNameFormat(ChatColor format) {
				String newValue = Util.getChar(format);
				if (newValue == null) newValue = Util.getConfigNull(Option.NAME_FORMAT);
				if (!Util.verify(Option.NAME_FORMAT, newValue)) return false;
				String old = Util.getChar(getNameFormat());
				config.set(Option.getOptionPath(id, Option.NAME_FORMAT), newValue);
				callback.onCall(Option.NAME_FORMAT, old, newValue);
				return true;
			}
			
			@Override
			public ChatColor getChatColor() {
				String v = config.getString(Option.getOptionPath(id, Option.CHAT_COLOR));
				if (v.equals(Util.getConfigNull(Option.CHAT_COLOR))) return null;
				return ChatColor.getByChar(v);
			}
			
			@Override
			public boolean setChatColor(ChatColor color) {
				String newValue = Util.getChar(color);
				if (newValue == null) newValue = Util.getConfigNull(Option.CHAT_COLOR);
				if (!Util.verify(Option.CHAT_COLOR, newValue)) return false;
				String old = Util.getChar(getChatColor());
				config.set(Option.getOptionPath(id, Option.CHAT_COLOR), newValue);
				callback.onCall(Option.CHAT_COLOR, old, newValue);
				return true;
			}
			
			@Override
			public ChatColor getChatFormat() {
				String v = config.getString(Option.getOptionPath(id, Option.CHAT_FORMAT));
				if (v.equals(Util.getConfigNull(Option.CHAT_FORMAT))) return null;
				return ChatColor.getByChar(v);
			}
			
			@Override
			public boolean setChatFormat(ChatColor format) {
				String newValue = Util.getChar(format);
				if (newValue == null) newValue = Util.getConfigNull(Option.CHAT_FORMAT);
				if (!Util.verify(Option.CHAT_FORMAT, newValue)) return false;
				String old = Util.getChar(getChatFormat());
				config.set(Option.getOptionPath(id, Option.CHAT_FORMAT), newValue);
				callback.onCall(Option.CHAT_FORMAT, old, newValue);
				return true;
			}
			
			@Override
			public void unsafeSet(Option option, String value) {
				if (value.equalsIgnoreCase(Util.getConfigNull(option))) value = null;
				switch (option) {
					case PREFIX:
						config.set(Option.getOptionPath(id, Option.PREFIX), value);
						break;
					case SUFFIX:
						config.set(Option.getOptionPath(id, Option.SUFFIX), value);
						break;
					case MESSAGE_FORMAT:
						config.set(Option.getOptionPath(id, Option.MESSAGE_FORMAT), value);
						break;
					case NAME_COLOR:
						config.set(Option.getOptionPath(id, Option.NAME_COLOR), value);
						break;
					case NAME_FORMAT:
						config.set(Option.getOptionPath(id, Option.NAME_FORMAT), value);
						break;
					case CHAT_COLOR:
						config.set(Option.getOptionPath(id, Option.CHAT_COLOR), value);
						break;
					case CHAT_FORMAT:
						config.set(Option.getOptionPath(id, Option.CHAT_FORMAT), value);
						break;
					default:
						break;
				}
			}
		};
	}

}
