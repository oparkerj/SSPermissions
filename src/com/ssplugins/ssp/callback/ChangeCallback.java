package com.ssplugins.ssp.callback;

import com.ssplugins.ssp.util.Option;

public interface ChangeCallback {
	
	boolean onChange(Option option, String oldValue, String newValue);
	
	void afterChange(Option option, String oldValue, String newValue);
	
}
