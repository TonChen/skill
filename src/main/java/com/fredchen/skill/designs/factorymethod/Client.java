package com.fredchen.skill.designs.factorymethod;

/**
 * 建造型
 * 工厂方法模式的核心是一个抽象工厂类，而简单工厂模式把核心放在一个具体类上。
 * http://www.cnblogs.com/java-my-life/archive/2012/03/25/2416227.html
 * @author upgrade2004
 *
 */
public class Client {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String data = "";
		ExportFactory exportFactory = new ExportHtmlFactory();
		ExportFile ef = exportFactory.factory("financial");
		ef.export(data);
	}
}
