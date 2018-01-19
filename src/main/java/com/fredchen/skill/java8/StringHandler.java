package com.fredchen.skill.java8;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: fredchen
 * @Date: 2018/1/19 9:45
 */
public class StringHandler {

    public static void main(String[] args) {
        join();
    }

    /**
     * 第一个参数是分隔符，后面接收一个CharSequence类型的可变参数数组或一个Iterable。
     */
    public static void join() {
        String str = String.join(",", Lists.newArrayList("1", "2", "3"));
        String str1 = String.join(",", "1", "2", "3");
        System.err.println(str);
    }
}
