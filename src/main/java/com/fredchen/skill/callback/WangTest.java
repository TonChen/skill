package com.fredchen.skill.callback;

public class WangTest extends Wang {

	public WangTest(Li li) {
		super(li);
	}

	@Override
	public void result(String result) {
		System.out.println("不错..，"+result);
	}
	
}
