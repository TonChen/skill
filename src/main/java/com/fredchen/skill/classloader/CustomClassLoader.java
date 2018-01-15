package com.fredchen.skill.classloader;

import lombok.Cleanup;
import lombok.val;

import java.io.*;

/**
 * @author mrchen
 */
public class CustomClassLoader extends ClassLoader {

    private String classPath;

    public CustomClassLoader(String classPath) {
        super(ClassLoader.getSystemClassLoader());
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String fullClassName) throws ClassNotFoundException {

        byte[] b = loadClassToByte(fullClassName);

        return super.defineClass(fullClassName, b, 0, b.length);
    }


    private byte[] loadClassToByte(String fullClassName) {
        File file = new File(classPath + fullClassName.replaceAll("\\.","/") + ".class");

        try {
            @Cleanup
            FileInputStream fis = new FileInputStream(file);
            @Cleanup
            val baos = new ByteArrayOutputStream();
            int read = 0;
            while ((read = fis.read()) != -1){
                baos.write(read);
            }
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
