package com.ssplugins.ssperm.cmd;

import com.ssplugins.ssperm.Perms;
import com.ssplugins.ssperm.SSPerm;
import com.ssplugins.ssperm.perm.Group;
import com.ssplugins.ssperm.perm.SSPlayer;
import com.ssplugins.ssperm.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainTabCompleter implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> matches = new ArrayList<>();
		if (args.length == 0) return matches;
		StringUtil.copyPartialMatches(args[args.length - 1], getList(args.length, args, sender), matches);
		return matches;
	}
	
	private List<String> getList(int index, String[] args, CommandSender sender) {
		if (index == 1) return getSubcommands(sender);
		else if (index == 2) {
			switch (args[0].toLowerCase()) {
				case "remove":
				case "r":
					if (!Util.hasAny(sender, Perms.ADMIN, Perms.ALL)) break;
				case "group":
				case "g":
					if (!Util.hasAny(sender, Perms.GROUPS, Perms.MANAGE, Perms.ALL)) break;
					return Stream.concat(SSPerm.getAPI().getGroupManager().getGroups().stream().map(Group::getName), Stream.of("default")).collect(Collectors.toList());
				case "player":
				case "p":
					if (!Util.hasAny(sender, Perms.PLAYERS, Perms.MANAGE, Perms.ALL)) break;
					return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
			}
		}
		else if (index == 3) {
			switch (args[0].toLowerCase()) {
				case "group":
				case "g":
					if (!Util.hasAny(sender, Perms.GROUPS, Perms.MANAGE, Perms.ALL)) break;
					return Arrays.asList("add", "remove", "inherit", "uninherit", "prefix", "suffix", "format", "namecolor", "nameformat", "chatcolor", "chatformat");
				case "player":
				case "p":
					if (!Util.hasAny(sender, Perms.PLAYERS, Perms.MANAGE, Perms.ALL)) break;
					return Arrays.asList("add", "remove", "move", "prefix", "suffix", "format", "namecolor", "nameformat", "chatcolor", "chatformat");
			}
		}
		else if (index == 4) {
			if (!Util.hasAny(sender, Perms.PLAYERS, Perms.GROUPS, Perms.MANAGE, Perms.ALL)) return new ArrayList<>();
			switch (args[2].toLowerCase()) {
				case "namecolor":
				case "nc":
				case "chatcolor":
				case "cc":
					return Arrays.asList("a", "b", "c", "d", "e", "f", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
				case "nameformat":
				case "nf":
				case "chatformat":
				case "cf":
					return Arrays.asList("k", "l", "m", "n", "o", "r");
				case "remove":
				case "r":
					return getPermissions(args[1], args[0].equalsIgnoreCase("group") || args[0].equalsIgnoreCase("g"));
				case "inherit":
				case "i":
				case "uninherit":
				case "u":
				case "move":
				case "m":
					return SSPerm.getAPI().getGroupManager().getGroups().stream().map(Group::getName).collect(Collectors.toList());
			}
		}
		return new ArrayList<>();
	}
	
	private List<String> getPermissions(String name, boolean group) {
		if (group) {
			Optional<Group> op = SSPerm.getAPI().getGroupManager().getGroup(name);
			if (op.isPresent()) return new ArrayList<>(op.get().getPermissions().getAll());
		}
		else {
			Optional<SSPlayer> op = SSPerm.getAPI().getPlayerManager().getPlayerByName(name);
			if (op.isPresent()) return new ArrayList<>(op.get().getPermissions().getAll());
		}
		return new ArrayList<>();
	}
	
	private List<String> getSubcommands(CommandSender sender) {
		List<String> list = new ArrayList<>();
		boolean manage = sender.hasPermission(Perms.MANAGE);
		boolean all = sender.hasPermission(Perms.ALL);
		if (all) manage = true;
		list.add("help");
		if (sender.hasPermission(Perms.GROUPS) || manage) list.add("list");
		if (sender.hasPermission(Perms.GROUPS) || manage) list.add("group");
		if (sender.hasPermission(Perms.PLAYERS) || manage) list.add("player");
		if (sender.hasPermission(Perms.RELOAD)) list.add("reload");
		if (sender.hasPermission(Perms.ADMIN) || all) {
			list.add("create");
			list.add("remove");
			list.add("format");
		}
		return list;
	}
}
