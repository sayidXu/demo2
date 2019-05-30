package com.xutt.sky.learn.io.net.bio.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public final class Calculator {
	private final static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

	public static Object cal(String expression) throws ScriptException {
		return jse.eval(expression);
	}
	
	public static void main(String[] args) throws Exception{
		Object result = Calculator.cal("1+1");
		System.out.println("result:" + result);
	}
}
