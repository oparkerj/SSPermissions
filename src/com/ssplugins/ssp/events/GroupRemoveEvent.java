package com.ssplugins.ssp.events;

import com.ssplugins.ssp.util.SSEvent;

public class GroupRemoveEvent extends SSEvent {
	
	private String group;
	
	public GroupRemoveEvent(String group) {
		this.group = group;
	}
	
	public String getGroupName() {
		return group;
	}
}
