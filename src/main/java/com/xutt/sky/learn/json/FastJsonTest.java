package com.xutt.sky.learn.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

public class FastJsonTest {
	private static Logger logger = LoggerFactory.getLogger(FastJsonTest.class);
	
	public static void main(String[] args){
		String str = "{\"aa\":{\"aa1\":\"11111\",\"aa2\":\"22222\"},\"ipAddr\":\"172.16.90.208\",\"reqMethod\":\"2\",\"serverPort\":\"8888\"}";
		JSONObject jsonObject = JSON.parseObject(str);
		
		String ipAddr = jsonObject.getString("ipAddr");
		System.out.println("=====>ipAddr:" + ipAddr);
		
		//JSONPath 
		//参考https://blog.csdn.net/itguangit/article/details/78764212
		String aa1 = (String)JSONPath.read(str, "aa.aa1");
		System.out.println("=====>aa1:" + aa1);
		
		boolean flag = JSONPath.containsValue(jsonObject,"aa.aa1","11111");
		System.out.println("=====>flag:" + flag);
		//logger.debug("debug:flag:" + flag);
	}
}
