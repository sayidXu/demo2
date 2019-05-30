package com.xutt.sky.learn.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClient {
    private JedisPool jedisPool;
    
    public RedisClient() 
    { 
    	initPool();
    }
 
    /**
     * 初始化非切片池
     */
    private void initPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        //最大连接数，连接全部用完，进行等待
        config.setMaxTotal(20);
        //最大空余数
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000l);
        config.setTestOnBorrow(false);
//        jedisPool = new JedisPool(config,"172.16.90.34",6379);
        jedisPool = new JedisPool(config,"172.16.90.34",6379,10000,"test123");
    }
    
    /**
     * 销毁非切片池
     */
    public void destroyPool() 
    { 
    	if(jedisPool!=null){
    		jedisPool.close();
    	}
    }

    public Jedis getConnection() {
    	Jedis jedis = jedisPool.getResource();
        return jedis;
    }
}
