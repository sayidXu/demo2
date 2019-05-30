package com.xutt.sky.learn.io.net.bio;

import java.io.IOException;
import java.util.Random;

import com.xutt.sky.learn.io.net.bio.client.Client;
import com.xutt.sky.learn.io.net.bio.server.ServerNormal;  
/** 
 * 测试方法 
 * @author yangtao__anxpp.com 
 * @version 1.0 
 */  
public class ClientStart {  
    //测试主方法  
    public static void main(String[] args) throws InterruptedException { 
        //运行客户端   
        final char operators[] = {'+','-','*','/'};  
        final Random random = new Random(System.currentTimeMillis());  
        new Thread(new Runnable() {  
            @SuppressWarnings("static-access")  
            @Override  
            public void run() {  
                //while(true){  
                    //随机产生算术表达式  
                    String expression = random.nextInt(10)+""+operators[random.nextInt(4)]+(random.nextInt(10)+1);  
                    Client.send(expression);  
                    try {  
                        Thread.currentThread().sleep(random.nextInt(1000));  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                //}  
            }  
        }).start();  
    }  
}  
