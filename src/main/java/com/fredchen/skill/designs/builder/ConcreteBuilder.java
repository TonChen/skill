package com.fredchen.skill.designs.builder;

/**
 * 具体建造者（ConcreteBuilder）角色：担任这个角色的是与应用程序紧密相关的一些类，它们在应用程序调用下创建产品的实例。
 * 这个角色要完成的任务包括：1.实现抽象建造者Builder所声明的接口，给出一步一步地完成创建产品实例的操作。2.在建造过程完成后，提供产品的实例。
 * @author upgrade2004
 *
 */
public class ConcreteBuilder implements Builder {

	private Product product = new Product();

	/**
	 * 产品零件建造方法1
	 */
	@Override
	public void buildPart1() {
		// 构建产品的第一个零件
		product.setPart1("编号：9527");
	}

	/**
	 * 产品零件建造方法2
	 */
	@Override
	public void buildPart2() {
		// 构建产品的第二个零件
		product.setPart2("名称：XXX");
	}

	/**
	 * 产品返还方法
	 */
	@Override
	public Product retrieveResult() {
		return product;
	}

}
