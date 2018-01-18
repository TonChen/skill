package com.fredchen.skill.designs.prototype.another;

public interface Prototype {
	public Prototype clone();

	public String getName();

	public void setName(String name);
}
