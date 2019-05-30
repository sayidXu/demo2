package com.xutt.sky.learn.redis.list;

import com.xutt.sky.learn.redis.RedisClient;

import redis.clients.jedis.Jedis;

public class MessageProducerTest extends Thread{
	public static void main(String[] args){
		MessageProducerTest messageProducer = new MessageProducerTest();
		Thread t1 = new Thread(messageProducer, "thread1");
        Thread t2 = new Thread(messageProducer, "thread2");
        Thread t3 = new Thread(messageProducer, "thread3");
        Thread t4 = new Thread(messageProducer, "thread4");
        Thread t5 = new Thread(messageProducer, "thread5");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
	}
	
	@Override
    public void run() {
		RedisClient redisClient = new RedisClient();
		
        Jedis jedis = null;
		try {
			jedis = redisClient.getConnection();
			int i = 0;
			Thread currentThread = Thread.currentThread();
	        while(true) {
	            putMessage(jedis,"message_" + currentThread.getId()+ "_" + i);
	            i++;
	            currentThread.sleep(5000L);
	        }
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 使用后一定关闭，还给连接池
			if (jedis != null) {
				jedis.close();
			}
			redisClient.destroyPool();
		}
    }
	
	public void putMessage(Jedis jedis,String message) {
        jedis.rpush("messageCount", message);
        System.out.println(Thread.currentThread().getName() + " put message:"+message+"!");
    }
}
