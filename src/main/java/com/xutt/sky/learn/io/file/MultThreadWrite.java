package com.xutt.sky.learn.io.file;

import java.util.Date;

import com.xutt.sky.util.FileUtils;

public class MultThreadWrite implements Runnable{
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new MultThreadWrite());
			thread.start();
		}
	}

	@Override
	public void run() {
		try {
			long threadId = Thread.currentThread().getId();
			String path = "555.txt";
			String content = threadId + "hello";
			StringBuffer sb = new StringBuffer();
			for(int j=0;j<10000;j++){
				sb.append(content);
			}
			sb.append("\n");
			
			int i = 1;
			long beginTime = new Date().getTime();
			while (i<=1) {
				++i;
				//System.out.println("["+threadId+"]第"+ (i++) +"次写入数据！");
				FileUtils.writeFile(path, sb.toString(), true);
			}
			long endTime = new Date().getTime();
			System.out.println("=======>"+threadId+":" + (endTime-beginTime));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
