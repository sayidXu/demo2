package com.xutt.sky.learn.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


public class JDomTest {

	public static void main(String[] args) {
		String xmlStr = "<root><event>registClient</event><request><key>111</key></request></root>";
		try {
			InputStream is = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			
			Element root = doc.getRootElement();
			System.out.println("root.getName()===>" + root.getName());
			System.out.println("root.getValue()===>" + root.getValue());
			System.out.println("root.getText()===>" + root.getText());
			System.out.println("root.getTextTrim()===>" + root.getTextTrim());
			System.out.println("root.getTextNormalize()===>" + root.getTextNormalize());
			System.out.println("root.getChildText():" + root.getChildText("event"));
			
			Element eventElement = root.getChild("event");
			String event = eventElement.getValue();
			System.out.println("event:" + event);
			System.out.println("eventElement.toString():" + eventElement.toString());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("xmlToJSON error:"+e.getMessage());
		}
	}

}
