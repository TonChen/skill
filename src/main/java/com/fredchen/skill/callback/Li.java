package com.fredchen.skill.callback;

public class Li {

	public void answer(Listener listener, String result) {
		System.out.println(result + "哈哈哈哈哈哈哈哈哈,开始处理了。。。。。");
		long start = System.currentTimeMillis();
		doSomething();
		long end = System.currentTimeMillis();
		System.out.println("花费时间："+(end - start)/1000);
		listener.result("已经完成！");
	}

	private void doSomething(){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
