package com.fredchen.skill.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CacheHealthChecker extends BaseHealthChecker {

	public CacheHealthChecker(CountDownLatch latch) {
		super("Cache Service", latch);
	}

	@Override
	public void verifyService() {
		System.out.println("Checking " + this.getServiceName());
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+":"+this.getServiceName() + " is UP");
	}

}
