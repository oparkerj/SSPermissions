package com.ssplugins.ssperm.callback;

public interface ListCallback<T> {
	
	void onUpdate(T item, boolean add);
	
}
