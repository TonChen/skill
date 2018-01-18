package com.fredchen.skill.designs.adapter.object;

import test.my.designs.adapter.clazz.Adaptee;

/**
 * 结构型
 * 对象适配器
 * 与类的适配器模式一样，对象的适配器模式把被适配的类的API转换成为目标类的API，与类的适配器模式不同的是，对象的适配器模式不是使用继承关系连接到Adaptee类，而是使用委派关系连接到Adaptee类。
 * http://www.cnblogs.com/java-my-life/archive/2012/04/13/2442795.html
 * 
 * @author upgrade2004
 *
 */
public class Adapter {
	private Adaptee adaptee;

	public Adapter(Adaptee adaptee) {
		this.adaptee = adaptee;
	}

	/**
	 * 源类Adaptee有方法sampleOperation1 因此适配器类直接委派即可
	 */
	public void sampleOperation1() {
		this.adaptee.sampleOperation1();
	}

	/**
	 * 源类Adaptee没有方法sampleOperation2 因此由适配器类需要补充此方法
	 */
	public void sampleOperation2() {
		// 写相关的代码
	}
}
