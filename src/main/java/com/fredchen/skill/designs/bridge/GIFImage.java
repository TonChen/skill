package com.fredchen.skill.designs.bridge;

/**
 * GIF格式图像：扩充抽象类
 * @author upgrade2004
 *
 */
public class GIFImage extends Image {
	public void parseFile(String fileName) {
		// 模拟解析GIF文件并获得一个像素矩阵对象m;
		Matrix m = new Matrix();
		imp.doPaint(m);
		System.out.println(fileName + "，格式为GIF。");
	}
}
