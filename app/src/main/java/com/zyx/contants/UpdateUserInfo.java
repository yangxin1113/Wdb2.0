package com.zyx.contants;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserInfo {
	public static UpdateUserInfo instance;
	private Map<String, onUpdateUserInfo> onUpdateUserInfoMap = new HashMap<String, onUpdateUserInfo>();

	public static UpdateUserInfo getInstance() {
		if (instance == null)
			instance = new UpdateUserInfo();
		return instance;
	}

	private UpdateUserInfo() {

	}

	public void notifyUserInfo(Map<String, Object> userInfo) {
		if (onUpdateUserInfoMap != null) {
			for (String key : onUpdateUserInfoMap.keySet()) {
				onUpdateUserInfoMap.get(key).notifyUserInfo(userInfo);
			}
		}
	}

	public void addOnUpdateUserInfo(String key, onUpdateUserInfo l) {
		onUpdateUserInfoMap.put(key, l);
	}

	public void remove(String key) {
		onUpdateUserInfoMap.remove(key);
	}

	public interface onUpdateUserInfo {
		public void notifyUserInfo(Map<String, Object> userInfo);
	}
}
