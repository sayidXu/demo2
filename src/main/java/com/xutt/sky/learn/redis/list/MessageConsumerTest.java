package com.xutt.sky.learn.redis.list;

import java.util.List;

import com.xutt.sky.learn.redis.RedisClient;

import redis.clients.jedis.Jedis;

public class MessageConsumerTest extends Thread{
	public static void main(String[] args){
		MessageConsumerTest messageConsumer = new MessageConsumerTest();
		Thread t1 = new Thread(messageConsumer, "thread1");
        Thread t2 = new Thread(messageConsumer, "thread2");
        Thread t3 = new Thread(messageConsumer, "thread3");
        Thread t4 = new Thread(messageConsumer, "thread4");
        Thread t5 = new Thread(messageConsumer, "thread5");
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
	        while(true) {
	            getMessage(jedis);
	        }
		} finally {
			// 使用后一定关闭，还给连接池
			if (jedis != null) {
				jedis.close();
			}
			redisClient.destroyPool();
		}
    }
	
	public void getMessage(Jedis jedis) {
        List<String> message = jedis.blpop(0,"messageCount");
        System.out.println(Thread.currentThread().getName() + " get message:"+message+"!");
    }
}
