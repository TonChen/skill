package com.fredchen.skill.designs.bridge;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class XMLUtil {
	// 该方法用于从XML配置文件中提取具体类类名，并返回一个实例对象
	public static Object getBean(String args) throws Exception {
		SAXReader reader = new SAXReader();
		String path = XMLUtil.class.getClassLoader().getResource("test/my/designs/bridge/XMLUtil/config.xml")
				.getPath();
		Document document = reader.read(new File(path));
		String cName = null;
		if (args.equals("image")) {
			cName = document.selectSingleNode("/config/image").getText();

		} else if (args.equals("os")) {
			// 获取第二个包含类名的节点，即具体实现类
			cName = document.selectSingleNode("/config/os").getText();
		}
		// 通过类名生成实例对象并将其返回
		Class<?> c = Class.forName(cName);
		Object obj = c.newInstance();
		return obj;
	}
}
