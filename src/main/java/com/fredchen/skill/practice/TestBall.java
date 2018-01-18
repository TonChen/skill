package com.fredchen.skill.practice;

/**
 * 一球从100米高度自由落下，每次落地后反跳回原高度的一半；再落下，求它在 第10次落地时，共经过多少米？第10次反弹多高？ 
 * @author upgrade2004
 *
 */
public class TestBall {
	public static void main(String[] args) {
		int count = 10;//反弹次数
		double totalHeight = 100;
		getHeight(count, totalHeight);
	}
	
	public static void getHeight(int count, double totalHeight){
		int totalLegth = -100;
		double nowHeight = totalHeight;
		for (int i = 0; i < count; i++) {
			totalLegth += nowHeight * 2;
			nowHeight = nowHeight / 2;
		}
		System.out.println("第"+ count +"次下落高度为："+nowHeight+"米。经过："+totalLegth+"米。");
	}
}
