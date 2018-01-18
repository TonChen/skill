package com.fredchen.skill.designs.factory.simple;

/**
 * 创建型
 * 简单工厂模式
 * http://www.cnblogs.com/java-my-life/archive/2012/03/28/2418836.html
 * @author upgrade2004
 *
 */
public class Client {
	public static void main(String[] args) {
		ComputerEngineer cf = new ComputerEngineer();
		cf.makeComputer(1, 1);
	}
}
