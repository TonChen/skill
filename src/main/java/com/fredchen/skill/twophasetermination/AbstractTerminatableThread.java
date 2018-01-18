package com.fredchen.skill.twophasetermination;

public abstract class AbstractTerminatableThread extends Thread implements Terminatable {
	public final TerminationToken terminationToken;

	public AbstractTerminatableThread() {
		super();
		this.terminationToken = new TerminationToken();
	}

	/**
	 * 
	 * @param terminationToken
	 * 线程间共享的线程终止标志实例
	 */
	public AbstractTerminatableThread(TerminationToken terminationToken) {
		super();
		this.terminationToken = terminationToken;
	}

	protected abstract void doRun() throws Exception;

	protected void doCleanup(Exception cause) {
	}

	protected void doTerminiate() {
		//调用目标线程的interrupt方法可以使一些阻塞方法（参见表1）通过抛出异常从而使目标线程停止。
		//但也有些阻塞方法如InputStream.read并不对interrupt方法调用作出响应，此时需要由TerminatableThread的子类实现doTerminiate方法，在该方法中实现一些关闭目标线程所需的额外操作。
		//例如，在Socket同步I/O中通过关闭socket使得使用该socket的线程若处于I/O等待会抛出SocketException。因此，terminate方法下一步调用doTerminate方法。
	}

	@Override
	public void run() {
		Exception ex = null;
		try {
			while (true) {
				/*
				 * 在执行线程的处理逻辑前先判断线程停止的标志。
				 */
				if (terminationToken.isToShutdown() && terminationToken.reservations.get() <= 0) {
					System.err.println("跳出循环......");
					break;
				}
				doRun();
			}
			
		} catch (Exception e) {
			// Allow the thread to terminate in response of a interrupt
			// invocation
			ex = e;
		} finally {
			doCleanup(ex);
		}
	}

	@Override
	public void interrupt() {
		terminate();
	}

	@Override
	public void terminate() {
		terminationToken.setToShutdown(true);
		try {
			doTerminiate();
		} finally {
			// 若无待处理的任务，则试图强制终止线程
			if (terminationToken.reservations.get() <= 0) {
				super.interrupt();
			}
			System.err.println("线程终止......");
		}
	}
}
