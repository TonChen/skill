package com.fredchen.skill.callback;

public abstract class Wang implements Listener {
	private Li li;

	public Wang(Li li) {
		this.li = li;
	}
	
	public void synAsk(){
		li.answer(Wang.this, "结果是：同步的 ");
	}
	
	public void ask(){
		new Thread(
			new Runnable() {
			@Override
			public void run() {
				li.answer(Wang.this, "结果是：异步的");
			}
		}).start();
		
		doSomething();
	}

	public void doSomething(){
		System.err.println("先其他任务。。。。。。。。");
	}
	
//	@Override
//	public void result(String result) {
//		System.out.println("不错，"+result);
//	}
	
	public abstract void result(String result);
}
