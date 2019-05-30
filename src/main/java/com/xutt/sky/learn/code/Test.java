package com.xutt.sky.learn.code;

public class Test {

	public static void main(String[] args) throws Exception {
		getBytesTest();
		getStringEncode();
		/*
		//locale
		Locale locale = Locale.getDefault();
		System.out.println(locale);
		System.out.println(locale.getLanguage() + "_" + locale.getCountry());
		
		System.out.println(System.getProperty("user.language"));
		System.out.println(System.getProperty("user.region"));*/
		
		//查看当前客户端默认字符集
//		System.out.println(Charset.defaultCharset());

		//查看当前jdk能支持的字符集
		/*SortedMap<String, Charset> map = Charset.availableCharsets();
		for (String alias : map.keySet()) {
			System.out.println("别名：" + alias + "\t字符集对象：" + map.get(alias));
		}*/
		

				
		/*
		 * String httpUrl =
		 * "http://localhost:9083/test/service/user/regist?aaa=??姝?&bbb=222";
		 * int index = httpUrl.indexOf("?"); if(index>=0){ String prefixStr =
		 * httpUrl.substring(0, index); String suffixStr =
		 * httpUrl.substring(index+1); suffixStr = URLEncoder.encode(suffixStr,
		 * "gbk"); httpUrl = prefixStr + "?" + suffixStr;
		 * System.out.println(httpUrl); }
		 * 
		 * httpUrl = URLDecoder.decode(httpUrl, "gbk");
		 * System.out.println(httpUrl);
		 */
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public static void getBytesTest() throws Exception{
		String str = "中国";
		byte[] bytes = str.getBytes("utf-8");
		System.out.print("utf-8:");
		CodeUtil.printHexadecimal(bytes);
		String result = new String(bytes, "gbk");
		System.out.println("utf8转gbk:"+result);
		
		str = "中国";
		bytes = str.getBytes("gbk");
		System.out.print("gbk:");
		CodeUtil.printHexadecimal(bytes);
		result = new String(bytes, "utf-8");
		System.out.println("gbk转utf8:"+result);
		
		str = "中国";
		bytes = str.getBytes();
		System.out.print("默认:");
		CodeUtil.printHexadecimal(bytes);
		System.out.println();
		
		String s_iso88591 = new String("中".getBytes("UTF-8"),"ISO8859-1");
		System.out.println(s_iso88591);
		String s_utf8 = new String(s_iso88591.getBytes("ISO8859-1"),"UTF-8");
		System.out.println(s_utf8);	
	}
	
	
	/**
	 * java字符编码(unicode)
	 * @throws Exception
	 */
	public static void getStringEncode() throws Exception{
		char c = '中';
		int i = c;
		String hexString = Integer.toHexString(i);
		System.out.println("binaryString = " + hexString);
		
		hexString = "";
		String str = "中国";
		char[] chars = str.toCharArray();
		for(int k =0;k<chars.length;k++){
			char cc = chars[k];
			int ii = cc;
			hexString = hexString +Integer.toHexString(ii) +" ";
		}
		System.out.println("binaryString = " + hexString);
	}

}
