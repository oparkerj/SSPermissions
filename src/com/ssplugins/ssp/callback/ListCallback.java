package com.ssplugins.ssp.callback;

public interface ListCallback<T> {
	
	void onUpdate(T item, boolean add);
	
}
