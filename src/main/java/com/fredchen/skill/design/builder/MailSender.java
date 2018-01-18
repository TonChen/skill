package com.fredchen.skill.design.builder;

public class MailSender implements ISender{

	@Override
	public void send() {
		System.out.println("MailSender.send()");
	}
	
}
