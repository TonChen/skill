package com.fredchen.skill.designs.singleton;

/**
 * 单元素的枚举类型已经成为实现Singleton的最佳方法。
 * 使用枚举来实现单实例控制会更加简洁，而且无偿地提供了序列化机制，并由JVM从根本上提供保障，绝对防止多次实例化，是更简洁、高效、安全的实现单例的方式。
 * http://www.cnblogs.com/java-my-life/archive/2012/03/31/2425631.html
 * 
 * @author upgrade2004
 *
 */
public enum EnumSingle {

	INSTANCE;

	public void singletonOperation() {
		// TODO 功能处理
	}

}
