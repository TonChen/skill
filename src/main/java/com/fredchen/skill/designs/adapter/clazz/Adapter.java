package com.fredchen.skill.designs.adapter.clazz;

/**
 * 结构型
 * 类适配器模式 适配器类是本模式的核心。适配器把源接口转换成目标接口。显然，这一角色不可以是接口，而必须是具体类。
 * Adaptee类并没有sampleOperation2()方法，而客户端则期待这个方法。为使客户端能够使用Adaptee类，提供一个中间环节，即类Adapter，把Adaptee的API与Target类的API衔接起来。
 * http://www.cnblogs.com/java-my-life/archive/2012/04/13/2442795.html
 * 
 * @author upgrade2004
 *
 */
public class Adapter extends Adaptee implements Target {

	@Override
	public void sampleOperation2() {
		// TODO
		System.err.println("11111111");
	}
	
}
