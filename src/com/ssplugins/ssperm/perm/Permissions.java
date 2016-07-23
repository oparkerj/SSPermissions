package com.ssplugins.ssperm.perm;

import java.util.Set;

public interface Permissions {
	
	boolean add(String perm);
	
	boolean remove(String perm);
	
	boolean contains(String perm);
	
	Set<String> getAll();

}
