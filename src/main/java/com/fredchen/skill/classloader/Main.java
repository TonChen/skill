package com.fredchen.skill.classloader;

/**
 * @Author: mrchen
 * @Date: 2018/1/16 0:04
 */
public class Main {
    public static void main(String[] args){
        new Thread(new ClassLoaderRunnable()).start();
    }
}
