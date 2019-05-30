package com.xutt.sky.portal.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	static Logger logger = Logger.getLogger(PropertiesUtil.class);

	private static Properties p = new Properties();

	static {
		InputStream inputStream1 = PropertiesUtil.class.getResourceAsStream("/config.properties");
		try {
			p.load(inputStream1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("load error:" + e);
		}

	}

	public static String getValue(String key) {
		return p.getProperty(key);
	}

}
