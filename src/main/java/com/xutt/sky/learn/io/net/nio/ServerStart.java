package com.xutt.sky.learn.io.net.nio;

import com.xutt.sky.learn.io.net.nio.server.Server;

/** 
 * 测试方法 
 * @author yangtao__anxpp.com 
 * @version 1.0 
 */  
public class ServerStart {  
    //测试主方法  
    @SuppressWarnings("resource")  
    public static void main(String[] args) throws Exception{  
        //运行服务器  
        Server.start();  
        //避免客户端先于服务器启动前执行代码  
        Thread.sleep(100);  
    }  
} 
