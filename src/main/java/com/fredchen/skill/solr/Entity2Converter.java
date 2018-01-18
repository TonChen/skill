package com.fredchen.skill.solr;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Entity2Converter {
	
	public static Object incrementProperty(String name, Object obj,
			Object valu, Object... url) throws Exception {
		name = name.trim();
		String prop = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		String mname = "get" + prop;
		Method method = obj.getClass().getMethod(mname, null);
		Object result = method.invoke(obj, new Object[0]);
		Map<Object, Object> bean = fileBean(url);// 获得所有的实体
		Object value = null;
		Class<?> class1 = obj.getClass();
		Field field[] = class1.getDeclaredFields();
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (Field f : field) {
			if (f.getGenericType().toString().lastIndexOf(".") != -1) {
				String str = f
						.getGenericType()
						.toString()
						.substring(
								f.getGenericType().toString().lastIndexOf(".") + 1,
								f.getGenericType().toString().length());
				map.put(f.getName(), str);
			} else {
				map.put(f.getName(), f.getGenericType());
			}

		}

		Object object = map.get(name);
		String str = valu.toString();
		String st = object.toString();
		Object object2 = bean.get(st);
		Class<?> class12[] = null;

		if (object2 != null) {
			class12 = new Class[] { valu.getClass() };
			value = valu;
		} else if (object2 == null && object != null) {
			class12 = new Class[] { valu.getClass() };
			value = valu;
		} else {
			class12 = new Class<?>[] { (Class<?>) object };
			if (st.equals("int")) {
				int i = ((Integer) result).intValue() + (Integer.parseInt(str));
				value = i;
			} else if (st.equals("short")) {
				value = ((Short) result).shortValue() + Short.parseShort(str);
			} else if (st.equals("double")) {
				value = ((Double) result).doubleValue()
						+ (Double.parseDouble(str));
			} else if (st.equals("float")) {
				value = ((Float) result).floatValue() + Float.parseFloat(str);
			} else if (st.equals("char")) {
				value = str;
			} else if (st.equals("long")) {
				value = ((Long) result).longValue() + Long.parseLong(str);
			} else if (st.equals("boolean")) {
				value = str;
			} else if (st.equals("byte")) {
				value = ((Byte) result).byteValue() + Byte.parseByte(str);
			}
		}
		mname = "set" + prop;
		method = obj.getClass().getMethod(mname, class12);
		method.invoke(obj, new Object[] { value });
		return value;
	}

	public static Map<Object, Object> fileBean(Object... objects)
			throws Exception {
		File directory = new File(".");
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (int j = 0; j < objects.length; j++) {
			String url = directory.getCanonicalPath();// 返回此抽象路径名的规范路径名字符串。
			if (objects[j].toString().indexOf("src") == -1) {
				objects[j] = objects[j].toString() + "/src";
			}
			File file = new File(url + objects[j]);
			for (int i = 0; i < file.listFiles().length; i++) {
				map.put(file.listFiles()[i].getName().substring(0,
						file.listFiles()[i].getName().length() - 5), true);
			}
		}
		return map;
	}

}
