package com.xutt.sky.learn.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {
	
	public static void main(String[] args){
		ExecutorService executor = Executors.newSingleThreadExecutor();
		FutureTask<?> futureTask = new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				for(int i=0;i<10000;i++){
					System.out.println("currentThreadId：" + Thread.currentThread().getId() + ",i:" + i);
					//Thread.sleep(1);
				}
				return null;
			}
		});
		
		FutureTask<?>  futureTask1 = new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("currentThreadId：" + Thread.currentThread().getId() + ",futureTask cancel");
				return null;
			}
		});
		
		executor.execute(futureTask);
		System.out.println("futureTask start");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		futureTask.cancel(true);
		
		executor.execute(futureTask1);
//		System.out.println("futureTask cancel");
		
	}
	
	

}
