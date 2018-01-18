package com.fredchen.skill.design.factory;

/**
 * 设计模式之工厂方法----普通工厂模式
 * @author upgrade2004
 *
 */
public class FactoryMethodA implements IFactory{

	@Override
	public void send() {
		System.out.println("FactoryMethodA.send()");
	}
	
}
