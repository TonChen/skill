package com.fredchen.skill.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMath {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Integer[] datas = {1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39,41,43,45,47,49,51,53,55,57,59,61,63,65,67,69,71,73,75,77,79,81,83,85,87,89,91,93,95,97,99};
		int total = 259;
		int parameter = 3;
		List<Integer[]> results = calculate(total, datas, parameter);
		for (Integer[] integers : results) {
			System.out.println(Arrays.asList(integers));
		}
		System.out.println("总共"+results.size()+"个。");
		System.out.println("耗时："+(System.currentTimeMillis() - start)+"毫秒。");
	}
	
	public static List<Integer[]> calculate(int total, Integer[] datas, int parameter){
		List<Integer[]> results = new ArrayList<Integer[]>();
		List<Integer> sources = Arrays.asList(datas);
		for (int i = 0; i < datas.length; i++) {
			int data1 = datas[i];
			for (int j = 0; j < datas.length; j++) {
				int data2 = datas[j];
				int data12 = data1 + data2;
				Integer remain = total - data12;
				if(sources.contains(remain)){
					Integer[] result = new Integer[parameter];
					result[0] = data1;
					result[1] = data2;
					result[2] = remain;
					results.add(result);
				}else{
					continue;
				}
			}
		}
		return results;
	}
}
