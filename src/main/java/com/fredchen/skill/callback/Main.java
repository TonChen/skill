package com.fredchen.skill.callback;

public class Main {
	public static void main(String[] args) {
		Li li = new Li();
		Wang wang = new WangTest(li);
		wang.synAsk();
		wang.ask();
	}
}
