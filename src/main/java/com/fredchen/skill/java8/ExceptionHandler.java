package com.fredchen.skill.java8;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: fredchen
 * @Date: 2018/1/18 17:54
 * jdk8 处理异常流的新特性
 */
public class ExceptionHandler {

    /**
     * jdk7出现的
     * @throws IOException
     */
    public static void tryWithResources7() throws IOException {
        try(InputStream ins = new FileInputStream("/home/biezhi/a.txt");OutputStream os = new FileOutputStream("/home/biezhi/b.txt")){
            char charStr = (char) ins.read();
            System.out.print(charStr);
        }
    }

    /**
     * 之前处理流的方式
     * @throws IOException
     */
    public static void tryWithResources6() throws IOException {
        InputStream stream = new FileInputStream("/home/biezhi/a.txt");
        try {
            // 使用流对象
            stream.read();
        } catch(Exception e){
            // 处理异常
        } finally {
            // 关闭流资源
            if(stream != null){
                stream.close();
            }
        }
    }


    /**
     * 处理反射异常
     */
    public static void handleReflective() {
        //BEFORE
        Object object = new Object();
        try {
            Class<?> clazz = Class.forName("com.biezhi.apple.User");
            clazz.getMethods()[0].invoke(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        //NOW 直接捕获ReflectiveOperationException异常
        try {
            Class<?> clazz = Class.forName("com.biezhi.apple.User");
            clazz.getMethods()[0].invoke(object);
        } catch (ReflectiveOperationException e){
            e.printStackTrace();
        }
    }
}
