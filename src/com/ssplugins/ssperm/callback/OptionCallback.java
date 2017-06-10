package com.ssplugins.ssperm.callback;

import com.ssplugins.ssperm.util.Option;

public interface OptionCallback {
	
	void onCall(Option option, String oldValue, String newValue);
	
}
