package com.xutt.sky.learn.io.net.netty.n2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyThread implements Runnable{
	private DemoServerHandler handler;
	
	public MyThread(DemoServerHandler handler){
		this.handler = handler;
	}
	
	@Override
	public void run() {
		try {
			//控制台输入
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			for (;;) {
			    String line = in.readLine();
			    if (line == null) {
			        continue;
			    }
			    //向服务端发送数据
			    if(handler.getCtx() != null){
				    handler.getCtx().writeAndFlush(line);
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
