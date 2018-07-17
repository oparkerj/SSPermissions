package com.ssplugins.ssp.perm;

import com.ssplugins.ssp.util.ConfigReader;

public interface SSPermAPI {

	GroupManager getGroupManager();

	PlayerManager getPlayerManager();
	
	boolean setChatFormat(String format);
	
	String getChatFormat(PlayerData data);
	
	ConfigReader getConfig();
	
	void reload();

}
