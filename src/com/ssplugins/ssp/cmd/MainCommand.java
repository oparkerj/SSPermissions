package com.ssplugins.ssp.cmd;

import com.ssplugins.ssp.Perms;
import com.ssplugins.ssp.SSPermissions;
import com.ssplugins.ssp.perm.*;
import com.ssplugins.ssp.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MainCommand implements CommandExecutor {
	
	public MainCommand() {}
	
	private void msg(CommandSender sender, String msg) {
		if (sender instanceof Player) sender.sendMessage(Util.color(msg));
		else sender.sendMessage(Util.discolor(msg));
	}
	
	private void msg(CommandSender sender, String... msg) {
		for (String s : msg) {
			msg(sender, s);
		}
	}
	
	private void msg(String id, String msg) {
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(id));
		if (offlinePlayer.isOnline()) msg(offlinePlayer.getPlayer(), msg);
	}
	
	private String[] getHelp(CommandSender sender) {
		List<String> list = new ArrayList<>();
		boolean manage = sender.hasPermission(Perms.MANAGE);
		boolean all = sender.hasPermission(Perms.ALL);
		if (all) manage = true;
		if (sender.hasPermission(Perms.GROUPS) || manage) list.add("&b/ssp list &a- &7List all groups.");
		if (sender.hasPermission(Perms.GROUPS) || manage) list.add("&b/ssp group <name> <action> <value> &a- &7Manage a group.");
		if (sender.hasPermission(Perms.PLAYERS) || manage) list.add("&b/ssp player <name> <action> <value> &a- &7Manage a player.");
		if (sender.hasPermission(Perms.RELOAD)) list.add("&b/ssp reload &a- &7Reload the plugin. All permissions will be updated.");
		if (sender.hasPermission(Perms.ADMIN) || all) {
			list.add("&b/ssp create <name> &a- &7Create a group.");
			list.add("&b/ssp remove <name> &a- &7Remove a group.");
			list.add("&b/ssp format <format> &a- &7Change the global chat format.");
		}
		if (list.isEmpty()) list.add("&cYou do not have permission to use this command.");
		return list.toArray(new String[0]);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			msg(sender, getHelp(sender));
		}
		else {
			if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")) {
				msg(sender, getHelp(sender));
			}
			else if (args[0].equalsIgnoreCase("group") || args[0].equalsIgnoreCase("g")) {
				group(sender, args);
			}
			else if (args[0].equalsIgnoreCase("player") || args[0].equalsIgnoreCase("p")) {
				player(sender, args);
			}
			else if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) {
				create(sender, args);
			}
			else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("r")) {
				remove(sender, args);
			}
			else if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l")) {
				groups(sender);
			}
			else if (args[0].equalsIgnoreCase("format") || args[0].equalsIgnoreCase("f")) {
				format(sender, args);
			}
			else if (args[0].equalsIgnoreCase("reload")) {
				reload(sender);
			}
			else {
				msg(sender, "&eUnknown argument. For help type &b/ssp help");
			}
		}
		
		return true;
	}
	
	private void noPerm(CommandSender sender) {
		sender.sendMessage(Util.color("&cYou don't have permission to use this command."));
	}
	
	private void groups(CommandSender sender) {
		if (!Util.hasAny(sender, Perms.GROUPS, Perms.MANAGE, Perms.ALL)) {
			noPerm(sender);
			return;
		}
		msg(sender, "&7Groups:");
		msg(sender, "&bdefault");
		SSPermissions.getAPI().getGroupManager().getGroups().forEach(group -> msg(sender, "&b" + group.getName()));
	}
	
	private void group(CommandSender sender, String[] args) {
		// /ssp group <name> <action> <value>
		if (!Util.hasAny(sender, Perms.GROUPS, Perms.MANAGE, Perms.ALL)) {
			noPerm(sender);
			return;
		}
		if (args.length == 2) {
			Optional<Group> optional = SSPermissions.getAPI().getGroupManager().getGroup(args[1]);
			if (!optional.isPresent()) {
				msg(sender, "&eGroup not found.");
				return;
			}
			Group group = optional.get();
			msg(sender, "&7Name: &b" + group.getName());
			if (group.getPermissions().length() == 0) {
				msg(sender, "&7Permissions: &aNone");
			}
			else {
				msg(sender, "&7Permissions:");
				group.getPermissions().getAll().forEach(s -> msg(sender, (s.startsWith("-") ? "&c" : "&a") + "- " + s));
			}
			msg(sender, "&7Inherits: &e" + (group.getInheritedGroups().size() == 0 ? "None" : Util.join(Util.groupsToStrings(group.getInheritedGroups()), ", ")));
		}
		else if (args.length < 4) {
			String[] help = new String[] {
					"&b/ssp group <group> <action> <value>",
					"&eActions (alias):",
					"&a<none> &e- &7Leave action blank for group info.",
					"&aadd (a) &e- &7Add a permission",
					"&aremove (r) &e- &7Remove a permission",
					"&ainherit (i) &e- &7Inherit the permissions of another group",
					"&auninherit (u) &e- &7Remove inherited permissions for a group",
					"&aprefix (p) &e- &7Set the prefix for the group",
					"&asuffix (s) &e- &7Set the suffix for the group",
					"&aformat (f) &e- &7Set the chat format for the group",
					"&anamecolor (nc) &e- &7Set the name color of players in the group",
					"&anameformat (nf) &e- &7Set the name format of players in the group",
					"&achatcolor (cc) &e- &7Set the chat color of players in the group",
					"&achatformat (cf) &e- &7Set the chat format of players in the group"
			};
			msg(sender, help);
		}
		else {
			Optional<Group> optional = SSPermissions.getAPI().getGroupManager().getGroup(args[1]);
			if (!optional.isPresent()) {
				msg(sender, "&eGroup not found.");
				return;
			}
			Group group = optional.get();
			if (args[2].equalsIgnoreCase("add") || args[2].equalsIgnoreCase("a")) {
				if (!group.getPermissions().add(args[3])) {
					msg(sender, "&eGroup already has permission.");
				}
				else {
					msg(sender, "&aAdded &e" + args[3] + " &ato group &b" + group.getName() + "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("remove") || args[2].equalsIgnoreCase("r")) {
				if (!group.getPermissions().remove(args[3])) {
					msg(sender, "&eGroup does not have permission.");
				}
				else {
					msg(sender, "&aRemoved &e" + args[3] + " &afrom group &b" + group.getName() + "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("inherit") || args[2].equalsIgnoreCase("i")) {
				Optional<Group> o = SSPermissions.getAPI().getGroupManager().getGroup(args[3]);
				if (!o.isPresent()) {
					msg(sender, "&eGroup &b" + args[3] + " &enot found.");
					return;
				}
				Group g = o.get();
				if (!group.inherit(g)) {
					msg(sender, "&b" + group.getName() + " &eis unable to inherit &b" + g.getName() + "&e.");
				}
				else {
					msg(sender, "&b" + group.getName() + " &anow inherits &b" + g.getName() + "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("uninherit") || args[2].equalsIgnoreCase("u")) {
				if (!group.unInherit(args[3])) {
					msg(sender, "&b" + group.getName() + " &edoes not inherit &b" + args[3] + "&e.");
				}
				else {
					msg(sender, "&b" + group.getName() + " &ano longer inherits &b" + args[3] + "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("prefix") || args[2].equalsIgnoreCase("p")) {
				args[3] = args[3].replaceAll("(\\\\_)", String.valueOf(ChatColor.COLOR_CHAR)).replaceAll("_", " ").replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "_");
				if (!group.getSettings().setPrefix(args[3])) {
					msg(sender, "&eUnable to set group prefix.");
				}
				else {
					msg(sender, "&aGroup &b" + group.getName() + " &aprefix set to &e" + args[3] + "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("suffix") || args[2].equalsIgnoreCase("s")) {
				args[3] = args[3].replaceAll("(\\\\_)", String.valueOf(ChatColor.COLOR_CHAR)).replaceAll("_", " ").replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "_");
				if (!group.getSettings().setSuffix(args[3])) {
					msg(sender, "&eUnable to set group suffix.");
				}
				else {
					msg(sender, "&aGroup &b" + group.getName() + " &asuffix set to &e" + args[3] + "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("format") || args[2].equalsIgnoreCase("f")) {
				String[] trim = new String[args.length - 3];
				System.arraycopy(args, 3, trim, 0, trim.length);
				String format = String.join(" ", trim);
				if (!group.getSettings().setMessageFormat(format)) {
					msg(sender, "&eFormat must contain at least <player> and <msg> variables.");
				}
				else {
					msg(sender, "&aChat format for &b" + group.getName() + " &aupdated to &f" + Util.color(format));
				}
			}
			else if (args[2].equalsIgnoreCase("namecolor") || args[2].equalsIgnoreCase("nc")) {
				String f = args[3].replaceAll("[^a-fA-F0-9]", "");
				if (f.length() > 1) f = f.substring(0, 1);
				if (f.isEmpty()) {
					msg(sender, "&eEnter a color code. (a-f 0-9)");
					return;
				}
				ChatColor c = ChatColor.getByChar(f);
				if (!group.getSettings().setNameColor(c)) {
					msg(sender, "&b" + c.name() + " &eis not a color.");
				}
				else {
					msg(sender, "&aSet group &b" + group.getName() + " &anamecolor to &e" + c.name()+ "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("nameformat") || args[2].equalsIgnoreCase("nf")) {
				String f = args[3].replaceAll("[^k-oK-OrR]", "");
				if (f.length() > 1) f = f.substring(0, 1);
				if (f.isEmpty()) {
					msg(sender, "&eEnter a format code. (k-o r)");
					return;
				}
				ChatColor c = ChatColor.getByChar(f);
				if (!group.getSettings().setNameFormat(c)) {
					msg(sender, "&b" + c.name() + " &eis not a format.");
				}
				else {
					msg(sender, "&aSet group &b" + group.getName() + " &anameformat to &e" + c.name()+ "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("chatcolor") || args[2].equalsIgnoreCase("cc")) {
				String f = args[3].replaceAll("[^a-fA-F0-9]", "");
				if (f.length() > 1) f = f.substring(0, 1);
				if (f.isEmpty()) {
					msg(sender, "&eEnter a color code. (a-f 0-9)");
					return;
				}
				ChatColor c = ChatColor.getByChar(f);
				if (!group.getSettings().setChatColor(c)) {
					msg(sender, "&b" + c.name() + " &eis not a color.");
				}
				else {
					msg(sender, "&aSet group &b" + group.getName() + " &achatcolor to &e" + c.name()+ "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("chatformat") || args[2].equalsIgnoreCase("cf")) {
				String f = args[3].replaceAll("[^k-oK-OrR]", "");
				if (f.length() > 1) f = f.substring(0, 1);
				if (f.isEmpty()) {
					msg(sender, "&eEnter a format code. (k-o r)");
					return;
				}
				ChatColor c = ChatColor.getByChar(f);
				if (!group.getSettings().setChatFormat(c)) {
					msg(sender, "&b" + c.name() + " &eis not a format.");
				}
				else {
					msg(sender, "&aSet group &b" + group.getName() + " &achatformat to &e" + c.name()+ "&a.");
				}
			}
			else {
				msg(sender, "&eUnknown action.");
			}
		}
	}
	
	private void player(CommandSender sender, String[] args) {
		// /ssp player <name> <action> <value>
		if (!Util.hasAny(sender, Perms.PLAYERS, Perms.MANAGE, Perms.ALL)) {
			noPerm(sender);
			return;
		}
		if (args.length == 2) {
			PlayerData data;
			String player;
			Optional<SSPlayer> optional = SSPermissions.getAPI().getPlayerManager().getPlayerByName(args[1]);
			if (!optional.isPresent()) {
				Optional<SSOfflinePlayer> op = SSPermissions.getAPI().getPlayerManager().getOfflinePlayerByName(args[1]);
				if (!op.isPresent()) {
					msg(sender, "&ePlayer not found.");
					return;
				}
				else {
					data = op.get();
					player = args[1];
				}
			}
			else {
				data = optional.get();
				player = optional.get().getPlayer().getName();
			}
			msg(sender, "&7Name: &b" + player);
			msg(sender, "&7Group: &d" + data.getGroup().getName());
			if (data.getPermissions().length() == 0) {
				msg(sender, "&7Permissions: &aNone");
			}
			else {
				msg(sender, "&7Permissions:");
				data.getPermissions().getAll().forEach(s -> msg(sender, (s.startsWith("-") ? "&c" : "&a") + "- " + s));
			}
		}
		else if (args.length < 4) {
			String[] help = new String[] {
					"&b/ssp group <group> <action> <value>",
					"&eActions (alias):",
					"&aadd (a) &e- &7Add a permission",
					"&aremove (r) &e- &7Remove a permission",
					"&amove (m) &e- &7Move a player into a group.",
					"&aprefix (p) &e- &7Set the prefix for the group",
					"&asuffix (s) &e- &7Set the suffix for the group",
					"&anamecolor (nc) &e- &7Set the name color of players in the group",
					"&anameformat (nf) &e- &7Set the name format of players in the group",
					"&achatcolor (cc) &e- &7Set the chat color of players in the group",
					"&achatformat (cf) &e- &7Set the chat format of players in the group"
			};
			msg(sender, help);
		}
		else {
			PlayerData data;
			String player;
			Optional<SSPlayer> optional = SSPermissions.getAPI().getPlayerManager().getPlayerByName(args[1]);
			if (!optional.isPresent()) {
				Optional<SSOfflinePlayer> op = SSPermissions.getAPI().getPlayerManager().getOfflinePlayerByName(args[1]);
				if (!op.isPresent()) {
					msg(sender, "&ePlayer not found.");
					return;
				}
				else {
					data = op.get();
					player = args[1];
				}
			}
			else {
				data = optional.get();
				player = optional.get().getPlayer().getName();
			}
			if (args[2].equalsIgnoreCase("add") || args[2].equalsIgnoreCase("a")) {
				if (!data.getPermissions().add(args[3])) {
					msg(sender, "&ePlayer already has permission.");
				}
				else {
					msg(sender, "&aAdded &e" + args[3] + " &ato &b" + player + "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("remove") || args[2].equalsIgnoreCase("r")) {
				if (!data.getPermissions().remove(args[3])) {
					msg(sender, "&ePlayer does not have permission.");
				}
				else {
					msg(sender, "&aRemoved &e" + args[3] + " &afrom player &b" + player + "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("move") || args[2].equalsIgnoreCase("m")) {
				Optional<Group> o = SSPermissions.getAPI().getGroupManager().getGroup(args[3]);
				if (!o.isPresent()) {
					msg(sender, "&eGroup &b" + args[3] + " &enot found.");
					return;
				}
				Group g = o.get();
				if (!g.addPlayer(data)) {
					msg(sender, "&b" + player + " &eis already in &b" + g.getName() + "&e.");
				}
				else {
					if (SSPermissions.getAPI().getConfig().getBoolean("msgPlayerWhenMoved")) msg(data.id(), "&eYou were moved to group &b" + g.getName() + "&e.");
					msg(sender, "&b" + player + " &awas moved to &b" + g.getName() + "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("prefix") || args[2].equalsIgnoreCase("p")) {
				args[3] = args[3].replaceAll("(\\\\_)", String.valueOf(ChatColor.COLOR_CHAR)).replaceAll("_", " ").replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "_");
				if (!data.getSettings().setPrefix(args[3])) {
					msg(sender, "&eUnable to set player prefix.");
				}
				else {
					msg(sender, "&b" + player + "'s &aprefix set to &e" + args[3] + "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("suffix") || args[2].equalsIgnoreCase("s")) {
				args[3] = args[3].replaceAll("(\\\\_)", String.valueOf(ChatColor.COLOR_CHAR)).replaceAll("_", " ").replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "_");
				if (!data.getSettings().setSuffix(args[3])) {
					msg(sender, "&eUnable to set player suffix.");
				}
				else {
					msg(sender, "&b" + player + "'s &asuffix set to &e" + args[3] + "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("format") || args[2].equalsIgnoreCase("f")) {
				String[] trim = new String[args.length - 3];
				System.arraycopy(args, 3, trim, 0, trim.length);
				String format = String.join(" ", trim);
				if (!data.getSettings().setMessageFormat(format)) {
					msg(sender, "&eFormat must contain at least <player> and <msg> variables.");
				}
				else {
					msg(sender, "&aChat format for &b" + player + " &aupdated to &f" + Util.color(format));
				}
			}
			else if (args[2].equalsIgnoreCase("namecolor") || args[2].equalsIgnoreCase("nc")) {
				String f = args[3].replaceAll("[^a-fA-F0-9]", "");
				if (f.length() > 1) f = f.substring(0, 1);
				if (f.isEmpty()) {
					msg(sender, "&eEnter a color code. (a-f 0-9)");
					return;
				}
				ChatColor c = ChatColor.getByChar(f);
				if (!data.getSettings().setNameColor(c)) {
					msg(sender, "&b" + c.name() + " &eis not a color.");
				}
				else {
					msg(sender, "&aSet &b" + player + "'s &anamecolor to &e" + c.name()+ "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("nameformat") || args[2].equalsIgnoreCase("nf")) {
				String f = args[3].replaceAll("[^k-oK-OrR]", "");
				if (f.length() > 1) f = f.substring(0, 1);
				if (f.isEmpty()) {
					msg(sender, "&eEnter a format code. (k-o r)");
					return;
				}
				ChatColor c = ChatColor.getByChar(f);
				if (!data.getSettings().setNameFormat(c)) {
					msg(sender, "&b" + c.name() + " &eis not a format.");
				}
				else {
					msg(sender, "&aSet &b" + player + "'s &anameformat to &e" + c.name()+ "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("chatcolor") || args[2].equalsIgnoreCase("cc")) {
				String f = args[3].replaceAll("[^a-fA-F0-9]", "");
				if (f.length() > 1) f = f.substring(0, 1);
				if (f.isEmpty()) {
					msg(sender, "&eEnter a color code. (a-f 0-9)");
					return;
				}
				ChatColor c = ChatColor.getByChar(f);
				if (!data.getSettings().setChatColor(c)) {
					msg(sender, "&b" + c.name() + " &eis not a color.");
				}
				else {
					msg(sender, "&aSet &b" + player + "'s &achatcolor to &e" + c.name()+ "&a.");
				}
			}
			else if (args[2].equalsIgnoreCase("chatformat") || args[2].equalsIgnoreCase("cf")) {
				String f = args[3].replaceAll("[^k-oK-OrR]", "");
				if (f.length() > 1) f = f.substring(0, 1);
				if (f.isEmpty()) {
					msg(sender, "&eEnter a format code. (k-o r)");
					return;
				}
				ChatColor c = ChatColor.getByChar(f);
				if (!data.getSettings().setChatFormat(c)) {
					msg(sender, "&b" + c.name() + " &eis not a format.");
				}
				else {
					msg(sender, "&aSet &b" + player + "'s &achatformat to &e" + c.name()+ "&a.");
				}
			}
			else {
				msg(sender, "&eUnknown action.");
			}
		}
	}
	
	private void create(CommandSender sender, String[] args) {
		// /ssp create <name>
		if (!Util.hasAny(sender, Perms.ADMIN, Perms.ALL)) {
			noPerm(sender);
			return;
		}
		if (args.length < 2) {
			msg(sender, "&b/ssp create <name> &a- &7Create a new group.");
		}
		else {
			GroupManager manager = SSPermissions.getAPI().getGroupManager();
			if (manager.groupExists(args[1])) {
				msg(sender, "&eGroup already exists.");
				return;
			}
			Group group = manager.createGroup(args[1]);
			msg(sender, "&aGroup &b" + group.getName() + " &acreated.");
		}
	}
	
	private void remove(CommandSender sender, String[] args) {
		// /ssp remove <name>
		if (!Util.hasAny(sender, Perms.ADMIN, Perms.ALL)) {
			noPerm(sender);
			return;
		}
		if (args.length < 2) {
			msg(sender, "&b/ssp remove <name> &a- &7Remove a group.");
		}
		else {
			GroupManager manager = SSPermissions.getAPI().getGroupManager();
			if (!manager.groupExists(args[1])) {
				msg(sender, "&eGroup does not exist.");
				return;
			}
			if (!manager.removeGroup(args[1])) {
				msg(sender, "&eUnable to remove group.");
			}
			else {
				msg(sender, "&aGroup &b" + args[1] + " &aremoved.");
			}
		}
	}
	
	private void format(CommandSender sender, String[] args) {
		// /ssp format <format>
		if (!Util.hasAny(sender, Perms.ADMIN, Perms.ALL)) {
			noPerm(sender);
			return;
		}
		if (args.length < 2) {
			msg(sender, "&b/ssp format <format> &a- &7Change the chat format.");
		}
		else {
			String[] trim = new String[args.length - 1];
			System.arraycopy(args, 1, trim, 0, trim.length);
			String format = String.join(" ", trim);
			if (!SSPermissions.getAPI().setChatFormat(format)) {
				msg(sender, "&eFormat must contain at least <player> and <msg> variables.");
				return;
			}
			msg(sender, "&aChat format updated to &a" + Util.color(format));
		}
	}
	
	private void reload(CommandSender sender) {
		if (!Util.hasAny(sender, Perms.RELOAD, Perms.ALL)) {
			noPerm(sender);
			return;
		}
		msg(sender, "&bReloading...");
		SSPermissions.getAPI().reload();
		msg(sender, "&bFinished reloading.");
		
	}
}
