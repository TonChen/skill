package com.fredchen.skill.design.singleton;

import java.util.Vector;

/**
 * 采用"影子实例"的办法为单例对象的属性同步更新
 * @author upgrade2004
 *
 */
public class GlobalConfig {
	private static GlobalConfig instance = null;
	private Vector<?> properties = null;

	private GlobalConfig() {
		// Load configuration information from DB or file
		// Set values for properties
	}

	private static synchronized void syncInit() {
		if (instance == null) {
			instance = new GlobalConfig();
		}
	}

	public static GlobalConfig getInstance() {
		if (instance == null) {
			syncInit();
		}
		return instance;
	}

	public Vector<?> getProperties() {
		return properties;
	}

	public void updateProperties() {
		// Load updated configuration information by new a GlobalConfig object
		GlobalConfig shadow = new GlobalConfig();
		properties = shadow.getProperties();
	}

	public static void main(String[] args) {
		getInstance().updateProperties();
		getInstance().getProperties();
	}
}