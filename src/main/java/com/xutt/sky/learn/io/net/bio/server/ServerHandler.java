package com.xutt.sky.learn.io.net.bio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import com.xutt.sky.learn.io.net.bio.util.Calculator;  
/** 
 * 客户端线程 
 * @author yangtao__anxpp.com 
 * 用于处理一个客户端的Socket链路 
 */  
public class ServerHandler implements Runnable{  
    private Socket socket;  
    public ServerHandler(Socket socket) {  
        this.socket = socket;  
    }  
    @Override  
    public void run() {  
    	System.out.println(Thread.currentThread().getId() + ":ServerHandler线程创建");
        BufferedReader in = null;  
        PrintWriter out = null;  
        try{  
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
            out = new PrintWriter(socket.getOutputStream(),true);  
            String expression;  
            String result; 
            while(true){  
                //通过BufferedReader读取一行  
                //如果已经读到输入流尾部，返回null,退出循环  
                //如果得到非空值，就尝试计算结果并返回  
            	System.out.println(new Date().getTime() +":readLine开始执行");
            	//readLine()方法是一个阻塞方法。（如果无数据可读，就会阻塞直到有数据可读;或者到达流的末尾，这个时候分别返回-1和null；有数据可读且出现换行符时，readline()方法才会返回）
                if((expression = in.readLine())==null) {
                	System.out.println(new Date().getTime() +":服务器收到消息：为空");  
                	break;
                }  
                System.out.println(new Date().getTime() +":readLine执行结束");
                System.out.println("服务器收到消息：" + expression);  
                try{  
                    result = Calculator.cal(expression).toString();  
                }catch(Exception e){  
                    result = "计算错误：" + e.getMessage();  
                }  
                out.println(result);  
            }  
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{  
        	System.out.println(Thread.currentThread().getId() + ":ServerHandler线程结束");
            //一些必要的清理工作  
            if(in != null){  
                try {  
                    in.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                in = null;  
            }  
            if(out != null){  
                out.close();  
                out = null;  
            }  
            if(socket != null){  
                try {  
                    socket.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                socket = null;  
            }  
        }  
    }  
    
}  