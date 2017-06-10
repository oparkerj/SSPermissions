package com.ssplugins.ssperm.util;

import java.util.Optional;

public interface Unloadable<T> {
	
	boolean isLoaded();
	
	Optional<T> refresh();
	
}
