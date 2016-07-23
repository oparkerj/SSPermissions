package com.ssplugins.ssperm;

import com.ssplugins.ssperm.perm.Group;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class AttMan {
	
	private Manager manager;
	
	private Map<String, PermissionAttachment> map = new HashMap<>();
	
	AttMan(Manager manager) {
		this.manager = manager;
	}
	
	void clean() {
		map.keySet().forEach(this::remove);
	}
	
	void remove(String id) {
		PermissionAttachment att = map.get(id);
		if (att != null) att.remove();
		map.remove(id);
	}
	
	private PermissionAttachment get(String id) {
		return map.get(id);
	}
	
	private boolean value(String perm) {
		return !perm.startsWith("-");
	}
	
	void setup(Player player) {
		PermissionAttachment att = player.addAttachment(SSPerm.get());
		manager.getPlayerManager().getPlayer(player).getAllPermissions().forEach(s -> att.setPermission(s, value(s)));
		map.put(player.getUniqueId().toString(), att);
	}
	
	void groupUpdate(String name, String perm, boolean add) {
		Optional<Group> optional = manager.getGroupManager().getGroup(name);
		if (!optional.isPresent()) return;
		Group group = optional.get();
		group.getPlayers().forEach(s -> playerUpdate(s, perm, add));
	}
	
	void playerUpdate(String id, String perm, boolean add) {
		PermissionAttachment att = get(id);
		if (att == null) return;
		if (add) att.setPermission(perm, value(perm));
		else att.unsetPermission(perm);
	}
	
	void playerSet(Player player, Group group) {
		PermissionAttachment att = get(player.getUniqueId().toString());
		att.getPermissions().forEach((s, set) -> att.unsetPermission(s));
		group.getAllPermissions().forEach(s -> att.setPermission(s, value(s)));
	}
}
