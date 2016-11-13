package com.ssplugins.ssperm.util;

import java.util.Optional;

public interface Unloadable {
	
	boolean isLoaded();
	
	Optional<?> refresh();
	
}
