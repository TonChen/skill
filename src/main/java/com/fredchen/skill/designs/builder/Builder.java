package com.fredchen.skill.designs.builder;

/**
 * 抽象建造者（Builder）角色：给 出一个抽象接口，以规范产品对象的各个组成成分的建造。
 * 一般而言，此接口独立于应用程序的商业逻辑。模式中直接创建产品对象的是具体建造者 (ConcreteBuilder)角色。
 * 具体建造者类必须实现这个接口所要求的两种方法：一种是建造方法(buildPart1和 buildPart2)，另一种是返还结构方法(retrieveResult)。
 * 一般来说，产品所包含的零件数目与建造方法的数目相符。换言之，有多少 零件，就有多少相应的建造方法。
 * @author upgrade2004
 *
 */
public interface Builder {
	
	public void buildPart1();

	public void buildPart2();

	public Product retrieveResult();
}
