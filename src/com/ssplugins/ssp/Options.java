package com.ssplugins.ssp;

import com.ssplugins.ssp.callback.Callback;
import com.ssplugins.ssp.callback.ChangeCallback;
import com.ssplugins.ssp.perm.Settings;
import com.ssplugins.ssp.util.Option;
import com.ssplugins.ssp.util.Util;
import org.bukkit.ChatColor;

class Options implements Settings {
	
	private String prefix;
	private String suffix;
	private String messageFormat;
	private ChatColor nameColor;
	private ChatColor nameFormat;
	private ChatColor chatColor;
	private ChatColor chatFormat;
	
	private ChangeCallback callback = null;
	
	Options() {
		prefix = null;
		suffix = null;
		messageFormat = null;
		nameColor = null;
		nameFormat = null;
		chatColor = null;
		chatFormat = null;
	}
	
	void setCallback(ChangeCallback callback) {
		this.callback = callback;
	}
	
	@Override
	public String getPrefix() {
		return Util.color(prefix);
	}
	
	@Override
	public boolean setPrefix(String prefix) {
		String old = this.prefix;
		if (!Callback.handle(callback, Option.PREFIX, old, prefix)) return false;
		if (prefix.equalsIgnoreCase(Util.getConfigNull(Option.PREFIX))) prefix = null;
		this.prefix = prefix;
		if (callback != null) callback.afterChange(Option.PREFIX, old, prefix);
		return true;
	}
	
	@Override
	public String getSuffix() {
		return Util.color(suffix);
	}
	
	@Override
	public boolean setSuffix(String suffix) {
		String old = this.suffix;
		if (!Callback.handle(callback, Option.SUFFIX, old, suffix)) return false;
		if (suffix.equalsIgnoreCase(Util.getConfigNull(Option.SUFFIX))) suffix = null;
		this.suffix = suffix;
		if (callback != null) callback.afterChange(Option.SUFFIX, old, suffix);
		return true;
	}
	
	@Override
	public String getMessageFormat() {
		return messageFormat;
	}
	
	@Override
	public boolean setMessageFormat(String messageFormat) {
		String old = this.messageFormat;
		if (!Callback.handle(callback, Option.MESSAGE_FORMAT, old, messageFormat)) return false;
		if (messageFormat.equalsIgnoreCase(Util.getConfigNull(Option.MESSAGE_FORMAT))) messageFormat = null;
		this.messageFormat = messageFormat;
		if (callback != null) callback.afterChange(Option.MESSAGE_FORMAT, old, messageFormat);
		return true;
	}
	
	@Override
	public ChatColor getNameColor() {
		return nameColor;
	}
	
	@Override
	public boolean setNameColor(ChatColor color) {
		String old = Util.getChar(this.nameColor);
		if (!Callback.handle(callback, Option.NAME_COLOR, this.nameColor, color)) return false;
		if (color != null && String.valueOf(color.getChar()).equalsIgnoreCase(Util.getConfigNull(Option.NAME_COLOR))) color = null;
		nameColor = color;
		if (callback != null) callback.afterChange(Option.NAME_COLOR, old, Util.getChar(color));
		return true;
	}
	
	@Override
	public ChatColor getNameFormat() {
		return nameFormat;
	}
	
	@Override
	public boolean setNameFormat(ChatColor format) {
		String old = Util.getChar(this.nameColor);
		if (!Callback.handle(callback, Option.NAME_FORMAT, this.nameFormat, format)) return false;
		if (format != null && String.valueOf(format.getChar()).equalsIgnoreCase(Util.getConfigNull(Option.NAME_FORMAT))) format = null;
		nameFormat = format;
		if (callback != null) callback.afterChange(Option.NAME_FORMAT, old, Util.getChar(format));
		return true;
	}
	
	@Override
	public ChatColor getChatColor() {
		return chatColor;
	}
	
	@Override
	public boolean setChatColor(ChatColor color) {
		String old = Util.getChar(this.chatColor);
		if (!Callback.handle(callback, Option.CHAT_COLOR, this.chatColor, color)) return false;
		if (color != null && String.valueOf(color.getChar()).equalsIgnoreCase(Util.getConfigNull(Option.CHAT_COLOR))) color = null;
		chatColor = color;
		if (callback != null) callback.afterChange(Option.CHAT_COLOR, old, Util.getChar(color));
		return true;
	}
	
	@Override
	public ChatColor getChatFormat() {
		return chatFormat;
	}
	
	@Override
	public boolean setChatFormat(ChatColor format) {
		String old = Util.getChar(this.chatFormat);
		if (!Callback.handle(callback, Option.CHAT_FORMAT, this.chatFormat, format)) return false;
		if (format != null && String.valueOf(format.getChar()).equalsIgnoreCase(Util.getConfigNull(Option.CHAT_FORMAT))) format = null;
		chatFormat = format;
		if (callback != null) callback.afterChange(Option.CHAT_FORMAT, old, Util.getChar(format));
		return true;
	}
	
	@Override
	public void unsafeSet(Option option, String value) {
		if (value.equalsIgnoreCase(Util.getConfigNull(option))) value = null;
		switch (option) {
			case PREFIX:
				prefix = value;
				break;
			case SUFFIX:
				suffix = value;
				break;
			case MESSAGE_FORMAT:
				messageFormat = value;
				break;
			case NAME_COLOR:
				nameColor = (value == null ? null : ChatColor.getByChar(value));
				break;
			case NAME_FORMAT:
				nameFormat = (value == null ? null : ChatColor.getByChar(value));
				break;
			case CHAT_COLOR:
				chatColor = (value == null ? null : ChatColor.getByChar(value));
				break;
			case CHAT_FORMAT:
				chatFormat = (value == null ? null : ChatColor.getByChar(value));
				break;
			default:
				break;
		}
	}
}
