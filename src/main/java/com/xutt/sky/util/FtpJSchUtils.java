package com.xutt.sky.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
/**
 * JSch
 * @author Administrator
 *
 */
public class FtpJSchUtils {
	private static Logger logger = LoggerFactory.getLogger(FtpJSchUtils.class);
	
	public static void main(String[] args){
		FtpConnection ftpConnection = null;
		try {
			FtpConnectionConfig config = new FtpConnectionConfig();
			config.setHost("172.16.90.34");
			config.setPort("22");
			config.setUser("root");
			config.setPassword("hadoop");
//			config.setTimeout(10000);
			
			ftpConnection = FtpJSchUtils.openConnection(config);
			
			//上传文件
			String uploadFile = "D:\\Workspace\\sky\\input.txt";
			String uploadPath = "/root/xutt";
			//upload(uploadFile,uploadPath,ftpConnection);
			
			//下载文件
			/*String downloadFile = "/root/xutt/01af083a-5d1d-4ea9-9e1f-4b72bc6fef66t.txt";
			String saveFile = "D:\\Workspace\\sky\\input2.txt";
			download(downloadFile,saveFile,ftpConnection);*/
			
			//删除文件
			/*String deleteFile = "/root/xutt/01af083a-5d1d-4ea9-9e1f-4b72bc6fef66t.txt";
			delete(deleteFile,ftpConnection);*/
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			FtpJSchUtils.closeConnection(ftpConnection);
		}
	}
	
	/**
	 * 打开连接
	 * 
	 * @param config
	 * @return
	 */
	public static FtpConnection openConnection(FtpConnectionConfig config){
		logger.info("正在获取ftp连接...+["+config.toString()+"]");
		long beginTime = new Date().getTime();
		FtpConnection ftpConnection = new FtpConnection();
		try {
			JSch jsch = new JSch();
 
			String host = config.getHost();
			int port = Integer.parseInt(config.getPort());
			String userName = config.getUser();
			String password = config.getPassword();
			
			//获取sshSession  账号-ip-端口
			Session sshSession =jsch.getSession(userName, host, port);
			sshSession.setPassword(password);
			
			Properties sshConfig = new Properties();
			
			//仅密码校验(提高获取连接的速度，测试结果：无效)
//			sshConfig.put("PreferredAuthentications","password");
			
			//严格主机密钥检查
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			
			//设置超时
			sshSession.setTimeout(config.getTimeout());
	        
			//开启sshSession链接(需要5+s的时间)
			sshSession.connect();
			
			//获取sftp通道
			ChannelSftp channel = (ChannelSftp)sshSession.openChannel("sftp");
			//开启
			channel.connect();
			
			ftpConnection.setSession(sshSession);
			ftpConnection.setChannelSftp(channel);
		} catch (Exception e) {
			logger.error("获取ftp连接失败!["+config.toString()+"]", e);
		}
		logger.info("获取ftp连接成功![" + config.toString() + "]");
		long endTime = new Date().getTime();
		logger.info("获取ftp连接耗时[" + (endTime - beginTime) + "]ms");
		return ftpConnection;
	}
	
	/**
	 * 关闭连接
	 * 
	 * @throws Exception
	 */
	public static void closeConnection(FtpConnection ftpConnection) {
		Session session = ftpConnection.getSession();
		Channel channel = ftpConnection.getChannelSftp();
		if (null != channel) {
			try {
				channel.disconnect();
			} catch (Exception e) {
				logger.error("关闭SFTP通道发生异常:", e);
			}
		}
		if (null != session) {
			try {
				session.disconnect();
			} catch (Exception e) {
				logger.error("SFTP关闭 session异常:", e);
			}
		}
	}
	
	
	/**
	 * 
	 * @param sourceFile 待上传文件路径
	 * @return 服务器上文件名
	 */
	
	/**
	 * 
	 * @param uploadFile 上传文件
	 * @param uploadPath 上传路径
	 * @param ftpConnection 连接
	 * @return 目标文件名成
	 */
	public static String upload(String uploadFile,String uploadPath,FtpConnection ftpConnection) {
		logger.info("上传文件[" + uploadFile + "]开始...");
		File file = null;
		String fileName = null;
		FileInputStream fi = null;
		ChannelSftp channelSftp = ftpConnection.getChannelSftp();
		try {
			channelSftp.cd(uploadPath);
			file = new File(uploadFile);
			//获取随机文件名
			fileName  = UUID.randomUUID().toString() + file.getName().substring(file.getName().length()-5);
			//文件名是 随机数加文件名的后5位
			fi = new FileInputStream(file);
//			sftp.setFilenameEncoding("UTF-8");
			channelSftp.put(fi, fileName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传文件[" + uploadFile + "]失败！",e);
		}finally{
			IOUtils.closeQuietly(fi);
		}
		logger.info("上传文件[" + uploadFile + "]成功！");
		return file == null ? null : fileName;
	}
	
	/**
	 * 下载文件
	 * 
	 * @param downloadFile 下载文件名称(带路径)
	 * @param saveFile 本地保存文件名称(带路径)
	 */
	public static void download(String downloadFile, String saveFile, FtpConnection ftpConnection) {
		logger.info("下载文件[" + downloadFile + "]开始...");
		try {
			// sftp.cd(directory);

			File file = new File(saveFile);
			ChannelSftp channelSftp = ftpConnection.getChannelSftp();
			channelSftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			logger.error("下载文件[" + downloadFile + "]失败！", e);
		}
		logger.info("下载文件[" + downloadFile + "]成功！");
	}
	
	/**
	 * 删除文件
	 * 
	 * @param deleteFile
	 *            要删除的文件名字
	 * @param ftpConnection ftp连接
	 */
	public static void delete(String deleteFile,FtpConnection ftpConnection) {
		logger.info("删除文件[" + deleteFile + "]开始...");
		try {
			ChannelSftp channelSftp = ftpConnection.getChannelSftp();
			channelSftp.rm(deleteFile);
		} catch (Exception e) {
			logger.error("删除文件["+deleteFile+"]失败!",e);
		}
		logger.info("删除文件[" + deleteFile + "]成功！");
	}
	
	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param ftpConnection
	 *            ftp连接
	 * @return
	 * @throws SftpException
	 */
	public Vector listFiles(String directory, FtpConnection ftpConnection) throws SftpException {
		ChannelSftp channelSftp = ftpConnection.getChannelSftp();
		return channelSftp.ls(directory);
	}
	
	/**
	 * ftp连接
	 * @author Administrator
	 *
	 */
	static class FtpConnection {

		private Session session;
		private ChannelSftp channelSftp;

		public Session getSession() {
			return session;
		}

		public void setSession(Session session) {
			this.session = session;
		}

		public ChannelSftp getChannelSftp() {
			return channelSftp;
		}

		public void setChannelSftp(ChannelSftp channelSftp) {
			this.channelSftp = channelSftp;
		}

	}
	
	static class FtpConnectionConfig {
		// 主机ip
		private String host;
		// 端口
		private String port;
		// 账号
		private String user;
		// 密码
		private String password;
		
		// 建立连接超时时间(单位：毫秒)
		private int timeout;
		
		public FtpConnectionConfig(){
			//默认60s
			this.timeout = 60000;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public String getPort() {
			return port;
		}

		public void setPort(String port) {
			this.port = port;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public int getTimeout() {
			return timeout;
		}

		public void setTimeout(int timeout) {
			this.timeout = timeout;
		}
		
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			buffer.append("host:" + this.host + ",");
			buffer.append("port:" + this.port + ",");
			buffer.append("user:" + this.user + ",");
			// buffer.append("password:"+this.password+",");
			return buffer.toString();
		}
	}
}

