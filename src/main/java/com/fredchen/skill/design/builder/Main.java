package com.fredchen.skill.design.builder;

public class Main {
	public static void main(String[] args) {
		Builder builder = new Builder();
		builder.produceMailSender(2);
		builder.produceSmsSender(1);
		System.out.println(builder.getSenders());
	}
}
