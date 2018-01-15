package com.fredchen.skill.classloader;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static com.sun.beans.finder.ClassFinder.findClass;

/**
 * @Author: mrchen
 * @Date: 2018/1/15 23:27
 */

@Slf4j
public class ClassLoaderFactory {

    private static final String BASE_PATH = "E:\\work\\git\\project\\skill\\target\\classes\\";
    private static final String TARGET_CLASS = "com.fredchen.skill.classloader.ClassLoaderTest";

    private static final Map<String, ClassInfo> loadInfo = new HashMap<>();


    public ClassInfo invoke(){
        File file = new File(BASE_PATH + TARGET_CLASS.replaceAll("\\.", "/") + ".class");
        long lastModify = file.lastModified();

        if(loadInfo.get(TARGET_CLASS) == null || loadInfo.get(TARGET_CLASS).getLastModifyTime() != lastModify){
            reloadClass(TARGET_CLASS, lastModify);
        }
        return loadInfo.get(TARGET_CLASS);
    }

    private void reloadClass(String className, long lastModify) {

        try {
            CustomClassLoader customClassLoader = new CustomClassLoader(BASE_PATH);
            ClassInfo classInfo = new ClassInfo(lastModify, customClassLoader);
            Class<?> clazz = customClassLoader.findClass(TARGET_CLASS);
            classInfo.setBaseClass((BaseClass) clazz.newInstance());
            //classInfo.setBaseClass((BaseClass) clazz.getConstructor(new Class[]{}).newInstance(new Object(){}));
            loadInfo.put(className, classInfo);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


}
