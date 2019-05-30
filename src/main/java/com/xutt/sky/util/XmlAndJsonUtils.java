package com.xutt.sky.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class XmlAndJsonUtils {

	/**
	 * xml转json
	 * 
	 * 当前节点存在属性或者子节点时，节点的文本内容存放到节点下的“#TEXT”中；否则直接存储到当前节点下
	 * 
	 * @param xml字符串
	 * @return json字符串
	 */
	public static String xmlToJson(String xml) {
		InputStream is = null;
		JSONObject obj = new JSONObject();
		try {
			is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			// 处理root节点的子节点
			Map map = iterateElement(root);

			// 转换root节点本身
			if (map.isEmpty()) {
				obj.put(root.getName(), root.getTextTrim());
			} else {
				obj.put(root.getName(), map);
			}
			return obj.toString();
		} catch (Exception e) {
			// e.printStackTrace();
			throw new RuntimeException("xmlToJSON error:", e);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	private static Map iterateElement(Element root) {
		List childrenList = root.getChildren();
		List attributeList = root.getAttributes();
		Element element = null;
		Map map = new HashMap();
		List list = null;

		// 子节点
		for (int i = 0; i < childrenList.size(); ++i) {
			list = new ArrayList();
			element = (Element) childrenList.get(i);
			// 查看当前节点是否有子节点
			if (element.getChildren().size() > 0) {
				if (root.getChildren(element.getName()).size() > 1) {
					if (map.containsKey(element.getName())) {
						list = (List) map.get(element.getName());
					}
					list.add(iterateElement(element));
					map.put(element.getName(), list);
				} else {
					map.put(element.getName(), iterateElement(element));
				}
			}
			// 查看当前节点是否是数组(存在多个相同节点，则认为是数组；否则是对象)
			else if (root.getChildren(element.getName()).size() > 1) {
				// 属性
				Map elementMap = new HashMap();
				List elementAttrList = element.getAttributes();
				for (int j = 0; j < elementAttrList.size(); ++j) {
					Attribute attribute = (Attribute) elementAttrList.get(j);
					elementMap.put("@" + attribute.getName(), attribute.getValue());
				}

				// 文本
				if (map.containsKey(element.getName())) {
					list = (List) map.get(element.getName());
				}

				if (!elementMap.isEmpty()) {
					elementMap.put("#TEXT", element.getTextTrim());
					list.add(elementMap);
				} else {
					list.add(element.getTextTrim());
				}
				map.put(element.getName(), list);
			}
			// 当前子节点下只有文本内容
			else {
				// 属性
				Map elementMap = new HashMap();
				List elementAttrList = element.getAttributes();
				for (int j = 0; j < elementAttrList.size(); ++j) {
					Attribute attribute = (Attribute) elementAttrList.get(j);
					elementMap.put("@" + attribute.getName(), attribute.getValue());
				}

				// 文本
				if (!elementMap.isEmpty()) {
					elementMap.put("#TEXT", element.getTextTrim());
					map.put(element.getName(), elementMap);
				} else {
					map.put(element.getName(), element.getTextTrim());
				}
			}
		}

		// 属性
		for (int i = 0; i < attributeList.size(); ++i) {
			Attribute attribute = (Attribute) attributeList.get(i);
			map.put("@" + attribute.getName(), attribute.getValue());
		}

		// 文本内容
		if (!map.isEmpty()) {
			map.put("#TEXT", root.getTextTrim());
		}
		return map;
	}

	/**
	 * json转xml
	 * 
	 * @param json字符串
	 * @return xml字符串
	 */
	public static String jsonToXml(Object json) {
		if (json == null) {
			return null;
		}
		json = net.sf.json.JSONObject.fromObject(com.alibaba.fastjson.JSONObject.parseObject(json.toString()));
		// json = net.sf.json.JSONObject.fromObject(json); 注销原因：字符串json直接转为json。
		Element elements = new Element("Request");
		getXMLFromObject(json, "", elements);
		XMLOutputter xmlOut = new XMLOutputter();
		String res = xmlOut.outputString(elements);
		res = res.substring(9, res.length() - 10);
		return res;
	}

	private static void getXMLFromObject(Object obj, String tag, Element parent) {
		if (obj == null) {
			return;
		}

		if (obj instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) obj;
			for (Iterator localIterator = jsonObject.keySet().iterator(); localIterator.hasNext();) {
				Object temp = localIterator.next();

				String eleStr = temp.toString();
				Object childValue = jsonObject.get(temp);
				// 判断当前节点是否是父节点的属性字段
				if (eleStr.startsWith("@")) {
					String attributeName = eleStr.substring(1);
					parent.setAttribute(attributeName, (String) childValue);
					continue;
				}
				// 判断当前节点是否是父节点的文本内容
				if ("#TEXT".equals(eleStr)) {
					parent.addContent((String) childValue);
					continue;
				}

				Element child = new Element(eleStr);

				// 此处childValue有三种场景：数组、对象、文本值
				// 数组
				if (childValue instanceof JSONArray) {
					getXMLFromObject(childValue, eleStr, parent);
				}
				// 对象
				else if (childValue instanceof JSONObject) {
					parent.addContent(child);
					getXMLFromObject(childValue, eleStr, child);
				}
				// 文本或null
				else {
					parent.addContent(child);
					getXMLFromObject(childValue, eleStr, child);
				}
			}
		} else if (obj instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray) obj;
			for (int i = 0; i < jsonArray.size(); ++i) {
				Object childValue = jsonArray.get(i);
				Element child = new Element(tag);
				parent.addContent(child);
				getXMLFromObject(childValue, tag, child);
			}
		} else if (obj instanceof Date) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			parent.setText(sf.format((Date) obj));
		} else {
			parent.setText(obj.toString());
		}
	}
}
