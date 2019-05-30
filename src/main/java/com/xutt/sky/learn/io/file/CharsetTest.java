package com.xutt.sky.learn.io.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.SortedMap;

import org.apache.commons.io.IOUtils;

public class CharsetTest {

	public static void main(String[] args) {
//		printCharset();
		writeWithCharset();
	}
	
	/**
	 * 列出jvm支持的所有字符集编码
	 */
	public static void printCharset(){
		SortedMap<String,Charset>  map = Charset.availableCharsets();
		for (String alias : map.keySet())
		{
			// 输出字符集的别名
			System.out.println(alias);
		}
	}
	
	/**
	 * 写文件-字符集编码
	 * 
	 * 写文件时，文件编码由第一次写入的内容编码决定
	 * 
	 */
	public static void writeWithCharset(){
		String targetPath = "C:\\Users\\Administrator\\Desktop\\tmp\\333";
		String targetFileName = "aaa3.txt";
		String fileData = "aaa中国人民万岁bbb";
		String encoding = "gbk";
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			// 确保targetDir存在
			File targetDir = new File(targetPath);
			if (!targetDir.exists()) {
				targetDir.mkdirs();
			}
			File targetFile = new File(targetDir, targetFileName);
			if (!targetFile.exists()) {
				targetFile.createNewFile();// 创建文档
			}
//			fw = new FileWriter(targetFile, true);
//			bw = new BufferedWriter(fw);
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile,true), encoding));
			bw.write(fileData);
		} catch (Exception e) {
			throw new RuntimeException("写入文件时出错！", e);
		} finally{
			IOUtils.closeQuietly(bw);
		}
	}
	
	/**
	 * 读文件时-字符集编码
	 * 
	 * Scanner指定分隔符读取文件内容时，如果字符编码不匹配，则会导致分隔符无法进行正常分割，从而“读不到数据”
	 * 
	 */
	public static void readWithCharset(){
		String filePath = "C:\\Users\\Administrator\\Desktop\\tmp\\222\\aaa.txt";
		String characterSet = "gb2313";
		String rowSign = "&&\r";
		Scanner in = null;
		try {
//			in = new Scanner(new File(filePath)).useDelimiter("\\s*"+rowSign+"\\s*");
			in = new Scanner(new File(filePath),characterSet).useDelimiter("\\s*"+rowSign+"\\s*");
			String lineData = "";
			String dataSizeError = "";
			while (in.hasNext()) {
				dataSizeError = "";
				lineData = "";
				lineData = in.next();
				byte[]  buff = lineData.getBytes();
				int i = buff.length;
				System.out.println("lineData:" + lineData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

}
