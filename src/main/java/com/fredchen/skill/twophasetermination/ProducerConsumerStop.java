package com.fredchen.skill.twophasetermination;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerStop {
	
	class SampleConsumer<P> {
		private final BlockingQueue<P> queue = new LinkedBlockingQueue<P>(10);

		private AbstractTerminatableThread workThread = new AbstractTerminatableThread() {
			@Override
			protected void doRun() throws Exception {
				terminationToken.reservations.decrementAndGet();
				P product = queue.take();
				System.err.println("消费："+product);
			}

		};

		public void placeProduct(P product) {
			if (workThread.terminationToken.isToShutdown()) {
				throw new IllegalStateException("Thread shutdown");
			}
			try {
				queue.put(product);
				workThread.terminationToken.reservations.incrementAndGet();
				System.err.println("生产："+product);
			} catch (InterruptedException e) {
				System.err.println("InterruptedException.........");
			}
		}

		public void shutdown() {
			workThread.terminate();
		}

		public void start() {
			workThread.start();
		}
	}

	public void test() {
		final SampleConsumer<String> aConsumer = new SampleConsumer<String>();

		AbstractTerminatableThread aProducer = new AbstractTerminatableThread() {
			private int i = new Random().nextInt(10);
			
			@Override
			protected void doRun() throws Exception {
				aConsumer.placeProduct(String.valueOf(i));
			}

			@Override
			protected void doCleanup(Exception cause) {
				// 生产者线程停止完毕后再请求停止消费者线程
				aConsumer.shutdown();
			}

		};

		aProducer.start();
		aConsumer.start();
	}
	
	public static void main(String[] args) {
		ProducerConsumerStop stop = new ProducerConsumerStop();
		stop.test();
	}
}
