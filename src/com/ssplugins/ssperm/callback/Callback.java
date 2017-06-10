package com.ssplugins.ssperm.callback;

import com.ssplugins.ssperm.util.Option;
import com.ssplugins.ssperm.util.Util;
import org.bukkit.ChatColor;

public class Callback {
	
	public static boolean handle(ChangeCallback callback, Option option, String oldValue, String newValue) {
		return callback == null || callback.onChange(option, oldValue, newValue);
	}
	
	public static boolean handle(ChangeCallback callback, Option option, ChatColor oldValue, ChatColor newValue) {
		return handle(callback, option, Util.getChar(oldValue), Util.getChar(newValue));
	}
	
	public static void handle(ListCallback<String> callback, String item, boolean add) {
		if (callback == null) return;
		callback.onUpdate(item, add);
	}
	
}
