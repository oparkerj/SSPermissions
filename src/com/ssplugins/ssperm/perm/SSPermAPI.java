package com.ssplugins.ssperm.perm;

public interface SSPermAPI {

	GroupManager getGroupManager();

	PlayerManager getPlayerManager();
	
	boolean setChatFormat(String format);
	
	void reload();

}
