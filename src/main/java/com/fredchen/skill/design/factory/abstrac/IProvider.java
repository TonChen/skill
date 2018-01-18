package com.fredchen.skill.design.factory.abstrac;

import test.my.design.factory.IFactory;

/**
 * 提供接口，供工厂方法实现
 * @author upgrade2004
 *
 */
public interface IProvider {
	IFactory provide();
}
