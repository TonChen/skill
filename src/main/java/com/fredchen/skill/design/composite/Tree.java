package com.fredchen.skill.design.composite;

public class Tree {
	private TreeNode root;

	public Tree(String name) {
		root = new TreeNode(name);
	}
	
	public static void main(String[] args) {
		TreeNode treeA = new TreeNode("A");
		TreeNode treeB = new TreeNode("B");
		TreeNode treeC = new TreeNode("C");
		
		treeA.add(treeB);
		treeB.add(treeC);
		Tree treeR = new Tree("R");
		treeR.root.add(treeA);
		System.out.println(treeR.root);
	}
}
