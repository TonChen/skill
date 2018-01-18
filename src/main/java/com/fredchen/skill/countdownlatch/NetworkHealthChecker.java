package com.fredchen.skill.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class NetworkHealthChecker extends BaseHealthChecker {

	public NetworkHealthChecker(CountDownLatch latch) {
		super("NetWork Service", latch);
	}

	@Override
	public void verifyService() {
		System.out.println("Checking " + this.getServiceName());
		try {
			Thread.sleep(7000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+":"+this.getServiceName() + " is UP");
	}

}
