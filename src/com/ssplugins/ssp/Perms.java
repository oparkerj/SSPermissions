package com.ssplugins.ssp;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class Perms {
	
	public static final Permission GROUPS = new Permission("ssp.manage.groups", "Permission to manage groups.", PermissionDefault.OP);
	public static final Permission PLAYERS = new Permission("ssp.manage.players", "Permission to manage players.", PermissionDefault.OP);
	public static final Permission ADMIN = new Permission("ssp.manage.admin", "Permission to create/remove groups.", PermissionDefault.OP);
	public static final Permission RELOAD = new Permission("ssp.manage.reload", "Permission to reload the plugin.", PermissionDefault.OP);
	public static final Permission MANAGE = new Permission("ssp.manage", "Permission to manage players and groups.", PermissionDefault.OP);
	public static final Permission ALL = new Permission("ssp.*", "Permission to use any subcommand.", PermissionDefault.OP);
	
}
