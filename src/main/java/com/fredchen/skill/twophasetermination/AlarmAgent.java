package com.fredchen.skill.twophasetermination;

public class AlarmAgent {

	// 省略其它代码
	private volatile boolean connectedToServer = false;

	public void sendAlarm(AlarmInfo alarm) throws Exception {
		// 省略其它代码
		System.out.println("Sending " + alarm);
		try {
			Thread.sleep(50);
		} catch (Exception e) {

		}
	}

	public void init() {
		// 省略其它代码
		connectedToServer = true;
	}

	public void disconnect() {
		// 省略其它代码
		System.out.println("disconnected from alarm server.");
	}

	public boolean waitUntilConnected() {
		// 省略其它代码
		return connectedToServer;
	}

}
