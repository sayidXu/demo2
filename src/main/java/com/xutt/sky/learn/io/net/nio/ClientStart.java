package com.xutt.sky.learn.io.net.nio;

import java.util.Scanner;

import com.xutt.sky.learn.io.net.nio.client.Client;

/** 
 * 测试方法 
 * @author yangtao__anxpp.com 
 * @version 1.0 
 */  
public class ClientStart {  
    //测试主方法  
    @SuppressWarnings("resource")  
    public static void main(String[] args) throws Exception{  
        //运行客户端   
        Client.start();  
        while(Client.sendMsg(new Scanner(System.in).nextLine()));  
    }  
} 
