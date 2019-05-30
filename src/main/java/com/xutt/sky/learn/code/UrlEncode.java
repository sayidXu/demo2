package com.xutt.sky.learn.code;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlEncode {
	public static void main(String[] args) throws Exception {
		String httpUrl = "http://localhost:9083/test/service/user/regist?aaa=雍正&bbb=222";
		int index = httpUrl.indexOf("?");
		if(index>=0){
			String prefixStr = httpUrl.substring(0, index);
			String suffixStr = httpUrl.substring(index+1);
			suffixStr = URLEncoder.encode(suffixStr, "gbk");
			httpUrl = prefixStr + "?" + suffixStr;
			System.out.println(httpUrl);
		}
		
		httpUrl = URLDecoder.decode(httpUrl, "gbk");
		System.out.println(httpUrl);
	}
}
