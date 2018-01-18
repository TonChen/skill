package com.fredchen.skill.designs.factory.abstracts;

import test.my.designs.factory.simple.Cpu;
import test.my.designs.factory.simple.IntelCpu;
import test.my.designs.factory.simple.IntelMainboard;
import test.my.designs.factory.simple.Mainboard;

public class IntelFactory implements AbstractFactory {

	@Override
	public Cpu createCpu() {
		return new IntelCpu(755);
	}

	@Override
	public Mainboard createMainboard() {
		return new IntelMainboard(755);
	}

}