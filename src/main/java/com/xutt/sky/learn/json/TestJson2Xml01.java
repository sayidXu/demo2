package com.xutt.sky.learn.json;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

/**
 * json2xml之使用XmlSerializer.write()
 * 
 * 现状：自己实现，代码TextProtocolUtil.JsonToXml()
 * 
 * @author Administrator
 *
 */
public class TestJson2Xml01 {
	public static void main(String[] args) throws Exception {
		String xmlStr = "";
		String jsonStr = "";
		
		//普通带属性
		System.out.println("普通带属性========>");
		jsonStr = "{\"a\":{\"b\":{\"@age\":\"66\",\"#text\":\"123\"},\"c\":\"张三\"}}";
		xmlStr = jsonToXml(jsonStr);
		System.out.println(xmlStr);

		// 对象包含对象
		System.out.println("对象包含对象========>");
		jsonStr = "{\"a\":{\"b\":{\"@age\":\"66\",\"#text\":\"123\"},\"c\":\"张三\",\"d\":{\"d1\":{\"@length\":\"10\",\"#text\":\"ddd\"},\"d2\":\"ddd\"}}}";
		xmlStr = jsonToXml(jsonStr);
		System.out.println(xmlStr);

		// 数组带属性（有问题：json数组转换之后跟预期不符）
		System.out.println("数组带属性========>");
		jsonStr = "{\"a\":{\"b\":{\"@age\":\"66\",\"#text\":\"123\"},\"c\":\"张三\",\"d\":[{\"d1\":{\"@length\":\"10\",\"#text\":\"ddd1\"},\"d2\":\"ddd2\"},{\"d1\":{\"@length\":\"10\",\"#text\":\"ddd11\"},\"d2\":\"ddd22\"}]}}";
		xmlStr = jsonToXml(jsonStr);
		System.out.println(xmlStr);

		// 数组包含数组带属性(同上)
		System.out.println("数组包含数组带属性========>");
		jsonStr = "{\"a\":{\"b\":{\"@age\":\"66\",\"#text\":\"123\"},\"c\":\"张三\",\"d\":[{\"d1\":{\"@length\":\"10\",\"#text\":\"ddd1\"},\"d2\":[{\"d21\":\"ddd211\",\"d22\":\"ddd221\"},{\"d21\":\"ddd212\",\"d22\":\"ddd222\"}]},{\"d1\":{\"@length\":\"10\",\"#text\":\"ddd11\"},\"d2\":\"ddd22\"}]}}";
		xmlStr = jsonToXml(jsonStr);
		System.out.println(xmlStr);
	}
    
	public static String jsonToXml(String json) {  
        try {  
            XMLSerializer serializer = new XMLSerializer();  
            JSON jsonObject = JSONSerializer.toJSON(json);  
            return serializer.write(jsonObject);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }
}
