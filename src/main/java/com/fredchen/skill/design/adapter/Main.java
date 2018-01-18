package com.fredchen.skill.design.adapter;

public class Main {
	public static void main(String[] args) {
		Targetable targetable = new Adapter();
		targetable.method1();
		targetable.method2();
		
		Targetable targetable1 = new SourceA();
		Targetable targetable2 = new SourceB();
		targetable1.method1();
		targetable1.method2();
		
		targetable2.method1();
		targetable2.method2();
	}
}
