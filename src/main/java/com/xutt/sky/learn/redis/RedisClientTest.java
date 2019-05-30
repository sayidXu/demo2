package com.xutt.sky.learn.redis;

import redis.clients.jedis.Jedis;

public class RedisClientTest {

	public static void main(String[] args) {
		RedisClient redisClient = new RedisClient();

		//hashOperate(redisClient);
		
		

		redisClient.destroyPool();
	}
	
	public void ListOperate(RedisClient redisClient) {
		Jedis jedis = null;
		try {
			jedis = redisClient.getConnection();
			jedis.rpush("engineId_msgInfos", "{ \"data\": { \"MSG_CONTENT\": \"某某某于什么事件在什么地方进行就诊，就诊结果显示是慢性表\", \"MSG_ID\": \"\", \"MSG_TITLE\": \"您有一份数据需要上报\", \"MSG_TYPE\": \"1\", \"PAGE_CODE\": \"协同页面库id\", \"PARAMS\": \"\", \"URL\": \"\" }, \"dataType\": \"数据类型\", \"terminalCode\": \"工作台编码\"}");
			
			String terminalInfo = jedis.hget("terminalInfos", "222");
			System.out.println(terminalInfo);
		} finally {
			// 使用后一定关闭，还给连接池
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public static void hashOperate(RedisClient redisClient) {
		Jedis jedis = null;
		try {
			jedis = redisClient.getConnection();
//			jedis.hset("terminalInfos", "111","{\"ENGINE_ID\":\"1111\",\"ACCOUNT_CODE\":\"接入账号\",\"ACCOUNT_PWD\":\"接入密码\",\"ROLE_CODES\":[\"1111\",\"2222\"]}");
//			jedis.hset("terminalInfos", "222","{\"ENGINE_ID\":\"2222\",\"ACCOUNT_CODE\":\"接入账号\",\"ACCOUNT_PWD\":\"接入密码\",\"ROLE_CODES\":[\"1111\",\"2222\"]}");
			String terminalInfo = jedis.hget("terminalInfos", "222");
			System.out.println(terminalInfo);
		} finally {
			// 使用后一定关闭，还给连接池
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}
}
