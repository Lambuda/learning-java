package com.paparazzi.domain;

import java.io.Serializable;

public class Result<T> implements Serializable {

	private Boolean flag; // 状态码
	private String msg; // 返回信息
	private T data;


	public Result() {
		this.flag = true;
		this.msg = "SUCCESS";
	}


	public void setError(String msg) {
		this.flag = false;
		this.msg = msg;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
