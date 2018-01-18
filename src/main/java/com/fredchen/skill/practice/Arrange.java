package com.fredchen.skill.practice;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * 用1、2、2、3、4、5这六个数字，用java写一个main函数，打印出所有不同的排列，如：512234、412345等，要求："4"不能在第三位，"3"
 * 与"5"不能相连。
 * 
 * @author upgrade2004
 * 
 */
public class Arrange {

	private String[] b = new String[] { "1", "2", "2", "3", "4", "5" };
	private int n = b.length;
	private boolean[] visited = new boolean[n];
	// visited = false;
	private int[][] a = new int[n][n];
	private String result = "";
	private TreeSet treeSet = new TreeSet();

	public static void main(String[] args) {
		new Arrange().start();
	}

	private void start() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					a[i][j] = 0;
				} else {
					a[i][j] = 1;
				}
			}
		}
		a[3][5] = 0;
		a[5][3] = 0;
		for (int i = 0; i < n; i++) {
			this.depthFirstSearch(i);
		}
		Iterator it = treeSet.iterator();
		while (it.hasNext()) {
			String string = (String) it.next();

			if (string.indexOf("4") != 2) {
				System.out.println(string);
			}
		}
	}

	private void depthFirstSearch(int startIndex) {
		visited[startIndex] = true;
		result = result + b[startIndex];
		if (result.length() == n) {
			treeSet.add(result);
		}
		for (int j = 0; j < n; j++) {
			if (a[startIndex][j] == 1 && !visited[j]) {
				depthFirstSearch(j);
			} else {
				continue;
			}
		}
		result = result.substring(0, result.length() - 1);
		visited[startIndex] = false;
	}
}
