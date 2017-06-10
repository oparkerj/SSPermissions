package com.ssplugins.ssperm.events;

import com.ssplugins.ssperm.perm.Group;
import com.ssplugins.ssperm.util.Option;
import com.ssplugins.ssperm.util.SSEvent;

public class GroupOptionsUpdatedEvent extends SSEvent {
	
	private Group group;
	private Option option;
	private String oldValue;
	private String newValue;
	
	public GroupOptionsUpdatedEvent(Group group, Option option, String oldValue, String newValue) {
		this.group = group;
		this.option = option;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public Option getOption() {
		return option;
	}
	
	public String getOldValue() {
		return oldValue;
	}
	
	public String getNewValue() {
		return newValue;
	}
}
