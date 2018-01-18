package com.fredchen.skill.countdownlatch;//package test.my.countdownlatch;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.commons.lang.time.StopWatch;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//public class TestCountDown {
//	protected Log logger = LogFactory.getLog(getClass());
//	/**
//	 * @category 解析动态列表-for循环
//	 * @param userid
//	 * @param list
//	 * @return List<BroadcastData>
//	 */
//	public static List<BroadcastData> parseFeedData(int userid,
//			List<FeedData> list) {
//		List<BroadcastData> result = new ArrayList<BroadcastData>();
//
//		if (null != list) {
//			for (FeedData fdata : list) {
//				try {
//					BroadcastData bdata = parseFeedData(userid, fdata);
//					if ((null != bdata && !bdata.content.isEmpty())
//							|| (null != bdata.images && !bdata.images.isEmpty())
//							|| (null != bdata.videos && !bdata.videos.isEmpty())) {
//						result.add(bdata);
//					}
//				} catch (Exception e) {
//					logger.error("BroadcastHelper parseFeedData accurred an error:"
//							+ e);
//				}
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * @category 解析动态列表-CountDownLatch异步
//	 * @param userid
//	 * @param list
//	 * @return List<BroadcastData>
////	 * @throws ServerTimeoutException
//	 */
//	public static List<BroadcastData> parseFeedDataAsync(final int userid,
//			List<FeedData> list) throws ServerTimeoutException {
//		logger.info("parseFeedDataAsync called.");
//
//		StopWatch watch = new StopWatch();
//		watch.reset();
//		watch.start();
//
//		if (null != list) {
//			final CountDownLatch latch = new CountDownLatch(list.size());
//			final List<BroadcastData> data = Collections
//					.synchronizedList(new ArrayList<BroadcastData>(list.size()));
//			final ExecutorService threadPool = Executors.newFixedThreadPool(10);// 可指定list.size(),是否有必要，待验证
//			for (final FeedData fdata : list) {
//				try {
//					Runnable task = new Runnable() {
//						@Override
//						public void run() {
//							logger.info("parseFeedDataAsync sub thread run.");
//							BroadcastData bdata;
//							try {
//								bdata = parseFeedData(userid, fdata);
//								if ((null != bdata && !bdata.content.isEmpty())
//										|| (null != bdata.images && !bdata.images
//												.isEmpty())
//										|| (null != bdata.videos && !bdata.videos
//												.isEmpty())) {
//									data.add(bdata);
//								}
//							} catch (Exception e) {
//								logger.error("parseFeedDataAsync sub thread run error:"
//										+ e);
//							} finally {
//								latch.countDown();
//							}
//						}
//					};
//					// task.run();//用原始的线程跑，比用线程池要慢一倍
//					threadPool.submit(task);
//				} catch (Exception e) {
//					logger.error("parseFeedDataAsync occurred an error:" + e);
//				}
//			}
//			boolean bSuccess = false;
//			while (true) {
//				try {
//					bSuccess = latch.await(120L, TimeUnit.SECONDS);
//					threadPool.shutdown();
//					break;
//				} catch (InterruptedException e) {
//					logger.error("parseFeedDataAsync CountDownLatch await InterruptedException.");
//				}
//			}
//			if (!bSuccess)
//				throw new ServerTimeoutException(
//						"parseFeedDataAsync CountDownLatch timeout");
//
//			Collections.sort(data, new Comparator<BroadcastData>() {
//				public int compare(BroadcastData o1, BroadcastData o2) {
//					return o1.getBroadid() > o2.getBroadid() ? 1 : 0;
//				};
//			});
//			watch.stop();
//			logger.info(String.format(
//					"[# parseFeedDataAsync=> consume=%s/ms #]", watch.getTime()));
//			return data;
//		}
//		return null;
//	}
//}
