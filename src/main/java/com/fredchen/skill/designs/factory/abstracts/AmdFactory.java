package com.fredchen.skill.designs.factory.abstracts;

import com.fredchen.skill.designs.factory.simple.Cpu;
import com.fredchen.skill.designs.factory.simple.IntelCpu;
import com.fredchen.skill.designs.factory.simple.IntelMainboard;
import com.fredchen.skill.designs.factory.simple.Mainboard;

public class AmdFactory implements AbstractFactory {

	@Override
	public Cpu createCpu() {
		return new IntelCpu(938);
	}

	@Override
	public Mainboard createMainboard() {
		return new IntelMainboard(938);
	}

}