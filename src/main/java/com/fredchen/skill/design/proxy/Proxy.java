package com.fredchen.skill.design.proxy;

/**
 * 代理模式 代理模式就是多一个代理类出来，替原对象进行一些操作 如果已有的方法在使用的时候需要对原有的方法进行改进，此时有两种办法：
 * 1、修改原有的方法来适应。这样违反了“对扩展开放，对修改关闭”的原则。
 * 2、就是采用一个代理类调用原有的方法，且对产生的结果进行控制。这种方法就是代理模式。
 * 
 * @author upgrade2004
 * 
 */
public class Proxy implements Sourceable {
	private Sourceable source;

	public Proxy(Sourceable source) {
		this.source = source;
	}

	@Override
	public void method() {
		before();
		source.method();
		after();
	}

	public void before(){
		System.out.println("Proxy.before()");
	}
	
	public void after(){
		System.out.println("Proxy.after()");
	}
}
