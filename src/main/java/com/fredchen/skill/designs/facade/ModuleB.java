package com.fredchen.skill.designs.facade;

/**
 * 子系统角色:实现了子系统的功能。它对客户角色和Facade时未知的。它内部可以有系统内的相互交互，也可以由供外界调用的接口。
 * 
 * @author upgrade2004
 *
 */
public class ModuleB {
	/**
	 * 提供给子系统外部使用的方法
	 */
	public void b1() {
	};

	/**
	 * 子系统内部模块之间相互调用时使用的方法
	 */
	private void b2() {
	};

	private void b3() {
	};
}
