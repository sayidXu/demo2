package com.xutt.sky.learn.io.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

/**
 * 补充WebStorm汉化包文件中丢失的key
 * 
 * @author Administrator
 *
 */
public class WebStormToCn {
	public static void main(String[] args) {
		try {
			String basePath = "D:\\tmp\\xxx\\"; // 要遍历的路径
			String cnPath = basePath + "resources_en_cn\\messages\\"; // 获取其file对象
			String enPath = basePath + "resources_en_en\\messages\\";
			File[] fs = new File(cnPath).listFiles(); // 遍历path下的文件和目录，放在File数组中
			for (File f : fs) { // 遍历File[]数组
				if (!f.isDirectory()) { // 若非目录(即文件)，则打印
					System.out.println("=================开始处理文件[" + f.getName() + "]");
					String descFilePath = cnPath + f.getName();
					String srcFilePath = enPath + f.getName();
					dealFile(descFilePath, srcFilePath);
					System.out.println("=================文件处理完成[" + f.getName() + "]");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}

	private static void dealFile(String descFilePath, String srcFilePath) {

		// 读文件(目标文件)
		Map<String, String> descFileMap = readFile(descFilePath);

		// 读文件(源文件)
		Map<String, String> srcFileMap = readFile(srcFilePath);

		// 比较
		Map<String, String> resultMap = compareMap(descFileMap, srcFileMap);

		// 输出文件
		writeFile(resultMap, descFilePath);
	}

	private static Map<String, String> readFile(String path) {
		BufferedReader bufferedReader = null;
		Map<String, String> destFileMap = new HashMap<String, String>();
		try {
			// 读文件
			Properties properties = new Properties();
			// 使用InPutStream流读取properties文件
			bufferedReader = new BufferedReader(new FileReader(path));
			properties.load(bufferedReader);

			Enumeration enumeration = properties.propertyNames();
			while (enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();
				String value = properties.getProperty(key);
				destFileMap.put(key, value);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				IOUtils.closeQuietly(bufferedReader);
			}
		}

		return destFileMap;
	}

	private static Map<String, String> writeFile(Map<String, String> resultMap, String path) {
		BufferedWriter bufferedWriter = null;
		Map<String, String> destFileMap = new HashMap<String, String>();
		try {
			// 写文件
			FileWriter writer = new FileWriter(path, true);
			bufferedWriter = new BufferedWriter(writer);
			for (String key : resultMap.keySet()) {
				bufferedWriter.write("\n" + key + "=" + resultMap.get(key) + "");
			}

			bufferedWriter.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bufferedWriter != null) {
				IOUtils.closeQuietly(bufferedWriter);
			}
		}

		return destFileMap;
	}

	private static Map<String, String> compareMap(Map<String, String> descFileMap, Map<String, String> srcFileMap) {
		Map<String, String> resultMap = new HashMap<String, String>();

		for (String key : srcFileMap.keySet()) {
			if (!descFileMap.containsKey(key)) {
				resultMap.put(key, srcFileMap.get(key));
			}
		}

		return resultMap;
	}

}
