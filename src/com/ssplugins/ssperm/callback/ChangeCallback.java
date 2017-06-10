package com.ssplugins.ssperm.callback;

import com.ssplugins.ssperm.util.Option;

public interface ChangeCallback {
	
	boolean onChange(Option option, String oldValue, String newValue);
	
	void afterChange(Option option, String oldValue, String newValue);
	
}
