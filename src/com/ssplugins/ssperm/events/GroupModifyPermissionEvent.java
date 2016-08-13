package com.ssplugins.ssperm.events;

import com.ssplugins.ssperm.perm.Group;
import com.ssplugins.ssperm.util.SSEvent;

public class GroupModifyPermissionEvent extends SSEvent {
	
	private Group group;
	private String permission;
	private boolean add;
	
	public GroupModifyPermissionEvent(Group group, String permission, boolean add) {
		this.group = group;
		this.permission = permission;
		this.add = add;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public boolean isAdd() {
		return add;
	}
}
