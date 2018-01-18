package com.fredchen.skill.designs.factory.simple;

/**
 * 主板 工厂
 * @author upgrade2004
 *
 */
public class MainboardFactory {
	public static Mainboard createMainboard(int type) {
		Mainboard mainboard = null;
		if (type == 1) {
			mainboard = new IntelMainboard(755);
		} else if (type == 2) {
			mainboard = new AmdMainboard(938);
		}
		return mainboard;
	}
}
