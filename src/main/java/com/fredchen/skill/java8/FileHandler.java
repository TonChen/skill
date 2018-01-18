package com.fredchen.skill.java8;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

/**
 * @Author: fredchen
 * @Date: 2018/1/18 18:03
 * JDK8 对文件的操作
 */

public class FileHandler {

    /**
     * 之前的方式
     */
    public void readFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("file.txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Path用于来表示文件路径和文件，和File对象类似，Path对象并不一定要对应一个实际存在的文件， 它只是一个路径的抽象序列。
     * 默认情况Files类中的所有方法都会使用UTF-8编码进行操作
     */
    public void newReadFile() throws IOException {
        Path path1 = Paths.get("/home/biezhi", "a.txt");
        Path path2 = Paths.get("/home/biezhi/a.txt");
        URI u = URI.create("file:////home/biezhi/a.txt");
        Path pathURI = Paths.get(u);


        Path filePath = FileSystems.getDefault().getPath("/home/biezhi", "a.txt");

        //相互转换
        File file = new File("/home/biezhi/a.txt");
        Path path = file.toPath();
        File f = path.toFile();
        URI uri = f.toURI();

        //使用Files类快速实现文件操作
        byte[] data = Files.readAllBytes(Paths.get("/home/biezhi/a.txt"));
        String content = new String(data, StandardCharsets.UTF_8);

        //按照行读取文件
        List<String> lines = Files.readAllLines(Paths.get("/home/biezhi/a.txt"));

        //将字符串写入到文件
        Files.write(Paths.get("/home/biezhi/b.txt"), "Hello JDK7!".getBytes());

        //按照行写入文件，Files.write方法的参数中支持传递一个实现Iterable接口的类实例。 将内容追加到指定文件可以使用write方法的第三个参数OpenOption
        Files.write(Paths.get("/home/biezhi/b.txt"), "Hello JDK7!".getBytes(),
                StandardOpenOption.APPEND);

        //Files还有一些其他的常用方法
        InputStream ins = Files.newInputStream(path);
        OutputStream ops = Files.newOutputStream(path);
        Reader reader = Files.newBufferedReader(path);
        Writer writer = Files.newBufferedWriter(path);

        //创建文件、目录
        if (!Files.exists(path)) {
            Files.createFile(path);
            Files.createDirectory(path);
        }

        //Files还提供了一些方法让我们创建临时文件/临时目录,
        // 这里的dir是一个Path对象，并且字符串prefix和suffix都可能为null。
        // 例如调用Files.createTempFile(null, ".txt")会返回一个类似/tmp/21238719283331124678.txt
        String prefix = "测试";
        String suffix = ".tmp";
        Files.createTempFile(path, prefix, suffix);
        Files.createTempFile(prefix, suffix);
        Files.createTempDirectory(path, prefix);
        Files.createTempDirectory(prefix);

        //读取一个目录下的文件请使用Files.list和Files.walk方法
        Files.list(path);
        Files.walk(path);

        //复制、移动一个文件内容到某个路径
        Files.copy(path1, path);
        Files.move(path1, path);

        //删除一个文件
        Files.delete(path);
    }


}
