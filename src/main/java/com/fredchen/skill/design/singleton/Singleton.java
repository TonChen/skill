package com.fredchen.skill.design.singleton;

/**
 * 简单的单例模式,不适合多线程
 * @author upgrade2004
 *
 */
public class Singleton {
	//私有构造器，防止被实例化
	private Singleton(){}
	
	private volatile static Singleton singleton = null;
	
	/**
	 * 延迟加载
	 * @return
	 */
	public static Singleton getInstance(){
		if(null == singleton){
			singleton = new Singleton();
		}
		return singleton;
	}
	
	public static void main(String[] args) {
		System.out.println(getInstance() == singleton);
	}
	
	/**
	 * 同步1,在Java指令中创建对象和赋值操作是分开进行的，也就是说instance = new Singleton();语句是分两步执行的。
	 * 但是JVM并不保证这两个操作的先后顺序，也就是说有可能JVM会为新的Singleton实例分配空间，然后直接赋值给singleton成员，然后再去初始化这个Singleton实例。
	 * a>A、B线程同时进入了第一个if判断
	   b>A首先进入synchronized块，由于instance为null，所以它执行instance = new Singleton();
       c>由于JVM内部的优化机制，JVM先画出了一些分配给Singleton实例的空白内存，并赋值给instance成员（注意此时JVM没有开始初始化这个实例），然后A离开了synchronized块。
       d>B进入synchronized块，由于instance此时不是null，因此它马上离开了synchronized块并将结果返回给调用该方法的程序。
       e>此时B线程打算使用Singleton实例，却发现它没有被初始化，于是错误发生了。
	 * @return
	 */
	public static Singleton getInstance2(){
		if(null == singleton){
			synchronized(singleton) {
				if(null == singleton){
					singleton = new Singleton();
				}
			}
		}
		return singleton;
	}
	
	/**
	 * 同步2
	 * @return
	 */
	public static Singleton getInstance3(){
		if(null == singleton){
			synInit();
		}
		return singleton;
	}

	private static synchronized void synInit() {
		if(null == singleton){
			singleton = new Singleton();
		}
	}
	
	/**
	 * 内部类创建,私有构造器
	 * @author upgrade2004
	 *
	 */
	private static class SingletonFactory{
		private static Singleton singleton = new Singleton();
	}
	
	public static Singleton getInstance4(){
		return SingletonFactory.singleton;
	}
	
	
	
}
