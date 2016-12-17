package com.ssplugins.ssperm.perm;

import com.ssplugins.ssperm.util.ConfigReader;

public interface SSPermAPI {

	GroupManager getGroupManager();

	PlayerManager getPlayerManager();
	
	boolean setChatFormat(String format);
	
	ConfigReader getConfig();
	
	void reload();

}
