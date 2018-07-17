package com.ssplugins.ssp;

import com.ssplugins.ssp.callback.ChangeCallback;
import com.ssplugins.ssp.callback.ListCallback;
import com.ssplugins.ssp.callback.OptionCallback;
import com.ssplugins.ssp.util.Config;
import com.ssplugins.ssp.util.Option;
import com.ssplugins.ssp.util.Util;

abstract class PermissionHolder {
	
	private PermList permList;
	private Options options;
	
	private OptionCallback optionCallback = null;
	private ListCallback<String> permissionCallback = null;
	
	PermissionHolder(boolean group, String id) {
		Config config = (group ? Manager.getGroupsConfig() : Manager.getPlayer(id));
		permList = new PermList();
		permList.setCallback((item, add) -> {
			if (add) Util.addToList(config, Option.getPermissionsPath(id), item);
			else Util.removeFromList(config, Option.getPermissionsPath(id), item);
			AttMan attMan = Manager.get().getAttMan();
			if (group) attMan.groupUpdate(id, item, add);
			else attMan.playerUpdate(id, item, add);
			if (permissionCallback != null) permissionCallback.onUpdate(item, add);
		});
		options = new Options();
		options.setCallback(new ChangeCallback() {
			@Override
			public boolean onChange(Option option, String oldValue, String newValue) {
				if (newValue == null) newValue = Util.getConfigNull(option);
				if (!Util.verify(option, newValue)) return false;
				config.set(Option.getOptionPath(id, option), newValue);
				return true;
			}
			
			@Override
			public void afterChange(Option option, String oldValue, String newValue) {
				if (optionCallback != null) optionCallback.onCall(option, oldValue, newValue);
			}
		});
		
		if (config.contains(Option.getPermissionsPath(id))) config.getConfig().getStringList(Option.getPermissionsPath(id)).forEach(permList::addSilent);
		else if (group) config.setDefault(Option.getPermissionsPath(id), Util.noneList());
		Util.loadOptions(options, config, id);
		if (group) config.setDefault(Option.getInheritsPath(id), Util.noneList());
		config.save();
	}
	
	void unloadCallbacks() {
		permList.setCallback(null);
		options.setCallback(null);
	}
	
	void setOptionCallback(OptionCallback optionCallback) {
		this.optionCallback = optionCallback;
	}
	
	void setPermissionCallback(ListCallback<String> callback) {
		this.permissionCallback = callback;
	}
	
	PermList getPermList() {
		return permList;
	}
	
	Options getOptions() {
		return options;
	}
}
