package com.ssplugins.ssperm;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class Perms {
	
	public static final Permission GROUPS = new Permission("ssperm.manage.groups", "Permission to manage groups.", PermissionDefault.OP);
	public static final Permission PLAYERS = new Permission("ssperm.manage.players", "Permission to manage players.", PermissionDefault.OP);
	public static final Permission ADMIN = new Permission("ssperm.manage.admin", "Permission to create/remove groups.", PermissionDefault.OP);
	public static final Permission MANAGE = new Permission("ssperm.manage", "Permission to manage players and groups.", PermissionDefault.OP);
	public static final Permission ALL = new Permission("ssperm.*", "Permission to use any subcommand.", PermissionDefault.OP);
	
}
