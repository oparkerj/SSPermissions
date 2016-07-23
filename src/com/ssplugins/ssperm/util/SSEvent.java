package com.ssplugins.ssperm.util;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class SSEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
