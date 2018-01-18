package com.fredchen.skill.designs.facade;

/**
 * 客户角色:通过调用Facede来完成要实现的功能。
 * 
 * @author upgrade2004
 *
 */
public class Client {
	public static void main(String[] args) {
		ModuleFacade facade = new ModuleFacade();
		facade.a1();
	}
}
