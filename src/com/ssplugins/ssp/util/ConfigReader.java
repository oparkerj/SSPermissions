package com.ssplugins.ssp.util;

import java.util.List;

public interface ConfigReader {
	
	String getString(String path);
	
	boolean getBoolean(String path);
	
	List<String> getStringList(String path);
	
	Object get(String path);
	
}
