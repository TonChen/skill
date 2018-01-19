package com.fredchen.skill.java8;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Author: fredchen
 * @Date: 2018/1/19 10:15
 * jdk8 新增的对null的检查，对流stream的时候很有用
 */

public class NullHandler {

    public static void main(String[] args) {
        Stream.of("a", "c", null, "d")
                .filter(Objects::nonNull)
                .forEach(System.out::println);
    }

}
