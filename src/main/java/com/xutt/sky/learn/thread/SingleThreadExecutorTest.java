package com.xutt.sky.learn.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorTest {
	public static void main(String[] args){
		ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
		for(int i =0;i<10;i++){
			threadExecutor.execute(new MyThread(i));
		}
		System.out.println("=====>main() is over.");
	}
	
	static class MyThread implements Runnable{
		public int index; 
		
		public MyThread(int index){
			this.index = index;
		}

		@Override
		public void run() {
			try {
				while(true){
					long currentThreadId = Thread.currentThread().getId();
					System.out.println("======>index:"+index+",currentThreadId:"+currentThreadId);
					Thread.currentThread().sleep(3000L);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
