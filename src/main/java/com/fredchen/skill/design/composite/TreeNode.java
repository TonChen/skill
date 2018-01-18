package com.fredchen.skill.design.composite;

import java.util.Enumeration;
import java.util.Vector;

/**
 * 组合模式 将多个对象组合在一起进行操作，常用于表示树形结构中，例如二叉树等。
 * 
 * @author upgrade2004
 * 
 */
public class TreeNode {
	private String name;
	private TreeNode parent;
	private Vector<TreeNode> children = new Vector<TreeNode>();

	public TreeNode(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public void add(TreeNode treeNode) {
		children.add(treeNode);
	}

	public void remove(TreeNode treeNode) {
		children.remove(treeNode);
	}

	public Enumeration<TreeNode> getChildren() {
		return children.elements();
	}

	@Override
	public String toString() {
		return "TreeNode [name=" + name + ", parent=" + parent + ", children="
				+ children + "]";
	}
}
