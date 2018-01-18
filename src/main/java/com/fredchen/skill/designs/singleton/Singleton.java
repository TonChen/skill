package com.fredchen.skill.designs.singleton;

/**
 * Double Check 双重锁检查
 * 在java1.4及以前版本中，很多JVM对于volatile关键字的实现的问题，会导致“双重检查加锁”的失败，因此“双重检查加锁”机制只只能用在java5及以上的版本。
 * 这种情况下也可能会出现线程安全问题，当一个线程完成new操作，但是还没有赋值给isntance，此时另一个线程进来判断instance为空，又会重新new。
 * 
 * 由于volatile关键字可能会屏蔽掉虚拟机中一些必要的代码优化，所以运行效率并不是很高。
 * 因此一般建议，没有特别的需要，不要使用。也就是说，虽然可以使用“双重检查加锁”机制来实现线程安全的单例，但并不建议大量采用，可以根据情况来选用
 * http://www.cnblogs.com/java-my-life/archive/2012/03/31/2425631.html
 * 
 * @author upgrade2004
 *
 */
public class Singleton {
	private volatile static Singleton instance = null;

	private Singleton() {
	}

	public static Singleton getInstance() {
		if (instance == null) {
			synchronized (Singleton.class) {
				if (instance == null) {
					instance = new Singleton();
				}
			}
		}
		return instance;
	}
}
