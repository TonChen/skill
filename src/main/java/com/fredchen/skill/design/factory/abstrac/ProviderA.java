package com.fredchen.skill.design.factory.abstrac;

import com.fredchen.skill.design.factory.FactoryMethodA;
import com.fredchen.skill.design.factory.IFactory;

public class ProviderA implements IProvider{

	@Override
	public IFactory provide() {
		System.out.println("ProviderA.provide()");
		return new FactoryMethodA();
	}

}
