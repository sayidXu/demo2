package com.xutt.sky.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UUIDUtils {
	public static void main(String[] args){
		long uuid = getUUID();
		System.out.println(uuid);
	}
	
	/**
	 * uuid组成:yyyymmddhh24missSSS+6位随机数
	 * @return
	 */
	public static long getUUID(){
		long uuid = 0;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime = format.format(new Date());
		//5位随机数
		int randomNum = (int)((Math.random()*9+1)*10000);
		
		uuid = Long.parseLong((currentTime + String.valueOf(randomNum)));
		
		return uuid;
	}
}
