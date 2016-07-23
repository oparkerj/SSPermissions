package com.ssplugins.ssperm.callback;

import org.bukkit.ChatColor;

public class Callback {
	
	public static boolean handle(ChangeCallback callback, String key, String oldValue, String newValue) {
		return callback == null || callback.onChange(key, oldValue, newValue);
	}
	
	public static boolean handle(ChangeCallback callback, String key, ChatColor oldValue, ChatColor newValue) {
		return handle(callback, key, (oldValue == null ? null : String.valueOf(oldValue.getChar())), (newValue == null ? null : String.valueOf(newValue.getChar())));
	}
	
	public static void handle(ListCallback<String> callback, String item, boolean add) {
		if (callback == null) return;
		callback.onUpdate(item, add);
	}
	
}
