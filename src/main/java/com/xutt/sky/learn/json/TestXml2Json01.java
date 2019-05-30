package com.xutt.sky.learn.json;

import java.io.ByteArrayOutputStream;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
  
import net.sf.json.JSON;  
import net.sf.json.JSONSerializer;  
import net.sf.json.xml.XMLSerializer;  
  
import org.dom4j.Document;  
import org.dom4j.DocumentHelper;  
import org.dom4j.Element;  
import org.dom4j.io.OutputFormat;  
import org.dom4j.io.XMLWriter;  

/**
 * xml2json之使用XmlSerializer.read()
 * 
 * 现状：自己实现，代码TextProtocolUtil.xmlToJSON()
 * 
 * @author Administrator
 *
 */
public class TestXml2Json01 {
	public static void main(String[] args) throws Exception {
		String xmlStr = "";
		String jsonStr = "";
		/* 1、xml带属性转json */
		//普通带属性
		xmlStr = "<a><b age='66'>123</b><c>张三</c></a>";
		jsonStr = xmltoJson(xmlStr);
		System.out.println(jsonStr);
		
		//对象包含对象
		xmlStr = "<a><b age='66'>123</b><c>张三</c><d><d1 length='10'>ddd</d1><d2>ddd</d2></d></a>";
		jsonStr = xmltoJson(xmlStr);
		System.out.println(jsonStr);
		
		//数组带属性
		xmlStr = "<a><b age='66'>123</b><c>张三</c><d><d1 length='10'>ddd1</d1><d2>ddd2</d2></d><d><d1 length='10'>ddd11</d1><d2>ddd22</d2></d></a>";
		jsonStr = xmltoJson(xmlStr);
		System.out.println(jsonStr);
		
		//数组包含数组带属性
		xmlStr = "<a><b age='66'>123</b><c>张三</c><d><d1 length='10'>ddd1</d1><d2><d21>ddd211</d21><d22>ddd221</d22></d2><d2><d21>ddd212</d21><d22>ddd222</d22></d2></d><d><d1 length='10'>ddd11</d1><d2>ddd22</d2></d></a>";
		jsonStr = xmltoJson(xmlStr);
		System.out.println(jsonStr);
	}
    
	/** 
     * map to xml xml <node><key label="key1">value1</key><key 
     * label="key2">value2</key>......</node> 
     *  
     * @param map 
     * @return 
     */  
    public static String maptoXml(Map map) {  
        Document document = DocumentHelper.createDocument();  
        Element nodeElement = document.addElement("node");  
        for (Object obj : map.keySet()) {  
            Element keyElement = nodeElement.addElement("key");  
            keyElement.addAttribute("label", String.valueOf(obj));  
            keyElement.setText(String.valueOf(map.get(obj)));  
        }  
        return doc2String(document);  
    }  
  
    /** 
     * list to xml xml <nodes><node><key label="key1">value1</key><key 
     * label="key2">value2</key>......</node><node><key 
     * label="key1">value1</key><key 
     * label="key2">value2</key>......</node></nodes> 
     *  
     * @param list 
     * @return 
     */  
    public static String listtoXml(List list) throws Exception {  
        Document document = DocumentHelper.createDocument();  
        Element nodesElement = document.addElement("nodes");  
        int i = 0;  
        for (Object o : list) {  
            Element nodeElement = nodesElement.addElement("node");  
            if (o instanceof Map) {  
                for (Object obj : ((Map) o).keySet()) {  
                    Element keyElement = nodeElement.addElement("key");  
                    keyElement.addAttribute("label", String.valueOf(obj));  
                    keyElement.setText(String.valueOf(((Map) o).get(obj)));  
                }  
            } else {  
                Element keyElement = nodeElement.addElement("key");  
                keyElement.addAttribute("label", String.valueOf(i));  
                keyElement.setText(String.valueOf(o));  
            }  
            i++;  
        }  
        return doc2String(document);  
    }  
  
    /** 
     * json to xml {"node":{"key":{"@label":"key1","#text":"value1"}}} conver 
     * <o><node class="object"><key class="object" 
     * label="key1">value1</key></node></o> 
     *  
     * @param json 
     * @return 
     */  
    public static String jsontoXml(String json) {  
        try {  
            XMLSerializer serializer = new XMLSerializer();  
            JSON jsonObject = JSONSerializer.toJSON(json);  
            return serializer.write(jsonObject);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * xml to map xml <node><key label="key1">value1</key><key 
     * label="key2">value2</key>......</node> 
     *  
     * @param xml 
     * @return 
     */  
    public static Map xmltoMap(String xml) {  
        try {  
            Map map = new HashMap();  
            Document document = DocumentHelper.parseText(xml);  
            Element nodeElement = document.getRootElement();  
            List node = nodeElement.elements();  
            for (Iterator it = node.iterator(); it.hasNext();) {  
                Element elm = (Element) it.next();  
                map.put(elm.attributeValue("label"), elm.getText());  
                elm = null;  
            }  
            node = null;  
            nodeElement = null;  
            document = null;  
            return map;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * xml to list xml <nodes><node><key label="key1">value1</key><key 
     * label="key2">value2</key>......</node><node><key 
     * label="key1">value1</key><key 
     * label="key2">value2</key>......</node></nodes> 
     *  
     * @param xml 
     * @return 
     */  
    public static List xmltoList(String xml) {  
        try {  
            List<Map> list = new ArrayList<Map>();  
            Document document = DocumentHelper.parseText(xml);  
            Element nodesElement = document.getRootElement();  
            List nodes = nodesElement.elements();  
            for (Iterator its = nodes.iterator(); its.hasNext();) {  
                Element nodeElement = (Element) its.next();  
                Map map = xmltoMap(nodeElement.asXML());  
                list.add(map);  
                map = null;  
            }  
            nodes = null;  
            nodesElement = null;  
            document = null;  
            return list;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * xml to json <node><key label="key1">value1</key></node> 转化为 
     * {"key":{"@label":"key1","#text":"value1"}} 
     *  
     * @param xml 
     * @return 
     */  
    public static String xmltoJson(String xml) {
    	String rootXml = "<root>"+xml+"</root>";
        XMLSerializer xmlSerializer = new XMLSerializer();  
        return xmlSerializer.read(rootXml).toString();
    }  
  
    /** 
     *  
     * @param document 
     * @return 
     */  
    public static String doc2String(Document document) {  
        String s = "";  
        try {  
            // 使用输出流来进行转化  
            ByteArrayOutputStream out = new ByteArrayOutputStream();  
            // 使用UTF-8编码  
            OutputFormat format = new OutputFormat("   ", true, "UTF-8");  
            XMLWriter writer = new XMLWriter(out, format);  
            writer.write(document);  
            s = out.toString("UTF-8");  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return s;  
    }  
}
