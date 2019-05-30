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
public class ServerStart {  
    //测试主方法  
    public static void main(String[] args) throws InterruptedException {  
        //运行服务器  
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
                try {  
                    ServerNormal.start();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }).start();  
        //避免客户端先于服务器启动前执行代码  
        Thread.sleep(100);
    }  
}  
