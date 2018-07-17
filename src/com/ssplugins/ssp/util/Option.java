package com.ssplugins.ssp.util;

public enum Option {
	
	PREFIX("prefix"),
	SUFFIX("suffix"),
	MESSAGE_FORMAT("messageFormat"),
	NAME_COLOR("nameColor"),
	NAME_FORMAT("nameFormat"),
	CHAT_COLOR("chatColor"),
	CHAT_FORMAT("chatFormat");
	
	private String name;
	
	Option(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public boolean isFormatOption() {
		return (this == NAME_FORMAT || this == CHAT_FORMAT);
	}
	
	public boolean isColorOption() {
		return (this == NAME_COLOR || this == CHAT_COLOR);
	}
	
	public static String getOptionPath(String id, Option option) {
		return id + ".options." + option;
	}
	
	public static String getPermissionsPath(String id) {
		return id + ".permissions";
	}
	
	public static String getInheritsPath(String id) {
		return id + ".inherits";
	}
	
	public static String getPlayersPath(String id) {
		return id + ".players";
	}
}
