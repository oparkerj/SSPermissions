package com.ssplugins.ssperm.events;

import com.ssplugins.ssperm.util.SSEvent;

public class GroupRemoveEvent extends SSEvent {
	
	private String group;
	
	public GroupRemoveEvent(String group) {
		this.group = group;
	}
	
	public String getGroupName() {
		return group;
	}
}
