package com.fredchen.skill.designs.facade;

/**
 * 结构型 外观模式(门面模式) 定义一个ModuleFacade类可以有效地屏蔽内部的细节，免得客户端去调用Module类时，发现一些不需要它知道的方法。
 * 比如a2()和a3()方法就不需要让客户端知道，否则既暴露了内部的细节，又让客户端迷惑。对客户端来说，他可能还要去思考a2()、a3()方法用来干什么呢？
 * 其实a2()和a3()方法是内部模块之间交互的，原本就不是对子系统外部的，所以干脆就不要让客户端知道。
 * 
 * 门面角色：外观模式的核心。它被客户角色调用，它熟悉子系统的功能。内部根据客户角色的需求预定了几种功能的组合。
 * http://blog.csdn.net/jason0539/article/details/22775311
 * 
 * @author upgrade2004
 *
 */
public class ModuleFacade {
	ModuleA a = new ModuleA();
	ModuleB b = new ModuleB();
	ModuleC c = new ModuleC();

	/**
	 * 下面这些是A、B、C模块对子系统外部提供的方法
	 */
	public void a1() {
		a.a1();
	}

	public void b1() {
		b.b1();
	}

	public void c1() {
		c.c1();
	}
}
