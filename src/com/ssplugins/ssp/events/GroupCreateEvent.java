package com.ssplugins.ssp.events;

import com.ssplugins.ssp.perm.Group;
import com.ssplugins.ssp.util.SSEvent;

public class GroupCreateEvent extends SSEvent {
	
	private Group group;
	
	public GroupCreateEvent(Group group) {
		this.group = group;
	}
	
	public Group getGroup() {
		return group;
	}
	
}
