package com.fredchen.skill.design.proxy;

public class Source implements Sourceable {

	@Override
	public void method() {
		System.out.println("Source.method()");
	}

}
