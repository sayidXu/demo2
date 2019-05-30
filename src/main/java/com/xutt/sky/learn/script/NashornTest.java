package com.xutt.sky.learn.script;

import java.io.FileReader;
import java.net.URL;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class NashornTest {
	public static void main(String[] args) throws Exception {

        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("javascript");

        //使用上下文，执行eval之后，js的变量和函数都存储于上下文，通过get和put可以获取和保存变量，java和javascript都可以使用同一个上下文
        se.put("myame", "liuxg !!");
        System.out.println(se.get("myame"));

        se.eval("var yourname = 'who are you ?'");
        System.out.println(se.get("yourname"));

        //执行变量
        String script = "var compo = {addr : \"广州\",tel : \"12345678901\"};";
        script += "println(compo.addr + \" : \" + compo.tel)";
        se.eval(script);

        //执行js函数
        String funscript = "function sum(a,b){return a + b ;}";
        se.eval(funscript);
        Invocable jsInvocable = (Invocable)se;
        Object obj = jsInvocable.invokeFunction("sum", 1,3);
        System.out.println("sum = " + obj);

        /*//执行js文件
        URL url = NashornTest.class.getClassLoader().getResource("rhino.js");//获取classpath下的js文件
        FileReader reader = new FileReader(url.getFile());
        se.eval(reader);
        Object obj1 = jsInvocable.invokeFunction("skyscolor");
        System.out.println(se.get("skycolor") + " -- " + obj1);

        //js和java代码混合用
        String jsjavacode =  "importPackage(java.util); var names = Arrays.asList(\"liuxg1\",\"liuxg2\",\"liuxg3\")";
        se.eval(jsjavacode);
        List<String> names = (List<String>) se.get("names");
        for (String name : names) {
            System.out.println(name);
        }*/

    }
}
