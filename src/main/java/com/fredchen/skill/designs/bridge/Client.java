package com.fredchen.skill.designs.bridge;

/**
 * 结构型 桥接模式
 * 
 * http://blog.csdn.net/l1028386804/article/details/45457969
 * http://blog.csdn.net/lovesomnus/article/details/23766363
 * @author upgrade2004
 *
 */
public class Client {
	public static void main(String args[]) throws Exception {
		Image image = new JPGImage();
		ImageImp imp = new WindowsImp();
		//image = (Image) XMLUtil.getBean("image");
		//imp = (ImageImp) XMLUtil.getBean("os");
		image.setImageImp(imp);
		image.parseFile("小龙女");
	}
}
