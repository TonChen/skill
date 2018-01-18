package com.fredchen.skill.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch，一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 * 
 * @author upgrade2004
 *
 */
public class Task implements Runnable {
	private static final CountDownLatch stopLatch = new CountDownLatch(500);
	final static ExecutorService threadPool = Executors.newFixedThreadPool(500);
	static long start = 0l;

	@Override
	public void run() {
		// System.out.println("=======:"+Thread.currentThread().getName() +
		// ":"+stopLatch.getCount());
		try {
			stopLatch.countDown();
			System.out.println("当前计数：" + stopLatch.getCount());
			stopLatch.await();
		} catch (InterruptedException e) {
			System.out.println(e.getStackTrace());
			e.printStackTrace();
		} finally {
			// stopLatch.countDown();
		}
		if (stopLatch.getCount() == 0) {
			System.out.println(String.format("[# parseFeedDataAsync=> consume=%s/ms #]", "name"));
			System.out.println("==========耗时：" + (System.currentTimeMillis() - start));
		}
		// System.out.println("+++++++:"+Thread.currentThread().getName() +
		// ":"+stopLatch.getCount());
	}

	public static void main(String[] args) throws InterruptedException {
		start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
//			new Thread(new Task()).start();
			 threadPool.submit(new Task());
		}
		 //stopLatch.countDown();
		stopLatch.await();//主线程等待
		System.out.println("end:" + stopLatch.getCount());
//		boolean b1 = true;
//		boolean b2 = true;
//		if(b1 & b2 ){
//			System.err.println(2 << 3);
//		}else{
//			System.err.println(b1 & b2);
//		}
	}
}
