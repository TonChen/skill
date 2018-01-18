package com.fredchen.skill.designs.builder;


/**
 * 导演者（Director）角色：担任这个角色的类调用具体建造者角色以创建产品对象。
 * 应当指出的是，导演者角色并没有产品类的具体知识，真正拥有产品类的具体知识的是具体建造者角色。
 * 导演者角色是与客户端打交道的角色。导演者将客户端创建产品的请求划分为对各个零件的建造请求，再将这些请求委派给具体建造者角色。
 * 具体建造者角色是做具体建造工作的，但是却不为客户端所知。
 * @author upgrade2004
 *
 */
public class Director {
	/**
	 * 持有当前需要使用的建造器对象
	 */
	private Builder builder;

	/**
	 * 构造方法，传入建造器对象
	 * 
	 * @param builder
	 * 建造器对象
	 */
	public Director(Builder builder) {
		this.builder = builder;
	}

	/**
	 * 产品构造方法，负责调用各个零件建造方法
	 */
	public void construct() {
		builder.buildPart1();
		builder.buildPart2();
	}
}
