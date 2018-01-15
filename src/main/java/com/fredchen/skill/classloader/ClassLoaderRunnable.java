package com.fredchen.skill.classloader;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: mrchen
 * @Date: 2018/1/15 23:58
 */

@Slf4j
public class ClassLoaderRunnable implements Runnable {

    @Override
    public void run() {
        while (true){

            ClassLoaderFactory classLoaderFactory = new ClassLoaderFactory();
            classLoaderFactory.invoke().getBaseClass().testClassLoader();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
