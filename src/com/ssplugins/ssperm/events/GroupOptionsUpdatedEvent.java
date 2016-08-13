package com.ssplugins.ssperm.events;

import com.ssplugins.ssperm.perm.Group;
import com.ssplugins.ssperm.util.SSEvent;

public class GroupOptionsUpdatedEvent extends SSEvent {
	
	private Group group;
	
	public GroupOptionsUpdatedEvent(Group group) {
		this.group = group;
	}
	
	public Group getGroup() {
		return group;
	}
}
