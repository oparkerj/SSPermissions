package com.ssplugins.ssperm.util;

import org.bukkit.event.Cancellable;

public abstract class SSCancellableEvent extends SSEvent implements Cancellable {
	
	private boolean cancelled;
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean b) {
		cancelled = b;
	}
}
