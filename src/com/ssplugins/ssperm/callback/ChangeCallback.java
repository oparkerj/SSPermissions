package com.ssplugins.ssperm.callback;

public interface ChangeCallback {
	
	boolean onChange(String key, String oldValue, String newValue);
	
	void afterChange();
	
}
