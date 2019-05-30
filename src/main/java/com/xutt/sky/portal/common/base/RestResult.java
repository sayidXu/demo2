package com.xutt.sky.portal.common.base;

public class RestResult<T> {

	private int returnTag;
	private String returnMsg;
	private T returnData;

	public RestResult(T returnData) {
		this.returnTag = 0;
		this.returnMsg = "OK!";
		this.returnData = returnData;
	}

	public RestResult(int returnTag, String returnMsg) {
		this.returnTag = returnTag;
		this.returnMsg = returnMsg;
	}

	public int getReturnTag() {
		return returnTag;
	}

	public void setReturnTag(int returnTag) {
		this.returnTag = returnTag;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public T getReturnData() {
		return returnData;
	}

	public void setReturnData(T returnData) {
		this.returnData = returnData;
	}

}
