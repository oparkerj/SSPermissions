package com.ssplugins.ssperm.perm;

import com.ssplugins.ssperm.callback.ListCallback;
import com.ssplugins.ssperm.util.Config;
import com.ssplugins.ssperm.util.Util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface Permissions {
	
	boolean add(String perm);
	
	boolean remove(String perm);
	
	boolean contains(String perm);
	
	Set<String> getAll();
	
	void set(List<String> perms);
	
	int length();
	
	static Permissions temp(String id, Config config, ListCallback<String> callback) {
		return new Permissions() {
			@Override
			public boolean add(String perm) {
				boolean b = Util.addToList(config, id + ".permissions", perm);
				callback.onUpdate(perm, true);
				return b;
			}
			
			@Override
			public boolean remove(String perm) {
				boolean b = Util.removeFromList(config, id + ".permissions", perm);
				callback.onUpdate(perm, false);
				return b;
			}
			
			@Override
			public boolean contains(String perm) {
				return Util.getList(config, id + ".permissions").contains(perm);
			}
			
			@Override
			public Set<String> getAll() {
				return new HashSet<>(Util.getList(config, id + ".permissions"));
			}
			
			@Override
			public void set(List<String> perms) {
				for (Iterator<String> it = getAll().iterator(); it.hasNext();) {
					String perm = it.next();
					if (!perms.contains(perm)) remove(perm);
				}
				perms.forEach(s -> {
					if (contains(s)) return;
					add(s);
				});
			}
			
			@Override
			public int length() {
				return getAll().size();
			}
		};
	}

}
