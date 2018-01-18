package com.fredchen.skill.practice;
public class Main {
	private double TotalHeight = 100;
	private double CurHeight = 50;

	public void drop(int times) {
		if ((times - 1) == 0) {
			return;
		}

		setTotalHeight(getTotalHeight() + 2 * getCurHeight());
		setCurHeight(getCurHeight() / 2);

		drop(times - 1);
	}

	public double getTotalHeight() {
		return TotalHeight;
	}

	public void setTotalHeight(double totalHeight) {
		TotalHeight = totalHeight;
	}

	public double getCurHeight() {
		return CurHeight;
	}

	public void setCurHeight(double curHeight) {
		CurHeight = curHeight;
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.drop(10);
		System.out.println("Total height is " + main.getTotalHeight());
		System.out.println("Current height is " + main.getCurHeight());
	}
}