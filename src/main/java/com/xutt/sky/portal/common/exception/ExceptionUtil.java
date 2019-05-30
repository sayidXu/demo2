package com.xutt.sky.portal.common.exception;

public class ExceptionUtil {

	public static Object run(RunTask task) {
		try {
			return task.run();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
