package com.ssplugins.ssp.callback;

import com.ssplugins.ssp.util.Option;

public interface OptionCallback {
	
	void onCall(Option option, String oldValue, String newValue);
	
}
