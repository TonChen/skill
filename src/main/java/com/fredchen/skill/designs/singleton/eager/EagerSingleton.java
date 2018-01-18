package com.fredchen.skill.designs.singleton.eager;

/**
 * 饿汉单例模式 饿汉式是典型的空间换时间，当类装载的时候就会创建类的实例，不管你用不用，先创建出来，然后每次调用的时候，就不需要再判断，节省了运行时间。
 * http://www.cnblogs.com/java-my-life/archive/2012/03/31/2425631.html
 * 
 * @author upgrade2004
 *
 */
public class EagerSingleton {
	private static EagerSingleton instance = new EagerSingleton();

	private EagerSingleton() {
	}

	public static EagerSingleton getInstance() {
		return instance;
	}
}
