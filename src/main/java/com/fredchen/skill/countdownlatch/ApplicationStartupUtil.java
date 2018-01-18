package com.fredchen.skill.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ApplicationStartupUtil {
	private static List<BaseHealthChecker> _services;
	private static CountDownLatch _latch;
	//private static final ApplicationStartupUtil INSTANCE = new ApplicationStartupUtil();

	private ApplicationStartupUtil() {
	}

	public static boolean checkExternalServices() throws Exception {
		_latch = new CountDownLatch(_services.size());
		_services = new ArrayList<BaseHealthChecker>();
		_services.add(new NetworkHealthChecker(_latch));
		_services.add(new CacheHealthChecker(_latch));
		_services.add(new DatabaseHealthChecker(_latch));

		Executor executor = Executors.newFixedThreadPool(_services.size());
		for (final BaseHealthChecker v : _services) {
			executor.execute(v);
		}
		_latch.await();
		for (final BaseHealthChecker v : _services) {
			if (!v.isServiceUp()) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		boolean result = false;
		try {
			result = ApplicationStartupUtil.checkExternalServices();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+":External services validation completed !! Result was :: " + result);
	}
}
