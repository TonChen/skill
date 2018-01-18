package com.fredchen.skill.design.bridge;

public class Main {
	public static void main(String[] args) {
		Sourceable source = new Source2();
		method(source);
	}

	public static void method(Sourceable source) {
		Bridge bridge = new MyBridge();
		bridge.setSource(source);
		bridge.method();
	}
}
