package com.fredchen.skill.design.decorator;

public class Main {
	public static void main(String[] args) {
		Sourceable source = new Source();
		Sourceable sourceable = new Decorator(source);
		sourceable.method();
	}
}
