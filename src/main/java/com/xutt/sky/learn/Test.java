package com.xutt.sky.learn;

import java.util.Date;

import com.xutt.sky.util.DateUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		System.out.println(DateUtils.formatDate(new Date(1556604187517L)));
		System.out.println(DateUtils.formatDate(new Date(1557454767759L)));
		
		/*while(true){
			System.out.println(DateUtils.formatDate(new Date()));
			Thread.sleep(2000l);
		}*/
		
		
		/*String spr_time = "$$";
		String clientFileName = "aaa$time$.txt";
		clientFileName = clientFileName.replaceAll(spr_time, "20190426");
		System.out.println("clientFileName:" + clientFileName);
		
		
		
		System.out.println(spr_time);*/
	}
	
	public static void test02(){
		
	}
	
	
	public static void test01(){
		String data = "<data><aa>111</aa><bb>222</bb></data>";
		//System.out.println(System.nanoTime());
		
		System.out.println("=========>" + Unpooled.copiedBuffer("Heartbeat",  
	            CharsetUtil.UTF_8));
		ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",  
	            CharsetUtil.UTF_8)); 
		System.out.println(HEARTBEAT_SEQUENCE.duplicate().toString());
	}

}
