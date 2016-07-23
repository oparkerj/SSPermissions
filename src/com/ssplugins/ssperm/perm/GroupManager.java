package com.ssplugins.ssperm.perm;

import java.util.List;
import java.util.Optional;

public interface GroupManager {
	
	Group createGroup(String name);
	
	boolean removeGroup(String name);

	boolean groupExists(String name);
	
	Optional<Group> getGroup(String name); // probably going to need to be optional
	
	List<Group> getGroups();
	
	Group getDefaultGroup();

}
