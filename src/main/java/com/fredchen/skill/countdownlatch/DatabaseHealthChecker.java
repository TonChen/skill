package com.fredchen.skill.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class DatabaseHealthChecker extends BaseHealthChecker {

	public DatabaseHealthChecker(CountDownLatch latch) {
		super("DB Service", latch);
	}

	@Override
	public void verifyService() {
		System.out.println("Checking " + this.getServiceName());
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+":"+this.getServiceName() + " is UP");
	}

}
