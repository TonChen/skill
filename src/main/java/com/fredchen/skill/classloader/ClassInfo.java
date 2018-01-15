package com.fredchen.skill.classloader;

import lombok.RequiredArgsConstructor;

/**
 * @Author: mrchen
 * @Date: 2018/1/15 23:18
 */

public class ClassInfo {
    private long lastModifyTime;
    private CustomClassLoader customClassLoader;
    private BaseClass baseClass;

    public ClassInfo(long lastModifyTime, CustomClassLoader customClassLoader) {
        this.lastModifyTime = lastModifyTime;
        this.customClassLoader = customClassLoader;
    }

    public long getLastModifyTime() {
        return lastModifyTime;
    }

    public CustomClassLoader getCustomClassLoader() {
        return customClassLoader;
    }

    public BaseClass getBaseClass() {
        return baseClass;
    }

    public void setBaseClass(BaseClass baseClass) {
        this.baseClass = baseClass;
    }
}
