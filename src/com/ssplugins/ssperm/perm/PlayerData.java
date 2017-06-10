package com.ssplugins.ssperm.perm;

import java.util.Set;

public interface PlayerData {
	
	String id();
	
	Permissions getPermissions();
	
	Set<String> getAllPermissions();
	
	Settings getSettings();
	
	Group getGroup();
	
	void leaveGroup();
	
	String getChatFormat();
	
}
