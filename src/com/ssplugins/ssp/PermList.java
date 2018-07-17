package com.ssplugins.ssp;

import com.ssplugins.ssp.callback.Callback;
import com.ssplugins.ssp.callback.ListCallback;
import com.ssplugins.ssp.perm.Permissions;
import com.ssplugins.ssp.util.Util;

import java.util.*;

class PermList implements Permissions {

	private Set<String> set = new HashSet<>();
	
	private ListCallback<String> callback = null;

	PermList() {}
	
	public PermList(PermList other) {
		set.addAll(other.rawSet());
	}
	
	void setCallback(ListCallback<String> callback) {
		this.callback = callback;
	}
	
	void combine(Set<String> other) {
		set.addAll(other);
	}

	Set<String> tempCombine(Set<String> other) {
		Set<String> l = new HashSet<>(set);
		l.addAll(other);
		return l;
	}
	
	private Set<String> rawSet() {
		return set;
	}
	
	void addSilent(String perm) {
		if (perm.equalsIgnoreCase(Util.getNone())) return;
		set.add(perm);
	}

	@Override
	public boolean add(String perm) {
		if (perm.equalsIgnoreCase(Util.getNone())) return false;
		if (set.add(perm)) {
			Callback.handle(callback, perm, true);
			return true;
		}
		else return false;
	}

	@Override
	public boolean remove(String perm) {
		if (set.remove(perm)) {
			Callback.handle(callback, perm, false);
			return true;
		}
		else return false;
	}

	@Override
	public boolean contains(String perm) {
		return set.contains(perm);
	}

	@Override
	public Set<String> getAll() {
		return new HashSet<>(set);
	}
	
	@Override
	public void set(List<String> perms) {
		for (Iterator<String> it = set.iterator(); it.hasNext();) {
			String perm = it.next();
			if (!perms.contains(perm)) {
				Callback.handle(callback, perm, false);
				it.remove();
			}
		}
		perms.forEach(s -> {
			if (contains(s)) return;
			add(s);
		});
	}
	
	@Override
	public int length() {
		return set.size();
	}
}
