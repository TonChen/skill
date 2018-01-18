package com.fredchen.skill.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class JAVA8Test {
	public static void main(String[] args) {
//		test9();
		char[]arr={119,119,119,122,104,117,122,104,117,97,118,99,111,109};
        for (int i = 0; i < arr.length; i++) { if(i==3){ System.out.print(".");
            System.out.print(arr[i]);
        }else if(i==arr.length-3){ System.out.print(".");
            System.out.print(arr[i]);
        }else{ System.out.print(arr[i]);
        }
        }
	}
	
	public static void test1(){
		List<String> list = Arrays.asList("peter", "anna", "mike", "xenia");
		Collections.sort(list, (a, b) -> a.compareTo(b));
		System.err.println(list);
	}
	
	public static void test2(){
		List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
		features.forEach((n) -> System.err.println(n));
		features.forEach(System.err::println);
	}
	
	public static void test3(){
		List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
		System.out.println("Languages which starts with J :");
		filter1(languages, (str)->str.startsWith("J"));
	 
	    System.out.println("Languages which ends with a ");
	    filter(languages, (str)->str.endsWith("a"));
	 
	    System.out.println("Print all languages :");
	    filter1(languages, (str)->true);
	 
	    System.out.println("Print no language : ");
	    filter(languages, (str)->false);
	 
	    System.out.println("Print language whose length greater than 4:");
	    filter1(languages, (str)->str.length() > 4);
	    
	}
	public static void filter(List<String> names, Predicate<String> condition) {
		names.forEach((n) -> {
			if(condition.test(n)){
				System.out.println(n);
			}
		});
	}
	 // 更好的办法
    public static void filter1(List<String> names, Predicate<String> condition) {
        names.stream().filter((name) -> (condition.test(name))).forEach((name) -> {
            System.out.println(name + " ");
        });
    }
	
	public static void test4(){
		List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
		Predicate<String> startsWithJ = (n) -> n.startsWith("J");
		Predicate<String> fourLetterLong = (n) -> n.length() == 4;
		languages.stream()
		    .filter(startsWithJ.and(fourLetterLong))
		    .forEach((n) -> System.out.print("nName, which starts with 'J' and four letter long is : " + n));
	}
	
	//map
	public static void test5(){
		// 使用lambda表达式
		List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
		costBeforeTax.stream().map((cost) -> cost + .12 * cost).forEach(System.out::println);
		costBeforeTax = costBeforeTax.stream().map((cost) -> cost + .12 * cost).map(cost -> cost.intValue()).collect(Collectors.toList());
		System.out.println(costBeforeTax);
	}
	
	//reduce
	public static void test6(){
		List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
		double bill = costBeforeTax.stream().map((cost) -> cost + .12*cost).reduce((sum, cost) -> sum + cost).get();
		System.out.println("Total : " + bill);
	}
	
	//对列表的每个元素应用函数
	public static void test7(){
		List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
		String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
		System.out.println(G7Countries);
	}
	
	public static void test8(){
		List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
		List<Integer> distinct = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
		System.out.printf("Original List : %s,  Square Without duplicates : %s %n", numbers, distinct);
	}
	
	//IntStream、LongStream 和 DoubleStream 等流的类中，有个非常有用的方法叫做 summaryStatistics() 。
	//可以返回 IntSummaryStatistics、LongSummaryStatistics 或者 DoubleSummaryStatistic s，描述流中元素的各种摘要数据。
	public static void test9(){
		//获取数字的个数、最小值、最大值、总和以及平均值
		List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
		IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x).summaryStatistics();
		System.out.println("Highest prime number in List : " + stats.getMax());
		System.out.println("Lowest prime number in List : " + stats.getMin());
		System.out.println("Sum of all prime numbers : " + stats.getSum());
		System.out.println("Average of all prime numbers : " + stats.getAverage());
	}
	
	public static void convertTest() {  
	    List<String> collected = new ArrayList<>();  
	    collected.add("alpha");  
	    collected.add("beta");  
	    collected.add("cool");  
	    collected.add("delta");  
	    collected = collected.stream().map(string -> string.toUpperCase())  
	            .collect(Collectors.toList());  
	    System.out.println(collected);//此处打印出来的是大写还是小写，为什么？  
	}
}
