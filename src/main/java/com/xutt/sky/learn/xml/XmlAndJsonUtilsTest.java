package com.xutt.sky.learn.xml;

import com.xutt.sky.util.XmlAndJsonUtils;

public class XmlAndJsonUtilsTest {
	public static void main(String[] args) throws Exception{
		jsonToXmlTest();
		xmlToJsonTest();
	}
	
	public static void jsonToXmlTest() {
		String jsonStr = "";
		String xmlStr = "";
		
		/** 1、普通-带子节点-带属性(根节点/子节点) */
		// 预期结果：<a sex='男' sex1='男1'>aaa<b age='66'age1='661'><b1>123</b1></b><c>张三</c></a>
		/*jsonStr = "{\"a\":{\"b\":{\"@age\":\"66\",\"b1\":\"123\",\"#TEXT\":\"\",\"@age1\":\"661\"},\"c\":\"张三\",\"#TEXT\":\"aaa\",\"@sex1\":\"男1\",\"@sex\":\"男\"}}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
				
		/** 2、普通-带子节点-不带属性(根节点/子节点) */
		//预期结果：<a>aaa<b><b1>123</b1></b><c>张三</c></a>
		/*jsonStr = "{\"a\":{\"b\":{\"b1\":\"123\",\"#TEXT\":\"\"},\"c\":\"张三\",\"#TEXT\":\"aaa\"}}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
		
		/** 3、普通-不带子节点-带属性(根节点) */
		//预期结果：<a sex='男'>aaa</a>
		/*jsonStr = "{\"a\":{\"#TEXT\":\"aaa\",\"@sex\":\"男\"}}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
		
		/** 4、普通-不带子节点-不带属性(根节点) */
		//预期结果：<a>aaa</a>
		/*jsonStr = "{\"a\":\"aaa\"}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
		
		/** 5、普通-不带子节点-带属性(子节点) */
		//预期结果：<a><b age='66'>123</b><c>张三</c></a>
		/*jsonStr = "{\"a\":{\"b\":{\"@age\":\"66\",\"#TEXT\":\"123\"},\"c\":\"张三\",\"#TEXT\":\"\"}}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
		
		/** 6、普通-不带子节点-不带属性(子节点) */
		//预期结果：<a><b>123</b><c>张三</c></a>
		/*jsonStr = "{\"a\":{\"b\":\"123\",\"c\":\"张三\",\"#TEXT\":\"\"}}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
		
		/** 7、对象包含对象-带子节点-带属性(根节点/子节点) */
		//预期结果：<a age='18'><b>123</b><c>张三</c><d length='8'><d1 length='10'>ddd</d1><d2>ddd</d2></d></a>
		/*jsonStr = "{\"a\":{\"@age\":\"18\",\"d\":{\"d1\":{\"#TEXT\":\"ddd\",\"@length\":\"10\"},\"d2\":\"ddd\",\"#TEXT\":\"\",\"@length\":\"8\"},\"b\":\"123\",\"c\":\"张三\",\"#TEXT\":\"\"}}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
		
		/** 8、对象包含对象-带子节点-不带属性(根节点/子节点) */
		//预期结果：<a><b>123</b><c>张三</c><d><d1>ddd</d1><d2>ddd</d2></d></a>
		/*jsonStr = "{\"a\":{\"d\":{\"d1\":\"ddd\",\"d2\":\"ddd\",\"#TEXT\":\"\"},\"b\":\"123\",\"c\":\"张三\",\"#TEXT\":\"\"}}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
		
		/** 9、单数组-带子节点-带属性(根节点/子节点) */
		//预期结果：<a age='66' age1='661'><b>123</b><c>张三</c><d sex='男' sex1='男1'><d1 length='10' length1='101'>ddd1</d1><d2>ddd2</d2></d><d sex='女'><d1 length='11'>ddd11</d1><d2>ddd22</d2></d></a>
		/*jsonStr = "{\"a\":{\"@age\":\"66\",\"d\":[{\"d1\":{\"@length1\":\"101\",\"#TEXT\":\"ddd1\",\"@length\":\"10\"},\"d2\":\"ddd2\",\"#TEXT\":\"\",\"@sex1\":\"男1\",\"@sex\":\"男\"},{\"d1\":{\"#TEXT\":\"ddd11\",\"@length\":\"11\"},\"d2\":\"ddd22\",\"#TEXT\":\"\",\"@sex\":\"女\"}],\"b\":\"123\",\"c\":\"张三\",\"#TEXT\":\"\",\"@age1\":\"661\"}}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
		
		/** 10、单数组(数组对象用一个节点包起来)-带子节点-带属性(根节点/子节点) */
		//预期结果：<a age='66'><b>123</b><c>张三</c><dp><d sex='男'><d1 length='10'>ddd1</d1><d2>ddd2</d2></d><d sex='女'><d1 length='11'>ddd11</d1><d2>ddd22</d2></d></dp></a>
		/*jsonStr = "{\"a\":{\"@age\":\"66\",\"b\":\"123\",\"c\":\"张三\",\"#TEXT\":\"\",\"dp\":{\"d\":[{\"d1\":{\"#TEXT\":\"ddd1\",\"@length\":\"10\"},\"d2\":\"ddd2\",\"#TEXT\":\"\",\"@sex\":\"男\"},{\"d1\":{\"#TEXT\":\"ddd11\",\"@length\":\"11\"},\"d2\":\"ddd22\",\"#TEXT\":\"\",\"@sex\":\"女\"}],\"#TEXT\":\"\"}}}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
		
		/** 11、单数组-不带子节点-不带属性(根节点/子节点) */
		//预期结果：<a><b>123</b><c>张三</c><d><d1>ddd1</d1><d2>ddd2</d2></d><d><d1>ddd11</d1><d2>ddd22</d2></d></a>
		/*jsonStr = "{\"a\":{\"d\":[{\"d1\":\"ddd1\",\"d2\":\"ddd2\",\"#TEXT\":\"\"},{\"d1\":\"ddd11\",\"d2\":\"ddd22\",\"#TEXT\":\"\"}],\"b\":\"123\",\"c\":\"张三\",\"#TEXT\":\"\"}}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
		
		/** 12、单数组-不带子节点-带属性(子节点) */
		//预期结果：<a age='66' age1='661'><b>123</b><c>张三</c><d sex='男' sex1='男1'>111</d><d sex='女'>222</d></a>
		/*jsonStr = "{\"a\":{\"@age\":\"66\",\"d\":[{\"#TEXT\":\"111\",\"@sex1\":\"男1\",\"@sex\":\"男\"},{\"#TEXT\":\"222\",\"@sex\":\"女\"}],\"b\":\"123\",\"c\":\"张三\",\"#TEXT\":\"\",\"@age1\":\"661\"}}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
		
		/** 13、单数组-不带子节点-部分不带属性(子节点)*/
		//预期结果：<a age='66' age1='661'><b>123</b><c>张三</c><d sex='男' sex1='男1'>111</d><d sex='女'>222</d><d>333</d></a>
		/*jsonStr = "{\"a\":{\"@age\":\"66\",\"@age1\":\"661\",\"b\":\"123\",\"c\":\"张三\",\"d\":[{\"@sex\":\"男\",\"@sex1\":\"男1\",\"#TEXT\":\"111\"},{\"@sex\":\"女\",\"#TEXT\":\"222\"},\"333\"]}}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
		
		/** 14、单数组(xml根部就是数组)-带子节点-带属性(根节点/子节点) */
		//预期结果：<d sex='男' sex1='男1'><d1 length='10' length1='101'>ddd1</d1><d2>ddd2</d2></d><d sex='女'><d1 length='11'>ddd11</d1><d2>ddd22</d2></d>
		/*jsonStr = "{\"d\":[{\"@sex\":\"男\",\"@sex1\":\"男1\",\"d1\":{\"@length\":\"10\",\"@length1\":\"101\",\"#TEXT\":\"ddd1\"},\"d2\":\"ddd2\"},{\"@sex\":\"女\",\"d1\":{\"@length\":\"11\",\"#TEXT\":\"ddd11\"},\"d2\":\"ddd22\"}]}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
		
		/** 15、单数组(json根部就是数组)-带子节点-带属性(根节点/子节点) */
		//预期结果：不支持（转换成xml后对象无挂载节点）
		jsonStr = "[{\"@sex\":\"男\",\"@sex1\":\"男1\",\"d1\":{\"@length\":\"10\",\"@length1\":\"101\",\"#TEXT\":\"ddd1\"},\"d2\":\"ddd2\"},{\"@sex\":\"女\",\"d1\":{\"@length\":\"11\",\"#TEXT\":\"ddd11\"},\"d2\":\"ddd22\"}]";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);
		
		/** 16、数组包含数-带子节点-带属性(根节点/子节点) */
		//预期结果：<a age='66'><b>123</b><c>张三</c><d><d1 length='10'>ddd1</d1><d2><d21 length='11' sex='男'>ddd211</d21><d22>ddd221</d22></d2><d2><d21>ddd212</d21><d22>ddd222</d22></d2></d><d><d1 length='10'>ddd11</d1><d2>ddd22</d2></d></a>
		/*jsonStr = "{\"a\":{\"@age\":\"66\",\"d\":[{\"d1\":{\"#TEXT\":\"ddd1\",\"@length\":\"10\"},\"d2\":[{\"d22\":\"ddd221\",\"d21\":{\"#TEXT\":\"ddd211\",\"@length\":\"11\",\"@sex\":\"男\"},\"#TEXT\":\"\"},{\"d22\":\"ddd222\",\"d21\":\"ddd212\",\"#TEXT\":\"\"}],\"#TEXT\":\"\"},{\"d1\":{\"#TEXT\":\"ddd11\",\"@length\":\"10\"},\"d2\":\"ddd22\",\"#TEXT\":\"\"}],\"b\":\"123\",\"c\":\"张三\",\"#TEXT\":\"\"}}";
		xmlStr = XmlAndJsonUtils.jsonToXml(jsonStr);
		System.out.println(xmlStr);*/
	}
	
	public static void xmlToJsonTest() {
		String xmlStr = "";
		String jsonStr = "";
		/** 1、普通-带子节点-带属性(根节点/子节点) */
		//预期结果：{"a":{"b":{"@age":"66","b1":"123","#TEXT":"","@age1":"661"},"c":"张三","#TEXT":"aaa","@sex1":"男1","@sex":"男"}}
		/*xmlStr = "<a sex='男' sex1='男1'>aaa<b age='66' age1='661'><b1>123</b1></b><c>张三</c></a>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);*/
		
		/** 2、普通-带子节点-不带属性(根节点/子节点) */
		//预期结果：{"a":{"b":{"b1":"123","#TEXT":""},"c":"张三","#TEXT":"aaa"}}
		/*xmlStr = "<a>aaa<b><b1>123</b1></b><c>张三</c></a>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);*/
		
		/** 3、普通-不带子节点-带属性(根节点) */
		//预期结果：{"a":{"#TEXT":"aaa","@sex":"男"}}
		/*xmlStr = "<a sex='男'>aaa</a>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);*/
		
		/** 4、普通-不带子节点-不带属性(根节点) */
		//预期结果：{"a":"aaa"}
		/*xmlStr = "<a>aaa</a>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);*/
		
		/** 5、普通-不带子节点-带属性(子节点) */
		//预期结果：{"a":{"b":{"@age":"66","#TEXT":"123"},"c":"张三","#TEXT":""}}
		/*xmlStr = "<a><b age='66'>123</b><c>张三</c></a>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);*/
		
		/** 6、普通-不带子节点-不带属性(子节点) */
		//预期结果：{"a":{"b":"123","c":"张三","#TEXT":""}}
		/*xmlStr = "<a><b>123</b><c>张三</c></a>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);*/
		
		/** 7、对象包含对象-带子节点-带属性(根节点/子节点) */
		//预期结果：{"a":{"@age":"18","d":{"d1":{"#TEXT":"ddd","@length":"10"},"d2":"ddd","#TEXT":"","@length":"8"},"b":"123","c":"张三","#TEXT":""}}
		/*xmlStr = "<a age='18'><b>123</b><c>张三</c><d length='8'><d1 length='10'>ddd</d1><d2>ddd</d2></d></a>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);*/
		
		/** 8、对象包含对象-带子节点-不带属性(根节点/子节点) */
		//预期结果：{"a":{"d":{"d1":"ddd","d2":"ddd","#TEXT":""},"b":"123","c":"张三","#TEXT":""}}
		/*xmlStr = "<a><b>123</b><c>张三</c><d><d1>ddd</d1><d2>ddd</d2></d></a>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);*/
		
		/** 9、单数组-带子节点-带属性(根节点/子节点) */
		//预期结果：{"a":{"@age":"66","d":[{"d1":{"@length1":"101","#TEXT":"ddd1","@length":"10"},"d2":"ddd2","#TEXT":"","@sex1":"男1","@sex":"男"},{"d1":{"#TEXT":"ddd11","@length":"11"},"d2":"ddd22","#TEXT":"","@sex":"女"}],"b":"123","c":"张三","#TEXT":"","@age1":"661"}}
		/*xmlStr = "<a age='66' age1='661'><b>123</b><c>张三</c><d sex='男' sex1='男1'><d1 length='10' length1='101'>ddd1</d1><d2>ddd2</d2></d><d sex='女'><d1 length='11'>ddd11</d1><d2>ddd22</d2></d></a>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);*/
		
		/** 10、单数组(数组对象用一个节点包起来)-带子节点-带属性(根节点/子节点) */
		//预期结果：{"a":{"@age":"66","b":"123","c":"张三","#TEXT":"","dp":{"d":[{"d1":{"#TEXT":"ddd1","@length":"10"},"d2":"ddd2","#TEXT":"","@sex":"男"},{"d1":{"#TEXT":"ddd11","@length":"11"},"d2":"ddd22","#TEXT":"","@sex":"女"}],"#TEXT":""}}}
		/*xmlStr = "<a age='66'><b>123</b><c>张三</c><dp><d sex='男'><d1 length='10'>ddd1</d1><d2>ddd2</d2></d><d sex='女'><d1 length='11'>ddd11</d1><d2>ddd22</d2></d></dp></a>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);*/
		
		/** 11、单数组-不带子节点-不带属性(根节点/子节点) */
		//预期结果：{"a":{"d":[{"d1":"ddd1","d2":"ddd2","#TEXT":""},{"d1":"ddd11","d2":"ddd22","#TEXT":""}],"b":"123","c":"张三","#TEXT":""}}
		/*xmlStr = "<a><b>123</b><c>张三</c><d><d1>ddd1</d1><d2>ddd2</d2></d><d><d1>ddd11</d1><d2>ddd22</d2></d></a>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);*/
		
		/** 12、单数组-不带子节点-带属性(子节点) */
		//预期结果：{"a":{"@age":"66","d":[{"#TEXT":"111","@sex1":"男1","@sex":"男"},{"#TEXT":"222","@sex":"女"}],"b":"123","c":"张三","#TEXT":"","@age1":"661"}}
		/*xmlStr = "<a age='66' age1='661'><b>123</b><c>张三</c><d sex='男' sex1='男1'>111</d><d sex='女'>222</d></a>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);*/
		
		/** 13、单数组-不带子节点-部分不带属性(子节点) */
		//预期结果：{"a":{"@age":"66","@age1":"661","b":"123","c":"张三","d":[{"@sex1":"男1","#TEXT":"111","@sex":"男"},{"#TEXT":"222","@sex":"女"},"333"],"#TEXT":""}}
		xmlStr = "<a age='66' age1='661'><b>123</b><c>张三</c><d sex='男' sex1='男1'>111</d><d sex='女'>222</d><d>333</d></a>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);
		
		/** 14、单数组(根部就是数组)-带子节点-带属性(根节点/子节点)【不支持】 */
		//预期结果：报错
		/*xmlStr = "<d sex='男' sex1='男1'><d1 length='10' length1='101'>ddd1</d1><d2>ddd2</d2></d><d sex='女'><d1 length='11'>ddd11</d1><d2>ddd22</d2></d>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);*/
		
		/** 15、数组包含数-带子节点-带属性(根节点/子节点) */
		//预期结果：{"a":{"@age":"66","d":[{"d1":{"#TEXT":"ddd1","@length":"10"},"d2":[{"d22":"ddd221","d21":{"#TEXT":"ddd211","@length":"11","@sex":"男"},"#TEXT":""},{"d22":"ddd222","d21":"ddd212","#TEXT":""}],"#TEXT":""},{"d1":{"#TEXT":"ddd11","@length":"10"},"d2":"ddd22","#TEXT":""}],"b":"123","c":"张三","#TEXT":""}}
		/*xmlStr = "<a age='66'><b>123</b><c>张三</c><d><d1 length='10'>ddd1</d1><d2><d21 length='11' sex='男'>ddd211</d21><d22>ddd221</d22></d2><d2><d21>ddd212</d21><d22>ddd222</d22></d2></d><d><d1 length='10'>ddd11</d1><d2>ddd22</d2></d></a>";
		jsonStr = XmlAndJsonUtils.xmlToJson(xmlStr);
		System.out.println(jsonStr);*/
	}
}
