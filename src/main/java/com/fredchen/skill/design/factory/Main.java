package com.fredchen.skill.design.factory;

import com.fredchen.skill.design.factory.abstrac.IProvider;
import com.fredchen.skill.design.factory.abstrac.ProviderA;

public class Main {
	public static void main(String[] args) {
		send("B").send();//普通工厂方法
		new Main().produceA().send();//多个工厂方法
		produceAFA().send();//多个工厂方法
		
		IProvider provider = new ProviderA();//抽象工厂方法,如果你现在想增加一个功能：发及时信息，则只需做一个实现类，实现Sender接口，同时做一个工厂类，实现Provider接口，就OK了，无需去改动现成的代码。这样做，拓展性较好！
		provider.provide().send();
		
//		String passwordAfter = PasswordHelper.encryptPasswordWithSalt(null, "cfth");
//		System.out.println(passwordAfter);
	}
	
	/**
	 * 普通工厂方法
	 * @param flag
	 * @return
	 */
	public static IFactory send(String flag){
		if("A".equals(flag)){
			return new FactoryMethodA();
		}else if("B".equals(flag)){
			return new FactoryMethodB();
		}else{
			return null;
		}
	}
	
	/**
	 * 多个工厂方法
	 * @return
	 */
	public IFactory produceA(){
		return new FactoryMethodA();
	}
	
	public IFactory produceB(){
		return new FactoryMethodB();
	}
	
	/**
	 * 静态工厂方法
	 * @return
	 */
	public static IFactory produceAFA(){
		return new FactoryMethodA();
	}
	
	public static IFactory produceFB(){
		return new FactoryMethodB();
	}
	
}
