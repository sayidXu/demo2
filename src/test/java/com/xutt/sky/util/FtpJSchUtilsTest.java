package com.xutt.sky.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jcraft.jsch.ChannelSftp;
import com.xutt.sky.util.FtpJSchUtils.FtpConnection;
import com.xutt.sky.util.FtpJSchUtils.FtpConnectionConfig;

/**
 * 多文件上传/下载，文件不释放
 * @author Administrator
 *
 */
public class FtpJSchUtilsTest {
	Logger logger = LoggerFactory.getLogger(FtpJSchUtilsTest.class);
	public static void main(String[] args) throws InterruptedException {
		//upload();
		download();
	}
	/**
	 * 多文件上传测试，检查上传后jvm不停或者gc不释放的情况下源文件是否可以正常删除
	 * 
	 * @param args
	 */
	public static void upload() {
		FtpConnectionConfig config = new FtpConnectionConfig();
		config.setHost("172.16.90.34");
		config.setPort("22");
		config.setUser("root");
		config.setPassword("hadoop");
//		config.setTimeout(10000);
		
		FtpConnection ftpConnection = FtpJSchUtils.openConnection(config);
		ChannelSftp channelSftp = ftpConnection.getChannelSftp();
		
		//上传文件
		String uploadFilePath = "C:\\Users\\Administrator\\Desktop\\tmp\\222";
		String uploadPath = "/root/xutt";
		String clientFileName = "aa.*\\.txt";
		//FtpJSchUtils.upload(uploadFile,uploadPath,ftpConnection);
		
		List<String> fileNameList = null;
		FileInputStream input = null;
		try {
			File file = new File(uploadFilePath);
			
			if (file.isDirectory()) {// clientFilePath必须为文件夹路径，则上传文件夹下所有的文件（但是不上传文件夹）
				String[] files = file.list();// 得到所有文件列表
				fileNameList = new ArrayList<String>();
				boolean flag = false;
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						flag = (files[i]).matches(clientFileName);// 文件名称为正则表达式
						File f = new File(uploadFilePath + File.separator + files[i]);
						if (flag && f.isFile()) {
							fileNameList.add(files[i]);
						}
					}
				}
				channelSftp.cd(uploadPath);
				for (String fileName : fileNameList) {
					input = new FileInputStream(uploadFilePath + File.separator + fileName);
					channelSftp.put(input, fileName);
					IOUtils.closeQuietly(input);
				}
			}
			
			// 上传后是否需要删除掉原文件(1.删除，2.不删除)
			for (String fileName : fileNameList) {
				File delFile = new File(uploadFilePath + File.separator  + fileName);
				if (delFile.exists()) {
					delFile.delete();
				}
			}
			Thread.sleep(1000000000l);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//IOUtils.closeQuietly(input);
		}	

		FtpJSchUtils.closeConnection(ftpConnection);
	}
	
	
	/**
	 * 多文件下载测试，检查下载后jvm不停或者gc不释放的情况下存放文件是否外部删除
	 * 
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void download() throws InterruptedException {
		FtpConnectionConfig config = new FtpConnectionConfig();
		config.setHost("172.16.90.34");
		config.setPort("22");
		config.setUser("root");
		config.setPassword("hadoop");
//		config.setTimeout(10000);
		
		FtpConnection ftpConnection = FtpJSchUtils.openConnection(config);
		ChannelSftp channelSftp = ftpConnection.getChannelSftp();
		
		//上传文件
		String targetPath = "C:\\Users\\Administrator\\Desktop\\tmp\\333";
		String serverPaths = "['/root/xutt/aaa1.txt','/root/xutt/aabbb20190426.txt']";
		//FtpJSchUtils.upload(uploadFile,uploadPath,ftpConnection);
		
		System.out.println("下载文件开始...");
		FileOutputStream outStream = null;
		try {
			JSONArray serverPathsArr = JSON.parseArray(serverPaths);
			for (Object obj : serverPathsArr) {
				String serverPath = (String) obj;
				String fileName = new File(serverPath).getName();
				//校验targetDir存在
				File targetDir = new File(targetPath);
				
				if (!targetDir.exists()) {
					targetDir.mkdirs();
				}
				File file = new File(targetDir, fileName);
				outStream = new FileOutputStream(file);
				channelSftp.get(serverPath, outStream);
			}
		} catch (Exception e) {
			throw new RuntimeException("下载文件失败！", e);
		} finally {
			IOUtils.closeQuietly(outStream);
		}
		System.out.println("下载文件成功！");

		FtpJSchUtils.closeConnection(ftpConnection);
		
		Thread.sleep(1000000000l);
	}

}
