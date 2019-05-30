package com.xutt.sky.learn.thread;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueDemo1 {

	private static Queue<Integer> widgetCacheQueue = new ConcurrentLinkedQueue<Integer>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			Thread tt = new Thread(new Rund());
			tt.start();
		}
		//查看结果是否是10000
		System.out.println("size:" + widgetCacheQueue.size());
	}

	static class Rund implements Runnable {

		public void run() {
			test();
		}

		/**
		 * 1W次，总有那么几次线程不安全
		 */
		public void test() {
			widgetCacheQueue.offer(1);
		}

	}

}
