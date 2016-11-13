package com.ssplugins.ssperm.events;

import com.ssplugins.ssperm.perm.Group;
import com.ssplugins.ssperm.util.SSEvent;

public class GroupOptionsUpdatedEvent extends SSEvent {
	
	private Group group;
	private String option;
	
	public GroupOptionsUpdatedEvent(Group group, String option) {
		this.group = group;
		this.option = option;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public String getOption() {
		return option;
	}
}
