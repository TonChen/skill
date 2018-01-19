package com.fredchen.skill.java8;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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
        URI u = URI.create("file:/E://logs/oms-web");
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
        Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);

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


        //文件写操作
        try (BufferedWriter writer1 = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer1.write("测试文件写操作");
        }

        //遍历一个文件夹
        Path dir = Paths.get("D:\\webworkspace");
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)){
            stream.forEach((n) -> System.out.println(n.getFileName()));
        }

        //注：遍历整个目录需要使用：Files.walkFileTree
        try (Stream<Path> stream = Files.list(Paths.get("E:\\logs\\"))){
            Iterator<Path> ite = stream.iterator();
            while(ite.hasNext()){
                Path pp = ite.next();
                System.out.println(pp.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path startingDir = Paths.get("C:\\apache-tomcat-8.0.21");
        List<Path> result = new LinkedList<Path>();
        Files.walkFileTree(startingDir, new FindJavaVisitor(result));
        System.out.println("result.size()=" + result.size());

        //创建目录和文件
        if(Files.exists(Paths.get("E://TEST"))) {
            Files.createDirectories(Paths.get("E://TEST"));
            Files.createFile(Paths.get("E://TEST/test.txt"));

            //从文件复制到输出流
            Files.copy(Paths.get("C://my.ini"), System.out);
            //从文件复制到文件
            Files.copy(Paths.get("C://my.ini"), Paths.get("C://my2.ini"), StandardCopyOption.REPLACE_EXISTING);
            //从输入流复制到文件
            Files.copy(System.in, Paths.get("C://my3.ini"), StandardCopyOption.REPLACE_EXISTING);
        }


        //读取文件属性
        Path zip = Paths.get(uri);
        System.out.println(Files.getLastModifiedTime(zip));
        System.out.println(Files.size(zip));
        System.out.println(Files.isSymbolicLink(zip));
        System.out.println(Files.isDirectory(zip));
        System.out.println(Files.readAttributes(zip, "*"));

        //读取和设置文件权限
        Path profile = Paths.get("/home/digdeep/.profile");
        PosixFileAttributes attrs = Files.readAttributes(profile, PosixFileAttributes.class);// 读取文件的权限
        Set<PosixFilePermission> posixPermissions = attrs.permissions();
        posixPermissions.clear();
        String owner = attrs.owner().getName();
        String perms = PosixFilePermissions.toString(posixPermissions);
        System.out.format("%s %s%n", owner, perms);

        posixPermissions.add(PosixFilePermission.OWNER_READ);
        posixPermissions.add(PosixFilePermission.GROUP_READ);
        posixPermissions.add(PosixFilePermission.OTHERS_READ);
        posixPermissions.add(PosixFilePermission.OWNER_WRITE);

        Files.setPosixFilePermissions(profile, posixPermissions);    // 设置文件的权限




        //复制、移动一个文件内容到某个路径
        Files.copy(path1, path);
        Files.move(path1, path);

        //删除一个文件
        Files.delete(path);
    }

    private static class FindJavaVisitor extends SimpleFileVisitor<Path>{
        private List<Path> result;
        public FindJavaVisitor(List<Path> result){
            this.result = result;
        }
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
            if(file.toString().endsWith(".java")){
                result.add(file.getFileName());
            }
            return FileVisitResult.CONTINUE;
        }
    }

}
