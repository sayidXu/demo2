package com.xutt.sky.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	public static void main(String args[]) {
//       writeFile("111"+File.separator+"221"+File.separator+"3331","测试内容");
		
//		String content = readFile("C:\\Users\\Administrator\\Desktop\\tmp\\222\\\\aaa20190426.txt");
//		logger.debug(content);
		
//		delFile("222.txt");
		
		
		
		Scanner in = null;
		try {
			String filePath="C:\\Users\\Administrator\\Desktop\\tmp\\222\\\\bbb20190426.txt";
			String rowSign="\\$\\$";
			in = new Scanner(new File(filePath)).useDelimiter("\\s*"+rowSign+"\\s*");
			String lineData = "";
			String dataSizeError = "";
			while (in.hasNext()) {
				dataSizeError = "";
				lineData = "";
				lineData = in.next();
				byte[]  buff = lineData.getBytes();
				int i = buff.length;
				/*if (i > 1024 * 1024) {//如果按照行分隔符得到的字符串大小>限定值时，直接入errorLog表，
					System.out.println("aaaaaaaaaaaa");
				}*/
				System.out.println(lineData + "yangbo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
    }
    
    /**
     * 读取指定文件内容
     * 
     * @param path 指定文件路径
     * @return
     */
	public static String readFile(String path) {
		StringBuffer content = new StringBuffer();
		BufferedReader br = null;
		try {
			FileReader reader = new FileReader(path);
			// 建立一个对象，它把文件内容转成计算机能读懂的语言
			br = new BufferedReader(reader);
			String lineStr;
			// 网友推荐更加简洁的写法
			while ((lineStr = br.readLine()) != null) {
				// 一次读入一行数据
				content.append(lineStr).append("\n");
			}
		} catch (FileNotFoundException e) {
			logger.warn("读取文件[" + path + "]时发生FileNotFoundException异常", e);
		} catch (IOException e) {
			logger.warn("读取文件[" + path + "]时发生IOException异常", e);
		} finally {
			IOUtils.closeQuietly(br);
		}

		return content.toString();
	}
    
	/**
	 * 写入指定内容到指定文件(覆盖原内容)
	 * @param path 指定文件路径
	 * @param content 指定内容
	 * 
	 */
	public static void writeFile(String path, String content) {
		writeFile(path, content,false);
	}
	
	/**
	 * 写入指定内容到指定文件
	 * @param path 指定文件路径
	 * @param content 指定内容
	 * @param append 写入内容是否追加都文件末尾（true追加，false不追加）
	 */
	public static void writeFile(String path, String content, boolean append) {
		BufferedWriter bw = null;
		try {
			File file = new File(path);

			if (!file.exists()) {
				File parentFile = file.getParentFile();
				if (parentFile != null && !parentFile.exists()) {
					//创建多级目录
					parentFile.mkdirs();
				}

				// 创建新文件,有同名的文件的话直接覆盖
				file.createNewFile();
			}

			FileWriter writer = new FileWriter(file, append);
			bw = new BufferedWriter(writer);
			bw.write(content);
			// 把缓存区内容压入文件
			bw.flush();
		} catch (FileNotFoundException e) {
			logger.warn("写入文件[" + path + "]时发生FileNotFoundException异常", e);
		} catch (IOException e) {
			logger.warn("读取文件[" + path + "]时发生IOException异常", e);
		} finally {
			IOUtils.closeQuietly(bw);
		}
	}
	
	/**
	 * 删除指定文件
	 * @param path 指定文件路径
	 * @return
	 */
	public static boolean delFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			return file.delete();
		}
		return true;
	}
}
