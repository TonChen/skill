package com.fredchen.skill.design.facade;

public class Computer {
	private CPU cpu;
	private Disk disk;
	private Memory memory;
	
	public Computer(){}
	
	public Computer(CPU cpu, Disk disk, Memory memory){
		this.cpu = cpu;
		this.disk = disk;
		this.memory = memory;
	}
	
	
	
	public void startup() {
		System.out.println("Computer.startup()");
		cpu.startup();
		memory.startup();
		disk.startup();
		System.out.println("Computer.startup.completed.");
	}

	public void shutdown() {
		System.out.println("Computer.shutdown()");
		cpu.shutdown();
		memory.shutdown();
		disk.shutdown();
		System.out.println("Computer.shutdown.finished");
	}
}
