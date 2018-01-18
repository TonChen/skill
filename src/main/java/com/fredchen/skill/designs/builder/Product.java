package com.fredchen.skill.designs.builder;

/**
 * 产品（Product）角色：产品便是建造中的复杂对象。一般来说，一个系统中会有多于一个的产品类，而且这些产品类并不一定有共同的接口，而完全可以是不相关联的。
 * @author upgrade2004
 *
 */
public class Product {
	/**
	 * 定义一些关于产品的操作
	 */
	private String part1;
	private String part2;

	public String getPart1() {
		return part1;
	}

	public void setPart1(String part1) {
		this.part1 = part1;
	}

	public String getPart2() {
		return part2;
	}

	public void setPart2(String part2) {
		this.part2 = part2;
	}
}
