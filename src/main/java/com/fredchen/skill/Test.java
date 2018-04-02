package com.fredchen.skill;

import java.util.Arrays;

/**
 * @Author: fredchen
 * @Date: 2018/3/22 9:59
 */
public class Test {
    public static void main(String[] args) {
        int c[] = {1, 2, 3, 4};
        change(c);
        System.out.println("================");
        Arrays.stream(c).forEach(System.out::println);
    }

    private static void change(int[] a){
        int b[] = {5,5,5,5,5,5,5,6};
        a[0] = 5;
        a = new int[5];
        Arrays.stream(a).forEach(System.out::println);
    }
}
