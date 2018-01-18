package com.fredchen.skill.designs.factory.abstracts;

/**
 * 创建型
 * 抽象工厂模式，以产品族为基准:一系列产品
 * http://www.cnblogs.com/java-my-life/archive/2012/03/28/2418836.html
 * @author upgrade2004
 *
 */
public class Client {
	public static void main(String[] args) {
		// 创建装机工程师对象
		ComputerEngineer cf = new ComputerEngineer();
		// 客户选择并创建需要使用的产品对象
		AbstractFactory af = new IntelFactory();
		// 告诉装机工程师自己选择的产品，让装机工程师组装电脑
		cf.makeComputer(af);
	}
}
