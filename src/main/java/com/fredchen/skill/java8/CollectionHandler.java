package com.fredchen.skill.java8;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: fredchen
 * @Date: 2018/1/19 10:06
 * jdk8 对集合的一些处理
 */

public class CollectionHandler {

    public static void main(String[] args) throws IOException, URISyntaxException {
        convertTest();

    }

    public static void convertTest() {
        List<String> collected = new ArrayList<>();
        collected.add("alpha");
        collected.add("beta");
        collected.add("cool");
        collected.add("delta");
        List<String> list = collected.stream().map(string -> string.toUpperCase()).collect(Collectors.toList());
        System.out.println(list);//此处打印出来的是大写还是小写，为什么？
    }

}
