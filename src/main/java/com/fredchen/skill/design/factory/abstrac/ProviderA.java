package com.fredchen.skill.design.factory.abstrac;

import test.my.design.factory.FactoryMethodA;
import test.my.design.factory.IFactory;

public class ProviderA implements IProvider{

	@Override
	public IFactory provide() {
		System.out.println("ProviderA.provide()");
		return new FactoryMethodA();
	}

}
