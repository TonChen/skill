package com.fredchen.skill.designs.prototype;

/**
 * 简单形式的原型模式
 * 	比如要创建的对象都差不多，而创建过程又麻烦时，原型模式就很有用。
 * （1）客户(Client)角色：客户类提出创建对象的请求。
 * （2）抽象原型(Prototype)角色：这是一个抽象角色，通常由一个Java接口或Java抽象类实现。此角色给出所有的具体原型类所需的接口。
 * （3）具体原型（Concrete Prototype）角色：被复制的对象。此角色需要实现抽象的原型角色所要求的接口。
 * 
 * 需要创建的原型对象数目较少而且比较固定的话，可以采取第一种形式。在这种情况下，原型对象的引用可以由客户端自己保存。
 * http://www.cnblogs.com/java-my-life/archive/2012/04/11/2439387.html
 * @author upgrade2004
 *
 */
public class Client {
	/**
	 * 持有需要使用的原型接口对象
	 */
	private Prototype prototype;

	/**
	 * 构造方法，传入需要使用的原型接口对象
	 */
	public Client(Prototype prototype) {
		this.prototype = prototype;
	}

	public void operation(Prototype example) {
		// 需要创建原型接口的对象
		Prototype copyPrototype = (Prototype) prototype.clone();
	}
}
