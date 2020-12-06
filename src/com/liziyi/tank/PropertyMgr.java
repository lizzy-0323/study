package com.liziyi.tank;

import java.io.IOException;
import java.util.Properties;
//用于加载配置文件
public class PropertyMgr {
	//定义一个配置文件类的对象
	static Properties props = new Properties();
	
	static {
		try {
			props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("com/liziyi/tank/config"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//定义一个读取函数
	public static Object get(String key) {
		if(props == null) return null;
		return props.get(key);
	}
	//用于测试是否读取成功
	public static void main(String[] args) {
		System.out.println(PropertyMgr.get("initTankCount"));
		
	}
}
