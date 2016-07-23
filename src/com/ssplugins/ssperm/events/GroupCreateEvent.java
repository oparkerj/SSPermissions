package com.ssplugins.ssperm.events;

import com.ssplugins.ssperm.perm.Group;
import com.ssplugins.ssperm.util.SSEvent;

public class GroupCreateEvent extends SSEvent {
	
	private Group group;
	
	public GroupCreateEvent(Group group) {
		this.group = group;
	}
	
	public Group getGroup() {
		return group;
	}
	
}
