package com.fredchen.skill.design.proxy;


public class Main {
	public static void main(String[] args) {
		Sourceable source = new Source();
		Sourceable sourceable = new Proxy(source);
		sourceable.method();
	}
}
